package com.jieli.feature.vote.entity;

import com.jieli.feature.BaseComment;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class VoteComment extends BaseComment {
    private String voteId;
    private String userId;
    private String context;
    private Date addTime;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
