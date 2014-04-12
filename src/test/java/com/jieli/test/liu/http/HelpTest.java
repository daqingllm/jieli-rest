package com.jieli.test.liu.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpInfo;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-12
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class HelpTest {

    @Test
    public void addHelpInfo() throws IOException {
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setTitle("求帮忙搬家");
        helpInfo.setContent("求大神帮忙搬行李！！！无报酬，请吃饭。。");
        helpInfo.setAddTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/rest/feature/help/add")
                .setHeader("Cookie", "u=533c0010ef86c7014c36fa2f")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }
}
