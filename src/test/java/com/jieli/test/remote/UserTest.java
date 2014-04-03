package com.jieli.test.remote;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
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
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user?userId=533c09e5e4b05bd824aeda58")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
