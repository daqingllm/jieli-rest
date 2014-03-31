package com.jieli.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jieli.mongo.Model;

import java.util.*;

/**
 * @author ltebean
 */
public class Activity extends Model {

    public AcivityTag tag;

    public String type;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="GMT+8")
    public Date beginDate;

    public String location;

    public String title;

    public String description;

    public int fee;

    public int maxMembers;

    public String arrangement;

    public String serviceInfo;

    public String sponsorInfo;

    //关注的用户
    public List<String> followMembers = new ArrayList<String>();

    //活动安排
    public List<Arrangement> details = new ArrayList<Arrangement>();

    //参与用户
    public Map<String, String> joinMembers=new LinkedHashMap<String, String>();

    //串局邀请
    public List<String> invitees = new ArrayList<String>();

    public List<Comment> comments = new ArrayList<Comment>();

    public String associationId;

    public String sponsorUserId;

}
