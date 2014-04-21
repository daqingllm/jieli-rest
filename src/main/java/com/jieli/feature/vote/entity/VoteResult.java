package com.jieli.feature.vote.entity;

import com.jieli.mongo.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-21.
 */
public class VoteResult extends Model{
    private String voteId;
    private Map<Integer, Integer> optionVotes; //投票选项index，票数
    private List<Vote> voteList; //投票记录
    private Integer totalVote; //总票数
    private Integer participants; //参与人数

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public Map<Integer, Integer> getOptionVotes() {
        return optionVotes;
    }

    public void setOptionVotes(Map<Integer, Integer> optionVotes) {
        this.optionVotes = optionVotes;
    }

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

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }
}
