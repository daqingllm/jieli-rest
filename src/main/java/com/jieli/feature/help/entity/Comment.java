package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * 互帮互助评论
 * Created by YolandaLeo on 14-3-19.
 */
public class Comment extends Model {
    public Integer id;
    /**
     * 评论发表人id
     */
    public Integer commentUserId;
    /**
     * 评论内容
     */
    public String comment;
    /**
     * 发表时间
     */
    public Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
