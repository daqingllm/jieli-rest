package com.jieli.test.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.Activity;
import com.jieli.activity.Arrangement;
import com.jieli.util.PushUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-10
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class ActivityTest {

    @Test
    public void testCreate() throws IOException {
        Activity activity = new Activity();
        activity.associationId = "5348205ce4b00b2ae52d3f5a";
        activity.beginDate = new Date(new Date().getTime() + 1000000000);
//        activity.beginDate = new Date();
        activity.tag = AcivityTag.OFFICIAL;
//        activity.sponsorUserId = "533799caef869f8e93d30d9c";
        activity.url = "http://xianxing-test.qiniudn.com/12834560259126414_bynam.jpg";
        activity.title = "一个新的活动";
        activity.location = "天安门广场";
        activity.description = "官方有奖竞猜";
        activity.fee = 100;
        activity.maxMembers = 100;
        activity.arrangement = "4、5两天";
//        activity.serviceInfo = Arrays.asList("服务信息1", "服务信息2");
        activity.sponsorInfo = "赞助信息";
        activity.invitees = Arrays.asList("user1", "user2", "user3");
        Arrangement a1 = new Arrangement();
        a1.title = "4号";
        a1.content = "abcd";
        Arrangement a2 = new Arrangement();
        a2.title = "5号";
        a2.content = "abcd";
        activity.details.add(a1); activity.details.add(a2);

        Response response = Request.Post("http://162.243.151.219:8080/rest/activity")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString(new ObjectMapper().writeValueAsString(activity), ContentType.APPLICATION_JSON)
//                .bodyString("{\"beginDate\":\"\",\"details\":[],\"fee\":0,\"followMembers\":[],\"invitees\":[],\"joinMembers\":{},\"location\":\"\",\"maxMembers\":0,\"tag\":\"PRIVATE\",\"title\":\"是地方\",\"type\":\"null发起了读书会\"}", ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoad() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/rest/activity?activityId=535f9262e4b09c3b501250bc")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testOngoing() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/rest/activity/ongoing")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testHistory() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/activity/history?tag=OFFICIAL")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testComment() throws IOException {
        Map<String, String> infos = new HashMap<String, String>();
        infos.put("content", "是地方但是");
        infos.put("topicId", "535f9262e4b09c3b501250bc");

        Response response = Request.Post("http://162.243.151.219:8080/rest/activity/comment")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString(new ObjectMapper().writeValueAsString(infos), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void loadComment() throws IOException {

        Response response = Request.Get("http://162.243.151.219:8080/rest/comment/load?topicId=535f9262e4b09c3b501250bc&topicType=activity")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testInvite() throws IOException {
//        Map<String, String> infos = new HashMap<String, String>();
//        infos.put("content", "是地方但是");
//        infos.put("topicId", "534821b9e4b00b2ae52d3f5d");
        List<String> userIds = Arrays.asList("534950a2e4b0be347782c7ce");

        Response response = Request.Post("http://162.243.151.219:8080/rest/activity/invite?activityId=535131ece4b007071c52d06e")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString(new ObjectMapper().writeValueAsString(userIds), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testUpload() throws IOException {
//        Map<String, String> infos = new HashMap<String, String>();
//        infos.put("content", "是地方但是");
//        infos.put("topicId", "534821b9e4b00b2ae52d3f5d");
        List<String> pics = Arrays.asList("http://xianxing-test.qiniudn.com/13345832224833124_20090529113434168.jpg");

        Response response = Request.Post("http://162.243.151.219:8080/rest/activity/upload?activityId=535131ece4b007071c52d06e")
                .addHeader("Cookie", "u=5348210be4b00b2ae52d3f5c")
                .bodyString(new ObjectMapper().writeValueAsString(pics), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testPush() throws IOException {
        PushUtils.pushMessage("123");
    }
}
