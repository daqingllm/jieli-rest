package com.jieli.feature.vote.entity;

import com.jieli.feature.vote.entity.VoteComment;
import com.jieli.mongo.Model;

import java.util.Date;
import java.util.List;
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
    private String description;
    private List<String> options; //投票选项
    private Map<Integer, String> optionPicsMap; //投票选项，对应图片
    private Map<Integer, Integer> optionVotes; //投票选项，投票人数
    boolean multiple; //是否多选
    private List<Vote> voteList; //投票记录
    private Integer totalVote; //每个选项投票数总和
    private Integer participants;

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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Map<Integer, String> getOptionPicsMap() {
        return optionPicsMap;
    }

    public void setOptionPicsMap(Map<Integer, String> optionPicsMap) {
        this.optionPicsMap = optionPicsMap;
    }

    public Map<Integer, Integer> getOptionVotes() {
        return optionVotes;
    }

    public void setOptionVotes(Map<Integer, Integer> optionVotes) {
        this.optionVotes = optionVotes;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public List<Vote> getVoteList() { return voteList; }

    public void setVoteList(List<Vote> voteList) { this.voteList = voteList; }

    public Integer getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(Integer totalVote) {
        this.totalVote = totalVote;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}
