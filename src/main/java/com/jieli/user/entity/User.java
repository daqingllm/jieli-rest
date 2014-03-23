package com.jieli.user.entity;

import com.jieli.common.entity.InterestTag;
import com.jieli.mongo.Model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-9
 * Time: 下午9:28
 * 用户结构
 */
public class User extends Model {

    //头像
    public String userFace;

    public String name;

    //0：女，1：男
    public int sex;

    public String birthday;

    //星座
    public String constellation;

    //协会身份
    public String identity;

    //积分
    public int score;

    //0：国内，1：海外
    public String area;

    public String school;

    public String degree;

    //行业、专业
    public String profession;

    public String phone;

    public String mail;

    public String weixin;

    public List<InterestTag> interestTags;

    //协会
    public String associationId;
}
