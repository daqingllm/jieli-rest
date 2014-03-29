package com.jieli.feature.vote.entity;

import com.jieli.mongo.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.Date;
import java.util.List;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class Vote extends Model {
    private String id;
    private String userId;
    private List<Integer> voteIndex;
    private Date addTime;

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

    public List<Integer> getVoteIndex() {
        return voteIndex;
    }

    public void setVoteIndex(List<Integer> voteIndex) {
        this.voteIndex = voteIndex;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


}
