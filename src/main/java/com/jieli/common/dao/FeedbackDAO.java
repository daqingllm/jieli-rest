package com.jieli.common.dao;

import com.jieli.common.entity.Feedback;
import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-7
 * Time: 下午9:16
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackDAO extends GenericDAO<Feedback> {

    public Iterable<Feedback> loadByAssociationId(String associationId) {
        return col.find("{associationId:#}", associationId).sort("{_id:-1}").as(Feedback.class);
    }

    public Iterable<Feedback> loadAll() {
        return col.find().as(Feedback.class);
    }
}
