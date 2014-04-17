package com.jieli.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.common.dao.AccountDAO;
import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-17.
 */
@Singleton
@Path("bhelp")
public class Help {
    private HelpDAO helpDAO = new HelpDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

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
    @Path("view")
    @Produces(MediaType.TEXT_HTML)
    public String getHelpList(@CookieParam("u")String sessionId, @QueryParam("associationId")String associationId) {
        if(!IdentifyUtils.isValidate(sessionId)) {
            return errorReturn;
        }
        if(!IdentifyUtils.isAdmin(sessionId)) {
            return errorReturn;
        }
        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        if (account == null || account.username == null || account.username == ""){
            return errorReturn;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", account.username);
        if(!MongoUtils.isValidObjectId(associationId)) {
            return FTLrender.getResult("error.ftl", params);
        }
        boolean isSuper = false;
        if(IdentifyUtils.isSuper(sessionId))
            isSuper = true;
        if(isSuper) {
            List<com.jieli.association.Association> associationList = new ArrayList<com.jieli.association.Association>();
            Iterable<com.jieli.association.Association> associationIte = associationDAO.loadAll();
            for(com.jieli.association.Association a : associationIte) {
                associationList.add(a);
            }
            params.put("associationList", associationList);
            associationId = associationList.get(0).get_id().toString();
        }
        Integer pageNo = 1;
        Integer pageSize = 50;
        List<SimpleHelpInfo> helpList = helpDAO.getHelpInfoList(pageNo, pageSize, associationId);

        String jsonVoteList;
        int i;
        ObjectMapper om = new ObjectMapper();
        try { //this is a trick, write Object list to json, read json to Java list add the attribute and rewrite to json
            String tmp = om.writeValueAsString(helpList);

            List<HashMap<String, Object>> l = (List<HashMap<String, Object>>)om.readValue(tmp, List.class);
            for(Map<String, Object> obj : l) {
                if (obj.get("associationId") instanceof String) {
                    Association association = associationDAO.loadById(obj.get("associationId").toString());
                    obj.put("associationName", association.name);
                }
            }
            jsonVoteList = om.writeValueAsString(l);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVoteList = "[]";
        }
        params.put("jsonVoteList", jsonVoteList);
        params.put("isSuper", isSuper);
        return FTLrender.getResult("help_list.ftl", params);
    }
}
