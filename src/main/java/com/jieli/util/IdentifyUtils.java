package com.jieli.util;

import com.jieli.dao.AccountDAO;
import com.jieli.entity.common.Account;
import com.jieli.entity.common.AccountState;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class IdentifyUtils {

    public static boolean isValidate(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return false;
        }
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.loadByObjectId(sessionId);
        if (account != null && account.state == AccountState.ENABLE) {
            return true;
        } else {
            return false;
        }
    }

    public static int getUserId(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return -1;
        }
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.loadByObjectId(sessionId);
        if (account != null) {
            return account.userId;
        } else {
            return -1;
        }
    }
}
