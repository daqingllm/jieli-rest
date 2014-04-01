package com.jieli.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jieli.mongo.Model;
import com.jieli.user.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-23
 * Time: PM11:05
 * To change this template use File | Settings | File Templates.
 */
public class News extends Model {

    private static final long serialVersionUID = -2926128873069182721L;

    public String type;
    public static final String associationType = "association";  // 协会资讯
    public static final String enterpriseType = "enterprise";  // 企业动态
    public static final String newsType = "news";  // 新闻


    public String title;

    public String overview;  // 资讯预览内容

    public String content; // 资讯实体内容

    public List<Image> images;
    public int imagesCount;

    public List<User> appreciateMembers;  // 点赞者
    public int appreciateCount;

    public Date addTime;


    public String associationId; // 只有[协会资讯]有

    public String enterpriseName;// 只有[企业动态]有






}
