package com.jieli.activity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-28
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
public enum AcivityTag {

    RECOMMEND("recommend"), OFFICIAL("official"), PRIVATE("private");

    private String value;

    private AcivityTag(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
