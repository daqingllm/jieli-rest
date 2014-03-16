package com.jieli.dao;

import com.jieli.entity.common.Account;
import com.jieli.mongo.GenericDAO;
import org.bson.types.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class AccountDAO extends GenericDAO<Account> {

    public Account loadByUsername(String username) {
        return col.findOne("{username:#}", username).as(Account.class);
    }

    public Account loadByObjectId(String objectId) {
        return col.findOne("{_id:#}", new ObjectId(objectId)).as(Account.class);
    }
}
