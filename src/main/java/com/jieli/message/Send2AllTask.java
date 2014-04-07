package com.jieli.message;

import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-7
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class Send2AllTask extends Thread {

    private Object msgContent;
    private String associationId;
    private MessageType type;

    private UserDAO userDAO = new UserDAO();
    private MessageDAO messageDAO = new MessageDAO();

    public Send2AllTask(Object obj, String associationId, MessageType type) {
        this.msgContent = obj;
        this.associationId = associationId;
        this.type = type;
    }

    @Override
    public void run() {
        Iterable<User> users = userDAO.loadAll(associationId);
        for (User user : users) {
            Message message = new Message();
            message.messageType = type;
            message.userId = user.get_id().toString();
            message.addTime = new Date();
            message.content = msgContent;
            message.importance = 1;
            messageDAO.save(message);
        }
    }
}
