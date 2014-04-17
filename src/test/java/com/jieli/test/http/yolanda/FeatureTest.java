package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpComment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.vote.entity.Vote;
import com.jieli.feature.vote.entity.VoteComment;
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
        helpInfo.setEmergency(true);
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/add")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    //@Test
    public void addHelpComment() throws IOException {
        HelpComment comment = new HelpComment();
        comment.setHelpId("5338115f30043866e28ac0b2");
        comment.setCommentUserId("5336bb6f3004cc09f49432e4");
        String context = "小明可以去";
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/add" +
                "?helpId=5338115f30043866e28ac0b2")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(context), ContentType.APPLICATION_JSON)
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
        Response response = Request.Post("http://localhost:8080/rest/feature/help/detail/comment/top?helpId=5338115f30043866e28ac0b2")
                .setHeader("Cookie", "u=5336bb6f3004cc09f49432e5")
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
        voteInfo.setTitle("喜欢的颜色");
        voteInfo.setOptions(Arrays.asList("红", "黄", "蓝", "绿"));
        voteInfo.setDescription("喜欢什么颜色？");
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
        vote.setVoteIndex(Arrays.asList(1));
        vote.setUserId("5336bbe13004cc09f49432e7");
        vote.setAddTime(new Date());
        String voteId = "533800e530044c7fc286a6c4";
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/vote/commitvote?voteId=533bfacf300460878a64a159")
                .addHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(vote), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addVoteComment() throws IOException {
        VoteComment comment = new VoteComment();
        comment.setVoteId("533bfacf300460878a64a159");
        comment.setCommentUserId("533be301300460878a64a157");
        comment.setComment("多点颜色选择更好");
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/vote/comment?voteId=533bfacf300460878a64a159")
                .addHeader("Cookie", "u=533be303300460878a64a158")
                .addHeader("voteId", "533bfacf300460878a64a159")
                .bodyString(mapper.writeValueAsString(comment), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }
}
