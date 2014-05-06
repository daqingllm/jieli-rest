package com.jieli.admin;

import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.association.GroupDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95 on 2014/4/19.
 */
@Singleton
@Path("/bgroup")
public class Group {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private GroupDAO groupDAO = new GroupDAO();
    private UserDAO userDAO = new UserDAO();

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(@CookieParam("u") String sessionId, @QueryParam("group") String group){
        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        ResponseEntity responseEntity = new ResponseEntity();

        if (IdentifyUtils.isSuper(sessionId)){
            return Response.status(200).entity(responseEntity).build();
        }

        Iterable<com.jieli.association.Group> groups = groupDAO.loadAll(IdentifyUtils.getAssociationId(sessionId));
        for (com.jieli.association.Group g : groups){
            if (g.name.equals(group)){
                groupDAO.deleteById(g.get_id().toString());
                {
                    Iterable<com.jieli.user.entity.User> users = userDAO.loadByGroup(IdentifyUtils.getAssociationId(sessionId), group);
                    for (User user : users) { user.group = ""; userDAO.save(user);}

                    responseEntity.code = 200;
                    return Response.status(200).entity(responseEntity).build();
                }
            }
        }
        responseEntity.code = 9004;
        responseEntity.msg = "无此分组";
        return  Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String groupList(@CookieParam("u")String sessionId){
        String er = CommonUtil.RoleCheckString(sessionId);
        if (er != null) return  er;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);

        String associationOps = "";
        if (account.state == AccountState.ADMIN) {
            params.put("isSuper", false);
        }
        else {
            params.put("isSuper", true);
            List<com.jieli.association.Association> associationList = new ArrayList<com.jieli.association.Association>();
            Iterable<com.jieli.association.Association> iterable = associationDAO.loadAll();
            for (Association association : iterable)
                associationOps += "<option value='" + association.get_id() + "'>" + association.name + "</option>";
        }
        params.put("associationOps",associationOps);

        String groupList = "[";
        Iterable<com.jieli.association.Group> groups = groupDAO.loadAll(account.associationId);
        List<com.jieli.association.Group> groupListRet = new ArrayList<com.jieli.association.Group>();
        com.jieli.association.Association association = associationDAO.loadById(account.associationId);
        String firstGroupName = "";
        for (com.jieli.association.Group group : groups){
            groupListRet.add(group);
            if (groupList.equals("[")) firstGroupName = group.name;
            groupList += CommonUtil.ReplaceObjectId(group).replace("\"associationId\":\"" + account.associationId + "\"", "\"associationId\":\"" + association.name + "\"") +",";
        }
        if (groupList.endsWith(",")) groupList = groupList.substring(0,groupList.length()-1);
        groupList += "]";

        params.put("groupList",groupList);

        params.put("groups",groupListRet);
        params.put("firstGroupName",firstGroupName);

        return FTLrender.getResult("group_list.ftl",params);
    }
}
