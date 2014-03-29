package com.jieli.feature.help.dao;

import com.jieli.feature.help.entity.Comment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.mongo.GenericDAO;
import com.jieli.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 互帮互助数据库读取
 * Created by YolandaLeo on 14-3-19.
 */
public class HelpDAO extends GenericDAO<HelpInfo>{
    /**
     * 获取单个互帮互助详情
     * @param helpId
     * @return
     */
    public HelpInfo loadHelpInfo(String helpId) {
        return col.findOne("helpId:#",helpId).as(HelpInfo.class);
    }

    /**
     * 获取协会内互帮互助帖子列表
     * @param associationId
     * @return
     */
    public List<SimpleHelpInfo> getHelpInfoList(String associationId) {
        Iterator<SimpleHelpInfo> iterator = col.find("associationId:#", associationId).as(SimpleHelpInfo.class).iterator();
        List<SimpleHelpInfo> resultList = new ArrayList<SimpleHelpInfo>();
        for(;iterator.hasNext();) {
            resultList.add(iterator.next());
        }
        return resultList;
    }

    /**
     * 获取评论列表
     * @param helpId
     * @return
     */
    public List<Comment> getCommentList(String helpId) {
        Iterator<Comment> iterator = col.find("helpId:#", helpId).as(Comment.class).iterator();
        List<Comment> resultList = new ArrayList<Comment>();
        for(;iterator.hasNext();) {
            resultList.add(iterator.next());
        }
        return resultList;
    }

    /**
     * 增加评论
     * @param comment
     * @return
     */
    public HelpInfo addComment(Comment comment) {
        String helpId = comment.getHelpId();
        HelpInfo help = loadHelpInfo(helpId);
        if(help == null) {
            return null;
        }
        List<Comment> commentList = help.getCommentList();
        commentList.add(comment);
        help.setCommentList(commentList);
        return save(help);
    }

    /**
     * 删除评论
     * @param commentId
     */
    public HelpInfo deleteComment(String helpId, String commentId) {
        HelpInfo help = loadHelpInfo(helpId);
        if(help == null) {
            return null;
        }
        List<Comment> commentList = help.getCommentList();
        if(commentList == null || commentList.isEmpty()) {
            return null;
        }
        Comment comment = loadComment(commentId, commentList);
        if(commentList.remove(comment)) {
            help.setCommentList(commentList);
            return save(help);
        }
        else {
            return null;
        }
    }

    /**
     * 增加关注
     * @param helpId
     * @return
     */
    public HelpInfo addFocus(String helpId) {
        HelpInfo help = loadHelpInfo(helpId);
        Integer attention = help.getAttentionNum();
        attention++;
        help.setAttentionNum(attention);
        return save(help);
    }

    /**
     * 评论置顶
     * @param helpId
     * @param commentId
     * @return
     */
    public HelpInfo topComment(String helpId, String commentId) {
        HelpInfo help = loadHelpInfo(helpId);
        Comment comment = loadComment(commentId, help.getCommentList());
        comment.setTop(true);
        return updateComment(helpId, comment);
    }

    private Comment loadComment(String commentId, List<Comment> comments) {
        if(CollectionUtils.isEmpty(comments)) {
            return null;
        }
        for (Comment comment : comments) {
            if(comment.getId() == commentId) {
                return comment;
            }
        }
        return null;
    }

    private HelpInfo updateComment(String helpId, Comment newComment) {
        HelpInfo help = loadHelpInfo(helpId);
        List<Comment> commentList = help.getCommentList();
        for(Comment comment : commentList) {
            if(comment.getId() == newComment.getId()) {
                comment = newComment;
                help.setCommentList(commentList);
                return save(help);
            }
        }
        return null;
    }

}
