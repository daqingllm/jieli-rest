package com.jieli.test.liu.http;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-6
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
public class CommentTest {

    @Test
    public void loadComments() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/comment/load?topicId=5337cf1cef868c3955e498c7&topicType=activity")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void deleteComment() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/comment/delete?commentId=5341075cef86bf409a51125d")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
