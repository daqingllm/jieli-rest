package com.jieli.service;

import com.jieli.dao.AccountDAO;
import com.jieli.dao.UserDAO;
import com.jieli.entity.common.Account;
import com.jieli.entity.common.ResponseEntity;
import com.jieli.entity.user.User;
import com.jieli.util.IdUtil;
import com.jieli.util.PasswordGenerator;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
            json.put("sessionId", account.getObjectId());
            responseEntity.body = json.toString();
            return Response.status(200).entity(responseEntity).build();
        }
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response register(Map<String, String> registerInfo) throws JSONException {
        ResponseEntity responseEntity = new ResponseEntity();
        String username = registerInfo.get("username");
        Account account = accountDAO.loadByUsername(username);
        if (account != null) {
            responseEntity.code = 1011;
            responseEntity.msg = "用户名已存在";
            return Response.status(200).entity(responseEntity).build();
        } else {
            String password = PasswordGenerator.getRandomString(8);
            Account newAccount = new Account();
            newAccount.username = username;
            newAccount.password = PasswordGenerator.md5Encode(password);
            int userId = IdUtil.getUserId();
            newAccount.userId = userId;
            accountDAO.save(newAccount);
            User user = new User();
            user.id = userId;
            userDAO.save(user);

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
