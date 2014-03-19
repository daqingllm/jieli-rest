package com.jieli.feature.help.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * 显示在互帮互助列表页
 * 简洁版HelpInfo
 * Created by YolandaLeo on 14-3-19.
 */
public class SimpleHelpInfo extends Model{
    public Integer id;
    public Integer userId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
