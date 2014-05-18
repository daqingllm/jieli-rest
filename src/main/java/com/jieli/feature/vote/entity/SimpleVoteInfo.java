package com.jieli.feature.vote.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class SimpleVoteInfo extends Model{
    private String id;
    private String associationId;
    private String userId;
    private String title;
    private String description;
    boolean multiple; //是否多选
    private Date deadLine;
    private Date addTime;
    private Integer participants;
    private Integer totalVote; //总票数
    public String picture;
    private boolean isUserVoted;

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

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

    public boolean isUserVoted() {
        return isUserVoted;
    }

    public void setUserVoted(boolean isUserVoted) {
        this.isUserVoted = isUserVoted;
    }
}
