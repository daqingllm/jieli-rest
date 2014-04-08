package com.jieli.activity;

import com.jieli.mongo.GenericDAO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-23
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class ActivityDAO extends GenericDAO<Activity> {

    public Iterable<Activity> loadAll() {
        return col.find().as(Activity.class);
    }

    public Iterable<Activity> findOngoingOfficial(String associationId) {
        Date now = new Date();
        return col.find("{tag:#, associationId:#, beginDate:{$gt:#}}", "OFFICIAL", associationId, now).sort("{beginDate:-1}").as(Activity.class);
    }

    public Iterable<Activity> findOngoingRecommend() {
        Date now = new Date();
        return col.find("{tag:#, beginDate:{$gt:#}}", "RECOMMEND", now).sort("{beginDate:-1}").as(Activity.class);
    }

    public Iterable<Activity> findOngoing(String associationId, int page, int count) {
        Date now = new Date();
        return col.find("{tag:{$in:[#,#]}, associationId:#, beginDate:{$gt:#}}", "OFFICIAL", "RECOMMEND", associationId, now)
                .skip(page*count).limit(count).sort("{beginDate:-1}").as(Activity.class);
    }

}
