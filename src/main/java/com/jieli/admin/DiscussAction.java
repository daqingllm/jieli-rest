package com.jieli.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.comment.*;
import com.jieli.comment.Comment;
import com.jieli.common.dao.AccountDAO;
import com.jieli.feature.discuss.dao.DiscussDAO;
import com.jieli.feature.discuss.entity.DiscussInfo;
import com.jieli.feature.discuss.entity.SimpleDiscussInfo;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.HelpStatus;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.mongo.BaseDAO;
import com.jieli.mongo.Collections;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.CollectionUtils;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meijia.lv on 14-6-2.
 */
@Singleton
@Path("bdiscuss")
public class DiscussAction {
    private DiscussDAO discussDAO = new DiscussDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private UserDAO userDAO = new UserDAO();
    private BaseDAO<com.jieli.comment.Comment> commentDAO = new BaseDAO<com.jieli.comment.Comment>(Collections.Comment, Comment.class);
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String getDiscussList(@CookieParam("u")String sessionId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        //String associationId = IdentityUtils.getAssociationId(sessionId);
        boolean isSuper = false;
        String associationId = "";
        if(IdentityUtils.isSuper(sessionId))
            isSuper = true;
        if(isSuper) {
            List<com.jieli.association.Association> associationList = new ArrayList<com.jieli.association.Association>();
            Iterable<com.jieli.association.Association> associationIte = associationDAO.loadAll();
            for(com.jieli.association.Association a : associationIte) {
                associationList.add(a);
            }
            params.put("associationList", associationList);
            associationId = associationList.get(0).get_id().toString();
        }
        else {
            associationId = IdentityUtils.getAssociationId(sessionId);
            if(!MongoUtils.isValidObjectId(associationId)) {
                return FTLrender.getResult("error.ftl", params);
            }
        }
        Integer pageNo = 1;
        Integer pageSize = 20;
        Integer type = 2;
        List<SimpleDiscussInfo> discussList = discussDAO.getDiscussInfoList(pageNo, pageSize, associationId, type);

        for(SimpleDiscussInfo h : discussList) {
            h.setId(h.get_id().toString());
        }
        String jsonDiscussList;
        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(discussList);

            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>)om.readValue(tmp, List.class);
            for(Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    com.jieli.association.Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }
            jsonDiscussList = om.writeValueAsString(l);
        } catch (Exception e) {
            e.printStackTrace();
            jsonDiscussList = "[]";
        }
        params.put("jsonDiscussList", jsonDiscussList);
        return FTLrender.getResult("discuss_list.ftl", params);
    }

    @GET
    @Path("/view")
    @Produces(MediaType.TEXT_HTML)
    public String viewDiscuss(@CookieParam("u")String sessionId, @QueryParam("d")String discussId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        if(!MongoUtils.isValidObjectId(discussId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        DiscussInfo discuss = discussDAO.loadById(discussId);

        List<com.jieli.comment.Comment> commentList = commentDAO.find("{topicId:#, topicType:#, isDeleted:#}",
                discuss.get_id(), TopicType.Help, false);
        if( !CollectionUtils.isEmpty(commentList) ){
            for(com.jieli.comment.Comment comment : commentList){
                String userId = comment.commentUserId;
                CommentUserInfo commentUserInfo = new CommentUserInfo();
                commentUserInfo.userId = userId;
                com.jieli.user.entity.User user = userDAO.loadById(userId);
                commentUserInfo.name = user.name;
                commentUserInfo.userFace = user.userFace;

                comment.commentUserInfo = commentUserInfo;
            }
        }
        commentList = sortComment(commentList, discuss.getTopCommentList());
        int topSize = 1; //mock data
        //int topSize = help.getTopCommentList().size();
        if(discuss == null) {
            return FTLrender.getResult("error.ftl", params);
        }
        boolean isSuper = IdentityUtils.isSuper(sessionId);
        boolean canDelete = false;
        if(!isSuper) {
            canDelete = true;
        }
        params.put("commentList", commentList);
        params.put("topSize", topSize);
        params.put("canDelete", canDelete);
        params.put("discuss", discuss);
        return FTLrender.getResult("discussinfo.ftl", params);
    }

    private List<com.jieli.comment.Comment> sortComment(List<com.jieli.comment.Comment> commentList, List<com.jieli.comment.Comment> topComments) {
        List<com.jieli.comment.Comment> topOnes = new ArrayList<com.jieli.comment.Comment>();
        List<com.jieli.comment.Comment> normalOnes = new ArrayList<com.jieli.comment.Comment>();
        if(commentList == null) {
            commentList = new ArrayList<com.jieli.comment.Comment>();
            topComments = new ArrayList<com.jieli.comment.Comment>();
            /*
            *mock data
            Comment comment = new Comment();
            comment.addTime = new Date();
            comment.content = "testtest";
            comment.topicType = TopicType.Help;
            comment.commentUserId = "533be198300460878a64a155";
            CommentUserInfo userInfo = new CommentUserInfo();
            userInfo.name = "薄荷";
            userInfo.userId = "533be198300460878a64a155";
            comment.commentUserInfo = userInfo;
            comment.topicId = "534f9b8f3004055aeb46f7b3";
            commentList.add(comment);
            topComments.add(comment);*/
        }

        for(com.jieli.comment.Comment c : commentList) {
            if(topComments.contains(c)) {
                topOnes.add(c);
            }
            else {
                normalOnes.add(c);
            }
        }
        commentList.clear();
        commentList.addAll(topOnes);
        commentList.addAll(normalOnes);
        return commentList;
    }
}
