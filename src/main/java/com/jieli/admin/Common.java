package com.jieli.admin;

import com.jieli.association.*;
import com.jieli.association.Association;

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
}
