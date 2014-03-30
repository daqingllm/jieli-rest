package com.jieli.test.http.liu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.Activity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午8:26
 * To change this template use File | Settings | File Templates.
 */
public class ActivityTest {

    @Test
    public void testCreate() throws IOException {
        Activity activity = new Activity();
        activity.associationId = "5337a309ef869d4225397d48";
        activity.beginDate = new Date(2015,1,1);
        activity.tag = AcivityTag.OFFICIAL;
        activity.title = "a1";

        Response response = Request.Post("http://localhost:8080/rest/activity")
                .addHeader("Cookie", "u=533799caef869f8e93d30d9d")
                .bodyString(new ObjectMapper().writeValueAsString(activity), ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response.returnContent().asString());
    }
}
