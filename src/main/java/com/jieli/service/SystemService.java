package com.jieli.service;

import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.FeedbackDAO;
import com.jieli.common.entity.Feedback;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.common.entity.SystemInfo;
import com.jieli.mongo.BaseDAO;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        feedbackDAO.save(feedback);

        responseEntity.code = 200;
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
        if (StringUtils.isNotEmpty(info.aboutSystem)) {
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
        if (StringUtils.isNotEmpty(info.aboutJieli)) {
            content = info.aboutJieli;
        }
        responseEntity.code = 200;
        responseEntity.body = content;
        return Response.status(200).entity(responseEntity).build();
    }

}
