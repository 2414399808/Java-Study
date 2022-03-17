package com.zsh.dao.impl;

import com.zsh.dao.RoleDao;
import com.zsh.dao.UserDao;
import com.zsh.domain.Role;
import com.zsh.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<User> list() {
        List<User> userList = this.findAll();
        for (int i = 0; i < userList.size(); i++) {
            List<Role> roleList = roleDao.findRoleByUserId(userList.get(i).getId());


            userList.get(i).setRoles(roleList);
        }
        return userList;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = jdbcTemplate.query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    @Override
    public Long addUser(User user) {
//        String sql =" insert into sys_user values(null,?,?,?,?)";
//        jdbcTemplate.update(sql,user.getUsername(),user.getEmail(), user.getPassword(),user.getPhoneNum());
        PreparedStatementCreator creator =new PreparedStatementCreator(){

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_user values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);
        long id = keyHolder.getKey().longValue();
        return id;
    }

    @Override
    public User login(User user) throws EmptyResultDataAccessException{
        User user1 = jdbcTemplate.queryForObject("select * from sys_user where username= ? and password =?", new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        return user1;
    }

    @Override
    public void saveUserRoleRel(Long id, Long[] ids) {
        for (Long roleId : ids) {
        String sql="insert into sys_user_role values(?,?)";
        jdbcTemplate.update(sql,id,roleId);

        }
    }
}
