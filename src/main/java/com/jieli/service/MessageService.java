package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.Message;
import com.jieli.message.MessageDAO;
import com.jieli.message.MessageType;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/message")
public class MessageService {

    private MessageDAO messageDAO = new MessageDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMessage(@CookieParam("u")String sessionId, @QueryParam("force")boolean force) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentityUtils.getUserId(sessionId);
        if (force) {
            Iterable<Message> messages = messageDAO.getForceMessages(userId);
            responseEntity.code = 200;
            responseEntity.body = messages;
            return Response.status(200).entity(responseEntity).build();
        }
        Iterable<Message> messages = messageDAO.getMessagesByUserId(userId);
        responseEntity.code = 200;
        responseEntity.body = messages;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMessageCount(@CookieParam("u")String sessionId) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentityUtils.getUserId(sessionId);
        Iterable<Message> messages = messageDAO.getMessagesByUserId(userId);
        int count = 0;
        int commentCount = 0;
        for (Message message : messages) {
            if (message.messageType == MessageType.COMMENT) {
                commentCount++;
            }
            count ++;
        }
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("total", count);
        resultMap.put("comment", commentCount);
        responseEntity.code = 200;
        responseEntity.body = resultMap;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response consume(@CookieParam("u")String sessionId, List<String> messageIds) {
        for (String messageId : messageIds) {
            Message message = messageDAO.loadById(messageId);
            if (message == null) continue;
            message.read = true;
            messageDAO.save(message);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }
}
