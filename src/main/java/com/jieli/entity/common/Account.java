package com.jieli.entity.common;

import com.jieli.mongo.Model;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class Account extends Model {

    public String username;

    public String password;

    public int userId;

    public AccountState state = AccountState.ENABLE;
}
