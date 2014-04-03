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
        Response response = Request.Get("http://localhost:8080/rest/user?userId=5336bbe13004cc09f49432e6")
                .addHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
