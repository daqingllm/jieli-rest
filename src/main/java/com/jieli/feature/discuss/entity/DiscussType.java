package com.jieli.feature.discuss.entity;

/**
 * Created by meijia.lv on 14-6-1.
 */
public enum  DiscussType {
    LIFE(1),
    ACTIVITY(2),
    HEALTH(3),
    READING(4),
    NEWS(5),
    ALL(6);

    private int type;
    private DiscussType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
