package com.jieli.feature.discuss.entity;

/**
 * Created by meijia.lv on 14-6-1.
 */
public enum  DiscussType {
    LIFE("生活"),
    ACTIVITY("活动"),
    HEALTH("健康"),
    READING("读书"),
    NEWS("新闻");
    private String type;
    private DiscussType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
