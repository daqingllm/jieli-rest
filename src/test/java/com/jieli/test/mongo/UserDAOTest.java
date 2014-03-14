package com.jieli.test.mongo;

import com.jieli.dao.UserDAO;
import com.jieli.entity.common.InterestTag;
import com.jieli.entity.user.PersonalDetail;
import com.jieli.entity.user.User;
import com.jieli.util.IdUtil;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-15
 * Time: 上午12:54
 * To change this template use File | Settings | File Templates.
 */
public class UserDAOTest {

    UserDAO userDAO = new UserDAO();

    @Test
    public void testInterest() {
        User user = new User();
        List<InterestTag> interestTags = new ArrayList<InterestTag>();
        interestTags.add(InterestTag.FOOTBALL);
        user.interestTags = interestTags;
        user.person = new PersonalDetail();
        user.person.name = "王十三";
        int userId = IdUtil.getUserId();
        user.id = userId;
        userDAO.save(user);

        User newUser = userDAO.loadById(userId);
        System.out.println(newUser.getObjectId().toString());
        System.out.println(newUser.person.name);
        for (InterestTag interestTag : newUser.interestTags) {
            System.out.println(interestTag);
        }

        userDAO.deleteById(userId);
    }
}
