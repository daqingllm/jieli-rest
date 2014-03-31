package com.jieli.service;

import com.jieli.activity.*;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.MessageDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * @author ltebean
 */

@Singleton
@Path("/activity")
public class ActivityService {

    private final ActivityDAO activityDAO = new ActivityDAO();

    private final RelatedActivityDAO  relatedDAO = new RelatedActivityDAO();

    private final UserDAO userDAO = new UserDAO();

    private final MessageDAO messageDAO = new MessageDAO();

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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadActivity(@CookieParam("u")String sessionId,@QueryParam("activityId") String activityId) {
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
    public Response upsertActivity(@CookieParam("u")String sessionId,Activity activity) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        activityDAO.save(activity);
//        handleMessageWhenUpsertActivity(activity);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.body = "{\"_id\":\"" + activity.get_id() + "\"}";
        responseEntity.code=200;
        return  Response.status(200).entity(responseEntity).build();
    }

    private void handleMessageWhenUpsertActivity(Activity activity) {
        if (activity.get_id() == null) {
            handleMessageWhenInsertActivity(activity);
        } else {
            handleMessageWhenUpdateActivity(activity);
        }
    }

    private void handleMessageWhenInsertActivity(Activity activity) {
        if (activity.tag.equals(AcivityTag.RECOMMEND)) {
            new MessageTask(activity.title) {

                private static final int SIZE = 50;

                private String title;

//                @Override
//                public void run() {
//                    long count = userDAO.getCollection().getDBCollection().count();
//                    for (int i=0; i*SIZE<count; ++i) {
//                        Iterable<User> users = userDAO.getCollection().find().skip(i*SIZE).limit(SIZE).as(User.class);
//                        for (User user : users) {
//                            Message message = new Message();
//                            message.messageType = MessageType.ACTIVITY;
//                            message.read = false;
//                            message.content = "[官方活动] " + activity.title;
//                            message
//                        }
//                    }
//                }
            };
        }
    }

    private void handleMessageWhenUpdateActivity(Activity activity) {

    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findRelatedActivities(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count) {
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
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.body = generateRelatedActivities(result);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    private Map<RelatedType, List<Activity>> generateRelatedActivities(List<ActivityInfo> infos) {
        Map<RelatedType, List<Activity>> result = new HashMap<RelatedType, List<Activity>>();
        for (ActivityInfo info : infos) {
            RelatedType type = info.type;
            if (result.get(type) == null) {
                List<Activity> activities = new ArrayList<Activity>();
                result.put(type, activities);
            }
            Activity activity = activityDAO.loadById(info.activityId);
            result.get(type).add(activity);
        }

        return result;
    }

    @GET
    @Path("/concern")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response concern(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId) {
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

        String userId = IdentifyUtils.getUserId(sessionId);
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        if (activity.followMembers.contains(userId)) {
            activity.followMembers.remove(userId);
            responseEntity.msg = "已取消";
        } else {
            activity.followMembers.add(userId);
            responseEntity.msg = "已关注";
        }
        activityDAO.save(activity);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/join")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response join(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, @QueryParam("part")String part) {
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

        if (StringUtils.isEmpty(part)) {
            part = "all";
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        if (activity.joinMembers.get(userId) == null) {
            activity.joinMembers.put(userId, part);
            responseEntity.msg = "已参加";
        } else {
            activity.joinMembers.remove(userId);
            responseEntity.msg = "已取消";
        }
        activityDAO.save(activity);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response comment(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, String content) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 3104;
            responseEntity.msg = "回复内容不能为空";
            return Response.status(200).entity(responseEntity).build();
        }
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Comment comment = new Comment();
        comment.userId = IdentifyUtils.getUserId(sessionId);
        comment.content = content;
        comment.date = new Date();
        activity.comments.add(comment);
        activityDAO.save(activity);
        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        return Response.status(200).entity(responseEntity).build();
    }

    private class MessageTask implements Runnable {

        private String content;

        public MessageTask(String content) {
            this.content = content;
        }

        @Override
        public void run() {
        }
    }

}
