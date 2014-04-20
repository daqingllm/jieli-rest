package com.jieli.feature.match;

import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-20
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class MatchTask {

    private int count;
    private User user2match;
    private MatchDAO matchDAO = new MatchDAO();
    private UserDAO userDAO = new UserDAO();

    public MatchTask(int topCount, User user) {
        this.count = topCount;
        this.user2match = user;
    }

    public List<Match> getResult() {
        List<Match> topMatches = new ArrayList<Match>();
        if (StringUtils.isEmpty(user2match.associationId)) {
            return topMatches;
        }
        Iterable<User> allUsers = userDAO.loadAll(user2match.associationId);
        for (User user : allUsers) {
            if (user.get_id().toString().equals(user2match.get_id().toString())) {
                continue;
            }
            Match match = new Match();
            match.userId1 = user2match.get_id().toString();
            match.userId2 = user.get_id().toString();
            match.score = MatchUtil.calcMatchingScore(user2match, user);
            topMatches.add(match);
            matchDAO.upsert(match);
        }
        Match[] matches = (Match[]) topMatches.toArray();
        Arrays.sort(matches);
        topMatches = Arrays.asList(matches);

        if (topMatches.size() > count) {
            topMatches = topMatches.subList(0, count);
        }
        return topMatches;
    }
}
