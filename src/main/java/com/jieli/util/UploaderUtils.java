package com.jieli.util;

import au.com.bytecode.opencsv.CSVReader;
import com.jieli.admin.CommonUtil;
import com.jieli.association.Group;
import com.jieli.association.GroupDAO;
import com.jieli.association.Identity;
import com.jieli.association.IdentityDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ProfessionTag;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xianxing on 2014/3/22.
 */
public class UploaderUtils {
    private  static  long lastIndex = 1;
    private  static  long lastNano = 1;

    private static Mac mac = null;
    //private static String bucketName = "xianxing-test";
    private static String bucketName = "jieli-images";

    public static void Init(){
        // xianxing-test
        Config.ACCESS_KEY = "BC1Z-BtDVW8dVxdbBBUXc59k1fIZievdyGCQfFj9";
        Config.SECRET_KEY = "3dQ3sP9bukfPn6hN6GGbTUnVf7Az6GT2FRHRF7Cd";
        // jieli-images
        Config.ACCESS_KEY = "hNiaFP5arH5pOPcF9Hj67Id0opYIy6QABVNmxe8M";
        Config.SECRET_KEY = "jvMCjKarxU0EpVmSelQlTKuXNpLvdgEkS2dudrz_";
        mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
    }

    public static String GetBucketName(){return bucketName;}

    public static String GetUploadToken(){
        try{
        // 空间名称
        //bucketName = "xianxing-test";
        // 上传策略，可以进行更多细节设置
        PutPolicy putPolicy = new PutPolicy(bucketName);
        if (mac == null)
            Init();
        String upTocken = putPolicy.token(mac);
        return upTocken;
        }
        catch (Exception e){
            return null;
        }
    }

    public static String writeToFile(InputStream uploadInputStream,String uploadFileLocation,String uploadFileName){
        if (uploadFileLocation.endsWith("\\") || uploadFileLocation.endsWith("/"));else
        uploadFileLocation += "\\";

        long nano = System.nanoTime();
        String prefix = nano+"_";
        //String FileName = uploadFileLocation + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss_").format(new Date()).toString() + uploadFileName;
        if (lastNano != nano){
            lastNano = nano;
            lastIndex = 1;
        }else{
            lastIndex ++;
        }

        String FileName = uploadFileLocation + prefix + lastIndex;

        try {
            OutputStream out= new FileOutputStream(new File(FileName));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = uploadInputStream.read(bytes)) != -1){
                out.write(bytes,0,read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return FileName;
    }

	public static String Upload7Niu(String dir,String fp){
        String tocken = GetUploadToken();
        PutExtra extra = new PutExtra();
        String localFile = fp;
        String key = fp.substring(dir.length());
        PutRet ret = IoApi.putFile(tocken, key, localFile, extra);
        //System.out.println(ret.getHash() + ret.getKey());

        // whatever delete fp
        File file = new File(fp);
        if (file.exists()) {
            if (!(file.delete())) file.deleteOnExit();
        }

        if (ret.ok())
            return key;
        else
            return "";
    }

    public static int [] InitUserByCSV(String FilePath,ArrayList<String> nameList,String associationId) throws IOException {
        File file = new File(FilePath);
        if (!file.exists()){
            return new int[]{};
        }

        nameList.clear();
        groupList.clear();
        identityList.clear();
        professionList.clear();


        Iterable<Group> groups = groupDAO.loadAll(associationId);
        for (Group group : groups) {
            groupList.add(group.name);
        }

        Iterable<Identity> identities = identityDAO.loadAll(associationId);
        for (Identity identity : identities) {
            identityList.add(identity.name);
        }

        String []sProfession = ProfessionTag.ALL;
        for (String s : sProfession) {
            professionList.add(s);
        }

        int totalItemsInExcel = 0;
        int successItems = 0;

        CSVReader reader = new CSVReader(new FileReader(FilePath));
        String [] nextLine;
        // first line omit
        nextLine = reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            //for (int i = 0; i < nextLine.length; i ++)
            //    System.out.println(nextLine[i]);
            String name = InitByLine(nextLine,associationId);

            if (name != null) {
                totalItemsInExcel++;

                if (name == "")
                    successItems++;

                if (name != "")
                    nameList.add(name);
            }
        }

        return new int[] {totalItemsInExcel,successItems,totalItemsInExcel - successItems};
    }

