package com.jieli.feature.help.dao;

import com.jieli.activity.Activity;
import com.jieli.feature.help.entity.HelpComment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.message.Message;
import com.jieli.message.MessageType;
import com.jieli.mongo.GenericDAO;
import com.jieli.user.entity.User;
import com.jieli.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-29
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
public class HelpDAO extends GenericDAO<HelpInfo> {
    /**
     * 获取单个互帮互助详情
     * @param helpId
     * @return
     */
    /*public HelpInfo loadById(String helpId) {
        return loadById(helpId);
    }*/

    /**
     * 获取协会内互帮互助帖子列表
     * @param associationId
     * @return
     */
    public List<SimpleHelpInfo> getHelpInfoList(String associationId) {
        Iterator<SimpleHelpInfo> iterator = col.find("{\"associationId\":#}", associationId).as(SimpleHelpInfo.class).iterator();
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
    /*public List<HelpComment> getCommentList(String helpId) {
        Iterator<HelpComment> iterator = col.find("{\"helpId\":#}", helpId).as(HelpComment.class).iterator();
        List<HelpComment> resultList = new ArrayList<HelpComment>();
        for(;iterator.hasNext();) {
            resultList.add(iterator.next());
        }
        return resultList;
    }*/

    /**
     * 增加评论
     * @param comment
     * @return
     */
    public HelpInfo addComment(HelpComment comment) {
        String helpId = comment.getHelpId();
        HelpInfo help = loadById(helpId);
        if(help == null) {
            return null;
        }
        List<HelpComment> commentList = help.getCommentList();
        if(commentList == null)
            commentList = new ArrayList<HelpComment>();
        commentList.add(comment);
        help.setCommentList(commentList);
        return save(help);
    }

    /**
     * 删除评论
     * @param commentIndex
     */
    public HelpInfo deleteComment(String helpId, Integer commentIndex) {
        HelpInfo help = loadById(helpId);
        if(help == null) {
            return null;
        }
        List<HelpComment> commentList = help.getCommentList();
        if(commentList == null || commentList.isEmpty()) {
            return null;
        }
        commentList.remove(commentIndex.intValue());
        help.setCommentList(commentList);
        return save(help);
    }

    /**
     * 增加关注
     * @param helpId
     * @return
     */
    public HelpInfo addFocus(String helpId, User user) {
        HelpInfo help = loadById(helpId);
        Integer attention = help.getAttentionNum();
        attention++;
        help.setAttentionNum(attention);
        List<User> focusList = help.getFocusList();
        if(focusList == null) {
            focusList = new ArrayList<User>();
        }
        focusList.add(user);
        help.setFocusList(focusList);
        return save(help);
    }

    /**
     * 评论置顶
     * @param helpId
     * @param commentIndex
     * @return
     */
    public HelpInfo topComment(String helpId, Integer commentIndex) {
        HelpInfo help = loadById(helpId);
        List<HelpComment> commentList = help.getCommentList();
        HelpComment comment = commentList.get(commentIndex);
        comment.setTop(true);
        commentList.set(commentIndex, comment);
        help.setCommentList(commentList);
        return save(help);
    }

    private HelpComment loadComment(String commentId, List<HelpComment> comments) {
        if(CollectionUtils.isEmpty(comments)) {
            return null;
        }
        /*for (HelpComment comment : comments) {
            if(comment.get_id() .equals(commentId)) {
                return comment;
            }
        }*/
        return comments.get(Integer.getInteger(commentId));
    }

    /*private HelpInfo updateComment(String helpId, HelpComment newComment) {
        HelpInfo help = loadById(helpId);
        List<HelpComment> commentList = help.getCommentList();
        for(HelpComment comment : commentList) {
            if(comment.get_id().equals(newComment.get_id())) {
                comment = newComment;
                help.setCommentList(commentList);
                return save(help);
            }
        }
        return null;
    }*/
}
