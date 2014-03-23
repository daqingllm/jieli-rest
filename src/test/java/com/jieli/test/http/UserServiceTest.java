package com.jieli.test.http;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 下午1:37
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest {

    @Test
    public void testLoadUser() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/user?userId=9")
                .addHeader("Cookie", "u=532445c6ef86bca0cdf2795b")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoadSelf() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=532445c6ef86bca0cdf2795b")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
