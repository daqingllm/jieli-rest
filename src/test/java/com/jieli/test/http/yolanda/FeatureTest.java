package com.jieli.test.http.yolanda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpComment;
import com.jieli.feature.help.entity.HelpInfo;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class FeatureTest {
    @Test
    public void getHelpList() throws IOException {
        Response response = Request.Get("http://localhost:8080/rest/feature/help")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
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
        helpInfo.setTitle("求陪同逛街");
        helpInfo.setContent("我明天想去久光百货，求陪同，要妹纸啊！");
        helpInfo.setAddTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/add")
                .setHeader("Cookie", "u=5336bbe13004cc09f49432e7")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
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

    @Test
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
                "?helpId=5338115f30043866e28ac0b2")
                .setHeader("Cookie", "u=5336bb6f3004cc09f49432e5")
                .bodyString(helpId, ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }

    @Test
    public void addTop() {

    }
}
