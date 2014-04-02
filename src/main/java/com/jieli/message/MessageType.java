package com.jieli.message;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-22
 * Time: 下午7:34
 * To change this template use File | Settings | File Templates.
 */
public enum MessageType {

    ACTIVITY("activity"),
    NEWS("news"),
    USER("user"),
    HELP("help"),
    ASSOCIATION("association"),
    VOTE("vote");

    private String value;

    private MessageType(String type) {
        this.value = type;
    }

    public String toString() {
        return this.value;
    }
}
