package com.jieli.test.remote;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-2
 * Time: 下午7:41
 * To change this template use File | Settings | File Templates.
 */
public class AccountTest {

    @Test
    public void testLogin() throws IOException {
        testSuper();
        testAdmin();
        testUser();
    }

    private void testUser() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"user\",\"password\":\"ln0j5t3y\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    private void testAdmin() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"password\":\"tf6wv7ye\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    private void testSuper() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"super\",\"password\":\"super\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testAuth() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/auth")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"associationId\":\"533c0568e4b05bd824aeda54\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/register")
                .addHeader("Cookie", "u=533c061de4b05bd824aeda56")
                .bodyString("{\"username\":\"user\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
