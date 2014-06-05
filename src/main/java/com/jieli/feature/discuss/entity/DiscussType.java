package com.jieli.feature.discuss.entity;

/**
 * Created by meijia.lv on 14-6-1.
 */
public enum  DiscussType {
    LIFE(1),
    ACTIVITY(2),
    READING(3),
    ALL(4);

    private int type;
    private DiscussType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
