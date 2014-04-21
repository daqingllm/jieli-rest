package com.jieli.feature.match;

import com.jieli.mongo.Model;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-16
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class Match extends Model implements Comparable<Match> {

    public String userId1;
    public String userId2;
    public int score = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        if (!super.equals(o)) return false;

        Match match = (Match) o;

        if (match.score != score) return false;
//        if (!userId1.equals(match.userId1)) return false;
//        if (!userId2.equals(match.userId2)) return false;
        if (userId1.equals(match.userId1) && userId2.equals(match.userId2)) {
            return true;
        } else if (userId1.equals(match.userId2) && userId2.equals(match.userId1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + userId1.hashCode();
        result = 31 * result + userId2.hashCode();
        result = 31 * result + (int) (score ^ (score >>> 32));
        return result;
    }

    @Override
    public int compareTo(Match o) {
        return o.score - score;
    }
}
