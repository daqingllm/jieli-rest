package com.jieli.service;

import com.jieli.activity.Activity;
import com.jieli.activity.ActivityDAO;
import com.jieli.activity.ActivityInfo;
import com.jieli.activity.RelatedActivityDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ltebean
 */

@Singleton
@Path("/activity")
public class ActivityService {

    private final ActivityDAO activityDAO = new ActivityDAO();

    private final RelatedActivityDAO  relatedDAO = new RelatedActivityDAO();

    @GET
    @Path("/ongoing")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findOngoingActivity(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
//        Iterable<Activity> result=activityDAO.getCollection().find("{type:#}",type).sort("{beginDate:-1}").skip(page*PAGE_SIZE).limit(PAGE_SIZE).as(Activity.class);
        Iterable<Activity> officials = activityDAO.findOngoingOfficial(associationId);
        Iterable<Activity> recommands = activityDAO.findOngoingRecommend();
        List<Activity> result = new ArrayList<Activity>();
        for (Activity activity : officials) {
            result.add(activity);
        }
        for (Activity activity : recommands) {
            result.add(activity);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=result;

        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadActivity(@CookieParam("u")String sessionId,@PathParam("activityId") String activityId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(activityId)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(activityId)) {
            responseEntity.code = 3102;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        Activity result=activityDAO.loadById(activityId);
        responseEntity.code=200;
        responseEntity.body=result;
        return  Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createActivity(@CookieParam("u")String sessionId,Activity activity) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        activityDAO.save(activity);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=activity;
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findRelatedActivities(@CookieParam("u")String sessionId, int page, int count) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count <= 0) {
            count = 10;
        }
        if (page <= 0) {
            page = 1;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        List<ActivityInfo> result = new ArrayList<ActivityInfo>();
        String userId = IdentifyUtils.getUserId(sessionId);
        List<ActivityInfo> infos = relatedDAO.findUserActivities(userId);
        if (page*count <= infos.size()) {
            result = infos.subList((page-1)*count, page*count);
        } else if ((page-1)*count < infos.size()) {
            result = infos.subList((page-1)*count, infos.size());
        } else {
            responseEntity.msg = "无数据";
        }

        responseEntity.body = result;
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

}
