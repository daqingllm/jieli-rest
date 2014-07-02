package com.jieli.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class CollectionUtils {

    public static boolean isEmpty(Collection<?> target) {
        if (target == null || target.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Object getByIndexAndCount(Iterable<?> target, int index, ImageCount imageCount){
        boolean validTarget = true;
        Object returnObject = NoneObject;
        imageCount.count = 0;

        if (target == null){
            return returnObject;
        }

        if (index < 0){
            index = -1;
        }

        for (Object object : target){
            if (index == imageCount.count){
                returnObject = object;
            }
            imageCount.count ++;
        }

        return returnObject;
    }

    public static final Object NoneObject= null;
}
