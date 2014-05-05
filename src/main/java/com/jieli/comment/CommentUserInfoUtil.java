package com.jieli.comment;

import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-5-5
 * Time: 下午8:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommentUserInfoUtil {

    public static CommentUserInfo generate(String userId) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.loadById(userId);
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.userId = userId;
        commentUserInfo.userFace = user.userFace;
        commentUserInfo.name = user.name;

        return commentUserInfo;
    }
}
