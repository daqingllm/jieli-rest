package com.jieli.util;

import org.bson.types.ObjectId;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-23
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class MongoUtils {

    public static boolean isValidObjectId(String objectId) {
        try {
            new ObjectId(objectId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
