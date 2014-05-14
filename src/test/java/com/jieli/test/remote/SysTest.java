package com.jieli.test.remote;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-8
 * Time: 下午11:42
 * To change this template use File | Settings | File Templates.
 */
public class SysTest {

    //@Test
    public void testFeedback() throws IOException {
        Response response = Request.Post("http://114.215.185.140:8080/rest/sys/feedback")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString("ssss", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
