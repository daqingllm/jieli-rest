package com.jieli.admin;

import com.jieli.util.FTLrender;
import com.sun.jersey.spi.resource.Singleton;

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
@Path("/buser")
public class User {
    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_HTML)
    public String UserLogin(){

        Map<String, Object> params = new HashMap<String, Object>();
        return FTLrender.getResult("login.ftl", params);
    }

}
