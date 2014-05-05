package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.vote.entity.Vote;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class FeatureTest {
    @Test
    public void getHelpList() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/feature/help?page=1&size=20&type=2")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();
        System.out.println(response.returnContent().asString());
    }

    //@Test
    public void getDetailHelpInfo() throws IOException {
        String helpId = "5337bce53004ecd7e04edfcd";
        Response response = Request.Get("http://localhost:8080/rest/feature/help/detail?helpId=5337bce53004ecd7e04edfcd")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addHelpInfo() throws IOException {
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setAssociationId("5337af643004e0056052bd5a");
        helpInfo.setTitle("求陪吃饭");
        helpInfo.setContent("我想去吃西贝，有木有人一起，今天晚饭");
        helpInfo.setAddTime(new Date());
        helpInfo.setType(0);
        helpInfo.setIsEmergency(true);
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/add")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addHelpComment() throws IOException {
        Map<String, String> commentInfo = new HashMap<String, String>();
        commentInfo.put("topicId", "534fa1ef30049137259c9dc4");
        commentInfo.put("content", "TestTestTest");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/add")
                .setHeader("Cookie", "u=533be303300460878a64a158")
                .bodyString(mapper.writeValueAsString(commentInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    //@Test
    public void deleteComment() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/delete" +
                "?helpId=5338115f30043866e28ac0b2")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(new Integer(0)), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addFocus() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String helpId = "5338115f30043866e28ac0b2";
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/focus" +
                "?helpId=534f9b8f3004055aeb46f7b3")
                .setHeader("Cookie", "u=5336bb6f3004cc09f49432e5")
                .bodyString(helpId, ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addTop() throws IOException {
        String helpId = "5338115f30043866e28ac0b2";
        Integer index = 1;
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/top?" +
                "helpId=534fa1ef30049137259c9dc4&commentId=53534626300492612fdc92dc")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(index.toString(), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void getVoteList() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/feature/vote?page=1&size=20")
                .setHeader("Cookie", "u=5336b7de3004f3462ed8868b")
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void getVoteInfo() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/feature/vote/detail?voteId=533bfacf300460878a64a159")
                .setHeader("Cookie", "u=533be303300460878a64a158")
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addVote() throws IOException, ParseException {
        VoteInfo voteInfo = new VoteInfo();
        voteInfo.setAssociationId("5337af643004e0056052bd5a");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        voteInfo.setDeadLine(format.parse("2014-05-01"));
        voteInfo.setMultiple(false);
        voteInfo.setTitle("晚饭吃什么");
        Map<Integer, String> options = new HashMap<Integer, String>();
        options.put(0, "绿茶");
        options.put(1, "西贝西北菜");
        options.put(2, "麻辣香锅");
        options.put(3, "外婆家");
        options.put(4, "Miss kiko");
        voteInfo.setOptions(options);
        voteInfo.setDescription("晚上聚餐去哪里？");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/vote/addvote")
                .setHeader("Cookie", "u=5336b7de3004f3462ed8868b")
                .bodyString(mapper.writeValueAsString(voteInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void vote() throws IOException {
        Vote vote = new Vote();
        vote.setVoteIndex(Arrays.asList(2));
        vote.setUserId("5346965130040cab020b3dd3");
        vote.setAddTime(new Date());
        String voteId = "535538c9300485b915c5df60";
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/vote/commitvote?voteId=535538c9300485b915c5df60")
                .addHeader("Cookie", "u=5346965130040cab020b3dd3")
                .bodyString(mapper.writeValueAsString(vote), ContentType.APPLICATION_JSON)
                .execute();
        //System.out.println(response.returnContent().asString());
    }

    @Test
    public void addVoteComment() throws IOException {
        Map<String, String> commentInfo = new HashMap<String, String>();
        commentInfo.put("topicId", "534fa1ef30049137259c9dc5");
        commentInfo.put("content", "喜欢的颜色好多好多~~~~");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/vote/comment")
                .addHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(commentInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void getOrientedMatch() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/feature/match")
                .addHeader("Cookie", "u=533be303300460878a64a158")
                .execute();
        System.out.println(response.returnContent().asString());
    }
}
