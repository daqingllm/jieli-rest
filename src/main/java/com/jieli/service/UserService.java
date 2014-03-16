package com.jieli.service;

import com.jieli.dao.DirectoryDAO;
import com.jieli.dao.UserDAO;
import com.jieli.entity.common.ResponseEntity;
import com.jieli.entity.user.Directory;
import com.jieli.entity.user.Friend;
import com.jieli.entity.user.User;
import com.jieli.entity.user.UserBasicInfo;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentifyUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
@Path("/user")
public class UserService {

    private UserDAO userDAO = new UserDAO();
    private DirectoryDAO directoryDAO = new DirectoryDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadUser(@HeaderParam("sessionId")String sessionId, @QueryParam("userId")String userId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId) || !StringUtils.isNumeric(userId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }

        int uid = Integer.parseInt(userId);
        User user = userDAO.loadById(uid);
        if (user == null) {
            responseEntity.code = 1102;
            responseEntity.msg = "用户不存在";
            return  Response.status(200).entity(responseEntity).build();
        }

        responseEntity.code = 200;
        responseEntity.body = user;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadSelf(@HeaderParam("sessionId")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        int userId = IdentifyUtils.getUserId(sessionId);
        if (userId < 0) {
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
    public Response editSelf(@HeaderParam("sessionId")String sessionId, User user) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        int userId = IdentifyUtils.getUserId(sessionId);
        user.id = userId;
        userDAO.updateById(user);
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/directory")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadDirectory(@HeaderParam("sessionId")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        int userId = IdentifyUtils.getUserId(sessionId);
        Directory directory = directoryDAO.loadByUserId(userId);
        if (directory == null || CollectionUtils.isEmpty(directory.content)) {
            responseEntity.code = 200;
            responseEntity.msg = "无记录";
            return Response.status(200).entity(responseEntity).build();
        }

        List<UserBasicInfo> baseUsers = new ArrayList<UserBasicInfo>();
        for (Friend friend : directory.content) {
            User user = userDAO.loadById(friend.userId);
            if (user == null) {
                continue;
            }

            UserBasicInfo baseUser = new UserBasicInfo();
            baseUser.userId = friend.userId;
            baseUser.group = friend.group;
            baseUser.special = friend.special;
            baseUser.name = user.name;
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
    public Response upsertFriend(@HeaderParam("sessionId")String sessionId, Friend friend) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        int userId = IdentifyUtils.getUserId(sessionId);
        directoryDAO.upsertFriend(userId, friend);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/directory/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteFriend(@HeaderParam("sessionId")String sessionId, @QueryParam("userId")String userId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId) || !StringUtils.isNumeric(userId)) {
            responseEntity.code = 1101;
            responseEntity.msg = "参数错误";
            return Response.status(200).entity(responseEntity).build();
        }

        int uid = IdentifyUtils.getUserId(sessionId);
        directoryDAO.deleteFriend(uid, Integer.parseInt(userId));
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }
}
