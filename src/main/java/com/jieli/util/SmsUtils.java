package com.jieli.util;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-6-2
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
public abstract class SmsUtils {

    public static void sendPassword(String phone, String password) throws IOException {
        String content = "http://sh.ipyy.com:8888/sms.aspx?action=send&userid=1462&account=sh100563&password=jielimag1107";
        content += "&mobile=" + phone;
        content += "&content=您的登录验证码是" + password + "，请妥善保管";
        Request.Get(content).execute();
    }
}
