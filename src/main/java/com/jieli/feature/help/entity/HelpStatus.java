package com.jieli.feature.help.entity;

import java.io.Serializable;

/**
 * Created by meijia.lv on 14-5-24.
 */
public enum HelpStatus implements Serializable{
    PENDING(1),
    PASSED(2),
    REJECTED(3);
    private int value;
    private HelpStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