    private static String InitByLine(String [] nextLine,String associationId){
        if (nextLine.length == 0) return null;
        if (nextLine[0].length() == 0) return null;

        // 至少要有前三列的内容
        if (nextLine.length < 3) {
            // 如果有名字，记录下来
            if (nextLine.length > 0) {
                return nextLine[0];
            }
            // 否则跳过总数的记录
            else {
                return null;
            }
        } else {
            String name = nextLine[0];
            String sex = nextLine[1];
            String phone = nextLine[2];

            Date birthday = null;
            if (nextLine.length >= 4 && nextLine[3].length() >= 8){
                DateFormat dt1 = new SimpleDateFormat("yyyy-M-d");
                try {
                    birthday = dt1.parse(nextLine[3]);
                } catch (ParseException e) {
                    // 生日不对
                    return name;
                }
            }

            String identity = (nextLine.length >= 5 ? nextLine[4] : null);
            String entName = (nextLine.length >= 6 ? nextLine[5] : "");
            String job = (nextLine.length >= 7 ? nextLine[6] : "");
            String group = (nextLine.length >= 8 ? nextLine[7] : null);

            /*2014年6月12日21:10:57*/
            String mail = (nextLine.length >= 9 ? nextLine[8] : null);
            String profession = (nextLine.length >= 10 ? nextLine[9] : null);

            if (!identityList.contains(identity)) identity = null;
            if (!groupList.contains(group)) group = null;
            if (!professionList.contains(profession)) profession = null;

            boolean initSuc = checkInitUser(sex, birthday, identity, group,(nextLine.length >= 4))
                    && InitAUser(name, sex, phone, birthday, identity, entName, job, group, mail, profession, associationId);

            System.out.println("123x" + sex + birthday + identity + group + name + phone + profession);

            if (initSuc) {
                return "";
            } else {
                return name;
            }
        }
    }

    private static boolean checkInitUser(String sex, Date birthday,String identity,String group,boolean birthdayNotNull){
        // 要么就是没有写生日 birthdayNotNull = false
        // 要么就是写了生日 birthdayNotNull = true，此时birthday不能为null
        return (sex.compareTo("男") == 0 || sex.compareTo("女")==0) && (!birthdayNotNull || (birthdayNotNull && birthday !=null)) && (identity == null || identityList.contains(identity)) && (group == null || groupList.contains(group));
    }

    private static boolean InitAUser(String name,String sex, String phone,Date birthday,String identity,String entName,String job,String group,String mail,String profession,String associationId) {
        if (UserNameIndex == Integer.MAX_VALUE) UserNameIndex = 0;

        String username = System.nanoTime() + "" + UserNameIndex;
        UserNameIndex ++;

        Account account = accountDAO.loadByUsername(username);
        if (account != null) {
            return false;
        } else {
            /*手机号重复*/
            Iterable<User> phoneUser = userDAO.find("{phone:\'"+phone+"\'}");
            for (User usser : phoneUser){
                return false;
            }

            String password = PasswordGenerator.getRandomString(6);
            User user = new User();
            user.associationId = associationId;

            user.name = name;
            user.sex = (sex.compareTo("男") == 0 ? 0 : 1);
            user.phone = phone;
            user.birthday = birthday;
            if (user.birthday != null){
                user.constellation = getConstellation(user.birthday.getMonth(),user.birthday.getDate());
            }
            user.enterpriseName = entName;
            user.job = job;
            user.mail = mail;
            user.profession = profession;
            user.enterpriseIndustry = profession;

            user.identity = identity;
            user.group = group;

            String userId = userDAO.save(user).get_id().toString();
            Account newAccount = new Account();
            newAccount.username = username;
            newAccount.password = password;
            newAccount.userId = userId;
            newAccount.state = AccountState.ENABLE;
            newAccount.associationId = associationId;
            accountDAO.save(newAccount);
        }
        return true;
    }

    private static int UserNameIndex = 0;
    private static void setUserNameIndex(int userNameIndex){
        UserNameIndex = userNameIndex;
    }

    private static AccountDAO accountDAO = new AccountDAO();
    private static UserDAO userDAO = new UserDAO();
    private static ArrayList<String> identityList = new ArrayList<String>();
    private static ArrayList<String> groupList = new ArrayList<String>();
    private static List<String> professionList = new ArrayList<String>();
    private static GroupDAO groupDAO = new GroupDAO();
    private static IdentityDAO identityDAO = new IdentityDAO();

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };


    public static String getConstellation(int month, int day) {
        if (month<0 || month+2 > constellationArr.length) return "";
        else return day < dayArr[month] ? constellationArr[month] : constellationArr[month + 1];
    }
}
