package com.jieli.feature.discuss.entity;

import com.jieli.comment.Comment;
import com.jieli.mongo.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by meijia.lv on 14-6-1.
 */
public class DiscussInfo extends Model{
    private String userId;
    private String userName;
    private String userFace;
    /**
     * 协会Id
     */
    private String associationId;
    /**
     * 讨论帖标题
     */
    private String title;
    /**
     * 标题注释
     */
    private String tips;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 关注人数
     */
    private Integer attentionNum;
    /**
     * 发帖时间
     */
    private Date addTime;
    /**
     * 置顶评论id
     */
    private List<Comment> topCommentList;
    /**
     * 关注列表
     */
    private List<String> focusList;
    /**
     * 根据DiscussType
     */
    private String type;
    /**
     * 0 人 1 物
     */
    private int typeDetail;

    /**
     * 是否紧急
     */
    public Boolean isEmergency;
    /**
     * 评论数
     */
    private int commentCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
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

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
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

    public List<Comment> getTopCommentList() {
        return topCommentList;
    }

    public void setTopCommentList(List<Comment> topCommentList) {
        this.topCommentList = topCommentList;
    }

    public List<String> getFocusList() {
        return focusList;
    }

    public void setFocusList(List<String> focusList) {
        this.focusList = focusList;
    }

    public int getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(int typeDetail) {
        this.typeDetail = typeDetail;
    }

    public Boolean getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
