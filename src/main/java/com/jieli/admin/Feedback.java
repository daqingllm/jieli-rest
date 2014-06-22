package com.jieli.admin;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.util.FTLrender;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by 95 on 2014/6/22.
 */
@Path("/bfeedback")
public class Feedback {
    private AccountDAO accountDAO = new AccountDAO();

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
            public String getFeedback(@CookieParam("u")String sessionId) {

        com.jieli.common.entity.Account account = accountDAO.loadById(sessionId);
        ResponseEntity responseEntity = new ResponseEntity();
        Map<String, Object> params = CommonUtil.GenerateCommonParams(account);

        return FTLrender.getResult("feedback.ftl", params);
    }
}
