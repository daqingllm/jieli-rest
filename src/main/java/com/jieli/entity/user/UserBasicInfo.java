package com.jieli.entity.user;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 下午8:32
 * To change this template use File | Settings | File Templates.
 */
public class UserBasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public int userId;

    public String name;

    //头像
    public String userFace;

    //0：女，1：男
    public int sex;

    //协会身份
    public String identity;

    //积分
    public int score;

    //是否为特别关注
    public boolean special;

    //分组
    public String group;
}
