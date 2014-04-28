package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.common.entity.Account;
import com.jieli.feature.match.Match;
import com.jieli.feature.match.MatchDAO;
import com.jieli.feature.match.MatchDisplay;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.*;
import com.jieli.user.entity.User;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.jongo.marshall.jackson.oid.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-24.
 */
@Singleton
@Path("bmatch")
public class MatchAction {
    private AssociationDAO associationDAO = new AssociationDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private UserDAO userDAO = new UserDAO();
    private MatchDAO matchDAO = new MatchDAO();

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
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String getAccountList(@CookieParam("u")String sessionId) throws JsonProcessingException {
        if (!IdentifyUtils.isAdmin(sessionId) || IdentifyUtils.isSuper(sessionId)) {
            return Common.errorReturn;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        ObjectMapper om = new ObjectMapper();
        String associationId = null;
        List<Account> accountList = new ArrayList<Account>();
        //List<com.jieli.common.entity.Account> accounts = new ArrayList<com.jieli.common.entity.Account>();
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for (com.jieli.association.Association association : associations) {
                Iterable<Account> accountAdmin = accountDAO.loadByAssociationId(association.get_id().toString(),
                        AccountState.ADMIN);
                Iterable<Account> accountEnable = accountDAO.loadByAssociationId(association.get_id().toString(),
                        AccountState.ENABLE);
                for(Account a : accountAdmin) {
                    if(a.username == null) {
                        a.username = "";
                    }
                    accountList.add(a);
                }
                for (Account b : accountEnable) {
                    if(b.username == null) {
                        b.username = "";
                    }
                    accountList.add(b);
                }
            }

        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
            com.jieli.association.Association association = associationDAO.loadById(associationId);
            if (association == null) {
                responseEntity.code = 2102;
                responseEntity.msg = "协会不存在";
                return Common.errorReturn;
            }
            Iterable<com.jieli.common.entity.Account> accountAdmin = accountDAO
                    .loadByAssociationId(associationId.toString(),AccountState.ADMIN);
            Iterable<com.jieli.common.entity.Account> accountEnable = accountDAO
                    .loadByAssociationId(associationId.toString(),AccountState.ENABLE);
            for(Account a : accountAdmin) {
                if(a.username == null) {
                    a.username = "";
                }
                accountList.add(a);
            }
            for (Account b : accountEnable) {
                if(b.username == null) {
                    b.username = "";
                }
                accountList.add(b);
            }
        }
        //trick, save _id str in password, to make url in jqgrid
        for(Account a : accountList) {
            a.password = a.get_id().toString();
        }

        String jsonAccountList;
        int i;
        try {
            String tmp = om.writeValueAsString(accountList);
            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>)om.readValue(tmp, List.class);
            for(Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
                String id = obj.get("password").toString();
                Account account = accountDAO.loadById(id);
                String name = userDAO.loadById(account.userId).name;
                obj.put("name", name);
            }
            jsonAccountList = om.writeValueAsString(l);
        } catch (IOException e) {
            e.printStackTrace();
            jsonAccountList = "[]";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isSuper",IdentifyUtils.getState(sessionId) == AccountState.SUPPER);
        params.put("jsonAccList",jsonAccountList);
        params.put("username",accountDAO.loadById(sessionId).username);

        return FTLrender.getResult("match_account_list.ftl", params);
    }

    @Path("/view")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String viewTopMatch(@CookieParam("u")String sessionId, @QueryParam("c")String center, @QueryParam("count")int count) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        params.put("isSuper", isSuper);
        Account self = accountDAO.loadById(sessionId);
        params.put("username", self.username);
        if (!IdentifyUtils.isValidate(center)){
            return FTLrender.getResult("error.ftl", params);
        }
        String  userId = IdentifyUtils.getUserId(center);
        if(count <= 0) {
            count = 5;
        }
        Iterable<Match> matches = matchDAO.getTopMatchByUserId(userId, count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            results.add(display);
        }
        params.put("displayList", results);
        return FTLrender.getResult("match_view.ftl", params);
    }

    @Path("/history")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String viewMatchHistory(@CookieParam("u")String sessionId, @QueryParam("c")String center, @QueryParam("count")int count) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        params.put("isSuper", isSuper);
        Account self = accountDAO.loadById(sessionId);
        params.put("username", self.username);
        if (!IdentifyUtils.isValidate(center)){
            return FTLrender.getResult("error.ftl", params);
        }
        String  userId = IdentifyUtils.getUserId(center);
        if(count <= 0) {
            count = 30;
        }
        Iterable<Match> matches = matchDAO.getTopMatchByUserId(userId, count);
        List<MatchDisplay> results = new ArrayList<MatchDisplay>();
        for (Match match : matches) {
            MatchDisplay display = new MatchDisplay();
            User user1 = userDAO.loadById(match.userId1);
            display.userId1 = user1.get_id().toString();
            display.name1 = user1.name;
            display.userFace1 = user1.userFace;
            User user2 = userDAO.loadById(match.userId2);
            display.userId2 = user2.get_id().toString();
            display.name2 = user2.name;
            display.userFace2 = user2.userFace;
            display.score = match.score;
            display.infos = match.matchInfos;
            results.add(display);
        }
        params.put("displayList", results);
        return FTLrender.getResult("match_view.ftl", params);
    }

}
