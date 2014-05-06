package com.jieli.admin;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
@Path("/bindex")
public class HelloWorld {
    private AccountDAO accountDAO = new AccountDAO();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String Index(@CookieParam("u") String sessionId){
        if (!IdentifyUtils.isValidate(sessionId)) {
            return CommonUtil.errorReturn;
        }

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return CommonUtil.errorReturn;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        if (account.state.compareTo(AccountState.SUPPER)==0)
            params.put("isSuper",true);
        else
            params.put("isSuper",false);

        params.put("username",account.username);

        return FTLrender.getResult("index.ftl", params);
    }

    @GET
    @Path("/render")
    @Produces(MediaType.TEXT_HTML)
    public String test(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isOk", true);
        params.put("message", "hello 大骏！");
        return FTLrender.getResult("helloworld.ftl", params);
    }


}
