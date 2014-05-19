package com.jieli.service;

import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.common.dao.AccountDAO;
import com.jieli.util.IdentityUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
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

    public AccountService() {
        Account account = accountDAO.loadByUsername("super");
        if (account == null) {
            account = new Account();
            User user = new User();
            userDAO.save(user);
            account.userId = user.get_id().toString();
            account.username = "super";
            account.password = "1b3231655cebb7a1f783eddf27d254ca";
            account.state = AccountState.SUPPER;
            accountDAO.save(account);
        }
    }

    @Path("/applogin")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response appLogin(@HeaderParam("app")String appInfo, Map<String, String> loginInfo) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();
        String name = loginInfo.get("name");
        String phone = loginInfo.get("phone");
        String password = loginInfo.get("password");
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            responseEntity.code = 1009;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        User user = userDAO.findLoginUser(name, phone);
        if (user == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        Account account = accountDAO.loadByUserId(user.get_id().toString());
        if (account == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        String temPassword = generateTempPassword(phone);
        if (!password.equals(temPassword)) {
            responseEntity.code = 1002;
            responseEntity.msg = "密码不正确";
            return Response.status(200).entity(responseEntity).build();
        }
        responseEntity.code = 200;
        responseEntity.msg = "登陆成功";
        JSONObject json = new JSONObject();
        json.put("sessionId", account.get_id().toString());
        json.put("associationId", account.associationId);
        json.put("role",account.state);
        json.put("userId", account.userId);
        responseEntity.body = json.toString();
        return Response.status(200).entity(responseEntity).build();
    }

    private String generateTempPassword(String phone) {
        int length = phone.length();
        if (length < 6) {
            return phone;
        }
        return phone.substring(length - 6);
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response login(Map<String, String> loginInfo) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();
        String username = loginInfo.get("name");
        Account account = accountDAO.loadByUsername(username);
        if (account == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户名不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        String password = loginInfo.get("password");
        if (!account.password.equals(password)) {
            responseEntity.code = 1002;
            responseEntity.msg = "密码不正确";
            return Response.status(200).entity(responseEntity).build();
        } else {
            responseEntity.code = 200;
            responseEntity.msg = "登陆成功";
            JSONObject json = new JSONObject();
            json.put("sessionId", account.get_id());
            json.put("associationId", account.associationId);
            json.put("role",account.state);
            json.put("userId", account.userId);
            responseEntity.body = json.toString();
            return Response.status(200).entity(responseEntity).build();
        }
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response register(@CookieParam("u")String sessionId, Map<String, String> registerInfo) throws JSONException {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        String associationId = null;
        ResponseEntity responseEntity = new ResponseEntity();
        if (IdentityUtils.isSuper(sessionId)) {
            associationId = registerInfo.get("associationId");
            if (StringUtils.isEmpty(associationId)) {
                responseEntity.code = 1016;
                responseEntity.msg = "超级账户注册用户需提供associationId";
                return Response.status(200).entity(responseEntity).build();
            }
        }
        associationId = IdentityUtils.getAssociationId(sessionId);
        String username = registerInfo.get("username");
        if (StringUtils.isEmpty(username) || username.length() < 5) {
            responseEntity.code = 1015;
            responseEntity.msg = "用户名为空或长度小于五";
            return Response.status(200).entity(responseEntity).build();
        }
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
            newAccount.password = password;
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
        if (!IdentityUtils.isSuper(sessionId)) {
            return Response.status(403).build();
        }
        ResponseEntity responseEntity = new ResponseEntity();
        String username = registerInfo.get("username");
        String associationId = registerInfo.get("associationId");
        if (StringUtils.isEmpty(associationId)) {
            responseEntity.code = 1016;
            responseEntity.msg = "超级账户注册用户需提供associationId";
            return Response.status(200).entity(responseEntity).build();
        }

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
            newAccount.password = password;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response changeAccount(@CookieParam("u")String sessionId, Account account) {
        ResponseEntity responseEntity = new ResponseEntity();
        Account current = accountDAO.loadByUsername(account.username);
        if (current == null) {
            responseEntity.code = 1001;
            responseEntity.msg = "用户名不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        if (current.state.value() <= AccountState.ADMIN.value() && !IdentityUtils.isSuper(sessionId)) {
            //协会管理员
//            if (!IdentityUtils.isAdmin(sessionId) || account.state.equals(AccountState.SUPPER)
//                    || !account.associationId.equals(current.associationId)
//                    || !account.userId.equals(account.userId)) {
//                return Response.status(403).build();
//            }
            if (!IdentityUtils.isAdmin(sessionId) || account.state.equals(AccountState.SUPPER) || !(current.associationId.equals(IdentityUtils.getAssociationId(sessionId)))) {
                return Response.status(403).build();
            }
            if (StringUtils.isNotEmpty(account.password)) {
                current.password = account.password;
            }
            current.state = account.state;
            accountDAO.save(current);
            if (current.state == AccountState.DISABLE) {
                userDAO.deleteById(current.userId);
            }
        } else if (IdentityUtils.isSuper(sessionId)){
//            account.set_id(current.get_id());
//            account.password = PasswordGenerator.md5Encode(account.password);
            User user = userDAO.loadById(account.userId);
            if (StringUtils.isNotEmpty(account.password)) {
                current.password = account.password;
            }
            if (StringUtils.isNotEmpty(account.associationId)) {
                current.associationId = account.associationId;
                if (user!=null)
                user.associationId = account.associationId;
            }
            current.state = account.state;
            accountDAO.save(current);
            if (user!=null)
            userDAO.save(user);
        }

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
    }
}
