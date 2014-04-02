package com.jieli.message;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午7:32
 * To change this template use File | Settings | File Templates.
 */
public class Message extends Model {

    public String userId;

    public MessageType messageType;

    public Object content;

    public Date addTime;

    public boolean read;

    public int importance;
}
