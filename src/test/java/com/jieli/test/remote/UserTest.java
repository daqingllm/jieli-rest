package com.jieli.test.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.user.entity.Friend;
import com.jieli.user.entity.User;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-2
 * Time: 下午9:22
 * To change this template use File | Settings | File Templates.
 */
public class UserTest {

    @Test
    public void testLoad() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user?userId=533c09e5e4b05bd824aeda57")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void editSelf() throws IOException {
        User user = new User();
        user.name = "詹姆斯哈登";
        user.sex = 0;

        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user/self")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .bodyString(new ObjectMapper().writeValueAsString(user), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

//    @Test
    public void editUser() throws IOException {
        User user = new User();
        user.name = "詹姆斯哈登";
        user.sex = 0;
        user.profession = "篮球";

        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .bodyString(new ObjectMapper().writeValueAsString(user), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void loadSelf() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user/self")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void loadDirectory() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user/directory")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void upsertFriend() throws IOException {
        Friend friend = new Friend();
        friend.userId = "533c061de4b05bd824aeda55";
        friend.group = "同学";
        friend.special = true;

        Friend friend1 = new Friend();
        friend1.userId = "533d22a4e4b05bd824aeda59";
        friend1.group = "同事";
        friend1.special = false;

        Friend friend2 = new Friend();
        friend2.userId = "533d2a36e4b05bd824aeda5b";
        friend2.group = "朋友";
        friend2.special = true;

        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user/directory")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .bodyString(new ObjectMapper().writeValueAsString(friend2), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void deleteFriend() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user/directory/delete?friendId=533d22a4e4b05bd824aeda59")
                .addHeader("Cookie", "u=533c09e5e4b05bd824aeda58")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
