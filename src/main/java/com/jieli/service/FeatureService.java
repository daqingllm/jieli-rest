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
import com.jieli.message.Message;
import com.jieli.message.MessageDAO;
import com.jieli.message.MessageType;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * 功能列表页接口
 * 包括互帮互助、默契匹配、投票列表
 * Created by YolandaLeo on 14-3-19.
 */
@Path("/feature")
public class FeatureService {
    private HelpDAO helpDAO = new HelpDAO();
    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private MessageDAO messageDAO = new MessageDAO();
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
        HelpInfo helpInfo = helpDAO.loadById(helpId);
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
    public Response addHelpInfo(@CookieParam("u") String sessionId, HelpInfo help) {
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
        if(result == null) {
            responseEntity.code = 1210;
            responseEntity.msg = "互帮互助帖子添加失败";
            return Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(result.get_id()).build();
    }

    /**
     * 互帮互助详情页获取评论列表
     * @param helpId
     * @return
     */
    /*@Path("help/detail/comment")
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
    }*/

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
    public Response addHelpComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, String context) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        User user = userDAO.loadById(userId);
        if (StringUtils.isEmpty(userId) ) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        if (StringUtils.isEmpty(helpId) || StringUtils.isEmpty(context)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadById(helpId);
        if(helpInfo.getUserId() == userId) {
            responseEntity.code = 1200;
            responseEntity.msg = "不能评论自己";
            return Response.status(403).entity(responseEntity).build();
        }
        HelpComment comment = new HelpComment();
        comment.setComment(context);
        comment.setCommentUserId(userId);
        comment.setHelpId(helpId);
        comment.setTop(false);
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
    public Response deleteComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, Integer commentIndex){
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
        if (!MongoUtils.isValidObjectId(helpId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo help = helpDAO.loadById(helpId);
        if(help == null) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        if(!help.getUserId().equals(userId)) {
            responseEntity.code = 1200;
            responseEntity.msg = "只有发帖人可删除评论";
            return Response.status(403).entity(responseEntity).build();
        }
        helpDAO.deleteComment(helpId, commentIndex);
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
        User user = userDAO.loadById(userId);
        if (StringUtils.isEmpty(helpId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadById(helpId);
        if(helpInfo == null) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        //已关注过不能再次关注
        if(helpInfo.getFocusList() != null) {
            for(User u : helpInfo.getFocusList()) {
                if(u.get_id().equals(userId)) {
                    responseEntity.code = 1209;
                    responseEntity.msg = "已关注过";
                    return Response.status(200).entity(responseEntity).build();
                }
            }
        }
        //自己不能关注自己
        if(helpInfo.getUserId().equals(userId)) {
            responseEntity.code = 1200;
            responseEntity.msg = "不能关注自己";
            return Response.status(403).entity(responseEntity).build();
        }

        HelpInfo result = helpDAO.addFocus(helpId, user);
        // 互帮互助动态
        Message message = new Message();
        message.messageType = MessageType.HELP;
        message.importance = 0;
        message.content = "帖子已经被我回复啦";
        message.objectId = result.get_id().toString();
        message.date = new Date();
        messageDAO.save(message);
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 评论置顶
     * @param helpId
     * @param commentIndex
     * @return
     */
    @Path("/help/detail/comment/top")
     @POST
     @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
     public Response addTop(@CookieParam("u")String sessionId, String helpId, Integer commentIndex) {
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
         if (StringUtils.isEmpty(helpId) || commentIndex == null) {
             responseEntity.code = 1101;
             responseEntity.msg = "缺少参数";
             return Response.status(200).entity(responseEntity).build();
         }
         HelpInfo help = helpDAO.loadById(helpId);
         if(help == null) {
             responseEntity.code = 1205;
             responseEntity.msg = "参数错误";
             return Response.status(200).entity(responseEntity).build();
         }
         if(help.getUserId() != userId) {
             responseEntity.code = 1200;
             responseEntity.msg = "不是发帖人";
             return Response.status(403).entity(responseEntity).build();
         }
         List<HelpComment> comments = help.getCommentList();

         HelpInfo result = helpDAO.topComment(helpId, commentIndex);
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
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
        VoteInfo voteInfo = voteDAO.loadById(voteId);
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addVote(@CookieParam("u")String sessionId, VoteInfo voteInfo) {
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
        if(voteInfo.getOptions() == null || voteInfo.getOptions().isEmpty()) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        voteInfo.setTotalVote(0);
        Map<Integer, Integer> optionVotesInit = new HashMap<Integer, Integer>();
        for(int i = 0; i < voteInfo.getOptions().size(); i++) {
            optionVotesInit.put(i, 0);
        }
        voteInfo.setOptionVotes(optionVotesInit);
        voteInfo.setUserId(userId);
        voteInfo.setAssociationId(associationId);
        voteInfo.setAddTime(new Date());
        voteInfo.setCommentList(new ArrayList<VoteComment>());

        VoteInfo result = voteDAO.addVote(voteInfo);
        //发起投票动态
        Message message = new Message();
        message.messageType = MessageType.VOTE;
        message.importance = 0;
        message.content = "我发起了一个投票";
        message.objectId = result.get_id().toString();
        message.date = new Date();
        messageDAO.save(message);
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    //TODO 关闭投票

    /**
     * 投票
     * 传入参数options，以选项index和分号拼装，eg. 1;3;
     * @param sessionId
     * @param vote
     * @return
     */
    @Path("/vote/commitvote")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response vote(@CookieParam("u")String sessionId, Vote vote, @HeaderParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(vote == null || voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteInfo voteInfo = voteDAO.loadById(voteId);
        if(voteId == null) {
            responseEntity.code = 1301;
            responseEntity.msg = "投票信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        VoteInfo result = voteDAO.vote(vote, voteId);
        //触发参与投票动态
        Message message = new Message();
        message.messageType = MessageType.VOTE;
        message.importance = 0;
        message.content = "我参与了一个投票";
        message.objectId = result.get_id().toString();
        message.date = new Date();
        messageDAO.save(message);
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addVoteComment(@CookieParam("u")String sessionId, VoteComment comment, @HeaderParam("voteId")String voteId) {
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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrientedMatch(@CookieParam("u")String sessionId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        User user = userDAO.loadById(userId);
    }*/
}