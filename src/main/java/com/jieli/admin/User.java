package com.jieli.admin;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 9001;
        if (IdentityUtils.isAdmin(sessionId) && !IdentityUtils.isSuper(sessionId)) {
            Iterable<com.jieli.user.entity.User> users = userDAO.loadAll(IdentityUtils.getAssociationId(sessionId));
            responseEntity.code = 200;
            responseEntity.body = users;
        }
        return Response.status(200).entity(responseEntity).build();
    }
}
