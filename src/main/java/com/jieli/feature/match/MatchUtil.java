package com.jieli.feature.match;

import com.jieli.user.entity.User;
import com.jieli.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-16
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
public class MatchUtil {

    private Match match = new Match();
    private User user1;
    private User user2;

    public MatchUtil(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        match.userId1 = user1.get_id().toString();
        match.userId2 = user2.get_id().toString();
    }

    public Match getMatch() {
        generateMatch();
        return match;
    }

    private void generateMatch() {
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
//        score += calEducation(user1, user2);
        score += 5;

        match.score = score;
    }

    private int calcInterests(List<String> interests1, List<String> interests2) {
        int count = 0;
        List<String> interests = new ArrayList<String>();
        for (String i : interests1) {
            for (String j : interests2) {
                if (i.equals(j)) {
                    count++;
                    interests.add(i);
                    break;
                }
            }
        }
        if (count == 0) return 0;

        String info = "相同爱好：";
        for (int i=0; i<interests.size(); ++i) {
            if (i != 0) {
                info += "、";
            }
            info += interests.get(i);
        }
        match.matchInfos.add(info);
        if (count == 1) return 3;
        if (count == 2) return 5;
        return 10;
    }

    private int calcProfession(String profession1, String profession2) {
        if (profession1.equals(profession2)) {
            match.matchInfos.add("相同行业：" + profession1);
            return 10;
        }
        return 0;
    }

    private int calBirthday(Date birthday1, Date birthday2) {
        int score = 0;
        if (birthday1.getYear() == birthday2.getYear()) {
            String info = "生于同一年";
            score = 2;
            if (birthday1.getMonth() == birthday2.getMonth() && birthday1.getDay() == birthday2.getDay()) {
                info = "同年同月同日生！";
                score = 8;
            }
            match.matchInfos.add(info);
        } else if (birthday1.getMonth() == birthday2.getMonth() && birthday1.getDay() == birthday2.getDay()) {
            match.matchInfos.add("同一天生日");
            score = 3;
        }
        return score;
    }

    private int calConstellation(String constellation1, String constellation2) {
        if (constellation1.equals(constellation2)) {
            match.matchInfos.add("星座相同");
            return 2;
        }
        return 0;
    }

    private int calEducation(User user1, User user2) {
        if (user1.eduPlace == user2.eduPlace && user1.degree == user2.degree) {
            String info = "均为";
            switch (user1.eduPlace) {
                case 0:
                    info += "国内";
                    break;
                case 1:
                    info += "国外";
                    break;
            }
            switch (user1.degree) {
                case 0:
                    info += "学士";
                    break;
                case 1:
                    info += "硕士";
                    break;
                case 2:
                    info += "博士";
                    break;
            }
            match.matchInfos.add(info);
            return 5;
        }
        return 0;
    }

}
