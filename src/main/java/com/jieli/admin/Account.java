package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
@Path("/baccount")
public class Account {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public String GetLoginPage(){
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("isOk", true);
        //params.put("message", "hello 大骏！");
        return FTLrender.getResult("login.ftl", params);
    }

    @GET
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public String editNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role){
        if (!IdentifyUtils.isValidate(sessionId)) {
            return News.errorReturn;
        }

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return News.errorReturn;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);
        boolean isSuper = IdentifyUtils.isSuper(sessionId);
        params.put("isSuper",isSuper);
        String associationOps = "";
        List<Association> associationList = new ArrayList<Association>();
        if (isSuper) {
            Iterable<Association> iterable = associationDAO.loadAll();
            for(Association association : iterable)
                associationOps += "<option value='"+association.get_id() + "'>"+association.name +"</option>";
        }
        params.put("associationOps",associationOps);

        return FTLrender.getResult("register.ftl", params);
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(@HeaderParam("app")String appInfo, Map<String, String> loginInfo) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();
        String username = loginInfo.get("username");
        com.jieli.common.entity.Account account = accountDAO.loadByUsername(username);
        if (account == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户名不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        String password = loginInfo.get("password");
        if (!account.password.equals(PasswordGenerator.md5Encode(password))) {
            responseEntity.code = 1002;
            responseEntity.msg = "密码不正确";
            return Response.status(200).entity(responseEntity).build();
        } else {
            if (account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
                responseEntity.code = 1003;
                responseEntity.msg = "用户无管理权限";
                return Response.status(200).entity(responseEntity).build();
            }
            else {
                responseEntity.code = 200;
                responseEntity.msg = "登陆成功";
                JSONObject json = new JSONObject();
                json.put("sessionId", account.get_id());
                json.put("associationId", account.associationId);
                json.put("role",PasswordGenerator.md5Encode(account.state+""));
                responseEntity.body = json.toString();
                return Response.status(200).entity(responseEntity).build();
            }
        }
    }


    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String loadUsers(@CookieParam("u")String sessionId,@QueryParam("id")String id /*,@QueryParam("state")int state*/) throws JsonProcessingException {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return News.errorReturn;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        ObjectMapper om = new ObjectMapper();
        String associationId = null;
        //List<com.jieli.common.entity.Account> accounts = new ArrayList<com.jieli.common.entity.Account>();
        String accountList = "[";
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
            Iterable<Association> associations = associationDAO.loadAll();
            for (Association association : associations) {
                Iterable<com.jieli.common.entity.Account> accountAdmin = accountDAO.loadByAssociationId(association.get_id().toString(), AccountState.ADMIN);
                for (com.jieli.common.entity.Account account : accountAdmin) {
                    String tmp = om.writeValueAsString(account);
                    if (tmp.indexOf("{\"time\":") > -1) {
                        tmp = tmp.substring(0, 7) + "\"" + account.get_id().toString() + "\"" + tmp.substring(tmp.indexOf("},", 7) + 1);
                    }
                    accountList += tmp + ",";
                }

                Iterable<com.jieli.common.entity.Account> accountEnable = accountDAO.loadByAssociationId(association.get_id().toString(), AccountState.ENABLE);
                for (com.jieli.common.entity.Account account : accountEnable) {
                    String tmp = om.writeValueAsString(account);
                    if (tmp.indexOf("{\"time\":") > -1) {
                        tmp = tmp.substring(0, 7) + "\"" + account.get_id().toString() + "\"" + tmp.substring(tmp.indexOf("},", 7) + 1);
                    }
                    accountList += tmp + ",";
                }
            }
        } else {
            associationId = IdentifyUtils.getAssociationId(sessionId);
            Association association = associationDAO.loadById(associationId);
            if (association == null) {
                responseEntity.code = 2102;
                responseEntity.msg = "协会不存在";
                return News.errorReturn;
            }
            Iterable<com.jieli.common.entity.Account> accountAdmin = accountDAO.loadByAssociationId(associationId.toString(),AccountState.ADMIN);
            for (com.jieli.common.entity.Account account : accountAdmin)
                accountList += om.writeValueAsString(account)+",";

            Iterable<com.jieli.common.entity.Account> accountEnable = accountDAO.loadByAssociationId(associationId.toString(),AccountState.ENABLE);
            for (com.jieli.common.entity.Account account : accountEnable)
                accountList += om.writeValueAsString(account)+",";
        }

        if (accountList.endsWith(",")) accountList = accountList.substring(0,accountList.length()-1)+"";
        accountList += "]";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isSuper",IdentifyUtils.getState(sessionId) == AccountState.SUPPER);
        params.put("jsonAccList",accountList);
        params.put("username",accountDAO.loadById(sessionId).username);

        return FTLrender.getResult("change_account.ftl", params);
    }
}
