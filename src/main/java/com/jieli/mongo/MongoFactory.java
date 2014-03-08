package com.jieli.mongo;

import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-8
 * Time: 下午8:41
 * To change this template use File | Settings | File Templates.
 */
public class MongoFactory {

    public static Mongo getMongo() {
        Mongo mongo = null;
        try {
            mongo = new Mongo("localhost");
        } catch (UnknownHostException e) {
            return null;
        }
        return mongo;
    }

}
