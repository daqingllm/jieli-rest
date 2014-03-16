package com.jieli.util;

import java.util.Collection;

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
}
