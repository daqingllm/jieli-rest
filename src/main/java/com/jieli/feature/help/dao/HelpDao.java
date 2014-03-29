package com.jieli.feature.help.dao;

import com.jieli.feature.help.entity.HelpComment;
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
     * 发表互帮互助帖子
     * @param help
     * @return
     */
    public HelpInfo addHelp(HelpInfo help) {
        return save(help);
    }

    /**
     * 获取评论列表
     * @param helpId
     * @return
     */
    public List<HelpComment> getCommentList(String helpId) {
        Iterator<HelpComment> iterator = col.find("helpId:#", helpId).as(HelpComment.class).iterator();
        List<HelpComment> resultList = new ArrayList<HelpComment>();
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
    public HelpInfo addComment(HelpComment comment) {
        String helpId = comment.getHelpId();
        HelpInfo help = loadHelpInfo(helpId);
        if(help == null) {
            return null;
        }
        List<HelpComment> commentList = help.getCommentList();
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
        List<HelpComment> commentList = help.getCommentList();
        if(commentList == null || commentList.isEmpty()) {
            return null;
        }
        HelpComment comment = loadComment(commentId, commentList);
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
        HelpComment comment = loadComment(commentId, help.getCommentList());
        comment.setTop(true);
        return updateComment(helpId, comment);
    }

    private HelpComment loadComment(String commentId, List<HelpComment> comments) {
        if(CollectionUtils.isEmpty(comments)) {
            return null;
        }
        for (HelpComment comment : comments) {
            if(comment.getId() == commentId) {
                return comment;
            }
        }
        return null;
    }

    private HelpInfo updateComment(String helpId, HelpComment newComment) {
        HelpInfo help = loadHelpInfo(helpId);
        List<HelpComment> commentList = help.getCommentList();
        for(HelpComment comment : commentList) {
            if(comment.getId() == newComment.getId()) {
                comment = newComment;
                help.setCommentList(commentList);
                return save(help);
            }
        }
        return null;
    }

}
