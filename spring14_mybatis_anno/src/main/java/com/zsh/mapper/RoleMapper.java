package com.zsh.mapper;

import com.zsh.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("select * from sys_role where id in (select roleid from sys_user_role where userId = #{uid})")
    List<Role> selectRoleByUid(int uid);
}
