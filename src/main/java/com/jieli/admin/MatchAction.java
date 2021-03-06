package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.feature.match.Match;
import com.jieli.feature.match.MatchDAO;
import com.jieli.feature.match.MatchDisplay;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;

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

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String getAccountList(@CookieParam("u")String sessionId) throws JsonProcessingException {
        if (!IdentityUtils.isSuper(sessionId)) {
            return CommonUtil.errorReturn;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        ObjectMapper om = new ObjectMapper();
        String associationId = null;
        List<Account> accountList = new ArrayList<Account>();
        Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
        for (com.jieli.association.Association association : associations) {
            Iterable<Account> accountEnable = accountDAO.loadByAssociationId(association.get_id().toString(),
                    AccountState.ENABLE);
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
        Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        params.put("jsonAccList",jsonAccountList);
        params.put("username",accountDAO.loadById(sessionId).username);

        return FTLrender.getResult("match_account_list.ftl", params);
    }

    @Path("/view")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String viewTopMatch(@CookieParam("u")String sessionId, @QueryParam("c")String center, @QueryParam("count")int count) {
        Account self = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(self);
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        params.put("isHistory", false);
        if (!IdentityUtils.isValidate(center)){
            return FTLrender.getResult("error.ftl", params);
        }
        String  userId = IdentityUtils.getUserId(center);
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
            display.infos.add("test");
            results.add(display);
        }
        params.put("displayList", results);
        return FTLrender.getResult("match_view.ftl", params);
    }

    @Path("/history")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String viewMatchHistory(@CookieParam("u")String sessionId) {
        Account self = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(self);
        if(!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }
        if(!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }
        params.put("isHistory", true);
        Integer count = 30;
        Iterable<Match> matches = matchDAO.getTopMatch(count);
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
        return FTLrender.getResult("match_history.ftl", params);
    }

}
