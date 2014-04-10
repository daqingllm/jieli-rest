package com.jieli.admin;

import com.jieli.association.Association;
import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.news.Image;
import com.jieli.news.NewsDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.CollectionUtils;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.PasswordGenerator;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

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
    public String CreateNews(@CookieParam("u")String sessionId,@CookieParam("a")String associationId,@CookieParam("r")String role) {

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

        if (account == null || account.username == null || account.username == "" || account.state == AccountState.ENABLE || account.state == AccountState.DISABLE){
            return errorReturn;
        }


        Map<String, Object> params = new HashMap<String, Object>();
        // 这里用 user name ， 还是 account username ？
        // 目前super user name 是空的

        params.put("username",account.username);
        //params.put("username",user.name);


        boolean isSuper = false;
        String assIdOptionList = "";

        boolean b1 = associationId.equals("undefined");
        boolean b2 = PasswordGenerator.md5Encode(AccountState.SUPPER+"").equals(role);

        if (b1 && b2) {
            isSuper = true;
            Iterable<Association> associations = associationDAO.loadAll();
            for (Association association : associations) {
                //System.out.println(activity.get_id());
                assIdOptionList += "<option value='"+association.get_id()+"'>"+association.name+"</option>";
            }

            //assIdOptionList = "";
        }else {
            Association association = associationDAO.loadById(associationId);
            assIdOptionList += "<option value='"+associationId+"' selected>"+association.name+"</option>";
        }

        params.put("isSuper",isSuper);
        params.put("assIdOptionList",assIdOptionList);

        return FTLrender.getResult("new_article.ftl", params);
    }

    // 不仅要处理多associationid，还要转义json中的引号
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addNews(@CookieParam("u")String sessionId, com.jieli.news.News news){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        String sc = news.content;
        /*while(sc.indexOf("\\u0027") >= 0) sc = sc.replace("\\u0027","'");
        while(sc.indexOf("\\u0022") >= 0) sc = sc.replace("\\u0027","\"");*/

        ResponseEntity responseEntity = new ResponseEntity();
        if(news!=null){
            if( !CollectionUtils.isEmpty(news.images) ){
                news.imagesCount = news.images.size();
            }
            news.addTime = new Date();

            String[] assList = news.associationId.split(",");
            AccountState accountState = accountDAO.loadById(sessionId).state;
            if ((accountState.compareTo(AccountState.SUPPER) == 0) && (assList.length > 1)){
                int l = assList.length - 1;
                responseEntity.body = "{\"_id\":\"";
                while (l >= 0){
                    com.jieli.news.News singleNews = singleNews =copyDeep(news);
                    singleNews.associationId = assList[l];
                    newsDAO.save(singleNews);
                    responseEntity.body = responseEntity.body + singleNews.get_id().toString() + (l==0?"":",");
                    l--;
                }
                responseEntity.body = responseEntity.body + "\"}";
            }else {
                news.associationId = assList[0];
                newsDAO.save(news);
                responseEntity.body = "{\"_id\":\"" + news.get_id().toString() + "\"}";
            }

            //newsDAO.save(news);
        }

        responseEntity.code = 200;
        return  Response.status(200).entity(responseEntity).build();

    }

    private com.jieli.news.News copyDeep(com.jieli.news.News n){
        com.jieli.news.News nn = new com.jieli.news.News();

        nn.addTime = n.addTime;
        nn.appreciateCount = 0;
        nn.appreciateUserIds = new ArrayList<String>();
        nn.associationId = n.associationId;
        nn.content = n.content;
        nn.images = new ArrayList<Image>();
        nn.imagesCount = 0;
        nn.overview = n.overview;
        nn.title = n.title;
        nn.type = n.type;

        return nn;
    }

    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();
    private NewsDAO newsDAO = new NewsDAO();
}
