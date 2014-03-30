package com.jieli.test.liu.http;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class AccountTest {

    @Test
    public void testLogin() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"test\",\"password\":\"t9p160fo\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .bodyString("{\"username\":\"test\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
