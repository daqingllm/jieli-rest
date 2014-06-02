package com.jieli.feature.discuss.dao;

import com.jieli.comment.Comment;
import com.jieli.feature.discuss.entity.DiscussInfo;
import com.jieli.feature.discuss.entity.DiscussType;
import com.jieli.feature.discuss.entity.SimpleDiscussInfo;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meijia.lv
 * Date: 14-3-29
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
public class DiscussDAO extends GenericDAO<DiscussInfo> {
    /**
     * 获取协会内讨论帖子列表
     * @param associationId
     * @return
     */
    public List<SimpleDiscussInfo> getDiscussInfoList(int pageNo, int pageSize, String associationId, int type) {
        Iterable<SimpleDiscussInfo> iterable;
        if(pageNo == 0 || pageSize == 0) {
            if(type != DiscussType.ALL.getType()) {
                iterable = col.find("{associationId:#, type:#}", associationId, type)
                        .sort("{addTime:-1}").as(SimpleDiscussInfo.class);
            }
            else {
                iterable = col.find("{associationId:#}", associationId)
                        .sort("{addTime:-1}").as(SimpleDiscussInfo.class);
            }
        }
        else {
            if(type != DiscussType.ALL.getType()) {
                iterable = col.find("{associationId:#, type:#}", associationId, type)
                        .sort("{addTime:-1}").skip((pageNo - 1) * pageSize).limit(pageSize).as(SimpleDiscussInfo.class);
            }
            else {
                iterable = col.find("{associationId:#}", associationId)
                        .sort("{addTime:-1}").skip((pageNo - 1) * pageSize).limit(pageSize).as(SimpleDiscussInfo.class);
            }
        }

        List<SimpleDiscussInfo> resultList = new ArrayList<SimpleDiscussInfo>();
        for(SimpleDiscussInfo v : iterable) {
            resultList.add(v);
        }
        return resultList;
    }

    /**
     * 发表帖子
     * @param discuss
     * @return
     */
    public DiscussInfo addDiscuss(DiscussInfo discuss) {
        return save(discuss);
    }


    /**
     * 增加关注
     * @param discussId
     * @return
     */
    public DiscussInfo addFocus(String discussId, String userId) {
        DiscussInfo discuss = loadById(discussId);
        Integer attention = discuss.getAttentionNum();
        attention++;
        discuss.setAttentionNum(attention);
        List<String> focusList = discuss.getFocusList();
        if(focusList == null) {
            focusList = new ArrayList<String>();

        }
        focusList.add(userId);
        discuss.setFocusList(focusList);
        return save(discuss);
    }

    /**
     * 评论置顶
     * @param discussId
     * @param comment
     * @return
     */
    public DiscussInfo topComment(String discussId, Comment comment) {
        DiscussInfo discuss = loadById(discussId);
        List<Comment> topCommentList = discuss.getTopCommentList();
        if(topCommentList == null) {
            topCommentList = new ArrayList<Comment>();
        }
        topCommentList.add(comment);
        discuss.setTopCommentList(topCommentList);
        return save(discuss);
    }

}
