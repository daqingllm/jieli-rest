package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.Date;
import java.util.List;

/**
 * 互帮互助详细内容
 * Created by YolandaLeo on 14-3-19.
 */
public class HelpInfo extends Model{
    private String userId;
    /**
     * 协会Id
     */
    private String associationId;
    /**
     * 互帮互助标题
     */
    private String title;
    /**
     * 互帮互助内容
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
    private List<String> topCommentList;
    /**
     * 关注列表
     */
    private List<String> focusList;
    /**
     * 0 需求 1 供给
     */
    private int type;

    /**
     * 是否紧急
     */
    private boolean isEmergency;

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

    public List<String> getFocusList() { return focusList; }

    public void setFocusList(List<String> focusList) { this.focusList = focusList; }

    public List<String> getTopCommentList() {
        return topCommentList;
    }

    public void setTopCommentList(List<String> topCommentList) {
        this.topCommentList = topCommentList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
}
