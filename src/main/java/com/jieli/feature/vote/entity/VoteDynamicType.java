package com.jieli.feature.vote.entity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-30
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public enum VoteDynamicType {

    JOIN("JOIN");

    private String value;

    private VoteDynamicType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
