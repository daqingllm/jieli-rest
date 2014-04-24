package com.jieli.test.liu.http;

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
 * Date: 14-3-15
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class AccountTest {

    @Test
    public void testLogin() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"password\":\"admin\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .addHeader("Cookie", "u=533c07a1ef86c7014c36fa31")
                .bodyString("{\"username\":\"Harden\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testAuth() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/auth")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"associationId\":\"533c0568e4b05bd824aeda54\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testChange() throws IOException {
        Account account = new Account();
        account.username = "桃子Strawberry";
        account.password = "admin";
//        account.associationId = "5337a309ef869d4225397d48";
//        account.userId = "533c07a1ef86c7014c36fa30";
        account.state = AccountState.ADMIN;

        Response response = Request.Post("http://localhost:8080/rest/account")
                .addHeader("Cookie", "u=5346965130040cab020b3dd3")
                .setHeader("app", "test")
                .bodyString(new ObjectMapper().writeValueAsString(account), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
