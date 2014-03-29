package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpComment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
import com.jieli.feature.vote.entity.Vote;
import com.jieli.feature.vote.entity.VoteComment;
import com.jieli.feature.vote.entity.VoteInfo;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能列表页接口
 * 包括互帮互助、默契匹配、投票列表
 * Created by YolandaLeo on 14-3-19.
 */
//@Path("/feature")
public class FeatureService {
    private HelpDAO helpDAO = new HelpDAO();
    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    /**
     * 获取互帮互助列表
     * @param sessionId
     * @return
     */
    @Path("/help")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getHelpList(@CookieParam("u")String sessionId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        User user = userDAO.loadById(userId);
        if(user == null) {
            responseEntity.code = 1104;
            responseEntity.msg = "账户已被删除";
            return  Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        if(StringUtils.isEmpty(associationId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "未加入协会";
            return Response.status(200).entity(responseEntity).build();
        }
        List<SimpleHelpInfo> simpleHelpInfoList = helpDAO.getHelpInfoList(associationId);

        responseEntity.code = 200;
        responseEntity.body = simpleHelpInfoList;
        return Response.status(200).entity(simpleHelpInfoList).build();
    }

    /**
     * 获取互帮互助详情
     * @param helpId
     * @return
     */
    @Path("/help/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDetailHelpInfo(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadHelpInfo(helpId);
        if(helpInfo == null) {
            responseEntity.code = 1201;
            responseEntity.msg = "互帮互助信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = helpInfo;
        return Response.status(200).entity(helpInfo).build();
    }

    @Path("/help/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addHelpInfo(@CookieParam("u") String sessionId, @HeaderParam("help") HelpInfo help) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (help  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        String userId = IdentifyUtils.getUserId(sessionId);
        help.setAssociationId(associationId);
        help.setAddTime(new Date());
        help.setAttentionNum(0);
        help.setUserId(userId);
        help.setCommentList(new ArrayList<HelpComment>());
        HelpInfo result = helpDAO.addHelp(help);
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(result).build();
    }

    /**
     * 互帮互助详情页获取评论列表
     * @param helpId
     * @return
     */
    @Path("help/detail/comment")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getHelpComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        List<HelpComment> commentList = helpDAO.getCommentList(helpId);
        if(commentList == null || commentList.isEmpty()) {
            responseEntity.code = 1201;
            responseEntity.msg = "互帮互助信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = commentList;
        return Response.status(200).entity(commentList).build();
    }

    /**
     * 增加评论
     * @param sessionId
     * @param context
     * @param helpId
     * @return
     */
    @Path("/help/detail/comment/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, @QueryParam("context")String context) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        User user = userDAO.loadById(userId);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(context)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpComment comment = new HelpComment();
        comment.setComment(context);
        comment.setCommentUserId(userId);
        comment.setHelpId(helpId);
        comment.setAddTime(new Date());
        HelpInfo addResult = helpDAO.addComment(comment);
        //TODO check whether comment is added
        if(addResult == null) {
            responseEntity.code = 1222;
            responseEntity.msg = "评论添加失败";
            return Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = addResult;
        return Response.status(200).entity(addResult).build();
    }

    /**
     * 删除评论
     * @param helpId
     * @return
     */
    @Path("/help/detail/comment/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, @QueryParam("commentId") String commentId){
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(commentId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo help = helpDAO.loadHelpInfo(helpId);
        if(help == null) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        helpDAO.deleteComment(helpId, commentId);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 加关注
     * @param helpId
     * return HelpInfo 用于刷新更新关注数
     */
    @Path("/help/detail/comment/focus")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addFocus(@CookieParam("u")String sessionId, String helpId){
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadHelpInfo(helpId);
        if(helpInfo == null) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo result = helpDAO.addFocus(helpId);
        //TODO 互帮互助动态
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 评论置顶
     * @param helpId
     * @param commentId
     * @return
     */
    @Path("/help/detail/comment/top")
     @POST
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response addTop(@CookieParam("u")String sessionId, String helpId, String commentId) {
         if(!IdentifyUtils.isValidate(sessionId)) {
             return Response.status(403).build();
         }
         String userId = IdentifyUtils.getUserId(sessionId);
         ResponseEntity responseEntity = new ResponseEntity();
         if (StringUtils.isEmpty(userId)) {
             responseEntity.code = 1103;
             responseEntity.msg = "账户出错";
             return Response.status(200).entity(responseEntity).build();
         }
         if (StringUtils.isEmpty(helpId) || StringUtils.isEmpty(commentId)) {
             responseEntity.code = 1101;
             responseEntity.msg = "缺少参数";
             return Response.status(200).entity(responseEntity).build();
         }
         HelpInfo help = helpDAO.loadHelpInfo(helpId);
         if(help == null) {
             responseEntity.code = 1205;
             responseEntity.msg = "参数错误";
             return Response.status(200).entity(responseEntity).build();
         }
         List<HelpComment> comments = help.getCommentList();
         boolean flag = false;
         for (HelpComment comment : comments) {
             if(comment.getId() == commentId)
                 flag = true;
         }
         if(!flag) {
             responseEntity.code = 1205;
             responseEntity.msg = "参数错误";
             return Response.status(200).entity(responseEntity).build();
         }
         HelpInfo result = helpDAO.topComment(helpId, commentId);
         if(result == null) {
             responseEntity.code = 1206;
             responseEntity.body = "置顶评论失败";
             return Response.status(200).entity(responseEntity).build();
         }
         responseEntity.code = 200;
         responseEntity.body = result;
         return Response.status(200).entity(responseEntity).build();

     }

    /**
     * 获取投票列表
     * @param sessionId
     * @return
     */
    @Path("/vote")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getVoteList(@CookieParam("u")String sessionId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        User user = userDAO.loadById(userId);
        if(user == null) {
            responseEntity.code = 1104;
            responseEntity.msg = "账户已被删除";
            return  Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        if(StringUtils.isEmpty(associationId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "未加入协会";
            return Response.status(200).entity(responseEntity).build();
        }
        List<SimpleVoteInfo> voteList = voteDAO.getVoteInfoList(associationId);
        responseEntity.code = 200;
        responseEntity.body = voteList;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 获取投票详情
     * @param sessionId
     * @param voteId
     * @return
     */
    @Path("/vote/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON + "charset=utf-8")
    public Response getVoteInfo(@CookieParam("u")String sessionId, @HeaderParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteInfo voteInfo = voteDAO.loadVoteInfo(voteId);
        if(voteInfo == null) {
            responseEntity.code = 1301;
            responseEntity.msg = "投票信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = voteInfo;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 发起投票
     * @param sessionId
     * @param voteInfo
     * @return
     */
    @Path("/vote/addvote")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "charset=utf-8")
    public Response addVote(@CookieParam("u")String sessionId, @HeaderParam("vote")VoteInfo voteInfo) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(voteInfo == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        String userId = IdentifyUtils.getUserId(sessionId);
        voteInfo.setUserId(userId);
        voteInfo.setAssociationId(associationId);
        voteInfo.setAddTime(new Date());
        voteInfo.setCommentList(new ArrayList<VoteComment>());
        VoteInfo result = voteDAO.addVote(voteInfo);
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 投票
     * 传入参数options，以选项index和分号拼装，eg. 1;3;
     * @param sessionId
     * @param vote
     * @return
     */
    @Path("/vote/commitvote")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "charset=utf-8")
    public Response vote(@CookieParam("u")String sessionId, @HeaderParam("vote")Vote vote, @HeaderParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(vote == null || voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteInfo voteInfo = voteDAO.loadVoteInfo(voteId);
        if(voteId == null) {
            responseEntity.code = 1301;
            responseEntity.msg = "投票信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        VoteInfo result = voteDAO.vote(vote, voteId);
        //TODO 触发投票动态
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 添加投票评论
     * @param sessionId
     * @param comment
     * @param voteId
     * @return
     */
    @Path("/vote/comment")
    @POST
    @Produces(MediaType.APPLICATION_JSON + "charset=utf-8")
    public Response addComment(@CookieParam("u")String sessionId, @HeaderParam("comment")VoteComment comment, @HeaderParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(comment == null || voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteInfo result = voteDAO.addComment(comment, voteId);
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(result).build();
    }

    /**
     * 定向匹配
     * @param sessionId
     * @return
     */
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON + "charset=utf-8")
    public Response getOrientedMatch(@CookieParam("u")String sessionId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        User user = userDAO.loadById(userId);
    }*/
}