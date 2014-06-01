package com.jieli.feature.discuss.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-6-1.
 */
public class SimpleDiscussInfo extends Model{
    private String id;
    private String userId;
    private String userName;
    private String userFace;
    /**
     * 协会Id
     */
    private String associationId;
    /**
     * 互帮互助标题
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
     * 类型 DiscussType
     */
    private String type;
    /**
     * 0 人 1 物
     */
    private int typeDetail;

    private int commentCount;

    private boolean isEmergency;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(int typeDetail) {
        this.typeDetail = typeDetail;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
}
