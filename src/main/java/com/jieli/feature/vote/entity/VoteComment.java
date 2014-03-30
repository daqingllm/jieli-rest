package com.jieli.feature.vote.entity;

import com.jieli.feature.BaseComment;

import java.util.Date;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class VoteComment extends BaseComment {
    private String voteId;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }
}
