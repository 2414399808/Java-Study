package com.zsh.service;

import com.zsh.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> list();
    void addRole(Role role);
    List<Role> roleList();
}
