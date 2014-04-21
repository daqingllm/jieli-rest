package com.jieli.feature.vote.dao;

import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
import com.jieli.feature.vote.entity.Vote;
import com.jieli.feature.vote.entity.VoteComment;
import com.jieli.feature.vote.entity.VoteInfo;
import com.jieli.mongo.GenericDAO;
import com.jieli.news.News;

import java.util.*;

/**
 * Created by YolandaLeo on 14-3-29.
 */
public class VoteDAO extends GenericDAO<VoteInfo> {

    /**
     * 获取投票列表
     * @param associationId
     * @return
     */
    public List<SimpleVoteInfo> getVoteInfoList(int pageNo, int pageSize, String associationId) {
        Iterable<SimpleVoteInfo> voteIterator = col.find("{\"associationId\":#}", associationId)
                .sort("{addTime:-1}").skip((pageNo - 1) * pageSize).limit(pageSize).as(SimpleVoteInfo.class);
        List<SimpleVoteInfo> resultList = new ArrayList<SimpleVoteInfo>();
        for(SimpleVoteInfo v : voteIterator) {
            resultList.add(v);
        }
        return resultList;
    }

    /**
     * 发布投票
     * @param voteInfo
     * @return
     */
    public VoteInfo addVote(VoteInfo voteInfo) {
        return save(voteInfo);
    }

    /**
     * 投票
     * @param vote
     * @param voteId
     * @return
     */
    public VoteInfo vote(Vote vote, String voteId) {
        VoteInfo v = loadById(voteId);
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
        List<Integer> optionVotes = v.getOptionVotes();
        Integer totalVote = v.getTotalVote();
        for(Integer i : vote.getVoteIndex()) {
            Integer num = optionVotes.get(i);
            num++;
            optionVotes.set(i, num);
            totalVote++;
        }
        participants++;
        v.setParticipants(participants);
        v.setTotalVote(totalVote);
        v.setOptionVotes(optionVotes);
        v.setVoteList(voteList);
        return save(v);
    }

    /**
     * 评论投票
     * @param comment
     * @param voteId
     * @return
     */
    /*public VoteInfo addComment(VoteComment comment, String voteId) {
        VoteInfo voteInfo = loadById(voteId);
        List<VoteComment> commentList = voteInfo.getCommentList();
        if(commentList == null) {
            commentList = new ArrayList<VoteComment>();
        }
        commentList.add(comment);
        voteInfo.setCommentList(commentList);
        return save(voteInfo);
    }*/

}
