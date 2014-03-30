package com.jieli.test.mongo.yolanda;

import com.beust.jcommander.internal.Lists;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.feature.vote.dao.VoteDAO;
import com.jieli.feature.vote.entity.SimpleVoteInfo;
import com.jieli.feature.vote.entity.Vote;
import com.jieli.feature.vote.entity.VoteComment;
import com.jieli.feature.vote.entity.VoteInfo;
import org.junit.Test;

import java.util.*;

/**
 * Created by YolandaLeo on 14-3-30.
 */
public class VoteDAOTest {
    private VoteDAO voteDAO = new VoteDAO();

    @Test
    public void addVote() {
        /*VoteInfo voteInfo = new VoteInfo();
        voteInfo.setAssociationId("5337af643004e0056052bd5a");
        voteInfo.setTotalVote(0);
        voteInfo.setMultiple(true);
        voteInfo.setUserId("5336bbe13004cc09f49432e6");
        voteInfo.setDeadLine(new Date(2014, 8, 1));
        voteInfo.setAddTime(new Date());
        voteInfo.setTitle("晚饭吃什么");
        voteInfo.setDescription("晚上聚餐去哪里？");
        voteInfo.setOptions(Arrays.asList("绿茶", "西贝西北菜", "麻辣香锅", "外婆家", "Miss kiko"));
        VoteInfo result = voteDAO.addVote(voteInfo);*/

        /*VoteInfo voteInfo = new VoteInfo();
        voteInfo.setAssociationId("5337af643004e0056052bd5a");
        voteInfo.setTotalVote(0);
        voteInfo.setMultiple(false);
        voteInfo.setUserId("5336bbe13004cc09f49432e6");
        voteInfo.setDeadLine(new Date(2014, 9, 1));
        voteInfo.setAddTime(new Date());
        voteInfo.setTitle("去看油菜花");
        voteInfo.setDescription("去哪里看油菜花？");
        voteInfo.setOptions(Arrays.asList("青海", "婺源", "云南", "奉贤"));
        VoteInfo result = voteDAO.addVote(voteInfo);*/

        VoteInfo voteInfo = new VoteInfo();
        Map<Integer, Integer> initOptionVote = new HashMap<Integer, Integer>();
        for(int i = 0; i < 7; i++) {
            initOptionVote.put(i, 0);
        }
        voteInfo.setOptionVotes(initOptionVote);
        voteInfo.setAssociationId("5337af643004e0056052bd5a");
        voteInfo.setTotalVote(0);
        voteInfo.setMultiple(true);
        voteInfo.setUserId("5336bbe13004cc09f49432e6");
        voteInfo.setDeadLine(new Date(2014, 9, 1));
        voteInfo.setAddTime(new Date());
        voteInfo.setTitle("羽毛球");
        voteInfo.setDescription("什么时候去打球？");
        voteInfo.setOptions(Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        VoteInfo result = voteDAO.addVote(voteInfo);

        System.out.println(result.get_id());
    }

    @Test
    public void getVoteInfoList() {
        String associationId = "5337af643004e0056052bd5a";
        List<SimpleVoteInfo> voteInfoList = voteDAO.getVoteInfoList(associationId);
        for(SimpleVoteInfo s : voteInfoList) {
            System.out.println(s.toString());
        }
    }

    @Test
    public void vote() {
        String voteId = "533807ec300406d49b957887";
        Vote vote = new Vote();
        vote.setUserId("5336bb6f3004cc09f49432e4");
        vote.setAddTime(new Date());
        vote.setVoteIndex(Arrays.asList(1, 2));
        VoteInfo result = voteDAO.vote(vote, voteId);
        for(Vote v : result.getVoteList()) {
            System.out.println(v.getVoteIndex().size());
        }
    }

    @Test
    public void addComment() {
        VoteComment comment = new VoteComment();
        String voteId = "533807ec300406d49b957887";
        comment.setAddTime(new Date());
        comment.setCommentUserId("5336bb6f3004cc09f49432e4");
        comment.setComment("写代码什么的，皮肤变差了，要去运动一下~");
        comment.setVoteId(voteId);
        VoteInfo result = voteDAO.addComment(comment, voteId);
        System.out.println(result.getCommentList().size());
    }
}
