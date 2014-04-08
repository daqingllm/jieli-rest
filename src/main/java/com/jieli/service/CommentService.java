package com.jieli.service;


import com.jieli.comment.Comment;
import com.jieli.comment.CommentMsg;
import com.jieli.comment.CommentUserInfo;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.Message;
import com.jieli.message.MessageType;
import com.jieli.mongo.BaseDAO;
import com.jieli.mongo.Collections;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM8:05
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/rest/comment")
public class CommentService {

    BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(Collections.Comment, Comment.class);
    BaseDAO<Message> messageDAO = new BaseDAO<Message>(Collections.Message, Message.class);
    UserDAO userDAO = new UserDAO();

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response add(@CookieParam("u")String sessionId,
                        @QueryParam("topicId")String topicId, @QueryParam("topicType")String topicType,
                        @QueryParam("commentedUserId")String commentedUserId,
                        @QueryParam("content")String content){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);

        Comment comment = new Comment();
        comment.topicId = topicId;
        comment.topicType = topicType;
        comment.commentUserId = userId;
        comment.commentedUserId = commentedUserId;
        comment.content = content;
        comment.addTime = new Date();
        commentDAO.save(comment);

        // 给被评论者发消息
        if(StringUtils.isNotEmpty(commentedUserId)){
            Message message = new Message();
            message.userId = commentedUserId; // 消息接受者即为被评论者
            message.messageType = MessageType.NEWS;

            CommentUserInfo commentUserInfo = new CommentUserInfo();
            commentUserInfo.userId = userId;
            User user = userDAO.loadById(userId);
            commentUserInfo.name = user.name;
            commentUserInfo.userFace = user.userFace;

            CommentMsg commentMsg = new CommentMsg();
            commentMsg.commentUser = commentUserInfo;
            commentMsg.commentContent = content;
            //commentMsg.topicBrief =  // 被评论内容概述

            message.content = commentMsg;
            message.read = false;
            message.addTime = new Date();

            messageDAO.save(message);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = "ok";
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response load(@QueryParam("topicId")String topicId, @QueryParam("topicType")String topicType, @QueryParam("page")int page, @QueryParam("count")int count){
        List<Comment> comments = commentDAO.find("{topicId:#, topicType:#, isDeleted:#}", topicId, topicType, false);

        if( !CollectionUtils.isEmpty(comments) ){
            for(Comment comment : comments){
                String userId = comment.commentUserId;
                CommentUserInfo commentUserInfo = new CommentUserInfo();
                commentUserInfo.userId = userId;
                User user = userDAO.loadById(userId);
                commentUserInfo.name = user.name;
                commentUserInfo.userFace = user.userFace;

                comment.commentUserInfo = commentUserInfo;
            }
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = comments;
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response delete(@QueryParam("commentId")String commentId) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (!MongoUtils.isValidObjectId(commentId)) {
            responseEntity.code = 7001;
            responseEntity.msg = "参数错误";
            return  Response.status(200).entity(responseEntity).build();
        }
        Comment comment = commentDAO.findOne("{_id:#}", new ObjectId(commentId));
        comment.isDeleted = true;
        commentDAO.save(comment);
        responseEntity.code = 200;
        return  Response.status(200).entity(responseEntity).build();
    }

}
