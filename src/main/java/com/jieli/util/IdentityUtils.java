package com.jieli.util;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import com.jieli.user.dao.DirectoryDAO;
import com.jieli.user.dao.UserDAO;
import com.jieli.user.entity.Directory;
import com.jieli.user.entity.User;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class IdentityUtils {

    public static boolean isValidate(String sessionId) {

        return checkValue(sessionId, AccountState.ENABLE);
    }

    public static boolean isAdmin(String sessionId) {

        return checkValue(sessionId, AccountState.ADMIN);
    }

    public static boolean isSuper(String sessionId) {

        return checkValue(sessionId, AccountState.SUPPER);
    }

    private static boolean checkValue(String sessionId, AccountState state) {
        Account account = getAccount(sessionId);
        if (account != null && account.state.value() >= state.value()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getUserId(String sessionId) {
        Account account = getAccount(sessionId);
        if (account != null) {
            return account.userId;
        } else {
            return null;
        }
    }

    public static String getUserName(String userId) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.loadById(userId);
        return user.name;
    }

    public static List<String> getConcerned(String userId) {
        DirectoryDAO directoryDAO = new DirectoryDAO();
        Directory directory = directoryDAO.loadByUserId(userId);
        if (directory == null || directory.concerned == null) {
            return new ArrayList<String>();
        }
        return directory.concerned;
    }

    public static String getAssociationId(String sessionId) {
        Account account = getAccount(sessionId);
        if (account == null) {
            return null;
        }

        return account.associationId;
    }

    public static AccountState getState(String sessionId) {
        Account account = getAccount(sessionId);
        if (account == null) {
            return null;
        }

        return account.state;
    }

    private static Account getAccount(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        if (!MongoUtils.isValidObjectId(sessionId)) {
            return null;
        }
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.loadById(sessionId);

        return account;
    }
}
