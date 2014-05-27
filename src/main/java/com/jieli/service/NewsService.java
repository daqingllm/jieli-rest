package com.jieli.service;

import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.CommentMessageUtil;
import com.jieli.mongo.BaseDAO;
import com.jieli.news.*;
import com.jieli.util.CollectionUtils;
import com.jieli.util.IdentityUtils;
import com.jieli.util.MongoUtils;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

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
@Path("/news")
public class NewsService {


    private final NewsDAO newsDAO = new NewsDAO();

    private final ImageDAO imageDAO = new ImageDAO();

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

        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        String associationId = IdentityUtils.getAssociationId(sessionId);
        List<News> newses = null;

        if (type.compareTo("history") != 0)
        {
            newses =  newsDAO.paginate(page, pagesize, "{associationId:#, type:#}", associationId, type);
        }
        else{
            newses =  newsDAO.paginateInOrder(page, pagesize, "{time:1}", "{associationId:#, type:#}", associationId, type);
        }


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
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response upsertNews(@CookieParam("u")String sessionId, @QueryParam("newsId")String newsId, @QueryParam("force")boolean force, News news){

        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }
        if (NewsType.newsType.equals(news.type)) {
            if (!IdentityUtils.isSuper(sessionId)) {
                return Response.status(403).build();
            }
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String associationId = IdentityUtils.getAssociationId(sessionId);
        if (StringUtils.isEmpty(news.associationId)) {
            if (StringUtils.isEmpty(associationId)) {
                responseEntity.code = 3101;
                responseEntity.msg = "缺少协会id信息";
                return Response.status(200).entity(responseEntity).build();
            }
            news.associationId = associationId;
        }

        if(news!=null){
            if( !CollectionUtils.isEmpty(news.images) ){
                news.imagesCount = news.images.size();
            }
            news.addTime = new Date();

            if (!StringUtils.isEmpty(newsId) && MongoUtils.isValidObjectId(newsId)) {
                News oldNews = newsDAO.loadById(newsId);
                if (oldNews != null) {
                    news.set_id(new ObjectId(newsId));
                    news.addTime = oldNews.addTime;
                    news.appreciateCount = oldNews.appreciateCount;
                    news.appreciateUserIds = oldNews.appreciateUserIds;
                    news.commentCount = oldNews.commentCount;
                }
            }

            newsDAO.save(news);
        }

        responseEntity.code = 200;
        responseEntity.body = "{\"_id\":\"" + news.get_id().toString() + "\"}";;
        return  Response.status(200).entity(responseEntity).build();

    }

    @GET
    @Path("/appreciate")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response appreciate(@CookieParam("u")String sessionId, @QueryParam("news_id")String _id) {
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        String userId = IdentityUtils.getUserId(sessionId);
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
     * @return
     */
    @GET
    @Path("/cover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response loadCoverImages(@CookieParam("u")String sessionId){
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }

        Iterable<Image> images = imageDAO.loadCoverImages();
        List<Image> imageList = new ArrayList<Image>();
        for (Image image : images){
            if (image.url.indexOf("?imageView/4/w/572/h/354") < 0)
                image.url += "?imageView/4/w/572/h/354";

            imageList.add(image);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = imageList;

        return  Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/cover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response setCoverImage(@CookieParam("u")String sessionId, News news) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (news == null) {
            responseEntity.code = 4008;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }
        if (!"association".equals(news.type) && !"enterprise".equals(news.type)) {
            responseEntity.code = 4009;
            responseEntity.msg = "类型错误";
            return Response.status(200).entity(responseEntity).build();
        }
        if (CollectionUtils.isEmpty(news.images)) {
            responseEntity.code = 4010;
            responseEntity.msg = "缺少图片";
            return Response.status(200).entity(responseEntity).build();
        }

        Iterable<Image> images = imageDAO.loadCoverImages();
        int count = 1;
        for (Image image_ : images){
            if (count == 4){
                String newsId = image_.newsId;
                News news_ = newsDAO.loadById(newsId);
                if (news_ != null) {
                    news_.topPic = false;
                    newsDAO.save(news_);
                }
                break;
            }
            count ++;
        }

        Image image = news.images.get(0);
        image.newsId = news.get_id().toString();
        imageDAO.save(image);

        News news_ = newsDAO.loadById(image.newsId);
        if (news_ != null){
            news_.topPic = true;
            newsDAO.save(news_);
        }

        responseEntity.code = 200;
        return  Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response comment(@CookieParam("u")String sessionId, Map<String, String> commentInfo) {
        //commentInfo内字段：content topicId commentedUserId(回复评论)

        if (!IdentityUtils.isValidate(sessionId)) {
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
        news.commentCount++;
        newsDAO.save(news);

        Comment comment = new Comment();
        comment.topicId = newsId;
        comment.topicType = TopicType.News;
        comment.topicTitle = news.title;
        comment.commentUserId = IdentityUtils.getUserId(sessionId);
        comment.commentedUserId = commentInfo.get("commentedUserId");
        comment.content = content;
        comment.addTime = new Date();
        commentDAO.save(comment);
        //message
        CommentMessageUtil.addCommentMessage(comment);

        responseEntity.code = 200;
        responseEntity.msg = "评论成功";
        responseEntity.body = comment;
        return Response.status(200).entity(responseEntity).build();
    }

}
