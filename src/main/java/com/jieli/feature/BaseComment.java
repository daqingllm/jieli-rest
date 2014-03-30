package com.jieli.feature;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * 互帮互助评论
 * Created by YolandaLeo on 14-3-19.
 */
public class BaseComment extends Model {
    /**
     * 评论发表人id
     */
    public String commentUserId;
    /**
     * 评论内容
     */
    public String comment;
    /**
     * 发表时间
     */
    public Date addTime;

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
