package com.jieli.test.remote;

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
 * Date: 14-4-2
 * Time: 下午8:33
 * To change this template use File | Settings | File Templates.
 */
public class AssociationTest {

    @Test
    public void testCreate() throws IOException {
        Association association = new Association();
        association.name = "北京土著";

        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/association")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .bodyString(new ObjectMapper().writeValueAsString(association), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testGet() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/association?id=533c0568e4b05bd824aeda54")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testUsers() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/association/user?id=533c0568e4b05bd824aeda54&state=1")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
