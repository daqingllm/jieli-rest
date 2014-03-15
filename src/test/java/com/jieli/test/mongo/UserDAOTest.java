package com.jieli.test.mongo;

import com.jieli.dao.AccountDAO;
import com.jieli.dao.UserDAO;
import com.jieli.entity.common.Account;
import com.jieli.entity.common.InterestTag;
import com.jieli.entity.user.*;
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

    AccountDAO accountDAO = new AccountDAO();
    UserDAO userDAO = new UserDAO();

//    @Test
    public void mockUser() {
        User user = new User();
        List<InterestTag> interestTags = new ArrayList<InterestTag>();
        interestTags.add(InterestTag.FOOTBALL);
        user.interestTags = interestTags;
        user.person = new PersonalDetail();
        user.person.name = "王十三";
        user.appInfo = new AppInfo();
        user.appInfo.score = 10;
        user.contact = new Contact();
        user.contact.phone = "13122223333";
        user.education = new Education();
        user.education.school = "清华大学";
        int userId = IdUtil.getUserId();
        user.id = userId;
        userDAO.save(user);

        Account account = new Account();
        account.userId = userId;
        account.username = "test";
        account.password = "test";
        accountDAO.save(account);
        Account newAccount = accountDAO.loadByUsername("test");
        System.out.println(newAccount.getObjectId());
        System.out.println(newAccount.username);
        User newUser = userDAO.loadById(newAccount.userId);
        System.out.println(newUser.person.name);
    }

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
        System.out.println(newUser.id);
        System.out.println(newUser.person.name);
        for (InterestTag interestTag : newUser.interestTags) {
            System.out.println(interestTag);
        }

        userDAO.deleteById(userId);
    }

    @Test
    public void testMd5() {
        Account account = accountDAO.loadByUsername("test");
        System.out.println(account.password);
    }

    @Test
    public void clearAll() {
        accountDAO.clear();
        userDAO.clear();
    }
}
