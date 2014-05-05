package com.jieli.comment;


import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM8:34
 * To change this template use File | Settings | File Templates.
 */
public class CommentMsg {

    public String msg;

    //0 发帖被评论；1 评论被回复
    public int commentType;

    public String topicId; // 评论内容id
    public String topicType; // 被评论内容类型

    public String commentId;
    public String commentContent;
    public CommentUserInfo commentUser;
    public Date commentTime;


}
