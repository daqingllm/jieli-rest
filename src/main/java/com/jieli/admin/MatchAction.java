package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.AccountState;
import com.jieli.common.entity.ResponseEntity;
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
 * Created by YolandaLeo on 14-4-24.
 */
@Singleton
@Path("bmatch")
public class MatchAction {
    private AssociationDAO associationDAO = new AssociationDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private UserDAO userDAO = new UserDAO();

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
    @Path("list")
    @Produces(MediaType.TEXT_HTML)
    public String getAccountList(@CookieParam("u")String sessionId) throws JsonProcessingException {
        if (!IdentifyUtils.isAdmin(sessionId) || IdentifyUtils.isSuper(sessionId)) {
            return Common.errorReturn;
        }
        ResponseEntity responseEntity = new ResponseEntity();
        ObjectMapper om = new ObjectMapper();
        String associationId = null;
        //List<com.jieli.common.entity.Account> accounts = new ArrayList<com.jieli.common.entity.Account>();
        String accountList = "[";
        if (IdentifyUtils.getState(sessionId) == AccountState.SUPPER) {
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
            associationId = IdentifyUtils.getAssociationId(sessionId);
            com.jieli.association.Association association = associationDAO.loadById(associationId);
            if (association == null) {
                responseEntity.code = 2102;
                responseEntity.msg = "协会不存在";
                return Common.errorReturn;
            }
            Iterable<com.jieli.common.entity.Account> accountAdmin = accountDAO.loadByAssociationId(associationId.toString(),AccountState.ADMIN);
            for (com.jieli.common.entity.Account account : accountAdmin)
                accountList += Common.ReplaceObjectId(account).replace("}",",\"name\":\""+Common.TransferNull(userDAO.loadById(account.userId) == null ? "" : userDAO.loadById(account.userId).name) + "\"},");

            Iterable<com.jieli.common.entity.Account> accountEnable = accountDAO.loadByAssociationId(associationId.toString(),AccountState.ENABLE);
            for (com.jieli.common.entity.Account account : accountEnable)
                accountList += Common.ReplaceObjectId(account).replace("}",",\"name\":\""+Common.TransferNull(userDAO.loadById(account.userId) == null ? "" : userDAO.loadById(account.userId).name) + "\"},");
        }

        accountList = Common.RemoveLast(accountList,",") + "]";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isSuper",IdentifyUtils.getState(sessionId) == AccountState.SUPPER);
        params.put("jsonAccList",accountList);
        params.put("username",accountDAO.loadById(sessionId).username);

        return FTLrender.getResult("match_account_list.ftl", params);
    }

}
