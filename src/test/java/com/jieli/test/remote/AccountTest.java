package com.jieli.test.remote;

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
        testSuper();
    }

    private void testSuper() throws IOException {
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/account/login")
                .setHeader("app", "test")
                .bodyString("{\"username\":\"super\",\"password\":\"super\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
