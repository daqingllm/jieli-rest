package com.jieli.association;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-16
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
public class GroupDAO extends GenericDAO<Group> {

    public Iterable<Group> loadAll(String associationId) {
        return col.find("{associationId:#}", associationId).as(Group.class);
    }
}
