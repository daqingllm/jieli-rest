package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.comment.*;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.mongo.*;
import com.jieli.news.Image;
import com.jieli.news.ImageDAO;
import com.jieli.news.NewsDAO;
import com.jieli.news.NewsType;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-4
 * Time: PM9:51
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/bnews")
public class News {
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String GetNewsList(@CookieParam("u")String sessionId,@QueryParam("page") String page, @QueryParam("rowNum") String rowNum, @QueryParam("pl") String preload,@QueryParam("aid") String associationId) throws JsonProcessingException {
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return response;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        int paramPage = 1,paramRowNum = 15,paramTotal=0,paramTotalPage = 0;
        try{
            paramPage = Integer.parseInt(page);
            if (paramPage <= 0) paramPage = 1;
        }catch (Exception e){
            paramPage = 1;
        }
        try{
            paramRowNum = Integer.parseInt(rowNum);
            if (paramRowNum <= 0) paramRowNum = 1;
        }catch (Exception e){
            paramRowNum = 15;
        }

        String newsList = "";
        String assIdOptionList = "";
        List<com.jieli.news.News> newses = new LinkedList<com.jieli.news.News>();
        if (IdentityUtils.isSuper(sessionId)){

            assIdOptionList = "<option value='' selected disabled>请选择协会</option>";
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for (com.jieli.association.Association association : associations) {
                if (!StringUtils.isEmpty(associationId) && associationId.equals(association.get_id().toString())) {
                    assIdOptionList += "<option value='"+association.get_id()+"' selected>"+association.name+"</option>";
                } else{
                    assIdOptionList += "<option value='"+association.get_id()+"'>"+association.name+"</option>";
                    if (!StringUtils.isEmpty(associationId)) continue;
                }
            }

            params.put("assIdOptionList",assIdOptionList);
            params.put("isSuper",true);

            String associationIdPointedBySuper = (!StringUtils.isEmpty(associationId) ? "associationId:\"" + associationId + "\"," : "");
            newses = newsDAO.paginateInOrder(paramPage, paramRowNum,"{addTime:-1}", "{"+associationIdPointedBySuper+"type: {$in: " +
                    "[\""+NewsType.newsType+"\",\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\",\"" +NewsType.benefitType+"\""+
                    "]}}");
        }else{
            params.put("isSuper",false);
            String association = account.associationId;
            params.put("assIdOptionList","<option value='"+association+"' selected>&nbsp;</option>");
            String associationWhere = "";
            if (association != null && association != "") associationWhere = "associationId:\"" + association + "\",";
            newses = newsDAO.paginateInOrder(paramPage, paramRowNum,"{addTime:-1}", "{"+associationWhere+"type: {$in: " +
                    "[\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\",\""+NewsType.benefitType+"\",\"" + NewsType.historyType + "\"" +
                    "]}}");
        }

        Map<String , String> associationNames = new HashMap<String, String>();
        Iterable<Association> associations = associationDAO.loadAll();
        for(Association association : associations) associationNames.put(association.get_id().toString(),association.name);

        for (com.jieli.news.News news : newses){
            paramTotal ++;
            String tmp = CommonUtil.ReplaceObjectId(news);
            newsList += tmp.replace("\"associationId\":\"" + news.associationId + "\"", "\"associationId\":\"" + news.associationId + "\"" + ",\"associationName\":\"" + associationNames.get(news.associationId) + "\"") + ",";
        }

        params.put("newsList","["+(newsList.length()>0?(newsList.substring(0,newsList.length()-1)):"")+"]");
        params.put("rowNum",paramRowNum);
        params.put("ti",paramTotal);
        //params.put("tp",paramTotalPage);
        params.put("cp",paramPage);

        return FTLrender.getResult("article_list.ftl",params);
    }

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public String CreateNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role) {

        // 判断用户是否已经登录
        if (!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }

        // 载入用户
        //String userId = IdentityUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return CommonUtil.errorReturn;
        }


        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        //params.put("username",user.name);


        boolean isSuper = false;
        String assIdOptionList = "";
        String interestOptionList = "";
        String professionOptionList = "";

        //boolean b1 = associationId.equals("undefined");
        //boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        //if (b1 && b2) {
        if (IdentityUtils.isSuper(sessionId)){
            isSuper = true;
            assIdOptionList = CommonUtil.MakeAssociationOptionListForSelect("");
        }else {
            assIdOptionList = CommonUtil.MakeAssociationOptionListForSelect(IdentityUtils.getAssociationId(sessionId));
        }
        interestOptionList = CommonUtil.MakeInterestOptionList();
        professionOptionList = CommonUtil.MakeProfessionOptionList();

        params.put("assIdOptionList",assIdOptionList);
        params.put("interestOptionList",interestOptionList);
        params.put("professionOptionList",professionOptionList);

        return FTLrender.getResult("new_article.ftl", params);
    }

    // 不仅要处理多associationid，还要转义json中的引号
