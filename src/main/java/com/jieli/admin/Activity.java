package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.ActivityDAO;
import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by 95 on 2014/4/15.
 */
@Path("/bactivity")
public class Activity {
    private AccountDAO accountDAO = new AccountDAO();
    private ActivityDAO activityDAO = new ActivityDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String newActivity(@CookieParam("u") String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return Common.errorReturn;
        }

        if (!IdentifyUtils.isSuper(sessionId) && !IdentifyUtils.isAdmin(sessionId)){
            return Common.errorReturn;
        }

        boolean isSuper = false;
        String associationList = "";

        if (IdentifyUtils.isSuper(sessionId)) {
            isSuper = true;
            associationList = Common.MakeAssociationOptionListForSelect("");
        }

        String username = accountDAO.loadById(sessionId).username;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isSuper",isSuper);
        params.put("username",username);
        params.put("associationList",associationList);

        return FTLrender.getResult("new_activity.ftl", params);
    }



    // 不仅要处理多associationid，还要转义json中的引号
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addNews(@CookieParam("u")String sessionId, com.jieli.activity.Activity activity) {
        Response response = Common.RoleCheckResponse(sessionId);
        if (response != null) return response;

        activity.addTime = new Date();

        int numSucc = 0;
        boolean isSuper = false;
        if (IdentifyUtils.isSuper(sessionId)){
            isSuper = true;
            activity.tag = AcivityTag.RECOMMEND;
            String []associations = activity.associationId.split(",");
            for (String assId : associations){
                if (associationDAO.loadById(assId) == null) continue;

                com.jieli.activity.Activity activityTmp = Common.copyDeepClean(activity);
                activityTmp.associationId = assId;

                activityDAO.save(activityTmp);
                numSucc ++;
            }
        }else {
            activity.tag = AcivityTag.OFFICIAL;
            activity.associationId = IdentifyUtils.getAssociationId(sessionId);

            activityDAO.save(activity);
            numSucc ++;
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.msg = "已经成功新建活动";
        responseEntity.body = numSucc;

        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String ActivityList(@CookieParam("u")String sessionId,@QueryParam("pl") String preload) throws JsonProcessingException {
        String response = Common.RoleCheckString(sessionId);
        if (response != null) return response;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);

        ////List<com.jieli.activity.Activity> activityList = new ArrayList<com.jieli.activity.Activity>();
        // 以后可能要做两个tab，分别显示进行中的和历史活动
        // List<com.jieli.activity.Activity> activityListHistory = new ArrayList<com.jieli.activity.Activity>();
        String activityList = "";

        if (IdentifyUtils.isSuper(sessionId)){
            params.put("isSuper",true);
        }
        else {
            params.put("isSuper",false);
            ObjectMapper objectMapper = new ObjectMapper();

            String associationName = associationDAO.loadById(account.associationId).name;
            // 找所有的官方进行中的活动，这段以后可能改，可能直接用find函数
            Iterable<com.jieli.activity.Activity> activities = activityDAO.findOngoingOfficial(account.associationId);

            for (com.jieli.activity.Activity activity : activities){
                ////activityList.add(activity);
                String tmp = objectMapper.writeValueAsString(activity).toString();
                String tmpObjectId = objectMapper.writeValueAsString(activity.get_id()).toString();
                String tmpId = activity.get_id().toString();

                activityList += tmp.replace(tmpObjectId,"\""+tmpId+"\"").replace("\"associationId\":\""+account.associationId+"\"","\"associationId\":\""+associationName+"\"");
            }

            // 找所有历史活动
            Iterable<com.jieli.activity.Activity> activities1 = activityDAO.findHistory(account.associationId,1,10,"official");
            for (com.jieli.activity.Activity activity : activities1){
                ////activityList.add(activity);
                String tmp = objectMapper.writeValueAsString(activity).toString();
                String tmpObjectId = objectMapper.writeValueAsString(activity.get_id()).toString();
                String tmpId = activity.get_id().toString();

                activityList += tmp.replace(tmpObjectId,"\""+tmpId+"\"").replace("\"associationId\":\""+account.associationId+"\"","\"associationId\":\""+associationName+"\"");
            }
        }

        params.put("activityList","["+activityList+"]");

        return FTLrender.getResult("activity_list.ftl",params);
    }
}
