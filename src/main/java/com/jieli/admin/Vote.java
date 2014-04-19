package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
import com.jieli.feature.vote.entity.VoteInfo;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-10.
 */
@Singleton
@Path("bvote")
public class Vote {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private String errorReturn = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "        <title>请先登录</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "\n" +
            "\t<span>您尚未登录，</span>\n" +
            "\t<span id=\"totalSecond\">5</span>秒后跳转到登陆页..</span>\n" +
            "\n" +
            "\t<script language=\"javascript\" type=\"text/javascript\">\n" +
            "\talert(document.cookie);\n" +
            "\t\tvar second = document.getElementById('totalSecond').textContent;\n" +
            "\n" +
            "\t\tif (navigator.appName.indexOf(\"Explorer\") > -1)\n" +
            "\t\t\tsecond = document.getElementById('totalSecond').innerText;\n" +
            "\t\telse\n" +
            "\t\t\tsecond = document.getElementById('totalSecond').textContent;\n" +
            "\n" +
            "\t\tsetInterval(\"redirect()\", 1000);\n" +
            "\t\t\n" +
            "\t\tfunction redirect()\n" +
            "\t\t{\n" +
            "\t\t\tif (second < 0)\n" +
            "\t\t\t\tlocation.href = '/rest/baccount/login';\n" +
            "\t\t\telse {\n" +
            "\t\t\t\tif (navigator.appName.indexOf(\"Explorer\") > -1)\n" +
            "\t\t\t\t\tdocument.getElementById('totalSecond').innerText = second--;\n" +
            "\t\t\t\telse\n" +
            "\t\t\t\t\tdocument.getElementById('totalSecond').textContent = second--;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t</script>\n" +
            "    </body>\n" +
            "</html>";

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String createVote(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);
        params.put("isSuper", isSuper);
        params.put("newVote", true);
        params.put("isEditable", true);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/view")
    public String viewVote(@CookieParam("u") String sessionId, @QueryParam("v")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        params.put("isSuper", isSuper);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("newVote", false);
        params.put("isEditable", false);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/edit")
    public String modifyVote(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        params.put("isSuper", isSuper);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("newVote", false);
        params.put("isEditable", true);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/list")
    public String getVoteList(@CookieParam("u")String sessionId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        boolean isSuper = false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        List<SimpleVoteInfo> voteList = new ArrayList<SimpleVoteInfo>();
        List<Association> associationList = new ArrayList<Association>();
        Integer pageNo = 1;
        Integer pageSize = 20;
        if(IdentifyUtils.isSuper(sessionId)) {
            isSuper = true;
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for(Association a : associations) {
                associationList.add(a);
            }
            params.put("associationList", associationList);
        }
        else {
            if(!MongoUtils.isValidObjectId(associationId)) {
                return FTLrender.getResult("error.ftl", params);
            }
        }
        if(associationId == null) {
            //首次super进入页面，未选择association
            //TODO selector传入associationId，ajax请求下一次列表页
            associationId = associationList.get(0).get_id().toString();
        }
        voteList = voteDAO.getVoteInfoList(pageNo, pageSize,
                associationId);
        for(SimpleVoteInfo v : voteList) {
            v.setId(v.get_id().toString());
        }
        String jsonVoteList;
        int i;
        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(voteList);

            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>)om.readValue(tmp, List.class);
            for(Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }
            jsonVoteList = om.writeValueAsString(l);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVoteList = "[]";
        }

        params.put("jsonVoteList", jsonVoteList);
        params.put("isSuper", isSuper);
        return FTLrender.getResult("vote_list.ftl", params);

    }
}
