package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.AcivityTag;
import com.jieli.activity.ActivityDAO;
import com.jieli.association.*;
import com.jieli.comment.*;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.common.entity.Account;
import com.jieli.mongo.BaseDAO;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by 95 on 2014/4/15.
 */
@Singleton
@Path("/bactivity")
public class Activity {
    private AccountDAO accountDAO = new AccountDAO();
    private ActivityDAO activityDAO = new ActivityDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private BaseDAO<com.jieli.comment.Comment> commentDAO = new BaseDAO<com.jieli.comment.Comment>(com.jieli.mongo.Collections.Comment, com.jieli.comment.Comment.class);

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String newActivity(@CookieParam("u") String sessionId) {
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }

        if (!IdentityUtils.isSuper(sessionId) && !IdentityUtils.isAdmin(sessionId)){
            return CommonUtil.errorReturn;
        }

        boolean isSuper = false;
        String associationList = "";

        if (IdentityUtils.isSuper(sessionId)) {
            isSuper = true;
            associationList = CommonUtil.MakeAssociationOptionListForSelect("");
        } else {
            isSuper = false;
            associationList = CommonUtil.MakeAssociationOptionListForSelect(IdentityUtils.getAssociationId(sessionId));
        }

        Account account = accountDAO.loadById(sessionId);

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        params.put("associationList",associationList);

