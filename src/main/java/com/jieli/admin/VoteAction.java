package com.jieli.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.comment.Comment;
import com.jieli.comment.CommentUserInfo;
import com.jieli.comment.TopicType;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.dao.VoteResultDAO;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
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
 * Created by YolandaLeo on 14-4-10.
 */
@Singleton
@Path("bvote")
public class VoteAction {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private VoteResultDAO voteResultDAO = new VoteResultDAO();
    private UserDAO userDAO = new UserDAO();
    private BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(Collections.Comment, Comment.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String createVote(@CookieParam("u")String sessionId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        params.put("newVote", true);
        params.put("isEditable", true);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/view")
    public String viewVote(@CookieParam("u") String sessionId, @QueryParam("v")String voteId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        if(!MongoUtils.isValidObjectId(voteId)) {
            return CommonUtil.errorReturn;
        }

        List<Comment> commentList = commentDAO.find("{topicId:#, topicType:#, isDeleted:#}",
                voteId, TopicType.Vote, false);
        if( !CollectionUtils.isEmpty(commentList) ){
            for(Comment comment : commentList){
                String userId = comment.commentUserId;
                CommentUserInfo commentUserInfo = new CommentUserInfo();
                commentUserInfo.userId = userId;
                com.jieli.user.entity.User user = userDAO.loadById(userId);
                commentUserInfo.name = user.name;
                commentUserInfo.userFace = user.userFace;

                comment.commentUserInfo = commentUserInfo;
            }
        }
        else {
            commentList = new ArrayList<Comment>();
        }
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("commentList", commentList);
        params.put("newVote", false);
        params.put("isEditable", false);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/edit")
    public String modifyVote(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("newVote", false);
        params.put("isEditable", true);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/list")
    public String getVoteList(@CookieParam("u")String sessionId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return CommonUtil.errorReturn;
        }
        String associationId = IdentityUtils.getAssociationId(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        List<SimpleVoteInfo> voteList = new ArrayList<SimpleVoteInfo>();
        List<Association> associationList = new ArrayList<Association>();
        Integer pageNo = 1;
        Integer pageSize = 20;
        if(IdentityUtils.isSuper(sessionId)) {
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for(Association a : associations) {
                associationList.add(a);
            }
            params.put("associationList", associationList);
        }
        else {
            if(!MongoUtils.isValidObjectId(associationId)) {
                return FTLrender.getResult("error.ftl", params);
            }
        }
        if(associationId == null) {
            //首次super进入页面，未选择association
            //TODO selector传入associationId，ajax请求下一次列表页
            associationId = associationList.get(0).get_id().toString();
        }
        voteList = voteDAO.getVoteInfoList(pageNo, pageSize,
                associationId);

        for(SimpleVoteInfo v : voteList) {
            v.setId(v.get_id().toString());
            v.setParticipants(voteResultDAO.loadByVoteId(v.get_id().toString()).getParticipants());
        }
        String jsonVoteList;
        int i;
        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(voteList);

            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>)om.readValue(tmp, List.class);
            for(Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }
            jsonVoteList = om.writeValueAsString(l);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVoteList = "[]";
        }

        params.put("jsonVoteList", jsonVoteList);
        return FTLrender.getResult("vote_list.ftl", params);

    }
}
