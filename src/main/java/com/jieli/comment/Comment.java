package com.jieli.comment;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM7:47
 * To change this template use File | Settings | File Templates.
 */
public class Comment extends Model {
    private static final long serialVersionUID = -3459753602651398540L;

    public String topicId; // 评论内容id
    public String topicType; // 被评论内容类型

    public String commentUserId;  // 回复者id
    public String commentedUserId; // 被回复者id

    public String content;

    public Date addTime;


    public CommentUserInfo commentUserInfo; // 评论者详细信息，用于展示

    public boolean isDeleted = false;  //是否已被删除

}
