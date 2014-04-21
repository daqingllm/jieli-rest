package com.jieli.service;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.match.Match;
import com.jieli.feature.match.MatchDisplay;
import com.jieli.feature.match.MatchMsg;
import com.jieli.feature.match.MatchTask;
import com.jieli.message.Message;
import com.jieli.message.MessageDAO;
import com.jieli.message.MessageType;
import com.jieli.user.dao.DirectoryDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.Directory;
import com.jieli.user.entity.Friend;
import com.jieli.user.entity.User;
import com.jieli.user.entity.UserBasicInfo;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/user")
public class UserService {

    private AccountDAO accountDAO = new AccountDAO();
    private UserDAO userDAO = new UserDAO();
    private DirectoryDAO directoryDAO = new DirectoryDAO();
    private MessageDAO messageDAO = new MessageDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadUser(@CookieParam("u")String sessionId, @QueryParam("userId")String userId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(userId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }

        User user = userDAO.loadById(userId);
        if (user == null) {
            responseEntity.code = 1102;
            responseEntity.msg = "用户不存在";
            return  Response.status(200).entity(responseEntity).build();
        }

        responseEntity.code = 200;
        responseEntity.body = user;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response editUser(@CookieParam("u")String sessionId, @QueryParam("userId")String userId, User user) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(Account.class);
        ResponseEntity responseEntity = new ResponseEntity();
        if (account == null) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        if (account.state.value() >= IdentifyUtils.getState(sessionId).value()) {
            return Response.status(403).build();
        }

        user.set_id(new ObjectId(userId));
        userDAO.save(user);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadSelf(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }

        User user = userDAO.loadById(userId);
        if (user == null) {
            responseEntity.code = 1104;
            responseEntity.msg = "账户已被删除";
            return  Response.status(200).entity(responseEntity).build();
        }

        responseEntity.code = 200;
        responseEntity.body = user;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response editSelf(@CookieParam("u")String sessionId, @QueryParam("first")boolean first, User user) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        user.set_id(new ObjectId(userId));
        userDAO.update(user);

        if (first) {
            String name = IdentifyUtils.getUserName(userId);
            MatchTask matchTask = new MatchTask(5, user);
            List<Match> result = matchTask.getResult();
            for (Match match : result) {
                MatchMsg matchMsg = new MatchMsg();

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
                matchMsg.display = display;
                if (match.score < 100) {
                    matchMsg.msg = "您和 " + name + " 的匹配值达到 " + match.score + " ，查看详情";
                } else {
                    matchMsg.msg = "您和 " + name + " 完全匹配，查看详情";
                }

                String userId2match = match.userId2;
                Message message = new Message();
                message.messageType = MessageType.MATCHING;
                message.userId = userId2match;
                message.addTime = new Date();
                message.content = matchMsg;
                messageDAO.save(message);
            }
        }

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/directory")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadDirectory(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        Directory directory = directoryDAO.loadByUserId(userId);
        List<UserBasicInfo> baseUsers = new ArrayList<UserBasicInfo>();
        List<String> friendIds = new ArrayList<String>();

        if (directory != null && !CollectionUtils.isEmpty(directory.content)) {
            for (Friend friend : directory.content) {
                User user = userDAO.loadById(friend.userId);
                if (user == null) {
                    continue;
                }
                friendIds.add(user.get_id().toString());

                UserBasicInfo baseUser = new UserBasicInfo();
                baseUser.userId = friend.userId;
                baseUser.special = true;
                baseUser.name = user.name;
                baseUser.group = user.group;
                baseUser.identity = user.identity;
                baseUser.score = user.score;
                baseUser.sex = user.sex;
                baseUser.userFace = user.userFace;

                baseUsers.add(baseUser);
            }
        }

        Iterable<User> allUsers = userDAO.loadAll(IdentifyUtils.getAssociationId(sessionId));
        for (User user : allUsers) {
            if (friendIds.contains(user.get_id().toString())) {
                continue;
            }
            UserBasicInfo baseUser = new UserBasicInfo();
            baseUser.userId = user.get_id().toString();
            baseUser.special = false;
            baseUser.name = user.name;
            baseUser.group = user.group;
            baseUser.identity = user.identity;
            baseUser.score = user.score;
            baseUser.sex = user.sex;
            baseUser.userFace = user.userFace;

            baseUsers.add(baseUser);
        }

        responseEntity.code = 200;
        responseEntity.body = baseUsers;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/directory")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertFriend(@CookieParam("u")String sessionId, Friend friend) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        if (friend.userId.equals(userId)) {
            responseEntity.code = 1107;
            responseEntity.msg = "不能添加自己";
            return Response.status(200).entity(responseEntity).build();
        }
        directoryDAO.upsertFriend(userId, friend);

        //被关注者添加被关注
        Directory friendDirectory = directoryDAO.loadByUserId(friend.userId);
        if (friendDirectory == null) {
            friendDirectory = new Directory();
            friendDirectory.userId = friend.userId;
        }
        if (friendDirectory.concerned == null) {
            friendDirectory.concerned = new ArrayList<String>();
        }
        friendDirectory.concerned.add(userId);
        directoryDAO.save(friendDirectory);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/directory/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteFriend(@CookieParam("u")String sessionId, @QueryParam("friendId")String friendId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(friendId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!MongoUtils.isValidObjectId(friendId)) {
            responseEntity.code = 1105;
            responseEntity.msg = "参数Id无效";
            return Response.status(200).entity(responseEntity).build();
        }

        String uid = IdentifyUtils.getUserId(sessionId);
        directoryDAO.deleteFriend(uid, friendId);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }
}
