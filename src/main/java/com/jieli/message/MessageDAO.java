package com.jieli.message;

import com.jieli.mongo.GenericDAO;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午8:20
 * To change this template use File | Settings | File Templates.
 */
public class MessageDAO extends GenericDAO<Message> {

    //目前是有多少未读就返回多少
    public Iterable<Message> getMessagesByUserId(String userId) {
        return getCollection().find("{userId:#,read:#}", userId, "false").as(Message.class);
    }
}
