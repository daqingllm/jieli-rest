package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;
import com.jieli.user.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 互帮互助详细内容
 * Created by YolandaLeo on 14-3-19.
 */
public class HelpInfo extends Model{
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
    //public Integer topCommentId;
    public List<String> focusList;

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
}
