package com.jieli.entity.user;

import com.jieli.mongo.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class Directory extends Model {

    public int userId;

    public List<Friend> content;
}
