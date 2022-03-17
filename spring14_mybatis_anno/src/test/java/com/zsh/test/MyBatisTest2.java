package com.zsh.test;

import com.zsh.domain.Order;
import com.zsh.domain.Role;
import com.zsh.domain.User;
import com.zsh.mapper.OrderMapper;
import com.zsh.mapper.RoleMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MyBatisTest2 {
    @Test
    public void test() throws IOException {


        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> all = mapper.findAll();
        System.out.println(all);

    }
    @Test
    public void test2() throws IOException {


        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        List<Role> roles = mapper.selectRoleByUid(1);
        System.out.println(roles);

    }
}
