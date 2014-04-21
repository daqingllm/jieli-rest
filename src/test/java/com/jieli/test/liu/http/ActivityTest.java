package com.jieli.test.liu.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.Activity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午8:26
 * To change this template use File | Settings | File Templates.
 */
public class ActivityTest {

    @Test
    public void testCreate() throws IOException, JSONException {
        Activity activity = new Activity();
//        activity.associationId = "5337a309ef869d4225397d48";
//        activity.beginDate = new Date(new Date().getTime()+10000000);
//        activity.tag = AcivityTag.PRIVATE;
//        activity.sponsorUserId = "533799caef869f8e93d30d9c";
        activity.title = "S1";
        activity.tag = AcivityTag.PRIVATE;
        JSONObject json = new JSONObject("{\"beginDate\":\"\",\"details\":[],\"fee\":0,\"followMembers\":[],\"invitees\":[],\"joinMembers\":{},\"location\":\"\",\"maxMembers\":0,\"tag\":\"PRIVATE\",\"title\":\"是地方\",\"type\":\"null发起了读书会\"}");

        Response response = Request.Post("http://localhost:8080/rest/activity")
                .addHeader("Cookie", "u=533c07a1ef86c7014c36fa31")
//                .bodyString(new ObjectMapper().writeValueAsString(activity), ContentType.APPLICATION_JSON)
                .bodyString(json.toString(), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoad() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/activity?activityId=5348ddc5ef86675f431c426b")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testOngoing() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/activity/ongoing")
                .addHeader("Cookie", "u=533c07a1ef86c7014c36fa31")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testHistory() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/activity/history?tag=OFFICIAL")
                .addHeader("Cookie", "u=533c07a1ef86c7014c36fa31")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testFollow() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/activity/concern?activityId=5348ddc5ef86675f431c426b")
                .addHeader("Cookie", "u=533c07a1ef86c7014c36fa31")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testJoin() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/activity/join?activityId=5337cf1cef868c3955e498c7&part=part1")
                .addHeader("Cookie", "u=533799caef869f8e93d30d9d")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testComment() throws IOException {
        Map<String, String> infos = new HashMap<String, String>();
        infos.put("content", "我是回复字数你妹");
        infos.put("topicId", "53482b95ef862ffc551c0d00");
        infos.put("commentedUserId", "533c0010ef86c7014c36fa2e");

        Response response = Request.Post("http://localhost:8080/rest/activity/comment")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .bodyString(new ObjectMapper().writeValueAsString(infos), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testFastTime() throws JsonProcessingException {
        Activity activity = new Activity();
        activity.beginDate = new Date();
        System.out.println(new ObjectMapper().writeValueAsString(activity));
        Date date = new Date(1397154458887L);
        System.out.println(date.toString());
    }

    @Test
    public void testAlbum() throws IOException {
//        Map<String, String> infos = new HashMap<String, String>();
//        infos.put("content", "我是回复字数你妹");
//        infos.put("topicId", "53482b95ef862ffc551c0d00");
//        infos.put("commentedUserId", "533c0010ef86c7014c36fa2e");
        List<String> picIds = Arrays.asList("111", "222", "333");

        Response response = Request.Post("http://localhost:8080/rest/activity/upload?activityId=5348ddc5ef86675f431c426b")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .bodyString(new ObjectMapper().writeValueAsString(picIds), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testInvite() throws IOException {
//        Map<String, String> infos = new HashMap<String, String>();
//        infos.put("content", "是地方但是");
//        infos.put("topicId", "534821b9e4b00b2ae52d3f5d");
        List<String> userIds = Arrays.asList("534950a2e4b0be347782c7ce");

        Response response = Request.Post("http://localhost:8080/rest/activity/invite?activityId=5348ddc5ef86675f431c426b")
                .addHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .bodyString(new ObjectMapper().writeValueAsString(userIds), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

}
