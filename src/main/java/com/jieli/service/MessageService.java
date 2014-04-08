package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.Message;
import com.jieli.message.MessageDAO;
import com.jieli.util.IdentifyUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
@Path("/rest/message")
public class MessageService {

    private MessageDAO messageDAO = new MessageDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getMessage(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        Iterable<Message> messages = messageDAO.getMessagesByUserId(userId);
        responseEntity.code = 200;
        responseEntity.body = messages;
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
