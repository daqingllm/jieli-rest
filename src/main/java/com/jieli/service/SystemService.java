package com.jieli.service;

import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.dao.FeedbackDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.Feedback;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.common.entity.SystemInfo;
import com.jieli.mongo.BaseDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.IdentityUtils;
import com.jieli.util.PasswordGenerator;
import com.jieli.util.SmsUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-7
 * Time: 下午9:04
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/sys")
public class SystemService {

    UserDAO userDAO = new UserDAO();

    AccountDAO accountDAO = new AccountDAO();

    FeedbackDAO feedbackDAO = new FeedbackDAO();

    AssociationDAO associationDAO = new AssociationDAO();

    BaseDAO<SystemInfo> systemDAO = new BaseDAO<SystemInfo>("systeminfo", SystemInfo.class);

    @POST
    @Path("/feedback")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response postFeedback(@CookieParam("u")String sessionId, String content) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        Feedback feedback = new Feedback();
        feedback.userId = IdentityUtils.getUserId(sessionId);
        feedback.name = IdentityUtils.getUserName(feedback.userId);
        feedback.associationId = IdentityUtils.getAssociationId(sessionId);
        feedback.associationName = associationDAO.loadById(feedback.associationId).name;
        feedback.date = new Date();
        feedback.Content = content;
        feedbackDAO.save(feedback);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/feedback")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getFeedback(@CookieParam("u")String sessionId, @QueryParam("associationId")String associationId) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();

        String assoId = IdentityUtils.getAssociationId(sessionId);
        if (StringUtils.isEmpty(assoId)) {
            if (StringUtils.isEmpty(associationId)) {
                Iterable<Feedback> feedbacks = feedbackDAO.loadAll();
                responseEntity.code = 200;
                responseEntity.body = feedbacks;
                return Response.status(200).entity(responseEntity).build();
            }
            Iterable<Feedback> feedbacks = feedbackDAO.loadByAssociationId(associationId);
            responseEntity.code = 200;
            responseEntity.body = feedbacks;
            return Response.status(200).entity(responseEntity).build();
        }
        Iterable<Feedback> feedbacks = feedbackDAO.loadByAssociationId(assoId);
        responseEntity.code = 200;
        responseEntity.body = feedbacks;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/aboutSystem")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertSystemInfo(@CookieParam("u")String sessionId, String content) {
        if (!IdentityUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 10;
            responseEntity.msg = "缺少内容";
            return Response.status(200).entity(responseEntity).build();
        }
        SystemInfo info = systemDAO.findOne("{}");
        if (info == null) {
            info = new SystemInfo();
        }
        info.aboutSystem = content;
        systemDAO.save(info);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/aboutSystem")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadSystemInfo(@CookieParam("u")String sessionId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String content = "尚无系统信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info != null && StringUtils.isNotEmpty(info.aboutSystem)) {
            content = info.aboutSystem;
        }
        responseEntity.code = 200;
        responseEntity.body = content;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/aboutJieli")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertJieliInfo(@CookieParam("u")String sessionId, String content) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 10;
            responseEntity.msg = "缺少内容";
            return Response.status(200).entity(responseEntity).build();
        }
        SystemInfo info = systemDAO.findOne("{}");
        if (info == null) {
            info = new SystemInfo();
        }
        info.aboutJieli = content;
        systemDAO.save(info);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/aboutJieli")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadJieliInfo(@CookieParam("u")String sessionId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String content = "尚无系统信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info != null && StringUtils.isNotEmpty(info.aboutJieli)) {
            content = info.aboutJieli;
        }
        responseEntity.code = 200;
        responseEntity.body = content;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/sms")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response sendPassword(@QueryParam("name")String name, @QueryParam("phone")String phone) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(name)) {
            responseEntity.code = 101;
            responseEntity.msg = "姓名或手机号为空";
            return Response.status(200).entity(responseEntity).build();
        }
        User user = userDAO.findLoginUser(name, phone);
        if (user == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        String newPassword = PasswordGenerator.getRandomString(6);
        Account account = accountDAO.loadByUserId(user.get_id().toString());
//        String password = account.password;
        account.password = newPassword;
        accountDAO.save(account);
        try {
            SmsUtils.sendPassword(phone, newPassword);
        } catch (IOException e) {
            responseEntity.code = 102;
            responseEntity.msg = "验证码发送失败";
            return Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

}
