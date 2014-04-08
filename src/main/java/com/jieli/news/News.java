package com.jieli.news;

import com.jieli.mongo.Model;

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

    public String associationId;
    public String type;  // NewsType

    public String title;

    public String overview;  // 资讯预览内容

    public String content; // 资讯实体内容

    public List<Image> images;
    public int imagesCount;

    public List<String> appreciateUserIds;  // 点赞者
    public int appreciateCount;

    public Date addTime;


}
