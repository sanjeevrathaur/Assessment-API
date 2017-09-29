package com.test.dao.impl;

import com.test.dao.AccountDAO;
import com.test.model.Account;
import com.test.utils.BusinessException;

import java.util.Collections;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {



    @Override
    public void save(Account account) throws BusinessException {

    }

    @Override
    public void update(Account account) throws BusinessException {

    }

    @Override
    public List<Account> getAccounts() throws BusinessException {

        return Collections.emptyList();

    }

    @Override
    public Account getAccount(Integer id) throws BusinessException {
        return null;

    }
}
