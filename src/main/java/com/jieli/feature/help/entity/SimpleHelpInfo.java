package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * 显示在互帮互助列表页
 * 简洁版HelpInfo
 * Created by YolandaLeo on 14-3-19.
 */
public class SimpleHelpInfo extends Model{
    public String id;
    public String userId;
    public String userName;
    public String userFace;
    /**
     * 协会Id
     */
    public String associationId;
    /**
     * 互帮互助标题
     */
    public String title;

    /**
     * 标题注释
     */
    public String tips;
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
     * 类型 0-需求，1-供给
     */
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