//    @POST
//    @Path("/add")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    public Response addNews(@CookieParam("u")String sessionId, com.jieli.news.News news){
//
//        if (!IdentityUtils.isValidate(sessionId)) {
//            return Response.status(403).build();
//        }
//
//        String sc = news.content;
//        /*while(sc.indexOf("\\u0027") >= 0) sc = sc.repla  ce("\\u0027","'");
//        while(sc.indexOf("\\u0022") >= 0) sc = sc.replace("\\u0027","\"");*/
//
//        ResponseEntity responseEntity = new ResponseEntity();
//        if(news!=null){
//            if (news.title == "" || news.title == null){
//                responseEntity.code = 9001;
//                responseEntity.msg = "请输入标题";
//                return Response.status(200).entity(responseEntity).build();
//            }
//
//            if (news.content == "" || news.content == null){
//                responseEntity.code = 9001;
//                responseEntity.msg = "请输入内容";
//                return Response.status(200).entity(responseEntity).build();
//            }
//
//            if( !CollectionUtils.isEmpty(news.images) ){
//                news.imagesCount = news.images.size();
//            }
//            news.addTime = new Date();
//
//            String[] assList = news.associationId.split(",");
//            AccountState accountState = accountDAO.loadById(sessionId).state;
//            if ((accountState.compareTo(AccountState.SUPPER) == 0) && (assList.length > 1)){
//                int l = assList.length - 1;
//                responseEntity.body = "{\"_id\":\"";
//                while (l >= 0){
//                    com.jieli.news.News singleNews = singleNews =copyDeep(news);
//                    singleNews.associationId = assList[l];
//                    newsDAO.save(singleNews);
//                    responseEntity.body = responseEntity.body + singleNews.get_id().toString() + (l==0?"":",");
//                    l--;
//                }
//                responseEntity.body = responseEntity.body + "\"}";
//            }else {
//                news.associationId = assList[0];
//                newsDAO.save(news);
//                responseEntity.body = "{\"_id\":\"" + news.get_id().toString() + "\"}";
//            }
//
//            //newsDAO.save(news);
//        }
//
//        responseEntity.code = 200;
//        return  Response.status(200).entity(responseEntity).build();
//
//    }

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNews(@CookieParam("u") String sessionId, @QueryParam("artid") String artid){

        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return  response;

        ResponseEntity responseEntity = new ResponseEntity();

        if (artid == null || artid == ""){
            responseEntity.code = 9001;
            responseEntity.msg = "无此资讯";
            return Response.status(200).entity(responseEntity).build();
        }

        // only delete first
        artid = artid.split(",")[0];

        com.jieli.news.News news = newsDAO.loadById(artid);

        if (news == null){
            responseEntity.code = 9001;
            responseEntity.msg = "无此资讯";
            return Response.status(200).entity(responseEntity).build();
        }

        if (!IdentityUtils.isSuper(sessionId) && !news.associationId.equals(IdentityUtils.getAssociationId(sessionId))){
            responseEntity.code = 9001;
            responseEntity.msg = "无权限";
            return Response.status(200).entity(responseEntity).build();
        }

        if (artid != "" && artid != null) {
            Iterable<Image> images = imageDAO.find("{newsId:\"" + artid + "\"}");
            for (Image image : images) {
                imageDAO.deleteById(image.get_id().toString());
            }
        }

        newsDAO.deleteById(artid);
        responseEntity.code = 200;
        responseEntity.msg = "删除成功";
        return Response.status(200).entity(responseEntity).build();
    }
