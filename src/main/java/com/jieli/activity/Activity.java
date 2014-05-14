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

    public Map<String, String> serviceInfo = new LinkedHashMap<String, String>();

    public Map<String, String> diamondInfo = new LinkedHashMap<String, String>();

    public Map<String, String> sponsorInfo2 = new LinkedHashMap<String, String>();

    public String sponsorInfo;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="GMT+8")
    public Date addTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="GMT+8")
	public Date actDate;

    public Map<String, List<String>> album = new HashMap<String, List<String>>();

    public List<String> officialAlbum = new ArrayList<String>();

    public List<String> getOfficialAlbum() {
        return officialAlbum;
    }

    public void setOfficialAlbum(List<String> officialAlbum) {
        this.officialAlbum = officialAlbum;
    }

    public Map<String, List<String>> getAlbum() {
        return album;
    }

    public void setAlbum(Map<String, List<String>> album) {
        this.album = album;
    }

    //关注的用户
    public List<String> followMembers = new ArrayList<String>();

    //活动安排
    public List<Arrangement> details = new ArrayList<Arrangement>();

    //参与用户
    public Map<String, String> joinMembers=new LinkedHashMap<String, String>();

    //串局邀请
    public List<String> invitees = new ArrayList<String>();

    public String associationId;

    public String sponsorUserId;

    // 活动图片地址
    public String url;

    public AcivityTag getTag() {
        return tag;
    }

    public String getType() {
        return type;
    }

    public Date getBeginDate() {
        return beginDate;
    }

	public Date getActDate() {
		return actDate;
	}
	
    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getFee() {
        return fee;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public String getArrangement() {
        return arrangement;
    }

    public Map<String, String> getServiceInfo() {
        return serviceInfo;
    }

    public String getSponsorInfo() {
        return sponsorInfo;
    }

    public List<String> getFollowMembers() {
        return followMembers;
    }

    public List<Arrangement> getDetails() {
        return details;
    }

    public Map<String, String> getJoinMembers() {
        return joinMembers;
    }

    public List<String> getInvitees() {
        return invitees;
    }

    public String getAssociationId() {
        return associationId;
    }

    public String getSponsorUserId() {
        return sponsorUserId;
    }

    public String getUrl() {
        return url;
    }
}
