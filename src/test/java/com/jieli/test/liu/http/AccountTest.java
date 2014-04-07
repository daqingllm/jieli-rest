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
                .bodyString("{\"username\":\"super\",\"password\":\"super\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testRegister() throws IOException {
        Response response = Request.Post("http://localhost:8080/rest/account/register")
                .bodyString("{\"username\":\"小明22\"}", ContentType.APPLICATION_JSON)
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
        Response response = Request.Post("http://localhost:8080/rest/account/auth")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"admin\",\"associationId\":\"533c0568e4b05bd824aeda54\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
