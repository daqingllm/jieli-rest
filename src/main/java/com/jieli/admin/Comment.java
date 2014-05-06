package com.jieli.admin;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.mongo.BaseDAO;
import com.jieli.mongo.Collections;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 95 on 2014/5/4.
 */
@Singleton
@Path("/bcomment")
public class Comment {
    private BaseDAO<com.jieli.comment.Comment> commentDAO = new BaseDAO<com.jieli.comment.Comment>(Collections.Comment, com.jieli.comment.Comment.class);

    @GET
    @Path("/comment")
    @Produces(MediaType.TEXT_HTML)
    public String GetNewsCommentList(@CookieParam("u")String sessionId,@QueryParam("artid") String newsId){
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return  response;

        ResponseEntity responseEntity = new ResponseEntity();
        List<com.jieli.comment.Comment> commentList = new LinkedList<com.jieli.comment.Comment>();
        String commentListString = "[";
        if (newsId != null){
            commentList = commentDAO.find("{topicId:#}", newsId);
            for (com.jieli.comment.Comment comment : commentList)
                commentListString += CommonUtil.ReplaceObjectId(comment)+",";
            if (commentListString.endsWith(",")){
                commentListString = commentListString.substring(0,commentListString.length()-1);
            }
            commentListString += "]";
        }else{
            return CommonUtil.errorReturn;
        }
        return  "";
    }
}
