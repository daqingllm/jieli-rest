package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-30
 * Time: 下午12:25
 * To change this template use File | Settings | File Templates.
 */
public class AssociationTest {

    @Test
    public void testCreate() throws IOException {
        Association association = new Association();
        association.name = "北京";

        Response response = Request.Post("http://localhost:8080/rest/association")
                .addHeader("Cookie", "u=5346965130040cab020b3dd3")
                .bodyString(new ObjectMapper().writeValueAsString(association), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testMapper() throws JsonProcessingException {
        Association association = new Association();
        association.name = "上海";
        System.out.println(new ObjectMapper().writeValueAsString(association));
    }
}
