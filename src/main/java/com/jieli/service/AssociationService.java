package com.jieli.service;

import com.jieli.association.*;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/association")
public class AssociationService {

    private AssociationDAO associationDAO = new AssociationDAO();

    private AccountDAO accountDAO = new AccountDAO();

    private GroupDAO groupDAO = new GroupDAO();

    private IdentifyDAO identifyDAO = new IdentifyDAO();

    private UserDAO userDAO = new UserDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAssociation(@CookieParam("u")String sessionId, @QueryParam("id")String id, @QueryParam("name")String name) {
        //先id,后name,无参all

        if (!IdentifyUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isNotEmpty(id)) {
            Association association = associationDAO.loadById(id);
            if (association != null) {
                responseEntity.code = 200;
                responseEntity.body = association;
                return Response.status(200).entity(responseEntity).build();
            }
        }
        if (StringUtils.isNotEmpty(name)) {
            Association association = associationDAO.loadByName(name);
            if (association == null) {
                responseEntity.code = 2102;
                responseEntity.msg = "协会不存在";
                return Response.status(200).entity(responseEntity).build();
            }
        }
        Iterable<Association> associations = associationDAO.loadAll();
        responseEntity.code = 200;
        responseEntity.body = associations;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsert(@CookieParam("u")String sessionId, Association association) throws JSONException {
        if (!IdentifyUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Association oldAssociation = associationDAO.loadByName(association.name);
        if (oldAssociation != null) {
            responseEntity.code = 2103;
            responseEntity.msg = "协会已存在";
            return Response.status(200).entity(responseEntity).build();
        }
        String id = associationDAO.save(association).get_id().toString();
        JSONObject json = new JSONObject();
        json.put("associationId", id);
        responseEntity.code = 200;
        responseEntity.body = json.toString();
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadUsers(@CookieParam("u")String sessionId,@QueryParam("id")String id ,@QueryParam("state")int state) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }

        Association association = associationDAO.loadById(associationId);
        if (association == null) {
            responseEntity.code = 2102;
            responseEntity.msg = "协会不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        Iterable<Account> accounts = null;
        if (state == 0){
            accounts = accountDAO.loadByAssociationId(associationId, AccountState.DISABLE);
        } else if (state == 1) {
            accounts = accountDAO.loadByAssociationId(associationId, AccountState.ENABLE);
        } else if (state == 2) {
            accounts = accountDAO.loadByAssociationId(associationId, AccountState.ADMIN);
        } else {
            responseEntity.code = 2103;
            responseEntity.msg = "参数无效";
            return Response.status(200).entity(responseEntity).build();
        }

        responseEntity.code = 200;
        responseEntity.body = accounts;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/organization")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response loadOrganization(@CookieParam("u")String sessionId, @QueryParam("id")String id) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }
        Association association = associationDAO.loadById(associationId);
        if (association == null) {
            responseEntity.code = 2102;
            responseEntity.msg = "协会不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Iterable<Identify> identifies = identifyDAO.loadAll(associationId);
        Map<String, Iterable<User>> result = new HashMap<String, Iterable<User>>();
        for (Identify identify : identifies) {
            Iterable<User> users = userDAO.loadByIdentify(associationId, identify.name);
            result.put(identify.name, users);
        }
        responseEntity.code = 200;
        responseEntity.body = result;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/group")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertGroup(@CookieParam("u")String sessionId, @QueryParam("id")String id, Group group) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数协会id";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }
        group.associationId = associationId;

        Iterable<Group> groups = groupDAO.loadAll(associationId);
        for (Group oldGroup : groups) {
            if (oldGroup.equals(group)) {
                responseEntity.code = 2301;
                responseEntity.msg = "分组已存在";
                return Response.status(200).entity(responseEntity).build();
            }
        }

        groupDAO.save(group);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/group")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getGroup(@CookieParam("u")String sessionId, @QueryParam("id")String id, @QueryParam("group")String group) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数协会id";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }

        if (StringUtils.isEmpty(group)) {
            Iterable<Group> groups = groupDAO.loadAll(associationId);
            responseEntity.code = 200;
            responseEntity.body = groups;
            return Response.status(200).entity(responseEntity).build();
        }

        Iterable<User> users = userDAO.loadByGroup(associationId, group);
        responseEntity.code = 200;
        responseEntity.body = users;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/identify")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertIdentify(@CookieParam("u")String sessionId, @QueryParam("id")String id, Identify identify) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数协会id";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }
        identify.associationId = associationId;

        Iterable<Identify> identifies = identifyDAO.loadAll(associationId);
        for (Identify oldIdentify : identifies) {
            if (oldIdentify.equals(identify)) {
                responseEntity.code = 2301;
                responseEntity.msg = "职位已存在";
                return Response.status(200).entity(responseEntity).build();
            }
        }

        identifyDAO.save(identify);
        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/identify")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getIdentify(@CookieParam("u")String sessionId, @QueryParam("id")String id, @QueryParam("identify")String identify) {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = null;
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            if (StringUtils.isEmpty(id)) {
                responseEntity.code = 2101;
                responseEntity.msg = "缺少参数协会id";
                return Response.status(200).entity(responseEntity).build();
            }
            associationId = id;
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
        }

        if (StringUtils.isEmpty(identify)) {
            Iterable<Identify> identifies = identifyDAO.loadAll(associationId);
            responseEntity.code = 200;
            responseEntity.body = identifies;
            return Response.status(200).entity(responseEntity).build();
        }

        Iterable<User> users = userDAO.loadByGroup(associationId, identify);
        responseEntity.code = 200;
        responseEntity.body = users;
        return Response.status(200).entity(responseEntity).build();
    }

}
