package com.jieli.activity;

import com.jieli.mongo.Model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class Activity extends Model {

    public AcivityTag tag;

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

    public List<String> followMembers;

    public Map<String, String> joinMembers=new LinkedHashMap<String, String>();

    public List<Comment> comments;

    public String associationId;

    public String sponsorUserId;

    public static final class Comment{

        public String userId;

        public String content;

        public Date date;
    }

}
