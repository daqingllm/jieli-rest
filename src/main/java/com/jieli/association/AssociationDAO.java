package com.jieli.association;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午6:05
 * To change this template use File | Settings | File Templates.
 */
public class AssociationDAO extends GenericDAO<Association> {

    public Association loadByName(String name) {
        return col.findOne("{name:#}", name).as(Association.class);
    }

    public Iterable<Association> loadAll() {
        return col.find().as(Association.class);
    }
}
