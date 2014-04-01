package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.news.Image;
import com.jieli.news.News;
import com.jieli.news.NewsDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-23
 * Time: PM11:30
 * To change this template use File | Settings | File Templates.
 */

@Singleton
@Path("/news")
public class NewsService {


    private final NewsDAO newsDAO = new NewsDAO();

    /**
     * 分页获取资讯
     * @param sessionId
     * @param type
     * @param page
     * @param pagesize
     * @return
     */
    @GET
    @Path("/paginate")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response findNews(@CookieParam("u")String sessionId, @QueryParam("type") String type, @QueryParam("page") int page, @QueryParam("pagesize") int pagesize){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        String associationId = IdentifyUtils.getAssociationId(sessionId);
        List<News> newses = newsDAO.paginate(page, pagesize, "{associationId:#, type:#}", associationId, type);


        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = newses;

        return  Response.status(200).entity(responseEntity).build();
    }


    /**
     * 获取一篇资讯的详细内容(不包括评论)
     * @param _id
     * @return
     */
    @GET
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response findNews(@QueryParam("news_id")String _id){

        News news = newsDAO.loadById(_id);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = news;

        return  Response.status(200).entity(responseEntity).build();

    }

    /**
     * 添加资讯
     * @param sessionId
     * @param news
     * @return
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addNews(@CookieParam("u")String sessionId, News news){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        if(news!=null){
            String associationId = IdentifyUtils.getAssociationId(sessionId);
            news.associationId = associationId;
            if( !CollectionUtils.isEmpty(news.images) ){
                news.imagesCount = news.images.size();
            }
            news.addTime = new Date();

            newsDAO.save(news);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = "ok";
        return  Response.status(200).entity(responseEntity).build();

    }


    /**
     * 获取资讯轮播图
     * @param sessionId
     * @param type
     * @param count
     * @return
     */
    @GET
    @Path("/cover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response loadCoverImages(@CookieParam("u")String sessionId, @QueryParam("type") String type, @QueryParam("count") int count){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        if(count<=0) count = 3;

        String associationId = IdentifyUtils.getAssociationId(sessionId);
        List<News> newses = newsDAO.findWithLimit(count, "{associationId:#, type:#, imagesCount:{$gt: 0}}", associationId, type);

        List<Image> images = null;
        if(newses!=null && newses.size()>0){
            images = new ArrayList<Image>(newses.size());
            for(News news : newses){
                images.add(news.images.get(0));
            }
        }


        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = images;

        return  Response.status(200).entity(responseEntity).build();
    }












}
