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
}