private ImageDAO imageDAO = new ImageDAO();
    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public String editNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role,@QueryParam("artid") String artid) {

        // 判断用户是否已经登录
        if (!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }

        // 载入用户
        //String userId = IdentityUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return CommonUtil.errorReturn;
        }


        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        //params.put("username",user.name);


        boolean isSuper = false;
        String assIdOptionList = "";
        String interestOptionList = "";
        String professionOptionList = "";

        //boolean b1 = associationId.equals("undefined");
        //boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        com.jieli.news.News n = newsDAO.loadById(artid);


        String got = "";
        if (n == null || n.addTime == null) got = "此文章已经不存在了。";

        //if (b1 && b2) {
        if (IdentityUtils.isSuper(sessionId)){
            isSuper = true;
            Iterable<Association> associations = associationDAO.loadAll();
            for (Association association : associations) {
                //System.out.println(activity.get_id());
                assIdOptionList += "<option value='"+association.get_id()+"'>"+association.name+"</option>";
            }

            //assIdOptionList = "";
        }else {
            if (n.type.compareTo(NewsType.newsType) == 0) got = "您无权修改新闻类型的资讯";
            if (!(n.associationId .equals( account.associationId))) got = "您无权修改其他协会的资讯";

            Association association = associationDAO.loadById(IdentityUtils.getAssociationId(sessionId));
            assIdOptionList += "<option value='"+ IdentityUtils.getAssociationId(sessionId)+"' selected>"+association.name+"</option>";
        }
        interestOptionList = CommonUtil.MakeInterestOptionList();
        professionOptionList = CommonUtil.MakeProfessionOptionList();

        String tmp = null;
        try {
            tmp = new ObjectMapper().writeValueAsString(n);

            //String tmp = om.writeValueAsString(n);

            String tmpObjectId = new ObjectMapper().writeValueAsString(n.get_id()).toString();
            String tmpId = n.get_id().toString();

            tmp = tmp.replace(tmpObjectId,"\""+tmpId+"\"");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            got = "获取资讯失败。";
        }
        /*
        if (tmp != null && tmp.indexOf("{\"time\":") > -1){
            tmp = tmp.substring(0,7) + "\"" + n.get_id().toString() + "\"" + tmp.substring(tmp.indexOf("},",7)+1);
        }*/

        List<com.jieli.comment.Comment> commentList = new LinkedList<com.jieli.comment.Comment>();
        String commentListString = "[";
        if (artid != null){
            commentList = commentDAO.find("{topicId:#,isDeleted:#}", artid, false);
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

        params.put("got",got);
        params.put("art_data",tmp);
        params.put("assIdOptionList",assIdOptionList);
        params.put("interestOptionList",interestOptionList);
        params.put("jsonCommentList",commentListString);
        params.put("topicId",artid);
        params.put("professionOptionList",professionOptionList);

        return FTLrender.getResult("edit_article.ftl", params);
    }

    @GET
    @Path("/comment")
    @Produces(MediaType.TEXT_HTML)
    public String GetNewsCommentList(@CookieParam("u")String sessionId,@QueryParam("artid") String newsId){
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return  response;

        ResponseEntity responseEntity = new ResponseEntity();
        List<com.jieli.comment.Comment> commentList = new LinkedList<com.jieli.comment.Comment>();
        String commentListString = "[";
        if (newsId != null){
            commentList = commentDAO.find("{topicId:#, isDeleted:#}", newsId, false);
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
            params.put("topicId",newsId);
            params.put("ctype","news");


            if (newsId == null || newsDAO.loadById(newsId) == null || newsDAO.loadById(newsId).title == null)
                params.put("topicTitle","");
            else
                params.put("topicTitle",newsDAO.loadById(newsId).title);

            return FTLrender.getResult("comment_list.ftl",params);
        }else{
            return CommonUtil.errorReturn;
        }
    }


    private com.jieli.news.News copyDeep(com.jieli.news.News n){
        com.jieli.news.News nn = new com.jieli.news.News();

        nn.addTime = n.addTime;
        nn.appreciateCount = 0;
        nn.appreciateUserIds = new ArrayList<String>();
        nn.associationId = n.associationId;
        nn.content = n.content;
        nn.images = new ArrayList<Image>();
        nn.imagesCount = 0;
        nn.overview = n.overview;
        nn.title = n.title;
        nn.type = n.type;

        return nn;
    }

    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private NewsDAO newsDAO = new NewsDAO();
    private BaseDAO<com.jieli.comment.Comment> commentDAO = new BaseDAO<com.jieli.comment.Comment>(com.jieli.mongo.Collections.Comment, com.jieli.comment.Comment.class);
}
