package com.jieli.test.http.liu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.user.entity.User;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
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
        Response response = Request.Get("http://localhost:8080/rest/user?userId=533799caef869f8e93d30d9c")
                .addHeader("Cookie", "u=533799caef869f8e93d30d9d")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoadSelf() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=533799caef869f8e93d30d9d")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void updateSelf() throws IOException {
        User user = new User();
        user.associationId = "5337a309ef869d4225397d48";
        user.name = "xy_user";
        Response response = Request.Post("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=533799caef869f8e93d30d9d")
                .bodyString(new ObjectMapper().writeValueAsString(user), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
