package com.jieli.service;

import com.jieli.activity.*;
import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.*;
import com.jieli.mongo.BaseDAO;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

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

    private final MessageDAO messageDAO = new MessageDAO();

    BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(com.jieli.mongo.Collections.Comment, Comment.class);

    @GET
    @Path("/ongoing")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findOngoingActivity(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (page <= 0) {
            page = 1;
        }
        if (count <= 0) {
            count = 15;
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        Iterable<Activity> activities = activityDAO.findOngoing(associationId, page-1, count);

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=activities;

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
        Activity result=activityDAO.loadById(activityId);
        responseEntity.code=200;
        responseEntity.body=result;
        return  Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertActivity(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, Activity activity) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (activity.tag == AcivityTag.RECOMMEND) {
            if (!IdentifyUtils.isSuper(sessionId)) {
                return Response.status(403).build();
            }
        }
        if (activity.tag == AcivityTag.OFFICIAL) {
            if (!IdentifyUtils.isAdmin(sessionId)) {
                return Response.status(403).build();
            }
        }

        boolean exist = false;
        if (!StringUtils.isEmpty(activityId) && MongoUtils.isValidObjectId(activity.get_id().toString())) {
            Activity oldActivity = activityDAO.loadById(activity.get_id().toString());
            if (oldActivity != null) {
                if (oldActivity.sponsorUserId.equals(IdentifyUtils.getUserId(sessionId))) {
                    activity.set_id(new ObjectId(activityId));
                    exist = true;
                }  else {
                    return Response.status(403).build();
                }
            }
        }
        if (exist) {
            for (String userId : activity.joinMembers.keySet()) {
                Message message = new Message();
                message.userId = userId;
                message.read = false;
                message.messageType = MessageType.ACTIVITY;
                message.addTime = new Date();

                ActivityMsg activityMsg = new ActivityMsg();
                activityMsg.activityId = activityId;
                activityMsg.msg = "活动内容有改变";

                message.content = activityMsg;
                messageDAO.save(message);
            }
        } else {
            if (activity.tag == AcivityTag.PRIVATE) {
                for (String userId : activity.invitees) {
                    Message message = new Message();
                    message.userId = userId;
                    message.read = false;
                    message.messageType = MessageType.ACTIVITY;
                    message.addTime = new Date();

                    ActivityMsg activityMsg = new ActivityMsg();
                    activityMsg.activityId = activityId;
                    activityMsg.msg = "接收到串局邀请";

                    message.content = activityMsg;
                    messageDAO.save(message);
                }
            } else {
                ActivityMsg activityMsg = new ActivityMsg();
                if (!StringUtils.isEmpty(activity.associationId)) {
                    if (activity.tag == AcivityTag.OFFICIAL) {
                        activityMsg.msg = "新的官方活动发布";
                    } else if (activity.tag == AcivityTag.RECOMMEND) {
                        activityMsg.msg = "新的推荐活动发布";
                    }
                    activityMsg.activityId = activityId;
                    Send2AllTask task = new Send2AllTask(activityMsg, activity.associationId, MessageType.ACTIVITY);
                    task.start();
                }
            }
        }
        activityDAO.save(activity);
        insertRelated(IdentifyUtils.getUserId(sessionId), activity.get_id().toString(), RelatedType.SPONSER);

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.body = "{\"_id\":\"" + activity.get_id() + "\"}";
        responseEntity.code=200;
        return  Response.status(200).entity(responseEntity).build();
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
            removeRelated(userId, activityId, RelatedType.FOLLOW);
        } else {
            activity.followMembers.add(userId);
            responseEntity.msg = "已关注";
            insertRelated(userId, activityId, RelatedType.FOLLOW);
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
            insertRelated(userId, activityId, RelatedType.JOIN);
        } else {
            activity.joinMembers.remove(userId);
            responseEntity.msg = "已取消";
            removeRelated(userId, activityId, RelatedType.JOIN);
        }
        activityDAO.save(activity);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    private void insertRelated(String userId, String activityId, RelatedType relatedType) {
        RelatedActivity relatedActivity = relatedDAO.findByUserId(userId);
        ActivityInfo info = new ActivityInfo();
        info.activityId = activityId;
        info.type = relatedType;
        if (relatedActivity.infos.contains(info)) {
            return;
        }
        relatedActivity.infos.add(info);
        relatedDAO.save(relatedActivity);
    }

    private void removeRelated(String userId, String activityId, RelatedType relatedType) {
        RelatedActivity relatedActivity = relatedDAO.findByUserId(userId);
        ActivityInfo info = new ActivityInfo();
        info.activityId = activityId;
        info.type = relatedType;
        if (relatedActivity.infos.contains(info)) {
            relatedActivity.infos.remove(info);
        }
    }

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response comment(@CookieParam("u")String sessionId, Map<String, String> commentInfo) {
        //commentInfo内字段：content topicId commentedUserId(回复评论)

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String content = commentInfo.get("content");
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 3104;
            responseEntity.msg = "回复内容不能为空";
            return Response.status(200).entity(responseEntity).build();
        }
        String activityId = commentInfo.get("topicId");
        if (StringUtils.isEmpty(activityId) || !MongoUtils.isValidObjectId(activityId)) {
            responseEntity.code = 3102;
            responseEntity.msg = "参数id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Comment comment = new Comment();
        comment.topicId = activityId;
        comment.topicType = TopicType.Activity;
        comment.commentUserId = IdentifyUtils.getUserId(sessionId);
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.addTime = new Date();
        commentDAO.save(comment);
        //message
        CommentMessageUtil.addCommentMessage(comment, MessageType.ACTIVITY);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        return Response.status(200).entity(responseEntity).build();
    }

}
