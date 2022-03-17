package com.zsh.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zsh.domain.Account;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class jdbcTemplateTest {
    @Test
    //测试jdbcTemplate开发步骤
    public void test1(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/db1?useSSL=false&amp;useServerPrepStmts=true");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("1234");
        JdbcTemplate template = new JdbcTemplate(druidDataSource);
        //设置数据源对象 知道数据原在哪里
        int row = template.update("insert into account value(?,?)", "zhangsan", 1000);
        System.out.println(row);

    }
    @Test
    //测试jdbcTemplate开发步骤
    public void test2(){
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplate template = (JdbcTemplate) app.getBean("jdbcTemplate");
        //设置数据源对象 知道数据原在哪里
        int row = template.update("insert into account value(?,?)", "lisi", 1000);
        System.out.println(row);

    }
}
