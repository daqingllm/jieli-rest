package com.jieli.feature.help.entity;

/**
 * Created by meijia.lv on 14-6-2.
 */
public enum HelpType {
    OFFER(0),
    NEED(1),
    ALL(2);
    private int type;
    private HelpType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
