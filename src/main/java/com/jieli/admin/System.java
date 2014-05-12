package com.jieli.admin;

import com.jieli.comment.CommentUserInfo;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.ResponseEntity;
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
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 95 on 2014/5/12.
 */
@Singleton
@Path("/bsys")
public class System {
    private AccountDAO accountDAO = new AccountDAO();
    BaseDAO<SystemInfo> systemDAO = new BaseDAO<SystemInfo>("systeminfo", SystemInfo.class);

    /*关于接力中国*/
    @GET
    @Path("/aboutjieli")
    @Produces(MediaType.TEXT_HTML)
    public String getAboutJieli(@CookieParam("u") String sessionId) {
        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return response;

        if (!IdentityUtils.isSuper(sessionId)) return CommonUtil.errorReturn;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);
        params.put("isSuper",true);

        String content = "尚无系统信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info!= null && StringUtils.isNotEmpty(info.aboutJieli)) {
            content = info.aboutJieli;
        }

        String contentAppend = content.replace("\"","").replace("\'","");
        if (content.indexOf("\"") > 0 || content.indexOf("\'") > 0) content = "请不要再内容中填写英文的单引号和双引号:"+contentAppend;

        params.put("content",content);
        return FTLrender.getResult("about_jieli.ftl",params);
    }

    /*关于软件*/
    @GET
    @Path("/aboutsystem")
    @Produces(MediaType.TEXT_HTML)
    public String getAboutSystem(@CookieParam("u") String sessionId) {

        String response = CommonUtil.RoleCheckString(sessionId);
        if (response != null) return response;

        if (!IdentityUtils.isSuper(sessionId)) return CommonUtil.errorReturn;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);
        params.put("isSuper",true);

        String content = "尚无软件信息";
        SystemInfo info = systemDAO.findOne("{}");
        if (info!= null && StringUtils.isNotEmpty(info.aboutSystem)) {
            content = info.aboutSystem;
        }

        String contentAppend = content.replace("\"","").replace("\'","");
        if (content.indexOf("\"") > 0 || content.indexOf("\'") > 0) content = "请不要再内容中填写英文的单引号和双引号:"+contentAppend;

        params.put("content",content);
        return FTLrender.getResult("about_system.ftl",params);
    }
}

