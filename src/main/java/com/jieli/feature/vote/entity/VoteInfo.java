package com.jieli.feature.vote.entity;

import com.jieli.mongo.Model;

import java.util.Date;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class VoteInfo extends Model {
    private String title;
    private String associationId;
    private String userId;
    private Date deadLine;
    private Date addTime;
    private String picture;
    private boolean force;
    private String description;
    private Map<Integer, String> options; //投票选项index，投票选项
    private Map<Integer, String> optionPicsMap; //投票选项，对应图片
    private Map<Integer, Integer> optionVotes; //投票选项index，票数
    private boolean multiple; //是否多选

    public String getAssociationId() {
        return associationId;
    }

    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Integer, String> getOptionPicsMap() {
        return optionPicsMap;
    }

    public void setOptionPicsMap(Map<Integer, String> optionPicsMap) {
        this.optionPicsMap = optionPicsMap;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public Map<Integer, String> getOptions() {
        return options;
    }

    public void setOptions(Map<Integer, String> options) {
        this.options = options;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public Map<Integer, Integer> getOptionVotes() {
        return optionVotes;
    }

    public void setOptionVotes(Map<Integer, Integer> optionVotes) {
        this.optionVotes = optionVotes;
    }
}
