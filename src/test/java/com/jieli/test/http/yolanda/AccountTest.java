package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.service.AccountService;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 下午1:53
 * To change this template use File | Settings | File Templates.
 */
public class AccountTest {

    @Test
    public void createSuper() {
        AccountService accountService = new AccountService();
    }

    @Test
    public void testLogin() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"super\",\"password\":\"super\"}", ContentType.APPLICATION_JSON)
       //         .bodyString("{\"username\":\"test\",\"password\":\"t9p160fo\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Map<String,String> registerInfo = new HashMap<String, String>();
        registerInfo.put("userName", "Leo");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .setHeader("Cookie", "u=5336b7de3004f3462ed8868b")
                .bodyString(mapper.writeValueAsString(registerInfo), ContentType.APPLICATION_JSON)
                .execute();

        /**小明22 password：pnd96vhd
        /*User user = new User();
        user.associationId = "100";
        user.birthday = "1998-11-13";
        user.name = "小明2";
        user.phone = "13012345678";
        ObjectMapper mapper = new ObjectMapper();
        String query = mapper.writeValueAsString(user);
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .bodyString(query, ContentType.APPLICATION_JSON).execute();*/
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testAuth() throws IOException {
        Map<String, String> registerInfo = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        registerInfo.put("associationId", "5337af643004e0056052bd5a");
        registerInfo.put("username", "admin");
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .addHeader("Cookie", "u=5367567530042cf4dc3ce1b7")
                .bodyString(mapper.writeValueAsString(registerInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent());
    }

    @Test
    public void testChangeAccount() throws IOException {
        Account account = new Account();
        account.username = "admin";
        account.password = "admin";
        account.userId = "5368362b300489f533d2a77c";
        account.associationId = "5337af643004e0056052bd5a";
        account.state = AccountState.ADMIN;
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/account/change")
                .addHeader("Cookie", "u=5367567530042cf4dc3ce1b7")
                .bodyString(mapper.writeValueAsString(account), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent());
    }
}
