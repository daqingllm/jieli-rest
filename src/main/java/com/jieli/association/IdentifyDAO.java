package com.jieli.association;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-7
 * Time: 下午8:18
 * To change this template use File | Settings | File Templates.
 */
public class IdentifyDAO extends GenericDAO<Identify> {

    public Iterable<Identify> loadAll(String associationId) {
        return col.find("{associationId:#}", associationId).as(Identify.class);
    }
}
