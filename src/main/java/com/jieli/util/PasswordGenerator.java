package com.jieli.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class PasswordGenerator {

    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i ++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static String md5Encode(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        md.update(password.getBytes());
        byte[] bs=md.digest();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<bs.length;i++){    //字节数组转换成十六进制字符串，形成最终的密文
            int v=bs[i]&0xff;
            if(v<16){
                sb.append(0);
            }else{
                sb.append(Integer.toHexString(v));
            }
        }
        return sb.toString();
    }
}
