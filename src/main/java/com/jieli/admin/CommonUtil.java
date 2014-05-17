package com.jieli.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jieli.activity.*;
import com.jieli.activity.Activity;
import com.jieli.association.*;
import com.jieli.association.Association;
import com.jieli.common.entity.*;
import com.jieli.mongo.Model;
import com.jieli.util.IdentityUtils;

import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by 95 on 2014/4/16.
 */
public class CommonUtil {
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
            "\t<span id=\"totalSecond\">2</span>秒后跳转到登陆页..</span>\n" +
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
            "\t\t\t\tlocation.href = '/app/baccount/login';\n" +
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

    public static String MakeInterestOptionList(){
        String all [] = InterestTag.ALL;
        String interestOptionList = "";
        for (String it : all){
            interestOptionList += "<option value='"+it+"'>"+it+"</option>";
        }
        return interestOptionList;
    }

    public static String MakeProfessionOptionList(){
        String all [] = ProfessionTag.ALL;
        String professionOptionList = "";
        for (String it : all){
            professionOptionList += "<option value='"+it+"'>"+it+"</option>";
        }
        return professionOptionList;
    }

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
        if (!IdentityUtils.isValidate(sessionId)){
            return errorReturn;
        }

        if (!IdentityUtils.isSuper(sessionId) && !IdentityUtils.isAdmin(sessionId)){
            return errorReturn;
        }

        return null;
    }

    /* common */
    public static Response RoleCheckResponse(String sessionId){
        // is user ?
        if (!IdentityUtils.isValidate(sessionId)){
            return Response.status(403).build();
        }

        if (!IdentityUtils.isSuper(sessionId) && !IdentityUtils.isAdmin(sessionId)){
            return Response.status(403).build();
        }

        return null;
    }

    public static String ReplaceObjectId(Model o){
        ObjectMapper objectMapper = new ObjectMapper();
        String ret = "";
        try {
            String tmp = objectMapper.writeValueAsString(o).toString();
            String tmpObjectId = objectMapper.writeValueAsString(o.get_id());
            String tmpId = o.get_id().toString();

            ret = tmp.replace(tmpObjectId,"\""+tmpId+"\"");
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
            ret = "";
        }
        return ret;
    }

    public static String TransferNull(String s){
        return s == null ? "" : s;
    }

    public static String RemoveLast(String src, String last){
        if (src == null) return  null;
        if (src.length() == 0) return src;
        if (last == null || last.length() == 0) return  src;

        if (src.length() < last.length()) return  src;

        if (src.endsWith(last)) return src.substring(0,src.length()-last.length());
        else  return src;
    }

    public static Map<String, Object> GenerateCommonParams(com.jieli.common.entity.Account account){
        if (account == null){
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        com.jieli.association.Association association = associationDAO.loadById(account.associationId);
        params.put("username",account.username);
        params.put("isSuper",account.state == AccountState.SUPPER);
        if(association!=null)
            params.put("associationName",association.name);
        else
            params.put("associationName","接力");

        return params;
    }
}
