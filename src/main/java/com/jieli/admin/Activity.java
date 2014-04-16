package com.jieli.admin;

import com.jieli.common.entity.*;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 95 on 2014/4/15.
 */
@Path("/bactivity")
public class Activity {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String viewVote(@CookieParam("u") String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Common.errorReturn;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isSuper",true);
        params.put("username","xianxing");
        params.put("associationList",",<option value='123'>上海</option>");

        return FTLrender.getResult("new_activity.ftl", params);
    }
}
