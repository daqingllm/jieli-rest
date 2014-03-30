package com.jieli.test.mongo.yolanda;

import com.jieli.feature.help.dao.HelpDAO;
import com.jieli.feature.help.entity.HelpComment;
import com.jieli.feature.help.entity.HelpInfo;
import com.jieli.feature.help.entity.SimpleHelpInfo;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by YolandaLeo on 14-3-30.
 */
public class HelpDaoTest {
    HelpDAO helpDao = new HelpDAO();
    UserDAO userDao = new UserDAO();
    @Test
    public void addHelpTest() {
        /*HelpInfo helpInfo = new HelpInfo();
        helpInfo.setUserId("5336bbe13004cc09f49432e6");
        helpInfo.setAttentionNum(0);
        helpInfo.setAddTime(new Date());
        helpInfo.setAssociationId("5337af643004e0056052bd5a");
        helpInfo.setContent("快来帮我做饭");
        helpInfo.setTitle("求做饭");
        HelpInfo result = helpDao.addHelp(helpInfo);*/

        /*HelpInfo helpInfo = new HelpInfo();
        helpInfo.setUserId("5336bbe13004cc09f49432e6");
        helpInfo.setAttentionNum(0);
        helpInfo.setAddTime(new Date());
        helpInfo.setAssociationId("5337af643004e0056052bd5a");
        helpInfo.setContent("我可以带你去公园放风筝！");
        helpInfo.setTitle("提供放风筝");
        HelpInfo result = helpDao.addHelp(helpInfo);*/
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setUserId("5336bbe13004cc09f49432e6");
        helpInfo.setAttentionNum(0);
        helpInfo.setAddTime(new Date());
        helpInfo.setAssociationId("5337af643004e0056052bd5a");
        helpInfo.setContent("求个司机帮忙开车");
        helpInfo.setTitle("求开车");
        HelpInfo result = helpDao.addHelp(helpInfo);
        System.out.println(result.toString());
    }

    @Test
    public void getHelpInfoList() {
        String associationId = "5337af643004e0056052bd5a";
        List<SimpleHelpInfo> resultList = helpDao.getHelpInfoList(associationId);
        for(SimpleHelpInfo s : resultList) {
            System.out.println(s.toString());
        }
    }

    @Test
    public void loadHelpInfo() {
        String helpId = "5337bce53004ecd7e04edfcd";
        HelpInfo result = helpDao.loadById(helpId);
        System.out.println(result.toString());
    }

    @Test
    public void addComment() {
        String helpId = "5337bce53004ecd7e04edfcd";
        HelpComment comment = new HelpComment();
        comment.setAddTime(new Date());
        comment.setComment("我也想搭车蹭饭");
        comment.setCommentUserId("5336bb6f3004cc09f49432e4");
        comment.setHelpId(helpId);
        comment.setTop(false);
        HelpInfo result = helpDao.addComment(comment);
        //System.out.println(result.toString());
    }

    @Test
    public void deleteComment() {
        String helpId = "5337bce53004ecd7e04edfcd";
        Integer index = 1;
        HelpInfo result = helpDao.deleteComment(helpId, index);
        System.out.println(result.commentList.size());
    }

    /*
    @Test
    public void getCommentList() {
        String helpId = "5337bce53004ecd7e04edfcd";
        List<HelpComment> commentList = helpDao.getCommentList(helpId);
        for(HelpComment c : commentList) {
            System.out.println(c.toString());
        }
    }*/

    @Test
    public void addFocus() {
        String helpId = "5337bce53004ecd7e04edfcd";
        User user = userDao.loadById("5336bb6f3004cc09f49432e4");
        HelpInfo result = helpDao.addFocus(helpId, user);
        System.out.println(result.getFocusList().size());
    }

    @Test
    public void topComment() {
        String helpId = "5337bce53004ecd7e04edfcd";
        Integer commentIndex = 2;
        helpDao.topComment(helpId, commentIndex);
    }
}
