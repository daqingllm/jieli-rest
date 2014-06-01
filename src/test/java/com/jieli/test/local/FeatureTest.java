package com.jieli.test.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.feature.help.entity.HelpInfo;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by YolandaLeo on 14-6-1.
 */
public class FeatureTest {
    @Test
    public void addHelpInfo() throws IOException {
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setTitle("求帮忙搬家11");
        helpInfo.setContent("求大神帮忙搬行李！！！无报酬，请吃饭。。");
        helpInfo.setAddTime(new Date());
        helpInfo.setType(1);
        helpInfo.setTypeDetail(1);
        helpInfo.isEmergency = true;
        ObjectMapper mapper = new ObjectMapper();
        Response response = Request.Post("http://localhost:8080/app/feature/help/add")
                .setHeader("Cookie", "u=536751603004959582a675e2")
                .bodyString(mapper.writeValueAsString(helpInfo), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(response.returnContent().asString());
    }
}
