package com.jieli.feature.match;

import com.jieli.user.entity.User;
import com.jieli.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-16
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class MatchUtil {

    public static int calcMatchingScore(User user1, User user2) {
        int score = 70;
        if (!CollectionUtils.isEmpty(user1.interests) && !CollectionUtils.isEmpty(user2.interests)) {
            score += calcInterests(user1.interests, user2.interests);
        }
        if (!StringUtils.isEmpty(user1.profession) && !StringUtils.isEmpty(user2.profession)) {
            score += calcProfession(user1.profession, user2.profession);
        }
        if (user1.birthday != null && user2.birthday != null) {
            score += calBirthday(user1.birthday, user2.birthday);
        }
        if (!StringUtils.isEmpty(user1.constellation) && !StringUtils.isEmpty(user2.constellation)) {
            score += calConstellation(user1.constellation, user2.constellation);
        }
        score += calEducation(user1, user2);

        return score;
    }

    private static int calcInterests(List<String> interests1, List<String> interests2) {
        int count = 0;
        for (String i : interests1) {
            for (String j : interests2) {
                if (i.equals(j)) {
                    count++;
                    break;
                }
            }
            if (count > 2) {
                break;
            }
        }
        if (count == 0) return 0;
        if (count == 1) return 3;
        if (count == 2) return 5;
        return 10;
    }

    private static int calcProfession(String profession1, String profession2) {
        if (profession1.equals(profession2)) {
            return 10;
        }
        return 0;
    }

    private static int calBirthday(Date birthday1, Date birthday2) {
        int score = 0;
        if (birthday1.getYear() == birthday2.getYear()) {
            score = 2;
            if (birthday1.getMonth() == birthday2.getMonth() && birthday1.getDay() == birthday2.getDay()) {
                score = 8;
            }
        } else if (birthday1.getMonth() == birthday2.getMonth() && birthday1.getDay() == birthday2.getDay()) {
            score = 3;
        }
        return score;
    }

    private static int calConstellation(String constellation1, String constellation2) {
        if (constellation1.equals(constellation2)) {
            return 2;
        }
        return 0;
    }

    private static int calEducation(User user1, User user2) {
        if (user1.eduPlace == user2.eduPlace && user1.degree == user2.degree) {
            return 5;
        }
        return 0;
    }

}
