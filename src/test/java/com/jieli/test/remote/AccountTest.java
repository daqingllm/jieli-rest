package com.jieli.test.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
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
//        testSuper();
//        testAdmin();
        testUser();
    }

    private void testUser() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/rest/account/login")
                .setHeader("app", "test")
//                .bodyString("{\"username\":\"Harden\",\"password\":\"nvb7pug2\"}", ContentType.APPLICATION_JSON)
                .bodyString("{\"username\":\"Carmelo\",\"password\":\"Carmelo\"}", ContentType.APPLICATION_JSON)
//                .bodyString("{\"username\":\"Harden\",\"password\":\"Harden\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    private void testAdmin() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"password\":\"admin\"}", ContentType.APPLICATION_JSON)
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
                .addHeader("Cookie", "u=53481be2e4b00b2ae52d3f58")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"associationId\":\"5348205ce4b00b2ae52d3f5a\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/rest/account/register")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString("{\"username\":\"Carmelo\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testChange() throws IOException {
        Account account = new Account();
        account.username = "Harden";
        account.password = "Harden";
        account.state = AccountState.ENABLE;

        Response response = Request.Post("http://162.243.151.219:8080/rest/account")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .setHeader("app", "test")
                .bodyString(new ObjectMapper().writeValueAsString(account), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
