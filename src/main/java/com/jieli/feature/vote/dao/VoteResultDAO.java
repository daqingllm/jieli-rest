package com.jieli.feature.vote.dao;

import com.jieli.feature.vote.entity.Vote;
import com.jieli.feature.vote.entity.VoteInfo;
import com.jieli.feature.vote.entity.VoteResult;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YolandaLeo on 14-4-21.
 */
public class VoteResultDAO extends GenericDAO<VoteResult> {

    public VoteResult loadByVoteId(String voteId) {
        VoteResult result = col.findOne("{voteId:#}", voteId).as(VoteResult.class);
        if(result == null) {
            result = new VoteResult();
            result.setVoteId(voteId);
            result.setTotalVote(0);
            result.setParticipants(0);
            result = save(result);
        }
        return result;
    }
    /**
     * 投票
     * @param vote
     * @param voteId
     * @return
     */
    public VoteResult vote(Vote vote, String voteId) {
        VoteResult v = loadByVoteId(voteId);
        List<Vote> voteList = v.getVoteList();
        Integer participants = v.getParticipants();
        if(voteList == null) {
            voteList = new ArrayList<Vote>();
            participants = 0;
        }
        if(participants == null) {
            participants = 0;
        }
        voteList.add(vote);
        Map<Integer, Integer> optionVotes = v.getOptionVotes();
        Integer totalVote = v.getTotalVote();
        for(Integer i : vote.getVoteIndex()) {
            Integer num = optionVotes.get(i);
            num++;
            optionVotes.put(i, num);
            totalVote++;
        }
        participants++;
        v.setParticipants(participants);
        v.setTotalVote(totalVote);
        v.setOptionVotes(optionVotes);
        v.setVoteList(voteList);
        return save(v);
    }
}
