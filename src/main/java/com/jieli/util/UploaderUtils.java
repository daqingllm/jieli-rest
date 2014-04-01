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
    private static Mac mac = null;
    private static String bucketName = "xianxing-test";

    public static void Init(){
        Config.ACCESS_KEY = "BC1Z-BtDVW8dVxdbBBUXc59k1fIZievdyGCQfFj9";
        Config.SECRET_KEY = "3dQ3sP9bukfPn6hN6GGbTUnVf7Az6GT2FRHRF7Cd";
        mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
    }

    public static String GetBucketName(){return bucketName;}

    public static String GetUploadToken(){
        try{
        // 空间名称
        bucketName = "xianxing-test";
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

        String prefix = System.nanoTime()+"_";
        //String FileName = uploadFileLocation + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss_").format(new Date()).toString() + uploadFileName;
        String FileName = uploadFileLocation + prefix + uploadFileName;

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
        if (ret.ok())
            return key;
        else
            return "";
    }
}
