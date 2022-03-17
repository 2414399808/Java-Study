package com.zsh.dao.impl;

import com.zsh.dao.RoleDao;
import com.zsh.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> list() {
        String sql="select * from sys_role";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));

        return roleList;
    }

    @Override
    public void addRole(Role role) {
        String sql="insert into sys_role values(null,?,?)";
        jdbcTemplate.update(sql,role.getRoleName(),role.getRoleDesc());
    }

    @Override
    public List<Role> findRoleByUserId(long id) {
       String sql="SELECT * FROM sys_role where id in (select roleid from sys_user_role where userid = ?) ";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class), id);
        return roleList;
    }

    @Override
    public List<Role> findAllRoleName() {
        String sql="select * from sys_role";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }


}
