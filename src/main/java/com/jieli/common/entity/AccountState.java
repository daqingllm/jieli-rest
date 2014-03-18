package com.jieli.common.entity;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 下午7:33
 * To change this template use File | Settings | File Templates.
 */
public enum AccountState {

    ENABLE(0),DISABLE(1);

    private int value;

    private AccountState(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
