package com.jieli.common.entity;

/**
 * Created by 95 on 2014/7/19.
 */
public enum PushMessageResult {

    SKIP(0),SUCCESS(1),NETWORK(2),OTHER_ERROR(3);

    private int value;

    private PushMessageResult(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}