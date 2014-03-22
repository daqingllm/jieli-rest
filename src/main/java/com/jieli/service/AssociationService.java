package com.jieli.service;

import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.IdentifyUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
@Path("/association")
public class AssociationService {

    private AssociationDAO associationDAO = new AssociationDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getAssociation(@CookieParam("u")String sessionId, @QueryParam("id")String id) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        Association association = associationDAO.loadById(id);
        responseEntity.code = 200;
        responseEntity.body = association;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsert(@CookieParam("u")String sessionId, Association association) throws JSONException {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String id = associationDAO.save(association).getObjectId().toString();
        JSONObject json = new JSONObject();
        json.put("associationId", id);
        responseEntity.code = 200;
        responseEntity.body = json.toString();
        return Response.status(200).entity(responseEntity).build();
    }
}
