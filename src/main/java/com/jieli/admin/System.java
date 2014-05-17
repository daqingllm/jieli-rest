package com.jieli.admin;

import com.jieli.association.*;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.SystemInfo;
import com.jieli.mongo.BaseDAO;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentityUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 95 on 2014/5/12.
 */
@Singleton
@Path("/bsys")
public class System {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    BaseDAO<SystemInfo> systemDAO = new BaseDAO<SystemInfo>("systeminfo", SystemInfo.class);

    /*关于接力中国*/
    @GET
    @Path("/aboutjieli")
    @Produces(MediaType.TEXT_HTML)
    public String getAboutJieli(@CookieParam("u") String sessionId) {
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        if (account==null || account.state != AccountState.ADMIN){
            return CommonUtil.errorReturn;
        }

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        String content = "尚无系统信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info!= null && StringUtils.isNotEmpty(info.aboutJieli)) {
            content = info.aboutJieli;
        }

        params.put("content",content);
        return FTLrender.getResult("about_jieli.ftl",params);
    }

    /*关于软件*/
    @GET
    @Path("/aboutsystem")
    @Produces(MediaType.TEXT_HTML)
    public String getAboutSystem(@CookieParam("u") String sessionId) {
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        if (account==null || account.state != AccountState.SUPPER){
            return CommonUtil.errorReturn;
        }

        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        String content = "尚无软件信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info!= null && StringUtils.isNotEmpty(info.aboutSystem)) {
            content = info.aboutSystem.replaceAll("\n", "<br/>");
        }

        params.put("content",content);
        return FTLrender.getResult("about_system.ftl",params);
    }
}

