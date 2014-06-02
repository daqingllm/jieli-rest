package com.jieli.news;

import com.jieli.mongo.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-3-23
 * Time: PM11:05
 * To change this template use File | Settings | File Templates.
 */
public class News extends Model {

    public String associationId;
    public String type;  // NewsType

    public String title;

    public Date time;

    public String overview;  // 资讯预览内容

    public String content; // 资讯实体内容

    public List<Image> images = new ArrayList<Image>();
    public int imagesCount;

    public List<String> appreciateUserIds = new ArrayList<String>();  // 点赞者
    public int appreciateCount;

    public Date addTime;

    public int commentCount;

    public String professionTag;
    public List<String> interestTags = new ArrayList<String>();

    public boolean topPic = false;

    public String getAssociationId() {
        return associationId;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public Date getTime() {
        return time;
    }

    public String getOverview() {
        return overview;
    }

    public String getContent() {
        return content;
    }

    public List<Image> getImages() {
        return images;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public List<String> getAppreciateUserIds() {
        return appreciateUserIds;
    }

    public int getAppreciateCount() {
        return appreciateCount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getProfessionTag() {
        return professionTag;
    }

    public List<String> getInterestTags() {
        return interestTags;
    }

    public boolean isTopPic() {
        return topPic;
    }
}
