package com.zsh.test;

import com.zsh.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JdbcTemplateCRUDtest {

    @Autowired
    private JdbcTemplate template;
      @Test
    public void testUpdate(){
          int update = template.update("update account set money= ? where name= ?", 100000, "zhangsan");
          System.out.println(update);
      }

    @Test
    public void testDelete(){
        int del = template.update("delete from account  where name= ?",  "lisi");
        System.out.println(del);
    }

    @Test
    public void queryAll(){
        List<Account> accounts = template.query("select * from account", new BeanPropertyRowMapper<Account>(Account.class));
        System.out.println(accounts);
    }

    @Test
    public void queryOne(){
        Account account = template.queryForObject("select * from account where name= ?", new BeanPropertyRowMapper<Account>(Account.class), "zhangsan");
        System.out.println(account);
    }
    @Test
    public void queryCount(){
        Long aLong = template.queryForObject("select Count(*) from account ", Long.class);
        System.out.println(aLong);
    }
}
