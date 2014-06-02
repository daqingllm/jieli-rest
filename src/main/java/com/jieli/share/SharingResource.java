package com.jieli.share;

import com.jieli.activity.Activity;
import com.jieli.activity.ActivityDAO;
import com.jieli.news.News;
import com.jieli.news.NewsDAO;
import com.jieli.util.FTLrender;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-6-2
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Path("/share")
public class SharingResource {

    NewsDAO newsDAO = new NewsDAO();
    ActivityDAO activityDAO = new ActivityDAO();

    @Path("/news")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String renderNews(@QueryParam("id")String id) {
        News news = newsDAO.loadById(id);
        if (news == null) {
            return "资讯不存在";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("news", news);
        params.put("time", (new java.text.SimpleDateFormat("yyyy-MM-dd")).format(news.addTime));
        return FTLrender.getResult("sharingnews.ftl", params);
    }

    @Path("/activity")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String renderActivity(@QueryParam("id")String id) {
        Activity activity = activityDAO.loadById(id);
        if (activity == null) {
            return "活动不存在";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("activity", activity);
        return FTLrender.getResult("sharingactivity.ftl", params);
    }

}
