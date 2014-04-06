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
@Path("/bnews")
public class News {

    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public String CreateNews(){
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("isOk", true);
//        params.put("message", "hello 大骏！");
        return FTLrender.getResult("new_article.ftl", params);
    }


}
