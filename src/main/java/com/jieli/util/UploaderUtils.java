package com.jieli.util;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import java.io.*;

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
}
