package com.jieli.activity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-30
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
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
