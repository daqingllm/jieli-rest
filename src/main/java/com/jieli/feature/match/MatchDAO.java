package com.jieli.feature.match;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-16
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
public class MatchDAO extends GenericDAO<Match> {

    public Iterable<Match> getTopMatch(int count) {
        return col.find().sort("{score:-1}").limit(count).as(Match.class);
    }

    public void upsert(Match match) {
        Match oldMatch = col.findOne("{userId1:#, userId2:#, score:#}", match.userId1, match.userId2, match).as(Match.class);
        if (oldMatch != null) {
            oldMatch.score = match.score;
            save(oldMatch);
        }
        oldMatch = col.findOne("{userId1:#, userId2:#, score:#}", match.userId2, match.userId1, match).as(Match.class);
        if (oldMatch != null) {
            oldMatch.score = match.score;
            save(oldMatch);
        } else {
            save(match);
        }
    }
}
