package com.jieli.user.entity;

import com.jieli.mongo.Model;

import java.util.Date;
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

    //分组
    public String group;

    //0：男，1：女
    public int sex;

    public Date birthday;

    //星座
    public String constellation;

    //协会身份
    public String identity;

    //积分
    public int score;

    //教育地点 0：国内，1：国外
    public int eduPlace;

    public String school;

    //学位 0：学士，1：硕士，2：博士，3：其他
    public int degree;

    //行业、专业
    public String profession;

    public String phone;

    public String mail;

    public String weixin;

//    public List<InterestTag> interestTags;
    public List<String> interests;

    //协会
    public String associationId;

    //企业
    public String enterpriseName;

    //公司网址
    public String enterpriseWebsite;

    //公司成立年份
    public Date enterpriseFoundDate;

    //公司行业
    public String enterpriseIndustry;

    //公司简介
    public String enterpriseDescription;
}
