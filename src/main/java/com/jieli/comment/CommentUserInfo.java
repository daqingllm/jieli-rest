package com.jieli.comment;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM8:40
 * To change this template use File | Settings | File Templates.
 */
public class CommentUserInfo {

    public String userId;
    //头像
    public String userFace;

    public String name;

    public String phone;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
