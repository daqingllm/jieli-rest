package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.Date;
import java.util.List;

/**
 * 互帮互助详细内容
 * Created by YolandaLeo on 14-3-19.
 */
public class HelpInfo extends Model{
    public String id;
    public String userId;
    /**
     * 协会Id
     */
    public String associationId;
    /**
     * 互帮互助标题
     */
    public String title;
    /**
     * 互帮互助内容
     */
    public String content;
    /**
     * 关注人数
     */
    public Integer attentionNum;
    /**
     * 发帖时间
     */
    public Date addTime;
    /**
     * 置顶评论id
     */
    public Integer topCommentId;
    /**
     * 评论列表
     */
    public List<Comment> commentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssociationId() {
        return associationId;
    }

    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(Integer attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getTopCommentId() {
        return topCommentId;
    }

    public void setTopCommentId(Integer topCommentId) {
        this.topCommentId = topCommentId;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
