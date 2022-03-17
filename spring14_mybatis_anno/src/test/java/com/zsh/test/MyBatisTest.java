package com.zsh.test;

import com.zsh.domain.User;
import com.zsh.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MyBatisTest {


    private UserMapper mapper;
   @Before
    public void before() throws IOException {
       SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
       SqlSession sqlSession = sqlSessionFactory.openSession(true);
        mapper = sqlSession.getMapper(UserMapper.class);
   }
   @Test
    public void test1(){
       List<User> all = mapper.findAll();
       System.out.println(all);
   }

    @Test
    public void test3(){
        List<User> userAndOrder = mapper.findUserAndOrder();
        System.out.println(userAndOrder);
    }

    @Test
    public void test4(){
        List<User> userAndRole = mapper.findUserAndRole();
        System.out.println(userAndRole);
    }
}
