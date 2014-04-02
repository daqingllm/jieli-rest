package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        Response response = Request.Get("http://localhost:8080/rest/user?userId=5336bbe13004cc09f49432e6")
                .addHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoadSelf() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void updateSelf() throws IOException {
        User user = new User();
        user.associationId = "5337af643004e0056052bd5a";
        user.name = "桃子";
        Response response = Request.Post("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=5336b7de3004f3462ed8868b")
                .bodyString(new ObjectMapper().writeValueAsString(user), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void editSelf() throws IOException {
        /*User user = new User();
        user.associationId = "5337af643004e0056052bd5a";
        user.name = "万能的小明";
        user.birthday = "1999-11-13";
        user.sex = 1;*/
        User user = new User();
        user.associationId = "5337af643004e0056052bd5a";
        user.name = "瓶子";
        user.birthday = "1998-12-12";
        user.sex = 0;
        ObjectMapper mapper = new ObjectMapper();
        String query = mapper.writeValueAsString(user);
        Response response = Request.Post("http://localhost:8080/rest/user/self")
                .addHeader("Cookie", "u=5336bb6f3004cc09f49432e5")
                .bodyString(query, ContentType.APPLICATION_JSON).execute();
        System.out.println(response.returnContent().asString());
    }
}
