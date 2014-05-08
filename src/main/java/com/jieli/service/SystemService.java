package com.jieli.service;

import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.FeedbackDAO;
import com.jieli.common.entity.Feedback;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
