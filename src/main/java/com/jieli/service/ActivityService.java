package com.jieli.service;

import com.jieli.activity.*;
import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.*;
import com.jieli.mongo.BaseDAO;
import com.jieli.user.entity.FriendMsg;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentityUtils;
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
    public Response findOngoingActivity(@CookieParam("u")String sessionId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String associationId = IdentityUtils.getAssociationId(sessionId);
        List<Activity> activities = new ArrayList<Activity>();
        Iterable<Activity> officials = activityDAO.findOngoingOfficial(associationId);
        Iterable<Activity> recommends = activityDAO.findOngoingRecommend(associationId);
        Iterable<Activity> privates = activityDAO.findOngoingPrivate(associationId);

        for (Activity official : officials) {
            activities.add(official);
        }
        for (Activity recommend : recommends) {
            activities.add(recommend);
        }
        for (Activity privateAct : privates) {
            activities.add(privateAct);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code=200;
        responseEntity.body=activities;

        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findHistoryActivity(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count, @QueryParam("tag")String tag) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(tag)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (page <= 0) {
            page = 1;
        }
        if (count <= 0) {
            count = 15;
        }

        String associationId = IdentityUtils.getAssociationId(sessionId);
        Iterable<Activity> activities = activityDAO.findHistory(associationId, page-1, count, tag);
        responseEntity.code = 200;
        responseEntity.body = activities;

        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadActivity(@CookieParam("u")String sessionId,@QueryParam("activityId") String activityId) {
        if (!IdentityUtils.isValidate(sessionId)) {
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
    public Response upsertActivity(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, @QueryParam("force")boolean force, Activity activity) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (activity.tag == AcivityTag.RECOMMEND) {
            if (!IdentityUtils.isSuper(sessionId)) {
                return Response.status(403).build();
            }
        }
        if (activity.tag == AcivityTag.OFFICIAL) {
            if (!IdentityUtils.isAdmin(sessionId)) {
                return Response.status(403).build();
            }
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = IdentityUtils.getAssociationId(sessionId);
        if (StringUtils.isEmpty(activity.associationId)) {
            if (StringUtils.isEmpty(associationId)) {
                responseEntity.code = 3101;
                responseEntity.msg = "缺少协会id信息";
                return Response.status(200).entity(responseEntity).build();
            }
            activity.associationId = associationId;
        }
        activity.addTime = new Date();

        List<String> oldActivityInvitees = new ArrayList<String>();
        boolean exist = false;
        if (!StringUtils.isEmpty(activityId) && MongoUtils.isValidObjectId(activityId)) {
            Activity oldActivity = activityDAO.loadById(activityId);
            if (oldActivity != null) {
                if (oldActivity.sponsorUserId.equals(IdentityUtils.getUserId(sessionId))) {
                    activity.set_id(new ObjectId(activityId));
                    activity.addTime = oldActivity.addTime;
                    activity.album = oldActivity.album;
                    activity.followMembers = oldActivity.followMembers;
                    activity.joinMembers = oldActivity.joinMembers;
                    activity.invitees = oldActivity.invitees;
                    exist = true;
                    oldActivityInvitees = oldActivity.invitees;
                }  else {
                    return Response.status(403).build();
                }
            }
        }
        activity.sponsorUserId = IdentityUtils.getUserId(sessionId);
        if (exist) {
            for (String userId : activity.joinMembers.keySet()) {
                Message message = new Message();
                message.userId = userId;
                message.read = false;
                message.messageType = MessageType.OTHER;
                message.force = force;
                message.addTime = new Date();

                ActivityMsg activityMsg = new ActivityMsg();
                activityMsg.activityId = activityId;
                activityMsg.tag = activity.tag;
                activityMsg.msg = activity.title + " 活动内容有改变";

                message.content = activityMsg;
                messageDAO.save(message);
            }
        } else {
            if (activity.tag == AcivityTag.PRIVATE) {

            } else if(force) {
                ActivityMsg activityMsg = new ActivityMsg();
                if (!StringUtils.isEmpty(activity.associationId)) {
                    if (activity.tag == AcivityTag.OFFICIAL) {
                        activityMsg.msg = activity.title + " 官方活动发布";
                    } else if (activity.tag == AcivityTag.RECOMMEND) {
                        activityMsg.msg = activity.title + " 推荐活动发布";
                    }
                    activityMsg.activityId = activityId;
                    activityMsg.tag = activity.tag;
                    Send2AllTask task = new Send2AllTask(activityMsg, activity.associationId, MessageType.OTHER);
                    task.start();
                }
            }
        }

        //邀请消息
        for (String userId : activity.invitees) {
            if (oldActivityInvitees.contains(userId)) {
                continue;
            }
            Message message = new Message();
            message.userId = userId;
            message.read = false;
            message.messageType = MessageType.INVITE;
            message.force = force;
            message.addTime = new Date();

            ActivityMsg activityMsg = new ActivityMsg();
            activityMsg.activityId = activityId;
            activityMsg.tag = activity.tag;
            activityMsg.msg = IdentityUtils.getUserName(activity.sponsorUserId) + "邀请你参加" + activity.title;

            message.content = activityMsg;
            messageDAO.save(message);
        }

        activityDAO.save(activity);
        insertRelated(IdentityUtils.getUserId(sessionId), activity.get_id().toString(), RelatedType.SPONSER);

        responseEntity.body = "{\"_id\":\"" + activity.get_id() + "\"}";
        responseEntity.code=200;
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findRelatedActivities(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count) {
        if (!IdentityUtils.isValidate(sessionId)) {
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
        String userId = IdentityUtils.getUserId(sessionId);
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

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findRelatedActivitiesByUserId(@CookieParam("u")String sessionId,@QueryParam("userId")String userId, @QueryParam("page")int page, @QueryParam("size")int count) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count <= 0) {
            count = 10;
        }
        if (page <= 0) {
            page = 1;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        List<ActivityInfo> result = new ArrayList<ActivityInfo>();
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

        responseEntity.body = generateDisplay(result);
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

    private List<RelatedDisplay> generateDisplay(List<ActivityInfo> infos) {
        List<RelatedDisplay> displays = new ArrayList<RelatedDisplay>();
        for (ActivityInfo info : infos) {
            RelatedDisplay display = new RelatedDisplay();
            display.activityId = info.activityId;
            Activity activity = activityDAO.loadById(info.activityId);
            if (activity == null) {
                continue;
            }
            display.type = info.type;
            display.title = activity.title;
            display.activityDate = activity.beginDate;
            display.time = info.time;
            displays.add(display);
        }
        return displays;
    }

    @GET
    @Path("/concern")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response concern(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId) {
        if (!IdentityUtils.isValidate(sessionId)) {
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

        String userId = IdentityUtils.getUserId(sessionId);
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        if (activity.followMembers.contains(userId)) {
            activity.followMembers.remove(userId);
            responseEntity.msg = "已取消";
            responseEntity.body = "{\"count\":"+activity.followMembers.size()+",\"concern\":"+0+"}";
            removeRelated(userId, activityId, RelatedType.FOLLOW);
        } else {
            activity.followMembers.add(userId);
            responseEntity.msg = "已关注";
            responseEntity.body = "{\"count\":"+activity.followMembers.size()+",\"concern\":"+1+"}";
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
        if (!IdentityUtils.isValidate(sessionId)) {
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
        String userId = IdentityUtils.getUserId(sessionId);
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        if (activity.joinMembers.get(userId) == null) {
            activity.joinMembers.put(userId, part);
            responseEntity.msg = "已参加";
            responseEntity.body = "{\"count\":"+activity.joinMembers.size()+",\"join\":"+1+"}";
            insertRelated(userId, activityId, RelatedType.JOIN);
            List<String> concernedUserIds = IdentityUtils.getConcerned(userId);

            for (String concernedUserId : concernedUserIds) {
                Message message = new Message();
                message.messageType = MessageType.FRIEND;
                message.userId = concernedUserId;
                message.addTime = new Date();

                FriendMsg friendMsg = new FriendMsg();
                friendMsg.msg = "您关注的 " + IdentityUtils.getUserName(userId) + " 参加了活动 " + activity.title;
                friendMsg.topicId = activityId;
                friendMsg.topicType = TopicType.Activity;
                message.content = friendMsg;

                messageDAO.save(message);
            }
        } else {
            activity.joinMembers.remove(userId);
            responseEntity.msg = "已取消";
            responseEntity.body = "{\"count\":"+activity.joinMembers.size()+",\"join\":"+0+"}";
            removeRelated(userId, activityId, RelatedType.JOIN);
        }
        activityDAO.save(activity);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    private void insertRelated(String userId, String activityId, RelatedType relatedType) {
        RelatedActivity relatedActivity = relatedDAO.findByUserId(userId);
        if (relatedActivity == null) {
            relatedActivity = new RelatedActivity();
            relatedActivity.userId = userId;
        }
        ActivityInfo info = new ActivityInfo();
        info.activityId = activityId;
        info.type = relatedType;
        info.time = new Date();
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

        if (!IdentityUtils.isValidate(sessionId)) {
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
        comment.topicTitle = activity.title;
        comment.tag = activity.tag.toString();
        comment.commentUserId = IdentityUtils.getUserId(sessionId);
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.addTime = new Date();
        commentDAO.save(comment);
        //message
        CommentMessageUtil.addCommentMessage(comment);

        if (activity.tag == AcivityTag.PRIVATE && !activity.sponsorUserId.equals(IdentityUtils.getUserId(sessionId))) {
            CommentMessageUtil.addCommentAuthorMessage(comment, activity.sponsorUserId);
        }

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        responseEntity.body = comment;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response uploadPics(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, List<String> pics) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(activityId) || CollectionUtils.isEmpty(pics)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(activityId)) {
            responseEntity.code = 3102;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }

        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        if (IdentityUtils.isAdmin(sessionId)) {
            activity.officialAlbum = pics;
            activityDAO.save(activity);

            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
        if (activity.album == null) {
            activity.album = new HashMap<String, List<String>>();
        }
        List<String> userPics = activity.album.get(userId);
        if (userPics == null) {
            userPics = new ArrayList<String>();
        }
        userPics.addAll(pics);
        activity.album.put(userId, userPics);
        activityDAO.save(activity);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/deletepic")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deletePics(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId,
                               @QueryParam("userId")String userId, @QueryParam("pic")String pic) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(activityId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(pic)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(activityId)) {
            responseEntity.code = 3102;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        if (activity.album == null || activity.album.get(userId) == null || !activity.album.get(userId).contains(pic)) {
            responseEntity.code = 3108;
            responseEntity.msg = "图片不存在";
            return Response.status(200).entity(responseEntity).build();
        } else {
            activity.album.get(userId).remove(pic);
            activityDAO.save(activity);
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }
    }

    @POST
    @Path("/invite")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response invite(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId, List<String> friendIds) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(activityId) || CollectionUtils.isEmpty(friendIds)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(activityId)) {
            responseEntity.code = 3102;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }

        String userId = IdentityUtils.getUserId(sessionId);
        Activity activity = activityDAO.loadById(activityId);
        if (activity == null) {
            responseEntity.code = 3103;
            responseEntity.msg = "活动不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        //邀请消息
        for (String friendId : friendIds) {
            Message message = new Message();
            message.userId = friendId;
            message.read = false;
            message.messageType = MessageType.INVITE;
            message.addTime = new Date();

            ActivityMsg activityMsg = new ActivityMsg();
            activityMsg.activityId = activityId;
            activityMsg.tag = activity.tag;
            activityMsg.msg = IdentityUtils.getUserName(userId) + "邀请你参加" + activity.title;

            message.content = activityMsg;
            messageDAO.save(message);
        }

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response delete(@CookieParam("u")String sessionId, @QueryParam("activityId")String activityId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(activityId)) {
            responseEntity.code = 3101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        Activity activity = activityDAO.loadById(activityId);
        String userId = IdentityUtils.getUserId(sessionId);
        if (activity == null || !activity.sponsorUserId.equals(userId)) {
            responseEntity.code = 403;
            responseEntity.msg = "权限不足";
            return Response.status(200).entity(responseEntity).build();
        }
        activityDAO.deleteById(activityId);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

}
