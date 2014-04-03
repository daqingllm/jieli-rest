package com.jieli.test.remote;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by YolandaLeo on 14-4-3.
 */
public class UserTest {
    @Test
    public void testLoadUser() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/user?userId=533c09e5e4b05bd824aeda58")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
