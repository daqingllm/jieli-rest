package com.jieli.feature.vote.entity;

import com.jieli.common.entity.InterestTag;
import com.jieli.mongo.Model;

import java.util.Date;

/**
 * 投票
 * Created by YolandaLeo on 14-3-19.
 */
public class VoteInfo extends Model{
    public Integer id;
    /**
     * 投票标题
     */
    public String title;
    /**
     * 截止日期
     */
    public Date deadline;
    /**
     * 投票参与人数
     */
    public Integer voteNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }
}
