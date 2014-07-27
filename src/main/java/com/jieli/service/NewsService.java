package com.jieli.service;

import com.jieli.comment.Comment;
import com.jieli.comment.TopicType;
import com.jieli.common.entity.PushMessageResult;
import com.jieli.common.entity.ResponseEntity;
import com.jieli.message.CommentMessageUtil;
import com.jieli.mongo.BaseDAO;
import com.jieli.news.*;
import com.jieli.util.*;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

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

        boolean needForcePost = false;
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
            } else if (StringUtils.isEmpty(newsId)){
                needForcePost = true;
            }

            newsDAO.save(news);

            if (force){
                NewsMsg newsMsg = new NewsMsg();
                String preOrPost = "";
                if (news.type.compareTo(NewsType.newsType)==0){
                    preOrPost = " 同舟发布";
                } else if (news.type.compareTo(NewsType.associationType)==0){
                    preOrPost = " 协会动态发布";
                } else if (news.type.compareTo(NewsType.enterpriseType)==0){
                    preOrPost = " 小组风采发布";
                }

                newsMsg.msg = news.title + preOrPost;
                newsMsg.newsId = news.get_id().toString();


                PushMessageResult pushMessageResult = PushUtils.pushMessageToAssociation(newsMsg.msg, news.associationId);
                if (!PushMessageResult.SUCCESS.equals(pushMessageResult)){
                    responseEntity.code = 3102;
                    if (PushMessageResult.NO_GROUP.equals(pushMessageResult)){
                        responseEntity.msg = "推送失败，无此推送分组，请联系系统管理员。";
                    }else {
                        responseEntity.msg = "推送失败。";
                    }
                    return Response.status(200).entity(responseEntity).build();
                }
            }
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
    public Response loadCoverImages(@CookieParam("u")String sessionId,@QueryParam("type") String type){
        if (!IdentityUtils.isValidate(sessionId)) {
            return Response.status(403).build();
        }
        String associationId = IdentityUtils.getAssociationId(sessionId);

        Iterable<Image> images = imageDAO.loadCoverImages(associationId,type);
        List<Image> imageList = new ArrayList<Image>();
        for (Image image : images){
            if (image.url.indexOf("?imageView/4/w/500/h/300") < 0)
                image.url += "?imageView/4/w/500/h/300";

            imageList.add(image);
        }

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = 200;
        responseEntity.body = imageList;

        return  Response.status(200).entity(responseEntity).build();
    }


    @POST
    @Path("/uncover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response unCoverImage(@CookieParam("u")String sessionId, News news) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (news == null || StringUtils.isEmpty(news.type) || StringUtils.isEmpty(news.associationId)) {
            responseEntity.code = 4008;
            responseEntity.msg = "缺少参数";
            return Response.status(200).entity(responseEntity).build();
        }

        ImageCount imageCount = new ImageCount(0);
        SetNewsTopPicTo(news, UnTopPicState);
        DeleteImageByNews(news);
        HandleExtraNewsThroughCurrentNewsAndCount(news, imageCount, TopPicState, DoNotDeleteExtraImage);
        //SetReplenishNewsTopPicToTrueAfterDeleteImageFromNews(news, imageCount);

        responseEntity.code = 200;
        responseEntity.msg = "已取消置顶, 目前此类资讯共有"+imageCount.count+"个置顶图片";
        return Response.status(200).entity(responseEntity).build();
    }

    @POST
    @Path("/cover")
    @Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
    public Response setCoverImage(@CookieParam("u")String sessionId, News news) {
        if (!IdentityUtils.isAdmin(sessionId)) {
            return Response.status(403).build();
        }

        ResponseEntity responseEntity = new ResponseEntity();
        if (news == null || StringUtils.isEmpty(news.associationId)) {
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

        ImageCount imageCount = new ImageCount(0);
        HandleExtraNewsThroughCurrentNewsAndCount(news, imageCount, UnTopPicState, DeleteExtraImage);
        //SetEdgedOutNewsTopPicToFalseBeforeInsertImageFromNews(news,imageCount);
        InsertImageByNews(news);
        SetNewsTopPicTo(news,TopPicState);

        responseEntity.code = 200;
        responseEntity.msg = "已置顶, 目前此类资讯共有"+(imageCount.count>3?4:imageCount.count+1)+"个置顶图片";
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

    private void SetNewsTopPicTo(News news, boolean state){
        if (news == null) return;

        if (news.topPic != state){
            news.topPic = state;
            newsDAO.save(news);
        }
    }

    // ExtraImage : 增加头图时候可能挤掉原头图,删除的时候可能增补老头图。这两种头图为ExtraImage
    private void HandleExtraNewsThroughCurrentNewsAndCount(News currentNews, ImageCount imageCount, boolean state, boolean deleteExtraNews){
        if (currentNews == null) return;

        Iterable<Image> images = imageDAO.loadCoverImages(currentNews.associationId,currentNews.type);
        Image extraImage = (Image)(CollectionUtils.getByIndexAndCount(images, 3, imageCount));

        if (extraImage != null){
            String newsId = extraImage.newsId;
            News extraNews = newsDAO.loadById(newsId);

            if (extraNews == null){
                return;
            }

            extraNews.topPic = state;
            newsDAO.save(extraNews);

            if (deleteExtraNews){
                imageDAO.deleteByNewsId(extraNews.get_id().toString());
            }
        }
    }

    /*
    private void SetEdgedOutNewsTopPicToFalseBeforeInsertImageFromNews(News news, ImageCount imageCount){
        if (news==null) return;

        Iterable<Image> images = imageDAO.loadCoverImages(news.associationId,news.type);
        Image edgedOutImage = (Image)(CollectionUtils.getByIndexAndCount(images, 3, imageCount));

        if (edgedOutImage != null) {
            String newsId = edgedOutImage.newsId;
            News edgedOutNews = newsDAO.loadById(newsId);
            if (edgedOutNews != null) {
                edgedOutNews.topPic = false;
                newsDAO.save(edgedOutNews);

                imageDAO.deleteById(edgedOutImage.get_id().toString());
            }
        }
    }

    private void SetReplenishNewsTopPicToTrueAfterDeleteImageFromNews(News news,ImageCount imageCount){
        if (news==null) return;

        Iterable<Image> newImages = imageDAO.loadCoverImages(news.associationId,news.type);
        Image replenishImage = (Image)(CollectionUtils.getByIndexAndCount(newImages, 3, imageCount));

        if (replenishImage != null) {
            String newsId = replenishImage.newsId;
            News replenishNews = newsDAO.loadById(newsId);
            if (replenishNews != null) {
                replenishNews.topPic = true;
                newsDAO.save(replenishNews);
            }
        }
    }
    */

    private void InsertImageByNews(News news){
        if (news == null || news.images == null || news.images.size() == 0) return;

        Image imageIn = news.images.get(0);

        Image image = new Image();
        image.url = imageIn.url;
        image.description = imageIn.description;
        image.placeholder = imageIn.placeholder;
        image.newsId = news.get_id().toString();
        image.associationId = news.associationId;
        image.type = news.type;
        imageDAO.save(image);
    }

    private void DeleteImageByNews(News news){
        if (news == null) return;

        Iterable<Image> images = imageDAO.find("{newsId:\"" + news.get_id().toString() + "\"}");
        if (images == null) return;
        for (Image image : images) {
            imageDAO.deleteById(image.get_id().toString());
        }
    }

    private static final boolean TopPicState = true;
    private static final boolean UnTopPicState = false;
    private static final boolean DeleteExtraImage = true;
    private static final boolean DoNotDeleteExtraImage = false;

}
