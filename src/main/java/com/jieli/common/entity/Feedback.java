package com.jieli.common.entity;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-7
 * Time: 下午9:08
 * To change this template use File | Settings | File Templates.
 */
public class Feedback extends Model {

    public String userId;

    public String name;

    public String associationId;

    public String associationName;

    public String Content;

    public Date date;
}
