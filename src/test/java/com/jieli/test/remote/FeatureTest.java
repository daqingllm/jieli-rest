package com.jieli.test.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.vote.entity.VoteInfo;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by YolandaLeo on 14-4-3.
 */
public class FeatureTest {
    @Test
    public void addHelpInfo() throws IOException {
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setTitle("求帮忙搬家");
        helpInfo.setContent("求大神帮忙搬行李！！！无报酬，请吃饭。。");
        helpInfo.setAddTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/feature/help/add")
                .setHeader("Cookie", "u=53481be2e4b00b2ae52d3f58")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addVote() throws IOException, ParseException {
        VoteInfo voteInfo = new VoteInfo();
        voteInfo.setAssociationId("533c0568e4b05bd824aeda54");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        voteInfo.setDeadLine(format.parse("2014-05-01"));
        voteInfo.setMultiple(true);
        voteInfo.setTitle("油菜花去哪儿");
        voteInfo.setOptions(Arrays.asList("云南", "奉贤", "江西婺源", "青海"));
        voteInfo.setDescription("去哪里看油菜花？");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://162.243.151.219:8080/jieli-1.0-SNAPSHOT/rest/feature/vote/addvote")
                .setHeader("Cookie", "u=533bfca63a6e26a4f86e916d")
                .bodyString(mapper.writeValueAsString(voteInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void testMatch() throws IOException {
        Response response = Request.Get("http://162.243.151.219:8080/rest/feature/topmatch")
                .setHeader("Cookie", "u=534a2256e4b0038297fb2136")
                .execute();
        System.out.println(response.returnContent().asString());
    }
}
