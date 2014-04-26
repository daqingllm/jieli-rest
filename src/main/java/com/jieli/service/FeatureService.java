package com.jieli.service;

import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.feature.match.*;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.dao.VoteResultDAO;
import com.jieli.feature.vote.entity.*;
import com.jieli.message.*;
import com.jieli.mongo.BaseDAO;
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
import java.util.*;

/**
 * 功能列表页接口
 * 包括互帮互助、默契匹配、投票列表
 * Created by YolandaLeo on 14-3-19.
 */
@Singleton
@Path("/feature")
public class FeatureService {
    private HelpDAO helpDAO = new HelpDAO();
    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(com.jieli.mongo.Collections.Comment, Comment.class);
    private MessageDAO messageDAO = new MessageDAO();
    private VoteResultDAO voteResultDAO = new VoteResultDAO();
    private MatchDAO matchDAO = new MatchDAO();
    /**
     * 获取互帮互助列表
     * @param sessionId
     * @param page
     * @param size
     * @param type 0-供给 1-需求 2-全部
     * @return
     */
    @Path("/help")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getHelpList(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int size, @QueryParam("type")int type) {
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
        if(page <= 0) {
            page = 1;
        }
        if(size <= 0) {
            size = 20;
        }
        List<SimpleHelpInfo> simpleHelpInfoList = helpDAO.getHelpInfoList(page, size, associationId, type);

        responseEntity.code = 200;
        responseEntity.body = simpleHelpInfoList;
        return Response.status(200).entity(responseEntity).build();
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
        return Response.status(200).entity(responseEntity).build();
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
        User user = userDAO.loadById(userId);
        help.setUserName(user.name);
        help.setUserFace(user.userFace);

        if(help.getType() != 0 || help.getType() != 1) {
            help.setType(0);
        }
        HelpInfo result = helpDAO.addHelp(help);
        if(result == null) {
            responseEntity.code = 1210;
            responseEntity.msg = "互帮互助帖子添加失败";
            return Response.status(200).entity(responseEntity).build();
        }
        List<String> concernedUserIds = IdentifyUtils.getConcerned(userId);
        for (String concernedUserId : concernedUserIds) {
            Message message = new Message();
            message.messageType = MessageType.FRIEND;
            message.userId = concernedUserId;
            if (help.getType() == 0) {
                message.content = "您关注的 " + IdentifyUtils.getUserName(userId) + " 发起了一条需求 " + help.getTitle();
            } else {
                message.content = "您关注的 " + IdentifyUtils.getUserName(userId) + " 参加了一条供给 " + help.getTitle();
            }
            message.addTime = new Date();
            messageDAO.save(message);
        }
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + result.get_id() + "\"}";
        return Response.status(200).entity(responseEntity).build();
    }

