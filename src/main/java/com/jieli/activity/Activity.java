package com.jieli.activity;

import com.jieli.mongo.Model;
import com.jieli.user.entity.User;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class Activity extends Model {

    public String type;

    public Date beginDate;

    public String location;

    public String title;

    public String description;

    public int fee;

    public int maxMembers;

    public String arrangement;

    public String serviceInfo;

    public String sponsorInfo;

    public List<User> followMembers;

    public Map<User,String> joinMembers=new LinkedHashMap<User, String>();

    public List<Comment> comments;


    public static final class Comment{

        public User user;

        public String content;

        public Date date;
    }

}
