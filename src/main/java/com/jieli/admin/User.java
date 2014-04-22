package com.jieli.admin;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-4
 * Time: PM9:51
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/buser")
public class User {
    private UserDAO userDAO = new UserDAO();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadUsers(@CookieParam("u") String sessionId) {
        Response response = Common.RoleCheckResponse(sessionId);
        if (response != null) return response;

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 9001;
        if (IdentifyUtils.isAdmin(sessionId) && !IdentifyUtils.isSuper(sessionId)) {
            Iterable<com.jieli.user.entity.User> users = userDAO.loadAll(IdentifyUtils.getAssociationId(sessionId));
            responseEntity.code = 200;
            responseEntity.body = users;
        }
        return Response.status(200).entity(responseEntity).build();
    }
}
