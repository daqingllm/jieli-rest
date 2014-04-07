package com.jieli.activity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public class ActivityInfo {

    public String activityId;

    public RelatedType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityInfo)) return false;

        ActivityInfo info = (ActivityInfo) o;

        if (activityId != null ? !activityId.equals(info.activityId) : info.activityId != null) return false;
        if (type != info.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityId != null ? activityId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
