package com.jieli.service;

import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.common.dao.AccountDAO;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/account")
public class AccountService {

    private AccountDAO accountDAO = new AccountDAO();
    private UserDAO userDAO = new UserDAO();

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(@HeaderParam("app")String appInfo, Map<String, String> loginInfo) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();
        String username = loginInfo.get("username");
        Account account = accountDAO.loadByUsername(username);
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
            responseEntity.code = 200;
            responseEntity.msg = "登陆成功";
            JSONObject json = new JSONObject();
            json.put("sessionId", account.get_id());
            responseEntity.body = json.toString();
            return Response.status(200).entity(responseEntity).build();
        }
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response register(@CookieParam("u")String sessionId, Map<String, String> registerInfo) throws JSONException {
        if (!IdentifyUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        String associationId = IdentifyUtils.getAssociationId(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        String username = registerInfo.get("username");
        Account account = accountDAO.loadByUsername(username);
        if (account != null) {
            responseEntity.code = 1011;
            responseEntity.msg = "用户名已存在";
            return Response.status(200).entity(responseEntity).build();
        } else {
            String password = PasswordGenerator.getRandomString(8);
            User user = new User();
            user.associationId = associationId;
            String userId = userDAO.save(user).get_id().toString();
            Account newAccount = new Account();
            newAccount.username = username;
            newAccount.password = PasswordGenerator.md5Encode(password);
            newAccount.userId = userId;
            newAccount.state = AccountState.ENABLE;
            newAccount.associationId = associationId;
            accountDAO.save(newAccount);

            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("password", password);
            responseEntity.code = 200;
            responseEntity.msg = "注册成功";
            responseEntity.body = json.toString();
            return Response.status(200).entity(responseEntity).build();
        }
    }

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response authorize(@CookieParam("u")String sessionId, Map<String, String> registerInfo) throws JSONException {
        if (!IdentifyUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String username = registerInfo.get("username");
        String associationId = registerInfo.get("associationId");
        Account account = accountDAO.loadByUsername(username);
        if (account != null) {
            responseEntity.code = 1011;
            responseEntity.msg = "用户名已存在";
            return Response.status(200).entity(responseEntity).build();
        } else {
            String password = PasswordGenerator.getRandomString(8);
            User user = new User();
            user.associationId = associationId;
            String userId = userDAO.save(user).get_id().toString();
            Account newAccount = new Account();
            newAccount.username = username;
            newAccount.password = PasswordGenerator.md5Encode(password);
            newAccount.userId = userId;
            newAccount.state = AccountState.ADMIN;
            newAccount.associationId = associationId;
            accountDAO.save(newAccount);

            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("password", password);
            responseEntity.code = 200;
            responseEntity.msg = "注册成功";
            responseEntity.body = json.toString();
            return Response.status(200).entity(responseEntity).build();
        }
    }
}
