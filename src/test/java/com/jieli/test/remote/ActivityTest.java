package com.jieli.test.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.Activity;
import com.jieli.activity.Arrangement;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

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
        activity.associationId = "533c0568e4b05bd824aeda54";
//        activity.beginDate = new Date(new Date().getTime() + 1000000000);
        activity.beginDate = new Date();
        activity.tag = AcivityTag.RECOMMEND;
//        activity.sponsorUserId = "533799caef869f8e93d30d9c";
        activity.title = "新的推荐活动";
        activity.location = "天安门广场";
        activity.description = "官方有奖竞猜";
        activity.fee = 100;
        activity.maxMembers = 100;
        activity.arrangement = "4、5两天";
        activity.serviceInfo = "服务信息";
        activity.sponsorInfo = "赞助信息";
//        activity.invitees = Arrays.asList("user1", "user2", "user3");
        Arrangement a1 = new Arrangement();
        a1.title = "4号";
        a1.content = "abcd";
        Arrangement a2 = new Arrangement();
        a2.title = "5号";
        a2.content = "abcd";
        activity.details.add(a1); activity.details.add(a2);

        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/activity")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .bodyString(new ObjectMapper().writeValueAsString(activity), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testLoad() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/activity?activityId=53481507e4b0b0138cc6abcf")
                .addHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testOngoing() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/activity/ongoing")
                .addHeader("Cookie", "u=533c061de4b05bd824aeda56")
                .execute();

        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testHistory() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/activity/history?tag=OFFICIAL")
                .addHeader("Cookie", "u=533c061de4b05bd824aeda56")
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
