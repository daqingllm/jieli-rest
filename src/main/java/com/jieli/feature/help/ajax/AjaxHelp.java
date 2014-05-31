package com.jieli.feature.help.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpStatus;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.IdentityUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meijia.lv on 14-4-19.
 */
@Path("/feature/ajaxhelp")
public class AjaxHelp {
    private HelpDAO helpDAO = new HelpDAO();
    private UserDAO userDAO = new UserDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getHelpList(@CookieParam("u")String sessionId, @QueryParam("a")String associationId, @QueryParam("page")int page, @QueryParam("size")int size, @QueryParam("t")String helpType) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String userId = IdentityUtils.getUserId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(userId)) {
            responseEntity.code = 1103;
            responseEntity.msg = "账户出错";
            return Response.status(200).entity(responseEntity).build();
        }
        boolean isSuper = IdentityUtils.isSuper(sessionId);
        boolean isAdmin = IdentityUtils.isAdmin(sessionId);
        if (!isSuper && isAdmin) {
            associationId = IdentityUtils.getAssociationId(sessionId);
        } else if (isSuper) {
            if (StringUtils.isEmpty(associationId)) {
                responseEntity.code = 1101;
                responseEntity.msg = "缺少参数";
                return Response.status(200).entity(responseEntity).build();
            }
        }

        page = 0;
        size = 0;

        int type = 2;
        if (helpType != null) {
            type = Integer.parseInt(helpType);
        }
        List<SimpleHelpInfo> simpleHelpInfoList = helpDAO.getHelpInfoList(page, size, associationId, type);
        for(SimpleHelpInfo h : simpleHelpInfoList) {
            if(h.getStatus() <= 0) {
                h.setStatus(HelpStatus.PENDING.getValue());
            }
            h.setId(h.get_id().toString());
        }
        String jsonHelpList;
        int i;
        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(simpleHelpInfoList);

            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>) om.readValue(tmp, List.class);
            for (Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }
            jsonHelpList = om.writeValueAsString(l);

            responseEntity.code = 200;
            responseEntity.body = jsonHelpList;
            return Response.status(200).entity(jsonHelpList).build();
        } catch (Exception e) {
            e.printStackTrace();
            jsonHelpList = "[]";
        }
        return Response.status(200).entity(responseEntity).build();
    }

}
