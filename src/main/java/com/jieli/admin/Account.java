package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.AssociationDAO;
import com.jieli.association.IdentityDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private UserDAO userDAO = new UserDAO();
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
        if (!IdentityUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return CommonUtil.errorReturn;
        }

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        params.put("username",account.username);
        boolean isSuper = IdentityUtils.isSuper(sessionId);
        params.put("isSuper",isSuper);
        String associationOps = "";
        String identityOps = "<option value='' selected='selected'>普通会员</option>";
        List<Association> associationList = new ArrayList<Association>();
        if (isSuper) {
            Iterable<com.jieli.association.Association> iterable = associationDAO.loadAll();
            for(com.jieli.association.Association association : iterable)
                associationOps += "<option value='"+association.get_id() + "'>"+association.name +"</option>";
        } else {
            Iterable<com.jieli.association.Identity> identities = new IdentityDAO().loadAll(IdentityUtils.getAssociationId(sessionId));
            for (com.jieli.association.Identity identity : identities)
                identityOps += "<option value='"+identity.name + "'>"+identity.name + "</option>";
        }
        params.put("associationOps",associationOps);
        params.put("identityOps",identityOps);

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
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    public String editUser(@CookieParam("u")String sessionId,@QueryParam("u")String u){
        if (!IdentityUtils.isAdmin(sessionId)) {
            return CommonUtil.errorReturn;
        }

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        String identityOps = "<option value='' selected='selected'>普通会员</option>";
        Iterable<com.jieli.association.Identity> identities = new IdentityDAO().loadAll(IdentityUtils.getAssociationId(sessionId));
        for (com.jieli.association.Identity identity : identities) {
            identityOps += "<option value='" + identity.name + "'>" + identity.name + "</option>";
        }
        params.put("identityOps", identityOps);

        com.jieli.common.entity.Account targetAccount = accountDAO.loadById(u);
        if (targetAccount == null || targetAccount.userId == null){
            params.put("user","");
            params.put("got","此账户未绑定用户");
            return FTLrender.getResult("edit_account.ftl", params);
        }

        User user = userDAO.loadById(targetAccount.userId);
        if (user != null){
            params.put("user",CommonUtil.ReplaceObjectId(user));
            params.put("got","");
        }else{
            params.put("user","");
            params.put("got","无此用户");
        }
        return FTLrender.getResult("edit_account.ftl", params);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String loadUsers(@CookieParam("u")String sessionId,@QueryParam("id")String id /*,@QueryParam("state")int state*/) throws JsonProcessingException {
        if (!IdentityUtils.isAdmin(sessionId) || IdentityUtils.isSuper(sessionId)) {
            return CommonUtil.errorReturn;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        ObjectMapper om = new ObjectMapper();
        String associationId = null;
        //List<com.jieli.common.entity.Account> accounts = new ArrayList<com.jieli.common.entity.Account>();
        String accountList = "[";
        if (IdentityUtils.getState(sessionId) == AccountState.SUPPER) {
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for (com.jieli.association.Association association : associations) {
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
            associationId = IdentityUtils.getAssociationId(sessionId);
            com.jieli.association.Association association = associationDAO.loadById(associationId);
            if (association == null) {
                responseEntity.code = 2102;
                responseEntity.msg = "协会不存在";
                return CommonUtil.errorReturn;
            }

            Iterable<com.jieli.common.entity.Account> accountEnable = accountDAO.loadByAssociationId(associationId.toString(),AccountState.ENABLE);
            for (com.jieli.common.entity.Account account : accountEnable) {
                User user = userDAO.loadById(account.userId);
                String phoneSub = user.phone.substring(5, 11);
                accountList += CommonUtil.ReplaceObjectId(account).replace("}",",\"name\":\""+ CommonUtil.TransferNull(user == null ? "" : user.name)
                        + "\",\"identity\":\"" + CommonUtil.TransferNull(user == null ? "" : user.identity)
                 + "\",\"phone\":\"" + CommonUtil.TransferNull(user == null ? "" : phoneSub) + "\"},");
            }
        }

        accountList = CommonUtil.RemoveLast(accountList, ",") + "]";

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);
        params.put("jsonAccList",accountList);
        params.put("username",accountDAO.loadById(sessionId).username);

        return FTLrender.getResult("account_list.ftl", params);
    }

    @POST
    @Path("/del")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteAccount(@CookieParam("u") String sessionId, com.jieli.common.entity.Account account){
        ResponseEntity responseEntity = new ResponseEntity();

        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        try{
            accountDAO.deleteById(account.get_id().toString());
        }catch (Exception e){
            ;
        }

        responseEntity.code = 200;

        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/reuser")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response initUserInfo(@CookieParam("u") String sessionId, com.jieli.user.entity.User user) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();

        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        String id = user.get_id().toString();
        User u = userDAO.loadById(id);
        if (u != null){
            user.associationId = u.associationId;
            userDAO.save(user);
        }
        responseEntity.code = 200;
        responseEntity.msg = "成功创建用户"+user.name;

        return  Response.status(200).entity(responseEntity).build();
    }


    @POST
    @Path("/atgroup")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addToGroup (@CookieParam("u") String sessionId, @QueryParam("uname") String userId, @QueryParam("group") String group) {

        User user = userDAO.loadById(userId);

        ResponseEntity responseEntity = new ResponseEntity();
        if (CommonUtil.RoleCheckResponse(sessionId) != null || user == null || (!IdentityUtils.isSuper(sessionId) && !user.associationId.equals(IdentityUtils.getAssociationId(sessionId)))) {
            responseEntity.code = 9001;
            responseEntity.msg = "无权限!";
            return Response.status(200).entity(responseEntity).build();
        }

        if (user != null && user.group != null && !user.group.isEmpty()) responseEntity.msg = user.group;
        user.group = group;
        userDAO.update(user);

        responseEntity.code = 200;
        responseEntity.body = user.name;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/dfgroup")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteFromGroup(@CookieParam("u") String sessionId, @QueryParam("uname") String userId, @QueryParam("group") String group){
        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        User user = userDAO.loadById(userId);
        ResponseEntity responseEntity = new ResponseEntity();

        if (user == null) {responseEntity.code=9001;responseEntity.msg="无此用户";return Response.status(200).entity(responseEntity).build();}

        if (!IdentityUtils.isSuper(sessionId) && !user.associationId.equals(IdentityUtils.getAssociationId(sessionId))){responseEntity.code=9001;responseEntity.msg="无权限修改此用户的信息";return Response.status(200).entity(responseEntity).build();}

        if (user.group.equals(group)){
            user.group = "";
            userDAO.save(user);
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }else{
            responseEntity.code = 9003;
            responseEntity.msg = "此用户不属于改组";
            return Response.status(200).entity(responseEntity).build();
        }
    }


    @POST
    @Path("/atidentity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addToIdentity (@CookieParam("u") String sessionId, @QueryParam("uname") String userId, @QueryParam("group") String group) {

        User user = userDAO.loadById(userId);

        ResponseEntity responseEntity = new ResponseEntity();
        if (CommonUtil.RoleCheckResponse(sessionId) != null || user == null || (!IdentityUtils.isSuper(sessionId) && !user.associationId.equals(IdentityUtils.getAssociationId(sessionId)))) {
            responseEntity.code = 9001;
            responseEntity.msg = "无权限!";
            return Response.status(200).entity(responseEntity).build();
        }

        if (user != null && user.identity != null && !user.identity.isEmpty()) responseEntity.msg = user.identity;
        user.identity = group;
        userDAO.update(user);

        responseEntity.code = 200;
        responseEntity.body = user.name;
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/dfidentity")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteFromIdentity(@CookieParam("u") String sessionId, @QueryParam("uname") String userId, @QueryParam("group") String group){
        Response response = CommonUtil.RoleCheckResponse(sessionId);
        if (response != null) return response;

        User user = userDAO.loadById(userId);
        ResponseEntity responseEntity = new ResponseEntity();

        if (user == null) {responseEntity.code=9001;responseEntity.msg="无此用户";return Response.status(200).entity(responseEntity).build();}

        if (!IdentityUtils.isSuper(sessionId) && !user.associationId.equals(IdentityUtils.getAssociationId(sessionId))){responseEntity.code=9001;responseEntity.msg="无权限修改此用户的信息";return Response.status(200).entity(responseEntity).build();}

        if (user.identity.equals(group)){
            user.identity = "";
            userDAO.save(user);
            responseEntity.code = 200;
            return Response.status(200).entity(responseEntity).build();
        }else{
            responseEntity.code = 9003;
            responseEntity.msg = "此用户不属于改组";
            return Response.status(200).entity(responseEntity).build();
        }
    }

}
