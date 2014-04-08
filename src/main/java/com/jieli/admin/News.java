package com.jieli.admin;

import com.jieli.common.dao.AccountDAO;
import com.jieli.user.dao.UserDAO;
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
@Path("/bnews")
public class News {

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public String CreateNews(@CookieParam("u")String sessionId) {

        String errorReturn = "<!DOCTYPE html>\n" +
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

        // 判断用户是否已经登录
        if (!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }

        // 载入用户
        //String userId = IdentifyUtils.getUserId(sessionId);
        //com.jieli.user.entity.User user = userDAO.loadById(userId);

        //com.jieli.common.entity.Account account = accountDAO.getCollection().findOne("{username:#}",userId).as(com.jieli.common.entity.Account.class);
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        // 判断用户是否有效
        //if (user == null || user.name == null || user.name == "") {
        //    return errorReturn;
        //}

        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        params.put("username",account.username);
        //params.put("username",user.name);

        return FTLrender.getResult("new_article.ftl", params);
    }

    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();

}
