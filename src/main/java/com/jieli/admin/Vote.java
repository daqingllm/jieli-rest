package com.jieli.admin;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.entity.VoteInfo;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-10.
 */
@Singleton
@Path("bvote")
public class Vote {
    private AccountDAO accountDAO = new AccountDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private String errorReturn = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "        <title>请先登录</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "\n" +
            "\t<span>您尚未登录，</span>\n" +
            "\t<span id=\"totalSecond\">5</span>秒后跳转到登陆页..</span>\n" +
            "\n" +
            "\t<script language=\"javascript\" type=\"text/javascript\">\n" +
            "\talert(document.cookie);\n" +
            "\t\tvar second = document.getElementById('totalSecond').textContent;\n" +
            "\n" +
            "\t\tif (navigator.appName.indexOf(\"Explorer\") > -1)\n" +
            "\t\t\tsecond = document.getElementById('totalSecond').innerText;\n" +
            "\t\telse\n" +
            "\t\t\tsecond = document.getElementById('totalSecond').textContent;\n" +
            "\n" +
            "\t\tsetInterval(\"redirect()\", 1000);\n" +
            "\t\t\n" +
            "\t\tfunction redirect()\n" +
            "\t\t{\n" +
            "\t\t\tif (second < 0)\n" +
            "\t\t\t\tlocation.href = '/rest/baccount/login';\n" +
            "\t\t\telse {\n" +
            "\t\t\t\tif (navigator.appName.indexOf(\"Explorer\") > -1)\n" +
            "\t\t\t\t\tdocument.getElementById('totalSecond').innerText = second--;\n" +
            "\t\t\t\telse\n" +
            "\t\t\t\t\tdocument.getElementById('totalSecond').textContent = second--;\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t</script>\n" +
            "    </body>\n" +
            "</html>";

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String createVote(@CookieParam("u")String sessionId) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username",account.username);
        params.put("newVote", true);
        params.put("isEditable", true);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/view")
    public String viewVote(@CookieParam("u") String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("newVote", false);
        params.put("isEditable", false);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/edit")
    public String modifyVote(@CookieParam("u")String sessionId, @QueryParam("voteId")String voteId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        if(!MongoUtils.isValidObjectId(voteId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        params.put("newVote", false);
        params.put("isEditable", true);
        params.put("voteId", voteId);
        return FTLrender.getResult("voteinfo.ftl", params);
    }
}
