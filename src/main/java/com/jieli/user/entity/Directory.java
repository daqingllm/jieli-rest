package com.jieli.user.entity;

import com.jieli.mongo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class Directory extends Model {

    public String userId;

    public List<Friend> content = new ArrayList<Friend>();

    public List<String> concerned = new ArrayList<String>();
}
