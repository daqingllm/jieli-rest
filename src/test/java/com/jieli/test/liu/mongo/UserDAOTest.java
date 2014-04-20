package com.jieli.test.liu.mongo;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.User;
import org.testng.annotations.Test;

import java.util.Arrays;
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

    @Test
    public void testInterest() {
        User user = new User();
//        List<InterestTag> interestTags = new ArrayList<InterestTag>();
//        interestTags.add(InterestTag.FOOTBALL);
//        user.interestTags = interestTags;
        String userId = userDAO.save(user).get_id().toString();
        System.out.println(userId);

        User newUser = userDAO.loadById(userId);
        System.out.println(newUser.get_id().toString());
//        for (InterestTag interestTag : newUser.interestTags) {
//            System.out.println(interestTag);
//        }

        userDAO.deleteById(userId);
    }

    @Test
    public void testMd5() {
        Account account = accountDAO.loadByUsername("test");
        System.out.println(account.password);
    }

//    @Test
    public void clearAll() {
        accountDAO.clear();
        userDAO.clear();
    }

    @Test
    public void getUser() {
        Account account = accountDAO.loadByUsername("test");
        System.out.println(account.get_id());
        Account account1 = accountDAO.loadById(account.get_id().toString());
        System.out.println(account1.userId);
    }

    @Test
    public void testSort() {
        List<Integer> arrays = Arrays.asList(4,2,3,1);
        Integer[] as = (Integer[]) arrays.toArray();
        Arrays.sort(as);
        arrays = Arrays.asList(as);
        for (Integer i : arrays) {
            System.out.println(i);
        }
    }
}
