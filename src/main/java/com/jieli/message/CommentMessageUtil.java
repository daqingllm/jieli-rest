package com.jieli.message;

import com.jieli.comment.Comment;
import com.jieli.comment.CommentMsg;
import com.jieli.comment.CommentUserInfo;
import com.jieli.comment.CommentUserInfoUtil;
import com.jieli.user.dao.UserDAO;
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

            CommentUserInfo commentUserInfo = CommentUserInfoUtil.generate(comment.commentUserId);

            CommentMsg commentMsg = new CommentMsg();
            commentMsg.commentId = comment.get_id().toString();
            commentMsg.commentUser = commentUserInfo;
            commentMsg.commentContent = comment.content;
            commentMsg.commentTime = comment.addTime;
            commentMsg.commentType = 1;
            commentMsg.topicType = comment.topicType;
            commentMsg.topicId = comment.topicId;
            commentMsg.tag = comment.tag;
            commentMsg.msg = "你在 " + comment.topicTitle + " 中的评论被 " + commentUserInfo.name + " 回复了";

            message.content = commentMsg;
            message.read = false;
            message.addTime = new Date();

            messageDAO.save(message);
        }
    }

    public static void addCommentAuthorMessage(Comment comment, String authorId) {
        Message message = new Message();
        message.userId = authorId;
        message.messageType = MessageType.COMMENT;

        CommentUserInfo commentUserInfo = CommentUserInfoUtil.generate(comment.commentUserId);

        CommentMsg commentMsg = new CommentMsg();
        commentMsg.commentId = comment.get_id().toString();
        commentMsg.commentUser = commentUserInfo;
        commentMsg.commentContent = comment.content;
        commentMsg.commentTime = comment.addTime;
        commentMsg.tag = comment.tag;
        commentMsg.commentType = 0;
        commentMsg.topicType = comment.topicType;
        commentMsg.topicId = comment.topicId;
        commentMsg.msg = "你发起的 " + comment.topicTitle + " 被 " + commentUserInfo.name + " 评论了";

        message.content = commentMsg;
        message.read = false;
        message.addTime = new Date();

        messageDAO.save(message);
    }
}
