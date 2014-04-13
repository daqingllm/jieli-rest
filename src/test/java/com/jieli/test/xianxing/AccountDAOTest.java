package com.jieli.test.xianxing;

import com.jieli.common.dao.AccountDAO;
import com.jieli.common.entity.Account;
import com.jieli.common.entity.AccountState;
import org.testng.annotations.Test;

import java.util.Iterator;

public class AccountDAOTest{
    private AccountDAO accountDAO = new AccountDAO();

    @Test
    public void getAccounts(){
        Iterable<Account> accounts = accountDAO.loadByAssociationId("5348cd0a7c1f43946d7e7549", AccountState.ADMIN);

    }
}