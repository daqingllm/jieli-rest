package com.jieli.service;

import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.CommentMessageUtil;
import com.jieli.message.MessageType;
import com.jieli.mongo.BaseDAO;
import com.jieli.news.Image;
import com.jieli.news.News;
import com.jieli.news.NewsDAO;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentifyUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-23
 * Time: PM11:30
 * To change this template use File | Settings | File Templates.
 */

@Singleton
@Path("/rest/news")
public class NewsService {


    private final NewsDAO newsDAO = new NewsDAO();

    BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(com.jieli.mongo.Collections.Comment, Comment.class);

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
    public Response findNews(@CookieParam("u")String sessionId, @QueryParam("type") String type, @QueryParam("page") int page, @QueryParam("count") int pagesize){

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
            if( !CollectionUtils.isEmpty(news.images) ){
                news.imagesCount = news.images.size();
            }
            news.addTime = new Date();

            newsDAO.save(news);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + news.get_id().toString() + "\"}";;
        return  Response.status(200).entity(responseEntity).build();

    }

    @GET
    @Path("/appreciate")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response appreciate(@CookieParam("u")String sessionId, @QueryParam("news_id")String _id) {
        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentifyUtils.getUserId(sessionId);
        if (StringUtils.isEmpty(_id) || !MongoUtils.isValidObjectId(_id)) {
            responseEntity.code = 4002;
            responseEntity.msg = "参数id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        News news = newsDAO.loadById(_id);
        if (news == null) {
            responseEntity.code = 4001;
            responseEntity.msg = "资讯不存在";
            return Response.status(200).entity(responseEntity).build();
        }
        if (news.appreciateUserIds.contains(userId)) {
            responseEntity.code = 4003;
            responseEntity.msg = "已经点赞";
            return Response.status(200).entity(responseEntity).build();
        }
        news.appreciateUserIds.add(userId);
        news.appreciateCount = news.appreciateUserIds.size();
        newsDAO.save(news);

        responseEntity.code = 200;
        return Response.status(200).entity(responseEntity).build();
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

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response comment(@CookieParam("u")String sessionId, Map<String, String> commentInfo) {
        //commentInfo内字段：content topicId commentedUserId(回复评论)

        if (!IdentifyUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String content = commentInfo.get("content");
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(content)) {
            responseEntity.code = 4004;
            responseEntity.msg = "回复内容不能为空";
            return Response.status(200).entity(responseEntity).build();
        }
        String newsId = commentInfo.get("topicId");
        if (StringUtils.isEmpty(newsId) || !MongoUtils.isValidObjectId(newsId)) {
            responseEntity.code = 4002;
            responseEntity.msg = "参数id无效";
            return Response.status(200).entity(responseEntity).build();
        }
        News news = newsDAO.loadById(newsId);
        if (news == null) {
            responseEntity.code = 4001;
            responseEntity.msg = "资讯不存在";
            return Response.status(200).entity(responseEntity).build();
        }

        Comment comment = new Comment();
        comment.topicId = newsId;
        comment.topicType = TopicType.News;
        comment.commentUserId = IdentifyUtils.getUserId(sessionId);
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.addTime = new Date();
        commentDAO.save(comment);
        //message
        CommentMessageUtil.addCommentMessage(comment, MessageType.NEWS);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        return Response.status(200).entity(responseEntity).build();
    }
}
