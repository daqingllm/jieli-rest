package com.jieli.test.liu.mongo;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.util.PasswordGenerator;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-4-2
 * Time: 下午7:37
 * To change this template use File | Settings | File Templates.
 */
public class PasswordTest {

    @Test
    public void generatePassword() {
        System.out.println(PasswordGenerator.md5Encode("admin"));
    }

    @Test
    public void insertSuper() {
        AccountDAO accountDAO = new AccountDAO();
        Account account = new Account();
        account.username = "super";
        account.password = "1b3231655cebb7a1f783eddf27d254ca";
        account.state = AccountState.SUPPER;
        account.userId = "";
        account.associationId = "";
        accountDAO.save(account);
    }
}
