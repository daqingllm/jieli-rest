package com.jieli.news;

import com.jieli.mongo.Model;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-31
 * Time: PM8:11
 * To change this template use File | Settings | File Templates.
 */
public class Image extends Model {

    public String placeholder;  // 图片在文章中占位符

    public String url;

    public String description;

    public String newsId;

    public String associationId;

    public String type;

}