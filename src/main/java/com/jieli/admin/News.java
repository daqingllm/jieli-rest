package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.news.Image;
import com.jieli.news.NewsDAO;
import com.jieli.news.NewsType;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.CollectionUtils;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;

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
    public String GetNewsList(@CookieParam("u")String sessionId,@QueryParam("page") String page, @QueryParam("rowNum") String rowNum, @QueryParam("pl") String preload) throws JsonProcessingException {
        String response = Common.RoleCheckString(sessionId);
        if (response != null) return response;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);

        int _page = 1,_rowNum = 15,_total=0,_totalPage = 0;
        try{
            _page = Integer.parseInt(page);
            if (_page <= 0) _page = 1;
        }catch (Exception e){
            _page = 1;
        }
        try{
            _rowNum = Integer.parseInt(rowNum);
            if (_rowNum <= 0) _rowNum = 1;
        }catch (Exception e){
            _rowNum = 15;
        }

        String newsList = "";
        List<com.jieli.news.News> newses = new LinkedList<com.jieli.news.News>();
        if (IdentifyUtils.isSuper(sessionId)){
            params.put("isSuper",true);
            newses = newsDAO.paginateInOrder(_page, _rowNum,"{addTime:-1}", "{type: {$in: [\""+NewsType.newsType+"\",\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\"]}}");
        }else{
            params.put("isSuper",false);
            newses = newsDAO.paginateInOrder(_page, _rowNum,"{addTime:-1}", "{type: {$in: [\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\"]}}");
        }

        Map<String , String> associationNames = new HashMap<String, String>();
        Iterable<Association> associations = associationDAO.loadAll();
        for(Association association : associations) associationNames.put(association.get_id().toString(),association.name);

        for (com.jieli.news.News news : newses){
            _total ++;
            String tmp = Common.ReplaceObjectId(news);
            newsList += tmp.replace("\"associationId\":\"" + news.associationId + "\"", "\"associationId\":\"" + associationNames.get(news.associationId) + "\"") + ",";
        }

        params.put("newsList","["+(newsList.length()>0?(newsList.substring(0,newsList.length()-1)):"")+"]");
        params.put("rowNum",_rowNum);
        params.put("ti",_total);
        //params.put("tp",_totalPage);
        params.put("cp",_page);

        return FTLrender.getResult("article_list.ftl",params);
    }

    @POST
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String NewsList(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role,@QueryParam("pl") String preload){
        if (preload != "y") preload = "n";

        // 判断用户是否已经登录
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Common.errorReturn;
        }

        // 载入用户
        //String userId = IdentifyUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return Common.errorReturn;
        }


        Map<String, Object> params = new HashMap<String, Object>();
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        params.put("username",account.username);
        //params.put("username",user.name);


        boolean isSuper = false;

        boolean b1 = associationId.equals("undefined");
        boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        String artListString = "[";
        List<com.jieli.news.News> newsList = new LinkedList<com.jieli.news.News>();

        if (b1 && b2) {
            params.put("isSuper",true);
            newsList = newsDAO.paginateInOrder(0,100,"{addTime:-1}","{type: {$in: [\""+ NewsType.newsType+"\",\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\"]}}");
        }else {
            params.put("isSuper",false);
            newsList = newsDAO.paginateInOrder(0,100,"{addTime:-1}","{type: {$in: [\""+NewsType.associationType+"\",\""+NewsType.enterpriseType+"\"]}}");
        }

        int i;
        ObjectMapper om = new ObjectMapper();
        for (i = 0; i < newsList.size(); i++){
            com.jieli.news.News n = newsList.get(i);
            try {
                //artListString += om.writeValueAsString(n);
                String tmp = om.writeValueAsString(n);

                String tmpObjectId = om.writeValueAsString(n.get_id()).toString();
                String tmpId = n.get_id().toString();

                tmp = tmp.replace(tmpObjectId,"\""+tmpId+"\"").replace("\"associationId\":\""+n.associationId+"\"","\"associationId\":\""+associationDAO.loadById(n.associationId).name+"\"");

                /*
                if (tmp.indexOf("{\"time\":") > -1){
                    tmp = tmp.substring(0,7) + "\"" + n.get_id().toString() + "\"" + tmp.substring(tmp.indexOf("},",7)+1);

                    String assid = n.associationId;
                    Association association = associationDAO.loadById(assid);

                    tmp = tmp.replace(assid,association.name);

                    //tmp = tmp.replace("_id","id");
                }*/
                artListString += tmp;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                continue;
            }
            if (i != newsList.size() - 1) artListString += ",";
        }
        artListString += "]";

        params.put("jsonArtList",artListString);
        return FTLrender.getResult("article_list.ftl", params);
    }


    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public String CreateNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role) {

        // 判断用户是否已经登录
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Common.errorReturn;
        }

        // 载入用户
        //String userId = IdentifyUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return Common.errorReturn;
        }


        Map<String, Object> params = new HashMap<String, Object>();
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        params.put("username",account.username);
        //params.put("username",user.name);


        boolean isSuper = false;
        String assIdOptionList = "";

        boolean b1 = associationId.equals("undefined");
        boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        if (b1 && b2) {
            isSuper = true;
            assIdOptionList = Common.MakeAssociationOptionListForSelect("");
        }else {
            assIdOptionList = Common.MakeAssociationOptionListForSelect(associationId);
        }

        params.put("isSuper",isSuper);
        params.put("assIdOptionList",assIdOptionList);

        return FTLrender.getResult("new_article.ftl", params);
    }

    // 不仅要处理多associationid，还要转义json中的引号
//    @POST
//    @Path("/add")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    public Response addNews(@CookieParam("u")String sessionId, com.jieli.news.News news){
//
//        if (!IdentifyUtils.isValidate(sessionId)) {
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

        Response response = Common.RoleCheckResponse(sessionId);
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

        if (!IdentifyUtils.isSuper(sessionId) && !news.associationId.equals(IdentifyUtils.getAssociationId(sessionId))){
            responseEntity.code = 9001;
            responseEntity.msg = "无权限";
            return Response.status(200).entity(responseEntity).build();
        }

        newsDAO.deleteById(artid);
        responseEntity.code = 200;
        responseEntity.msg = "删除成功";
        return Response.status(200).entity(responseEntity).build();
    }

    @GET
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public String editNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role,@QueryParam("artid") String artid) {

        // 判断用户是否已经登录
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Common.errorReturn;
        }

        // 载入用户
        //String userId = IdentifyUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return Common.errorReturn;
        }


        Map<String, Object> params = new HashMap<String, Object>();
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        params.put("username",account.username);
        //params.put("username",user.name);


        boolean isSuper = false;
        String assIdOptionList = "";

        boolean b1 = associationId.equals("undefined");
        boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        com.jieli.news.News n = newsDAO.loadById(artid);


        String got = "";
        if (n == null) got = "此文章已经不存在了。";


        if (b1 && b2) {
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

            Association association = associationDAO.loadById(associationId);
            assIdOptionList += "<option value='"+associationId+"' selected>"+association.name+"</option>";
        }

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
        if (tmp != null && tmp.indexOf("{\"time\":") > -1){
            tmp = tmp.substring(0,7) + "\"" + n.get_id().toString() + "\"" + tmp.substring(tmp.indexOf("},",7)+1);
        }

        params.put("isSuper",isSuper);
        params.put("got",got);
        params.put("art_data",tmp);
        params.put("assIdOptionList",assIdOptionList);

        return FTLrender.getResult("edit_article.ftl", params);
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
}
