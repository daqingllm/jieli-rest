package com.jieli.entity.user;

import com.jieli.entity.common.InterestTag;
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

    public String userFace;

    public PersonalDetail person;

    public AppInfo appInfo;

    public Education education;

    public Contact contact;

    public List<InterestTag> interestTags;
}
