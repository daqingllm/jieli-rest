package com.jieli.feature.vote.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.dao.VoteResultDAO;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
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
 * Created by YolandaLeo on 14-4-21.
 */
@Path("/feature/ajaxvote")
public class AjaxVote {
    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private VoteResultDAO voteResultDAO = new VoteResultDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getVoteList(@CookieParam("u")String sessionId, @QueryParam("a")String associationId, @QueryParam("page")int page, @QueryParam("size")int size) {
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
        List<SimpleVoteInfo> voteInfoList = voteDAO.getVoteInfoList(page, size, associationId);
        for(SimpleVoteInfo v : voteInfoList) {
            v.setId(v.get_id().toString());
            v.setParticipants(voteResultDAO.loadByVoteId(v.get_id().toString()).getParticipants());
        }

        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(voteInfoList);

            List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) om.readValue(tmp, List.class);
            for (Map<String, Object> obj : list) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }

            responseEntity.code = 200;
            responseEntity.body = list;
            return Response.status(200).entity(responseEntity).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

}
