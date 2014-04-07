package com.jieli.test.liu.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-6
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class MessageTest {

    @Test
    public void loadMessage() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/message")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void cosume() throws IOException {
        List<String> messages = Arrays.asList("53410f33ef86bf409a511263");
        Response response = Request.Post("http://localhost:8080/rest/message")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .bodyString(new ObjectMapper().writeValueAsString(messages), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
