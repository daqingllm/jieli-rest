package com.jieli.service;

import com.jieli.comment.Comment;
import com.jieli.comment.CommentUserInfoUtil;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.dao.HelpDynamicDAO;
import com.jieli.feature.help.entity.*;
import com.jieli.feature.match.*;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.dao.VoteDynamicDAO;
import com.jieli.feature.vote.dao.VoteResultDAO;
import com.jieli.feature.vote.entity.*;
import com.jieli.message.*;
import com.jieli.mongo.BaseDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.FriendMsg;
import com.jieli.user.entity.User;
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
    private HelpDynamicDAO helpDynamicDAO = new HelpDynamicDAO();
    private VoteDynamicDAO voteDynamicDAO = new VoteDynamicDAO();
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        String associationId = IdentityUtils.getAssociationId(sessionId);
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
        if(!IdentityUtils.isValidate(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (help  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentityUtils.getAssociationId(sessionId);
        String userId = IdentityUtils.getUserId(sessionId);
        help.setAssociationId(associationId);
        help.setAddTime(new Date());
        help.setAttentionNum(0);
        help.setUserId(userId);
        User user = userDAO.loadById(userId);
        help.setUserName(user.name);
        help.setUserFace(user.userFace);

        if(help.getType() != 0 && help.getType() != 1) {
            help.setType(0);
        }
        HelpInfo result = helpDAO.addHelp(help);
        if(result == null) {
            responseEntity.code = 1210;
            responseEntity.msg = "互帮互助帖子添加失败";
            return Response.status(200).entity(responseEntity).build();
        }
        insertHelpDynamic(userId, result.get_id().toString(), HelpDynamicType.SPONSER);

        List<String> concernedUserIds = IdentityUtils.getConcerned(userId);
        for (String concernedUserId : concernedUserIds) {
            Message message = new Message();
            message.messageType = MessageType.FRIEND;
            message.userId = concernedUserId;

            FriendMsg friendMsg = new FriendMsg();
            friendMsg.topicType = TopicType.Help;
            friendMsg.topicId = help.get_id().toString();
            if (help.getType() == 0) {
                friendMsg.msg = "您关注的 " + IdentityUtils.getUserName(userId) + " 发起了一条需求 " + help.getTitle();
            } else {
                friendMsg.msg = "您关注的 " + IdentityUtils.getUserName(userId) + " 参加了一条供给 " + help.getTitle();
            }
            message.content = friendMsg;
            message.addTime = new Date();
            messageDAO.save(message);
        }
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + result.get_id() + "\"}";
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 更新互帮互助
     * @param sessionId
     * @param newHelp
     * @return
     */
    @Path("/help/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateHelpInfo(@CookieParam("u") String sessionId, @QueryParam("helpId")String helpId, HelpInfo newHelp) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(newHelp == null || helpId == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
        HelpInfo oldHelp = helpDAO.loadById(helpId);
        if(!oldHelp.getUserId().equals(userId) && !IdentityUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "非自己互帮互助帖，不能修改";
            return Response.status(200).entity(responseEntity).build();
        }
        if(newHelp.getType() == 0 || newHelp.getType() == 1) {
            oldHelp.setType(newHelp.getType());
        }
        if(newHelp.getTitle() != null) {
            oldHelp.setTitle(newHelp.getTitle());
        }
        if(newHelp.getContent() != null) {
            oldHelp.setContent(newHelp.getContent());
        }
        helpDAO.save(oldHelp);
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + oldHelp.get_id() + "\"}";
        return Response.status(200).entity(responseEntity).build();
    }

    @Path("/help/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteHelpInfo(@CookieParam("u") String sessionId, List<String> helpIdList) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (helpIdList  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
        if(StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        for(String helpId : helpIdList) {
            HelpInfo help = helpDAO.loadById(helpId);
            if(help == null) {
                responseEntity.code = 1201;
                responseEntity.msg = "互帮互助信息不存在, id=" + helpId;
                return  Response.status(200).entity(responseEntity).build();
            }
            if (IdentityUtils.isAdmin(sessionId) || IdentityUtils.getUserId(sessionId).equals(help.getUserId())) {
                removeHelpDynamic(userId, helpId, HelpDynamicType.SPONSER);
                helpDAO.deleteById(helpId);
            } else {
                responseEntity.code = 1203;
                responseEntity.msg = "权限不足, id=" + helpId;
                return  Response.status(200).entity(responseEntity).build();
            }
        }

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
        if(!IdentityUtils.isValidate(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        if (helpInfo == null) {
            responseEntity.code = 3000;
            responseEntity.msg = "帖子不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Comment comment = new Comment();
        comment.commentUserId = userId;
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.topicType = TopicType.Help;
        comment.addTime = new Date();
        comment.topicId = helpId;
        comment.topicTitle = helpInfo.getTitle();
        commentDAO.save(comment);
        CommentMessageUtil.addCommentMessage(comment);

        if (!helpInfo.getUserId().equals(userId)) {
            CommentMessageUtil.addCommentAuthorMessage(comment, helpInfo.getUserId());
        }

        helpInfo.setCommentCount(helpInfo.getCommentCount() + 1);
        helpDAO.save(helpInfo);

        insertHelpDynamic(userId, helpId, HelpDynamicType.REPLY);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        responseEntity.body = comment;
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        // 网络延迟、多线程问题 index
        String userId = IdentityUtils.getUserId(sessionId);
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
        if(!help.getUserId().equals(userId) || !IdentityUtils.isAdmin(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        if(help == null || !MongoUtils.isValidObjectId(commentId)) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }
        if(!help.getUserId().equals(userId) && !IdentityUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(403).entity(responseEntity).build();
        }
        Comment top = getTopComment(help, commentId);
        if (top == null) {

            Comment comment = commentDAO.findOne("{_id:#}", new ObjectId(commentId));
            if (comment == null) {
                responseEntity.code = 1003;
                responseEntity.msg = "评论不存在";
                return Response.status(200).entity(responseEntity).build();
            }
            comment.commentUserInfo = CommentUserInfoUtil.generate(comment.commentUserId);
            comment.commentedUserInfo = CommentUserInfoUtil.generate(comment.commentedUserId);
            help.getTopCommentList().add(comment);
            helpDAO.save(help);
            responseEntity.code = 200;
            responseEntity.msg = "评论已置顶";
            responseEntity.body = help;
            return Response.status(200).entity(responseEntity).build();
        } else {
            help.getTopCommentList().remove(top);
            helpDAO.save(help);
            responseEntity.code = 200;
            responseEntity.msg = "置顶已取消";
            responseEntity.body = help;
            return Response.status(200).entity(responseEntity).build();
        }

    }

    private Comment getTopComment(HelpInfo helpInfo, String commentId) {
        if (CollectionUtils.isEmpty(helpInfo.getTopCommentList())) {
            helpInfo.setTopCommentList(new ArrayList<Comment>());
            return null;
        }
        for (Comment comment : helpInfo.getTopCommentList()) {
            if (comment == null) {
                continue;
            }
            if (comment.get_id().toString().equals(commentId)) {
                return comment;
            }
        }
        return null;
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        String associationId = IdentityUtils.getAssociationId(sessionId);
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
        if(!IdentityUtils.isValidate(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(!IdentityUtils.isAdmin(sessionId)) {
            responseEntity.code = 1403;
            responseEntity.msg = "权限不足";
            return Response.status(200).entity(responseEntity).build();
        }
        if(voteInfo == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        String associationId = IdentityUtils.getAssociationId(sessionId);
        String userId = IdentityUtils.getUserId(sessionId);
        if(voteInfo.getOptions() == null || voteInfo.getOptions().isEmpty()) {
            responseEntity.code = 1205;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }

        voteInfo.setUserId(userId);
        voteInfo.setAssociationId(associationId);
        voteInfo.setAddTime(new Date());
        Map<Integer, Integer> optionVotesInit = new HashMap<Integer, Integer>();
        for(int i = 0; i < voteInfo.getOptions().size(); i++) {
            optionVotesInit.put(i, 0);
        }
        voteInfo.setOptionVotes(optionVotesInit);
        VoteInfo result = voteDAO.addVote(voteInfo);

        VoteResult voteResult = new VoteResult();
        voteResult.setTotalVote(0);
        voteResult.setOptionVotes(optionVotesInit);
        voteResult.setVoteId(result.get_id().toString());
        voteResult.setParticipants(0);
        voteResultDAO.save(voteResult);

        if (voteInfo.isForce()) {
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
        if(!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
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
        //过期投票不可编辑
        if(!oldVote.getDeadLine().after(new Date())) {
            responseEntity.code = 1115;
            responseEntity.body = "投票已过期";
            return Response.status(200).entity(responseEntity).build();
        }
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

    @Path("/vote/deletevote")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteVote(@CookieParam("u")String sessionId, List<String> voteIdList) {
        if(!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (voteIdList  == null) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        for(String v : voteIdList) {
            if (!MongoUtils.isValidObjectId(v)) {
                responseEntity.code = 1105;
                responseEntity.msg = "参数Id无效";
                return Response.status(200).entity(responseEntity).build();
            }
        }

        String userId = IdentityUtils.getUserId(sessionId);
        if(StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }

        for(String voteId : voteIdList) {
            voteDAO.deleteById(voteId);
            voteResultDAO.deleteByVoteId(voteId);
        }
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(vote == null || voteId == null || StringUtils.isEmpty(voteId) || CollectionUtils.isEmpty(vote.getVoteIndex())) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(voteId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
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
        vote.setUserId(IdentityUtils.getUserId(sessionId));
        VoteResult voteResult = voteResultDAO.loadByVoteId(voteId);
        List<Vote> voteList = voteResult.getVoteList();
        VoteResult afterVote = null;
        if(voteList == null) {
            afterVote = voteResultDAO.vote(vote, voteId);
        }
        else {
            for(Vote v : voteList) {
                if(v.getUserId().equals(vote.getUserId())) {
                    responseEntity.code = 1302;
                    responseEntity.body = "不能重复投票";
                    return Response.status(200).entity(responseEntity).build();
                }
            }
            afterVote = voteResultDAO.vote(vote, voteId);
        }
        voteInfo.setOptionVotes(afterVote.getOptionVotes());
        voteInfo.setParticipants(afterVote.getParticipants());
        voteInfo.setTotalVote(afterVote.getTotalVote());
        voteDAO.save(voteInfo);

        insertVoteDynamic(userId, voteId, VoteDynamicType.JOIN);

        //触发参与投票动态
//        Message message = new Message();
//        message.messageType = MessageType.VOTE;
//        message.importance = 0;
//        message.content = "我参与了一个投票";
//        message.set_id(new ObjectId(result.get_id().toString()));
//        message.addTime = new Date();
//        messageDAO.save(message);
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + voteResult.get_id() + "\"}";
        return Response.status(200).entity(responseEntity).build();
    }

    /**
     * 获取投票结果
     * @param sessionId
     * @param voteId
     * @return
     */
    @Path("/vote/result")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getVoteResult(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentityUtils.isValidate(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentityUtils.getUserId(sessionId);
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
        VoteInfo voteInfo = voteDAO.loadById(voteId);
        if (voteInfo == null) {
            responseEntity.code = 3000;
            responseEntity.msg = "投票不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Comment comment = new Comment();
        comment.topicId = voteId;
        comment.topicType = TopicType.Vote;
        comment.topicTitle = voteInfo.getTitle();
        comment.content = content;
        comment.commentUserId = userId;
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.addTime = new Date();
        commentDAO.save(comment);
        CommentMessageUtil.addCommentMessage(comment);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        responseEntity.body = comment;
        return Response.status(200).entity(responseEntity).build();
    }

    /*@Path("/vote/comment")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteVoteComment(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId, @QueryParam("commentIndex")String commentIndex) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if(!IdentityUtils.isAdmin(sessionId)) {
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count == 0) {
            count = 5;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        User self = userDAO.loadById(IdentityUtils.getUserId(sessionId));
        if (!StringUtils.isEmpty(userId) && MongoUtils.isValidObjectId(userId)) {
            if (IdentityUtils.getUserId(sessionId).equals(userId)) {
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
            MatchDisplay display = makeDisplay(match);
            responseEntity.code = 200;
            responseEntity.body = display;
            return  Response.status(200).entity(responseEntity).build();
        }

        MatchTask task = new MatchTask(count, self);
        List<Match> matches = task.getResult();
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = makeDisplay(match);
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (CollectionUtils.isEmpty(userIds)) {
            responseEntity.code = 6104;
            responseEntity.msg = "用户列表为空";
            return Response.status(200).entity(responseEntity).build();
        }
        User self = userDAO.loadById(IdentityUtils.getUserId(sessionId));
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (String userId : userIds) {
            if (!StringUtils.isEmpty(userId) && MongoUtils.isValidObjectId(userId)) {
                if (IdentityUtils.getUserId(sessionId).equals(userId)) {
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
                MatchDisplay display = makeDisplay(match);
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
        if(!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        if (count == 0) {
            count = 5;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Iterable<Match> matches = matchDAO.getTopMatch(count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = makeDisplay(match);
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
        if(!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Iterable<Match> matches = matchDAO.getTopMatchByUserId(userId, count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = makeDisplay(match);
            results.add(display);
        }

        responseEntity.code = 200;
        responseEntity.body = results;
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/help/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findHelpDynamics(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count) {
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
        List<HelpDynamicShow> result = new ArrayList<HelpDynamicShow>();
        String userId = IdentityUtils.getUserId(sessionId);
        List<HelpDynamicShow> infos = helpDynamicDAO.findUserHelp(userId);
        if (page*count <= infos.size()) {
            result = infos.subList((page-1)*count, page*count);
        } else if ((page-1)*count < infos.size()) {
            result = infos.subList((page-1)*count, infos.size());
        } else {
            responseEntity.msg = "无数据";
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.body = generateHelpDynamics(result);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/help/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findHelpDynamicsByUserId(@CookieParam("u")String sessionId,@QueryParam("userId")String userId, @QueryParam("page")int page, @QueryParam("size")int count) {
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
        List<HelpDynamicShow> result = new ArrayList<HelpDynamicShow>();
        List<HelpDynamicShow> infos = helpDynamicDAO.findUserHelp(userId);
        if (page*count <= infos.size()) {
            result = infos.subList((page-1)*count, page*count);
        } else if ((page-1)*count < infos.size()) {
            result = infos.subList((page-1)*count, infos.size());
        } else {
            responseEntity.msg = "无数据";
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.body = generateHelpDisplay(result);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/vote/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findVoteDynamics(@CookieParam("u")String sessionId, @QueryParam("page")int page, @QueryParam("size")int count) {
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
        List<VoteDynamicShow> result = new ArrayList<VoteDynamicShow>();
        String userId = IdentityUtils.getUserId(sessionId);
        List<VoteDynamicShow> infos = voteDynamicDAO.findUserVote(userId);
        if (page*count <= infos.size()) {
            result = infos.subList((page-1)*count, page*count);
        } else if ((page-1)*count < infos.size()) {
            result = infos.subList((page-1)*count, infos.size());
        } else {
            responseEntity.msg = "无数据";
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.body = generateVoteDynamics(result);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/vote/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response findVoteDynamicsByUserId(@CookieParam("u")String sessionId,@QueryParam("userId")String userId, @QueryParam("page")int page, @QueryParam("size")int count) {
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
        List<VoteDynamicShow> result = new ArrayList<VoteDynamicShow>();
        List<VoteDynamicShow> infos = voteDynamicDAO.findUserVote(userId);
        if (page*count <= infos.size()) {
            result = infos.subList((page-1)*count, page*count);
        } else if ((page-1)*count < infos.size()) {
            result = infos.subList((page-1)*count, infos.size());
        } else {
            responseEntity.msg = "无数据";
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.body = generateVoteDisplay(result);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    private Map<HelpDynamicType, List<HelpInfo>> generateHelpDynamics(List<HelpDynamicShow> infos) {
        Map<HelpDynamicType, List<HelpInfo>> result = new HashMap<HelpDynamicType, List<HelpInfo>>();
        for (HelpDynamicShow info : infos) {
            HelpDynamicType type = info.type;
            if (result.get(type) == null) {
                List<HelpInfo> activities = new ArrayList<HelpInfo>();
                result.put(type, activities);
            }
            HelpInfo helpInfo = helpDAO.loadById(info.helpId);
            result.get(type).add(helpInfo);
        }

        return result;
    }

    private List<HelpDynamicDisplay> generateHelpDisplay(List<HelpDynamicShow> infos) {
        List<HelpDynamicDisplay> displays = new ArrayList<HelpDynamicDisplay>();
        for (HelpDynamicShow info : infos) {
            HelpDynamicDisplay display = new HelpDynamicDisplay();
            display.helpId = info.helpId;
            HelpInfo helpInfo = helpDAO.loadById(info.helpId);
            if (helpInfo == null) {
                continue;
            }
            display.type = info.type;
            display.title = helpInfo.getTitle();
            display.time = info.time;
            displays.add(display);
        }
        return displays;
    }

    private Map<VoteDynamicType, List<VoteInfo>> generateVoteDynamics(List<VoteDynamicShow> infos) {
        Map<VoteDynamicType, List<VoteInfo>> result = new HashMap<VoteDynamicType, List<VoteInfo>>();
        for (VoteDynamicShow info : infos) {
            VoteDynamicType type = info.type;
            if (result.get(type) == null) {
                List<VoteInfo> activities = new ArrayList<VoteInfo>();
                result.put(type, activities);
            }
            VoteInfo voteInfo = voteDAO.loadById(info.voteId);
            result.get(type).add(voteInfo);
        }

        return result;
    }

    private List<VoteDynamicDisplay> generateVoteDisplay(List<VoteDynamicShow> infos) {
        List<VoteDynamicDisplay> displays = new ArrayList<VoteDynamicDisplay>();
        for (VoteDynamicShow info : infos) {
            VoteDynamicDisplay display = new VoteDynamicDisplay();
            display.voteId = info.voteId;
            VoteInfo voteInfo = voteDAO.loadById(info.voteId);
            if (voteInfo == null) {
                continue;
            }
            display.type = info.type;
            display.title = voteInfo.getTitle();
            display.time = info.time;
            displays.add(display);
        }
        return displays;
    }

    private MatchDisplay makeDisplay(Match match) {
        MatchDisplay display = new MatchDisplay();
        User user1 = userDAO.loadById(match.userId1);
        display.userId1 = user1.get_id().toString();
        display.name1 = user1.name;
        display.userFace1 = user1.userFace;
        display.phone1 = user1.phone;
        User user2 = userDAO.loadById(match.userId2);
        display.userId2 = user2.get_id().toString();
        display.name2 = user2.name;
        display.userFace2 = user2.userFace;
        display.phone2 = user2.phone;

        display.score = match.score;
        display.infos = match.matchInfos;

        return display;
    }

    private void insertHelpDynamic(String userId, String topicId, HelpDynamicType type) {
        HelpDynamic helpDynamic = helpDynamicDAO.findByUserId(userId);
        if (helpDynamic == null) {
            helpDynamic = new HelpDynamic();
            helpDynamic.userId = userId;
        }
        HelpDynamicShow info = new HelpDynamicShow();
        info.helpId = topicId;
        info.type = type;
        info.time = new Date();
        if (helpDynamic.infos.contains(info)) {
            return;
        }
        helpDynamic.infos.add(info);
        helpDynamicDAO.save(helpDynamic);
    }

    private void insertVoteDynamic(String userId, String topicId, VoteDynamicType type) {
        VoteDynamic voteDynamic = voteDynamicDAO.findByUserId(userId);
        if (voteDynamic == null) {
            voteDynamic = new VoteDynamic();
            voteDynamic.userId = userId;
        }
        VoteDynamicShow info = new VoteDynamicShow();
        info.voteId = topicId;
        info.type = type;
        info.time = new Date();
        if (voteDynamic.infos.contains(info)) {
            return;
        }
        voteDynamic.infos.add(info);
        voteDynamicDAO.save(voteDynamic);
    }

    private void removeHelpDynamic(String userId, String topicId, HelpDynamicType type) {
        HelpDynamic helpDynamic = helpDynamicDAO.findByUserId(userId);
        HelpDynamicShow info = new HelpDynamicShow();
        info.helpId = topicId;
        info.type = type;
        if (helpDynamic != null && helpDynamic.infos.contains(info)) {
            helpDynamic.infos.remove(info);
            helpDynamicDAO.save(helpDynamic);
        }
    }
}