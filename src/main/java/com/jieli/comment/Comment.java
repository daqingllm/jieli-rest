package com.jieli.comment;

import com.jieli.mongo.Model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 14-4-1
 * Time: PM7:47
 * To change this template use File | Settings | File Templates.
 */
public class Comment extends Model {
    private static final long serialVersionUID = -3459753602651398540L;

    public String topicId; // 评论内容id
    public String topicType; // 被评论内容类型
    public String topicTitle;
    public String tag; // 为串局添加

    public String commentUserId;  // 回复者id
    public String commentedUserId; // 被回复者id

    public String content;

    public Date addTime;


    public CommentUserInfo commentUserInfo; // 评论者详细信息，用于展示
    public CommentUserInfo commentedUserInfo; // 被回复者详细信息，用于展示

    public boolean isDeleted = false;  //是否已被删除

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(String commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public CommentUserInfo getCommentUserInfo() {
        return commentUserInfo;
    }

    public void setCommentUserInfo(CommentUserInfo commentUserInfo) {
        this.commentUserInfo = commentUserInfo;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (isDeleted != comment.isDeleted) return false;
        if (addTime != null ? !addTime.equals(comment.addTime) : comment.addTime != null) return false;
        if (commentUserId != null ? !commentUserId.equals(comment.commentUserId) : comment.commentUserId != null)
            return false;
        if (commentUserInfo != null ? !commentUserInfo.equals(comment.commentUserInfo) : comment.commentUserInfo != null)
            return false;
        if (commentedUserId != null ? !commentedUserId.equals(comment.commentedUserId) : comment.commentedUserId != null)
            return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (topicId != null ? !topicId.equals(comment.topicId) : comment.topicId != null) return false;
        if (topicType != null ? !topicType.equals(comment.topicType) : comment.topicType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (topicId != null ? topicId.hashCode() : 0);
        result = 31 * result + (topicType != null ? topicType.hashCode() : 0);
        result = 31 * result + (commentUserId != null ? commentUserId.hashCode() : 0);
        result = 31 * result + (commentedUserId != null ? commentedUserId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        result = 31 * result + (commentUserInfo != null ? commentUserInfo.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        return result;
    }

}
