package com.zsh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void out(String outMan, double money) {
        jdbcTemplate.update("update account set money = money - ? where name = ?",money,outMan );
    }


    public void in(String inMan, double money) {
        jdbcTemplate.update("update account set money =money + ? where name= ?", money, inMan);
    }
}