        return FTLrender.getResult("new_activity.ftl", params);
    }



    // 不仅要处理多associationid，还要转义json中的引号
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addNews(@CookieParam("u")String sessionId, com.jieli.activity.Activity activity) {
        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        activity.addTime = new Date();

        int numSucc = 0;
        boolean isSuper = false;
        if (IdentityUtils.isSuper(sessionId)){
            isSuper = true;
            activity.tag = AcivityTag.RECOMMEND;
            String []associations = activity.associationId.split(",");
            for (String assId : associations){
                if (associationDAO.loadById(assId) == null) continue;

                com.jieli.activity.Activity activityTmp = CommonUtil.copyDeepClean(activity);
                activityTmp.associationId = assId;

                activityDAO.save(activityTmp);
                numSucc ++;
            }
        }else {
            activity.tag = AcivityTag.OFFICIAL;
            activity.associationId = IdentityUtils.getAssociationId(sessionId);

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
    public String ActivityList(@CookieParam("u")String sessionId,@QueryParam("page") String page, @QueryParam("rowNum") String rowNum, @QueryParam("pl") String preload) throws JsonProcessingException {
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return response;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        int _page = 1,_rowNum = 15,_total=0,_totalPage = 0;
        try{
            _page = Integer.parseInt(page);
            if (_page <= 0) _page = 1;
        }catch (Exception e){
            _page = 1;
        }
        try{
            _rowNum = Integer.parseInt(rowNum);
            if (_rowNum <= 0) _rowNum = 15;
        }catch (Exception e){
            _rowNum = 15;
        }

        ////List<com.jieli.activity.Activity> activityList = new ArrayList<com.jieli.activity.Activity>();
        // 以后可能要做两个tab，分别显示进行中的和历史活动
        // List<com.jieli.activity.Activity> activityListHistory = new ArrayList<com.jieli.activity.Activity>();
        String activityList = "";

        if (IdentityUtils.isSuper(sessionId)){
            Iterable<com.jieli.activity.Activity> activities = activityDAO.getCollection().find().sort("{beginDate:-1}").as(com.jieli.activity.Activity.class);
            for (com.jieli.activity.Activity activity : activities){
                if (_total >= (_page-1)*_rowNum && _total < _page*_rowNum) {
                    String tmp = CommonUtil.ReplaceObjectId(activity);
                    activityList += tmp + ",";
                }
                _total ++;
            }
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();

            String associationName = associationDAO.loadById(account.associationId).name;
            // 找所有的官方进行中的活动，这段以后可能改，可能直接用find函数
            Iterable<com.jieli.activity.Activity> activities1 = activityDAO.findOngoingOfficial(account.associationId);
            Iterable<com.jieli.activity.Activity> activities2 = activityDAO.findHistory(account.associationId,0,Integer.MAX_VALUE,"OFFICIAL");

            for (com.jieli.activity.Activity activity : activities1){
                if (_total >= (_page-1)*_rowNum && _total < _page*_rowNum) {
                    String tmp = CommonUtil.ReplaceObjectId(activity);
                    activityList += tmp.replace("\"associationId\":\"" + account.associationId + "\"", "\"associationId\":\"" + associationName + "\"") + ",";
                }
                _total ++;
            }

            // 找所有历史活动
            //activities2 = activityDAO.findHistory(account.associationId,0,Integer.MAX_VALUE,"OFFICIAL");
            for (com.jieli.activity.Activity activity : activities2){
                if (_total >= (_page-1)*_rowNum && _total < _page*_rowNum) {
                    String tmp = CommonUtil.ReplaceObjectId(activity);
                    activityList += tmp.replace("\"associationId\":\"" + account.associationId + "\"", "\"associationId\":\"" + associationName + "\"") + ",";
                }
                _total ++;
            }
        }

        _totalPage = (_total+1)/_rowNum;
        if (_page > _totalPage) _page = _totalPage+1;

        params.put("activityList","["+(activityList.length()>0?(activityList.substring(0,activityList.length()-1)):"")+"]");
        params.put("rowNum",_rowNum);
        params.put("ti",_total);
        params.put("tp",_totalPage);
        params.put("cp",_page);

        return FTLrender.getResult("activity_list.ftl",params);
    }

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getActivityPage(@CookieParam("u")String sessionId,@QueryParam("actid") String activityId){

        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.msg = "已成功删除";

        if (IdentityUtils.isAdmin(sessionId)){
            com.jieli.activity.Activity activity = activityDAO.loadById(activityId);
            if (activity == null)
                responseEntity.msg = "该活动已不存在";
            else{
                responseEntity.msg += "“"+activity.title+"”";
                activityDAO.deleteById(activityId);
            }
        } else if (IdentityUtils.isSuper(sessionId)){
            ;
        }
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public String editActivity(@CookieParam("u")String sessionId,@QueryParam("actid") String actid) {

        com.jieli.activity.Activity activity = activityDAO.loadById(actid);
        Account account = accountDAO.loadById(sessionId);

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        String activity_data = "";
        String associationList = "";

        String targetFTL = "edit_activity.ftl";
        try {
            if (activity == null) {
                params.put("got", "无此活动！");

                params.put("act_data","");
                params.put("topicId","");
                params.put("jsonCommentList","");
                params.put("associationList","");
                return FTLrender.getResult(targetFTL,params);

            } else if (activity.beginDate != null
                    && activity.beginDate.compareTo(new Date()) < 0) {
                params.put("got", "old");
                targetFTL = "history_activity.ftl";

            } else {
                params.put("got", "");
            }
        } catch (Exception e){
            params.put("got", "遇到问题了，"+e.toString());

            params.put("act_data","");
            params.put("topicId","");
            params.put("jsonCommentList","");
            params.put("associationList","");
            return FTLrender.getResult(targetFTL,params);

        }

        // 判断用户是否已经登录
        if (account == null ||
                (!IdentityUtils.isSuper(sessionId) && !activity.associationId.equals(account.associationId)) ||
                !IdentityUtils.isValidate(sessionId) ||
                !IdentityUtils.isAdmin(sessionId)
                ) {
            return CommonUtil.errorReturn;
        }

        activity_data = CommonUtil.ReplaceObjectId(activity);

        if (IdentityUtils.isSuper(sessionId)) {
            associationList = CommonUtil.MakeAssociationOptionListForSelect("");
        } else {
            associationList = CommonUtil.MakeAssociationOptionListForSelect(IdentityUtils.getAssociationId(sessionId));
        }


        List<com.jieli.comment.Comment> commentList = new LinkedList<com.jieli.comment.Comment>();
        String commentListString = "[";
        if (actid != null){
            commentList = commentDAO.find("{topicId:#,isDeleted:#}", actid, false);
            if (commentList != null && commentList.size() > 0) {
                for (com.jieli.comment.Comment comment : commentList)
                    commentListString += CommonUtil.ReplaceObjectId(comment) + ",";
                if (commentListString.endsWith(",")) {
                    commentListString = commentListString.substring(0, commentListString.length() - 1);
                }
            }
            commentListString += "]";
        }else{
            commentListString = "[]";
        }

        if (activity_data == "" || activity_data == null) activity_data = "{}";

        params.put("act_data",activity_data);
        params.put("topicId",actid);
        params.put("jsonCommentList",commentListString);
        params.put("associationList",associationList);

        return FTLrender.getResult(targetFTL,params);
    }


    @GET
    @Path("/view")
    @Produces(MediaType.TEXT_HTML)
    public String viewActivity(@CookieParam("u")String sessionId,@QueryParam("actid") String actid) {

        com.jieli.activity.Activity activity = activityDAO.loadById(actid);
        Account account = accountDAO.loadById(sessionId);

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        String activity_data = "";
        String associationList = "";

        if (activity == null)
            params.put("got","无此活动！");
        else if(activity.beginDate != null
                && activity.beginDate.compareTo(new Date()) < 0)
            params.put("got","old");
        else {
            params.put("got", "");

            // 判断用户是否已经登录
            if (account == null ||
                    (!IdentityUtils.isSuper(sessionId) && !activity.associationId.equals(account.associationId)) ||
                    !IdentityUtils.isValidate(sessionId) ||
                    !IdentityUtils.isAdmin(sessionId)
                    ) {
                return CommonUtil.errorReturn;
            }

            activity_data = CommonUtil.ReplaceObjectId(activity);

            if (IdentityUtils.isSuper(sessionId)) {
                associationList = CommonUtil.MakeAssociationOptionListForSelect("");
            } else {
                associationList = CommonUtil.MakeAssociationOptionListForSelect(IdentityUtils.getAssociationId(sessionId));
            }
        }
        params.put("act_data",activity_data);
        params.put("associationList",associationList);
        return FTLrender.getResult("view_activity.ftl",params);
    }

    @GET
    @Path("/comment")
    @Produces(MediaType.TEXT_HTML)
    public String GetNewsCommentList(@CookieParam("u")String sessionId,@QueryParam("actid") String activityId){
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return  response;

        ResponseEntity responseEntity = new ResponseEntity();
        List<com.jieli.comment.Comment> commentList = new LinkedList<com.jieli.comment.Comment>();
        String commentListString = "[";
        if (activityId != null){
            commentList = commentDAO.find("{topicId:#, isDeleted:#}", activityId, false);
            if (commentList != null) {
                for (com.jieli.comment.Comment comment : commentList) {
                    comment.commentUserInfo = CommentUserInfoUtil.generate(comment.commentUserId);
                    comment.commentedUserInfo = CommentUserInfoUtil.generate(comment.commentedUserId);
                    commentListString += CommonUtil.ReplaceObjectId(comment) + ",";
                }
            }
            if (commentListString.endsWith(",")){
                commentListString = commentListString.substring(0,commentListString.length()-1);
            }
            commentListString += "]";

            com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
            Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
            params.put("jsonCommentList",commentListString);
            params.put("topicId",activityId);
            params.put("ctype","activity");

            if (activityId == null || activityDAO.loadById(activityId) == null || activityDAO.loadById(activityId).title == null)
                params.put("topicTitle","");
            else
                params.put("topicTitle",activityDAO.loadById(activityId).title);

            return FTLrender.getResult("comment_list.ftl",params);
        }else{
            return CommonUtil.errorReturn;
        }
    }
}
