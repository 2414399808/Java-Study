package com.zsh.dao;

import com.zsh.domain.Role;

import java.util.List;

public interface RoleDao {
    List<Role> list();
    void addRole(Role role);
    List<Role> findRoleByUserId(long id);
    List<Role> findAllRoleName();
}
