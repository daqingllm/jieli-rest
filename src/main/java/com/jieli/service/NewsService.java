package com.jieli.service;

import com.jieli.common.entity.ResponseEntity;
import com.jieli.mongo.GenericDAO;
import com.jieli.news.Image;
import com.jieli.news.News;
import com.jieli.news.NewsDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import com.jieli.util.IdentifyUtils;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
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

    private final UserDAO userDAO = new UserDAO();


    @GET
    @Path("/paginate")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response findNews(@CookieParam("u")String sessionId, @QueryParam("type") String type, @QueryParam("page") int page, @QueryParam("pagesize") int pagesize){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        List<News> newses = null;
        if(News.associationType.equals(type)){
            String associationId = IdentifyUtils.getAssociationId(sessionId);
            newses = newsDAO.paginate(page, pagesize, "{associationId:#, type:#}", associationId, type);
        }else if(News.enterpriseType.equals(type)){
            String userId = IdentifyUtils.getUserId(sessionId);
            User user = userDAO.loadById(userId);
            newses = newsDAO.paginate(page, pagesize, "{enterpriseName:#, type:#}", user.enterpriseName, type);
        }else if(News.newsType.equals(type)) {
            newses = newsDAO.paginate(page, pagesize, "{type:#}", type);
        }


        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = newses;

        return  Response.status(200).entity(responseEntity).build();
    }


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


//    @POST
//    @Path("/load")
//    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
//    public Response upsertNews(){
//
//    }



    @GET
    @Path("/cover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response loadCoverImages(@CookieParam("u")String sessionId, @QueryParam("type") String type, @QueryParam("count") int count){

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        if(count<=0)
            count = 3;


        List<News> newses = null;
        if(News.associationType.equals(type)){
            String associationId = IdentifyUtils.getAssociationId(sessionId);
            newses = newsDAO.findWithLimit(count, "{associationId:#, type:#, imagesCount:{$gt: 0}}", associationId, type);
        }else if(News.enterpriseType.equals(type)){
            String userId = IdentifyUtils.getUserId(sessionId);
            User user = userDAO.loadById(userId);
            newses = newsDAO.findWithLimit(count, "{enterpriseName:#, type:#, imagesCount:{$gt: 0}}", user.enterpriseName, type);
        }else if(News.newsType.equals(type)) {
            newses = newsDAO.findWithLimit(count, "{type:#, imagesCount:{$gt: 0}}", type);
        }

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
