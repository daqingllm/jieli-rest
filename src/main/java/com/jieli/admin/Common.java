package com.jieli.admin;

import com.jieli.activity.*;
import com.jieli.activity.Activity;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.util.IdentifyUtils;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by 95 on 2014/4/16.
 */
public class Common {
    /* 如果发生权限错误，显示错误信息，并跳转至登陆页 */
    /* 暂时还没有样式设置 */
    public static final String errorReturn = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "        <title>请先登录</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "\n" +
            "\t<span>您尚未登录或无权限操作，</span>\n" +
            "\t<span id=\"totalSecond\">5</span>秒后跳转到登陆页..</span>\n" +
            "\n" +
            "\t<script language=\"javascript\" type=\"text/javascript\">\n" +
            "\t/*alert(document.cookie);*/\n" +
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

    /* 协会相关函数 */
    private static AssociationDAO associationDAO = new AssociationDAO();

    public static String MakeAssociationOptionListForSelect(String associationId){
        String assIdOptionList = "";
        if (associationId == "" || associationId == "undefined"){
            Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
            for (com.jieli.association.Association association : associations) {
                assIdOptionList += "<option value='"+association.get_id()+"'>"+association.name+"</option>";
            }
        }else{
            Association association = associationDAO.loadById(associationId);
            assIdOptionList += "<option value='"+associationId+"' selected>"+association.name+"</option>";
        }
        return assIdOptionList;
    }

    /* activity related functions */

    // 此复制功能用于一次批量插入多个活动，内容相同，协会不同，故不设置协会
    public static com.jieli.activity.Activity copyDeepClean(Activity actSrc){
        Activity actDes = new Activity();
        actDes.tag = actSrc.tag;
        actDes.type = actSrc.type;
        actDes.beginDate = new Date(actSrc.beginDate.getTime());
        actDes.location = actSrc.location;
        actDes.title = actSrc.title;
        actDes.description = actSrc.description;
        actDes.fee = actSrc.fee;
        actDes.maxMembers = actSrc.maxMembers;
        actDes.arrangement = actSrc.arrangement;
        actDes.serviceInfo = actSrc.serviceInfo;
        actDes.sponsorInfo = actSrc.sponsorInfo;
        actDes.addTime = new Date();
        actDes.actDate = new Date(actSrc.actDate.getTime());
        actDes = actSrc ;
        actDes.followMembers = new ArrayList<String>();
        actDes.details = new ArrayList<Arrangement>();
        for (Arrangement arrangement : actSrc.details)
            actDes.details.add(arrangement);
        actDes.joinMembers = new LinkedHashMap<String, String>();
        actDes.invitees = new ArrayList<String>();
        actDes.sponsorUserId = actSrc.sponsorUserId;
        actDes.url = actSrc.url;

        return  actDes;
    }

    /* Accounts */
    public static String RoleCheckString(String sessionId){
        // is user ?
        if (!IdentifyUtils.isValidate(sessionId)){
            return errorReturn;
        }

        if (!IdentifyUtils.isSuper(sessionId) && !IdentifyUtils.isAdmin(sessionId)){
            return errorReturn;
        }

        return null;
    }

    public static Response RoleCheckResponse(String sessionId){
        // is user ?
        if (!IdentifyUtils.isValidate(sessionId)){
            return Response.status(403).build();
        }

        if (!IdentifyUtils.isSuper(sessionId) && !IdentifyUtils.isAdmin(sessionId)){
            return Response.status(403).build();
        }

        return null;
    }
}
