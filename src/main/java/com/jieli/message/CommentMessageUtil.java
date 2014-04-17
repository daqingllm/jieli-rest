package com.jieli.message;

import com.jieli.comment.Comment;
import com.jieli.comment.CommentMsg;
import com.jieli.comment.CommentUserInfo;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-5
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommentMessageUtil {

    private static UserDAO userDAO = new UserDAO();
    private static MessageDAO messageDAO = new MessageDAO();

    public static void addCommentMessage(Comment comment) {

        // 给被评论者发消息
        if(StringUtils.isNotEmpty(comment.commentedUserId)){
            Message message = new Message();
            message.userId = comment.commentedUserId; // 消息接受者即为被评论者
            message.messageType = MessageType.COMMENT;

            CommentUserInfo commentUserInfo = new CommentUserInfo();
            commentUserInfo.userId = comment.commentUserId;
            User user = userDAO.loadById(commentUserInfo.userId);
            commentUserInfo.name = user.name;
            commentUserInfo.userFace = user.userFace;

            CommentMsg commentMsg = new CommentMsg();
            commentMsg.commentId = comment.get_id().toString();
            commentMsg.commentUser = commentUserInfo;
            commentMsg.commentContent = comment.content;
            //commentMsg.topicBrief =  // 被评论内容概述
            commentMsg.commentTime = comment.addTime;

            message.content = commentMsg;
            message.read = false;
            message.addTime = new Date();

            messageDAO.save(message);
        }
    }
}
