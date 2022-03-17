package com.zsh.service;

import com.zsh.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service("accountService")
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Transactional
    public void transfer(String outMan, String inMan, double money) {
        accountDao.out(outMan,money);

        accountDao.in(inMan,money);
    }
}
