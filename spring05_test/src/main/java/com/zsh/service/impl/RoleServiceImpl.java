package com.zsh.service.impl;

import com.zsh.dao.RoleDao;
import com.zsh.domain.Role;
import com.zsh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    @Qualifier("roleDao")
    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public List<Role> list() {
      return   roleDao.list();
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);
    }

    @Override
    public List<Role> roleList() {
        List<Role> roleList = roleDao.findAllRoleName();
        return roleList;
    }


}
