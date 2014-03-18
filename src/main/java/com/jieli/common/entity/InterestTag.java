package com.jieli.common.entity;

/**
 * 兴趣标签
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午12:44
 * To change this template use File | Settings | File Templates.
 */
public enum InterestTag {

    //example
    FOOTBALL("足球");

    private String value;

    private InterestTag(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
