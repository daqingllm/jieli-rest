package com.jieli.feature.help.dao;

import com.jieli.feature.help.entity.Comment;
import com.jieli.mongo.GenericDAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YolandaLeo on 14-3-21.
 */
public class CommentDAO extends GenericDAO<Comment> {
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
    public Comment addComment(Comment comment) {
        return save(comment);
    }

    public void deleteComment(String commentId) {

    }
}

