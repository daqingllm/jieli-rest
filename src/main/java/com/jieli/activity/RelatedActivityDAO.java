package com.jieli.activity;

import com.jieli.mongo.GenericDAO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class RelatedActivityDAO extends GenericDAO<RelatedActivity> {

    public List<ActivityInfo> findUserActivities(String userId) {
        RelatedActivity relatedActivity = col.findOne("{userId:#}", userId).as(RelatedActivity.class);
        Collections.sort(relatedActivity.infos, new InfoComparator());
        return relatedActivity.infos;
    }

    public class InfoComparator implements Comparator<ActivityInfo> {

        @Override
        public int compare(ActivityInfo o1, ActivityInfo o2) {
            return 0 - o1.activityId.compareTo(o2.activityId);
        }
    }
}
