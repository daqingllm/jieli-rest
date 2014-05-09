package com.jieli.user.dao;

import com.jieli.mongo.GenericDAO;
import com.jieli.user.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午12:58
 * To change this template use File | Settings | File Templates.
 */
public class UserDAO extends GenericDAO<User> {

    public Iterable<User> loadAll(String associationId) {
        return col.find("{associationId:#}", associationId).as(User.class);
    }

    public Iterable<User> loadByGroup(String associationId, String group) {
        return col.find("{associationId:#, group:#}", associationId, group).as(User.class);
    }

    public Iterable<User> loadByIdentity(String associationId, String identity) {
        return col.find("{associationId:#, identity:#}", associationId, identity).as(User.class);
    }
}
