package com.jieli.service;

import com.jieli.activity.Activity;
import com.jieli.activity.ActivityDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author ltebean
 */

@Singleton
@Path("/activity")
public class ActivityService {

    private final ActivityDAO activityDAO= new ActivityDAO();

    private final UserDAO userDAO = new UserDAO();

    private final int PAGE_SIZE=15;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findActivity(@CookieParam("u")String userId,@QueryParam("type") String type,@QueryParam("page") int page) {
        if (!IdentifyUtils.isValidate(userId)) {
            return Response.status(403).build();
        }
        Iterable<Activity> result=activityDAO.getCollection().find("{type:#}",type).sort("{beginDate:-1}").skip(page*PAGE_SIZE).limit(PAGE_SIZE).as(Activity.class);

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=result;

        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadActivity(@CookieParam("u")String userId,@PathParam("activityId") String activityId) {
        if (!IdentifyUtils.isValidate(userId)) {
            return Response.status(403).build();
        }
        Activity result=activityDAO.loadById(activityId);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=result;
        return  Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createActivity(@CookieParam("u")String userId,Activity activity) {
        if (!IdentifyUtils.isValidate(userId)) {
            return Response.status(403).build();
        }
        activityDAO.save(activity);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=activity;
        return  Response.status(200).entity(responseEntity).build();
    }



}
