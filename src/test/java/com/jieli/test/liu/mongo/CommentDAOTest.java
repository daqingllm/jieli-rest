package com.jieli.test.liu.mongo;

import com.jieli.comment.Comment;
import com.jieli.mongo.BaseDAO;
import com.jieli.mongo.Collections;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-8
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
public class CommentDAOTest {

    BaseDAO<Comment> commentDAO = new BaseDAO<Comment>(Collections.Comment, Comment.class);

    @Test
    public void reInit() {
        List<Comment> comments = commentDAO.find("{}");
        for (Comment comment : comments) {
            commentDAO.save(comment);
        }
    }
}
