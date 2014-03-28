package com.jieli.util;

import java.io.*;

/**
 * Created by xianxing on 2014/3/22.
 */
public class UploaderUtils {
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
}
