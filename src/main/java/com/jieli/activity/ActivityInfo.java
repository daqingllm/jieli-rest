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


    public enum RelatedType {

        SPONSER("sponser"), CONCERN("concern"), JOIN("join");

        private String value;

        private RelatedType(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }
}
