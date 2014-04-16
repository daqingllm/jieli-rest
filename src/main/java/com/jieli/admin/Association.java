package com.jieli.admin;

import com.jieli.association.AssociationDAO;
import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.*;
import com.jieli.common.entity.Account;
import com.jieli.util.FTLrender;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 95 on 2014/4/13.
 */
@Singleton
@Path("/bassociation")
public class Association {
    private AccountDAO accountDAO = new AccountDAO();
    private AssociationDAO associationDAO = new AssociationDAO();

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public String CreateAssociation(@CookieParam("u")String sessionId,@QueryParam("name")String name){
        if (!IdentifyUtils.isSuper(sessionId)){
            return Common.errorReturn;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("isOk", true);
        //params.put("message", "hello 大骏！");
        return FTLrender.getResult("new_association.ftl", params);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public String GetAssociations(@CookieParam("u")String sessionId){
        if (!IdentifyUtils.isSuper(sessionId))
            return Common.errorReturn;

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("username",account.username);

        String associationList = "[";
        Iterable<com.jieli.association.Association> associations = associationDAO.loadAll();
        for (com.jieli.association.Association association : associations) {
            int number=0;
            Iterable<Account> accounts1 = new AccountDAO().loadByAssociationId(association.get_id() + "", AccountState.ENABLE);
            for (Account account1 : accounts1) {
                System.out.println(account1.username);
                number ++;
            }
            Iterable<Account> accounts2 = new AccountDAO().loadByAssociationId(association.get_id() + "", AccountState.ADMIN);
            for (Account account2 : accounts2) {
                System.out.println(account2.username);
                number ++;
            }

            associationList += "{\"_id\":\"" + association.get_id() + "\",\"name\":\"" + association.name + "\",\"online\":0,\"userNumber\":" + number+"},";
        }
        if (associationList.endsWith(",")) associationList = associationList.substring(0,associationList.length()-1)+"]";
        else associationList += "]";

        params.put("jsonAssociList",associationList);
        params.put("isSuper",true);

        return FTLrender.getResult("association_list.ftl",params);
    }
}
