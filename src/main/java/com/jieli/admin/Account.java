package com.jieli.admin;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.FTLrender;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
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
    private AccountDAO accountDAO = new AccountDAO();

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public String GetLoginPage(){
        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("isOk", true);
        //params.put("message", "hello 大骏！");
        return FTLrender.getResult("login.ftl", params);
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
}