    @Path("/help/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteHelpInfo(@CookieParam("u") String sessionId, @QueryParam("helpId")String helpId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (helpId  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        if(StringUtils.isEmpty(userId)) {
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
        HelpInfo help = helpDAO.loadById(helpId);
        if(help == null) {
            responseEntity.code = 1201;
            responseEntity.msg = "互帮互助信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        helpDAO.deleteById(helpId);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
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
     * @return
     */
    @Path("/help/detail/comment/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addHelpComment(@CookieParam("u")String sessionId, Map<String, String> commentInfo) {
        //commentInfo内字段：content topicId commentedUserId(回复评论)
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
        String content = commentInfo.get("content");
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 3104;
            responseEntity.msg = "回复内容不能为空";
            return Response.status(200).entity(responseEntity).build();
        }
        String helpId = commentInfo.get("topicId");
        if (StringUtils.isEmpty(helpId) || !MongoUtils.isValidObjectId(helpId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadById(helpId);
//        if(helpInfo.getUserId() == userId) {
//            responseEntity.code = 1200;
//            responseEntity.msg = "不能评论自己";
//            return Response.status(403).entity(responseEntity).build();
//        }
        if (!helpInfo.getUserId().equals(userId)) {
            Message message = new Message();
            message.messageType = MessageType.COMMENT;
            message.userId = helpInfo.getUserId();
            message.content = "你发起的帮忙贴 " + helpInfo.getTitle() + " 被 " + IdentifyUtils.getUserName(IdentifyUtils.getUserId(sessionId)) + " 评论";
            message.addTime = new Date();
            messageDAO.save(message);
        }

        Comment comment = new Comment();
        comment.commentUserId = userId;
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.topicType = TopicType.Help;
        comment.addTime = new Date();
        comment.topicId = helpId;
        commentDAO.save(comment);
        CommentMessageUtil.addCommentMessage(comment);

        helpInfo.setCommentCount(helpInfo.getCommentCount() + 1);
        helpDAO.save(helpInfo);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 删除评论
     * @param helpId
     * @return
     */
    /*@Path("/help/detail/comment/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteComment(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, @QueryParam("commentIndex")Integer commentIndex){
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        // 网络延迟、多线程问题 index
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
        if(!help.getUserId().equals(userId) || !IdentifyUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(403).entity(responseEntity).build();
        }
        helpDAO.deleteComment(helpId, commentIndex);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }*/

    /**
     * 加关注
     * @param helpId
     * return HelpInfo 用于刷新更新关注数
     */
    @Path("/help/detail/comment/focus")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addFocus(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId){
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
        if (!MongoUtils.isValidObjectId(helpId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        HelpInfo helpInfo = helpDAO.loadById(helpId);
        if(helpInfo == null) {
            responseEntity.code = 1205;
            responseEntity.msg = "帖子不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        //已关注过不能再次关注
        if(helpInfo.getFocusList() != null) {
            if (helpInfo.getFocusList().contains(userId)) {
                responseEntity.code = 1209;
                responseEntity.msg = "已关注过";
                return Response.status(200).entity(responseEntity).build();
            }
        }
        //自己不能关注自己
        if(helpInfo.getUserId().equals(userId)) {
            responseEntity.code = 1200;
            responseEntity.msg = "不能关注自己";
            return Response.status(200).entity(responseEntity).build();
        }

        HelpInfo result = helpDAO.addFocus(helpId, userId);

//        Message message = new Message();
//        message.messageType = MessageType.HELP;
//        message.importance = 0;
//        message.content = "帖子已经被我回复啦";
//        message.set_id(new ObjectId(result.get_id().toString()));
//        message.addTime = new Date();
//        messageDAO.save(message);
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + result.get_id() + "\"}";
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
    public Response addTop(@CookieParam("u")String sessionId, @QueryParam("helpId")String helpId, @QueryParam("commentId")String commentId) {
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
        if (StringUtils.isEmpty(helpId) || commentId == null) {
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
        if(!help.getUserId().equals(userId) && !IdentifyUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(403).entity(responseEntity).build();
        }
        Comment comment = commentDAO.findOne("{_id:#}", new ObjectId(commentId));
        HelpInfo result = helpDAO.topComment(helpId, comment);
        if(result == null) {
            responseEntity.code = 1206;
            responseEntity.body = "置顶评论失败";
            return Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + result.get_id() + "\"}";;
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
    public Response getVoteList(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int size) {
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
        if(page <= 0) {
            page = 1;
        }
        if(size <= 0) {
            size = 20;
        }
        List<SimpleVoteInfo> voteList = voteDAO.getVoteInfoList(page, size, associationId);
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
    public Response getVoteInfo(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
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
    public Response addVote(@CookieParam("u")String sessionId, @QueryParam("force")boolean force, VoteInfo voteInfo) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(!IdentifyUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(200).entity(responseEntity).build();
        }
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

        voteInfo.setUserId(userId);
        voteInfo.setAssociationId(associationId);
        voteInfo.setAddTime(new Date());
        VoteInfo result = voteDAO.addVote(voteInfo);

        VoteResult voteResult = new VoteResult();
        voteResult.setTotalVote(0);
        Map<Integer, Integer> optionVotesInit = new HashMap<Integer, Integer>();
        for(int i = 0; i < voteInfo.getOptions().size(); i++) {
            optionVotesInit.put(i, 0);
        }
        voteResult.setOptionVotes(optionVotesInit);
        voteResult.setVoteId(result.get_id().toString());
        voteResult.setParticipants(0);
        voteResultDAO.save(voteResult);

        if (force) {
            VoteMsg voteMsg = new VoteMsg();
            voteMsg.voteId = result.get_id().toString();
            voteMsg.msg = result.getTitle() + " 投票发起";

            Send2AllTask task = new Send2AllTask(voteMsg, result.getAssociationId(), MessageType.OTHER);
            task.run();
        }


        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 编辑投票，只给管理后台用
     * @param sessionId
     * @return
     */
    @Path("/vote/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response modifyVote(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId, VoteInfo newVote) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        User user = userDAO.loadById(userId);
        if(user == null) {
            responseEntity.code = 1104;
            responseEntity.msg = "账户已被删除";
            return  Response.status(200).entity(responseEntity).build();
        }
        if(newVote == null || voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteInfo oldVote = voteDAO.loadById(voteId);
        if(newVote.getTitle() != null) {
            oldVote.setTitle(newVote.getTitle());
        }
        if(newVote.getDeadLine() != null) {
            oldVote.setDeadLine(newVote.getDeadLine());
        }
        if(newVote.getDescription() != null) {
            oldVote.setDescription(newVote.getDescription());
        }
        voteDAO.save(oldVote);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    /*@Path("/vote/deletevote")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteVote(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if(!IdentifyUtils.isAdmin(sessionId) || !IdentifyUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (voteId  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        if(StringUtils.isEmpty(userId)) {
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

        VoteInfo voteInfo = voteDAO.loadById(voteId);
        if(voteInfo == null) {
            responseEntity.code = 1201;
            responseEntity.msg = "投票信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        voteDAO.deleteById(voteId);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }*/

    /**
     * 参与投票
     * @param sessionId
     * @param vote
     * @return
     */
    @Path("/vote/commitvote")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response vote(@CookieParam("u")String sessionId, Vote vote, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(vote == null || voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentifyUtils.getUserId(sessionId);
        if(userId == null || StringUtils.isEmpty(userId)) {
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

        VoteInfo voteInfo = voteDAO.loadById(voteId);
        if(voteInfo == null) {
            responseEntity.code = 1301;
            responseEntity.msg = "投票信息不存在";
            return  Response.status(200).entity(responseEntity).build();
        }
        VoteResult voteResult = voteResultDAO.loadByVoteId(voteId);
        List<Vote> voteList = voteResult.getVoteList();
        if(voteList == null) {
            voteResultDAO.vote(vote, voteId);
        }
        else {
            for(Vote v : voteList) {
                if(v.getUserId().equals(vote.getUserId())) {
                    responseEntity.code = 1302;
                    responseEntity.body = "不能重复投票";
                    return Response.status(200).entity(responseEntity).build();
                }
            }
            voteResultDAO.vote(vote, voteId);
        }

        //触发参与投票动态
//        Message message = new Message();
//        message.messageType = MessageType.VOTE;
//        message.importance = 0;
//        message.content = "我参与了一个投票";
//        message.set_id(new ObjectId(result.get_id().toString()));
//        message.addTime = new Date();
//        messageDAO.save(message);
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + voteResult.get_id() + "\"}";;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 获取投票结果
     * @param sessionId
     * @param voteId
     * @return
     */
    @Path("vote/result")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getVoteResult(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(voteId == null || StringUtils.isEmpty(voteId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        VoteResult voteResult = voteResultDAO.loadByVoteId(voteId);
        responseEntity.code = 200;
        responseEntity.body = voteResult;
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 添加投票评论
     * @param sessionId
     * @return
     */
    @Path("/vote/comment")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addVoteComment(@CookieParam("u")String sessionId, Map<String, String> commentInfo) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        String content  = commentInfo.get("content");
        if(content == null) {
            responseEntity.code = 3104;
            responseEntity.msg = "回复内容不能为空";
            return Response.status(200).entity(responseEntity).build();
        }
        String voteId = commentInfo.get("topicId");
        if(voteId == null || !MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        Comment comment = new Comment();
        comment.topicId = voteId;
        comment.topicType = TopicType.Vote;
        comment.content = content;
        comment.commentUserId = userId;
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.addTime = new Date();
        commentDAO.save(comment);
        CommentMessageUtil.addCommentMessage(comment);
        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        return Response.status(200).entity(responseEntity).build();
    }

    /*@Path("/vote/comment")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteVoteComment(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId, @QueryParam("commentIndex")String commentIndex) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(!IdentifyUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(200).entity(responseEntity).build();
        }
        if(StringUtils.isEmpty(voteId) || StringUtils.isEmpty(commentIndex)) {
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
        List<VoteComment> commentList = voteInfo.getCommentList();
        commentList.remove(commentIndex);
        voteInfo.setCommentList(commentList);
        VoteInfo result = voteDAO.save(voteInfo);
        responseEntity.code = 200;
        responseEntity.body = result.get_id();
        return Response.status(200).entity(responseEntity).build();
    }*/

    @Path("/match")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrientedMatch(@CookieParam("u")String sessionId, @QueryParam("userId")String userId, @QueryParam("count")int count) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count == 0) {
            count = 5;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        User self = userDAO.loadById(IdentifyUtils.getUserId(sessionId));
        if (!StringUtils.isEmpty(userId) && MongoUtils.isValidObjectId(userId)) {
            if (IdentifyUtils.getUserId(sessionId).equals(userId)) {
                responseEntity.code = 6102;
                responseEntity.msg = "不能与自己匹配";
                return  Response.status(200).entity(responseEntity).build();
            }
            User user = userDAO.loadById(userId);
            if (user == null) {
                responseEntity.code = 1102;
                responseEntity.msg = "用户不存在";
                return  Response.status(200).entity(responseEntity).build();
            }
//            int score = MatchUtil.calcMatchingScore(self, user);
            MatchUtil matchUtil = new MatchUtil(self, user);
            Match match = matchUtil.getMatch();
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            responseEntity.code = 200;
            responseEntity.body = display;
            return  Response.status(200).entity(responseEntity).build();
        }

        MatchTask task = new MatchTask(count, self);
        List<Match> matches = task.getResult();
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            results.add(display);
        }

        responseEntity.code = 200;
        responseEntity.body = results;
        return Response.status(200).entity(responseEntity).build();
    }

    @Path("/match")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getOrientedMatchs(@CookieParam("u")String sessionId, List<String> userIds) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (CollectionUtils.isEmpty(userIds)) {
            responseEntity.code = 6104;
            responseEntity.msg = "用户列表为空";
            return Response.status(200).entity(responseEntity).build();
        }
        User self = userDAO.loadById(IdentifyUtils.getUserId(sessionId));
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (String userId : userIds) {
            if (!StringUtils.isEmpty(userId) && MongoUtils.isValidObjectId(userId)) {
                if (IdentifyUtils.getUserId(sessionId).equals(userId)) {
                    responseEntity.code = 6102;
                    responseEntity.msg = "不能与自己匹配";
                    return  Response.status(200).entity(responseEntity).build();
                }
                User user = userDAO.loadById(userId);
                if (user == null) {
                    responseEntity.code = 1102;
                    responseEntity.msg = "用户不存在";
                    return  Response.status(200).entity(responseEntity).build();
                }
                MatchUtil matchUtil = new MatchUtil(self, user);
                Match match = matchUtil.getMatch();
                MatchDisplay display = new MatchDisplay();
                User user1 = userDAO.loadById(match.userId1);
                display.userId1 = user1.get_id().toString();
                display.name1 = user1.name;
                display.userFace1 = user1.userFace;
                User user2 = userDAO.loadById(match.userId2);
                display.userId2 = user2.get_id().toString();
                display.name2 = user2.name;
                display.userFace2 = user2.userFace;
                display.score = match.score;
                display.infos = match.matchInfos;
                results.add(display);
            }
        }
        responseEntity.code = 200;
        responseEntity.body = results;
        return Response.status(200).entity(responseEntity).build();
    }

    @Path("/topmatch")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTopMatch(@CookieParam("u")String sessionId, @QueryParam("count")int count) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count == 0) {
            count = 5;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Iterable<Match> matches = matchDAO.getTopMatch(count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            results.add(display);
        }

        responseEntity.code = 200;
        responseEntity.body = results;
        return  Response.status(200).entity(responseEntity).build();
    }

    @Path("/admintopmatch")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getTopMatchByUserId(@CookieParam("u")String sessionId, @QueryParam("userId")String userId, @QueryParam("count")int count) {
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Iterable<Match> matches = matchDAO.getTopMatchByUserId(userId, count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            results.add(display);
        }

        responseEntity.code = 200;
        responseEntity.body = results;
        return  Response.status(200).entity(responseEntity).build();
    }
}