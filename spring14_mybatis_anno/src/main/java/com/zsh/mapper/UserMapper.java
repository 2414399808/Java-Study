package com.zsh.mapper;

import com.zsh.domain.Order;
import com.zsh.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Insert(" insert into tb_user (username, password, birthday)\n" +
            "        values (#{username},#{password},#{birthday})")
    void save(User user);
    @Update("update tb_user set username=#{username} ,password =#{password} where id =#{id}")
    void update(User user);
    @Delete("delete\n" +
            "        from tb_user\n" +
            "        where id=#{id}")
    void delete(int id);
    @Select(" select *\n" +
            "        from tb_user\n" +
            "        where id=#{id}")
    User findById(int id);
    @Select("select *\n" +
            "        from tb_user")
    List<User> findAll();


    @Select("select * from tb_user")
    @Results({
            @Result(id=true ,column = "id" , property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "birthday", property = "birthday"),
            @Result(
                property = "orderList",
                column = "id",
                javaType = List.class,
                many = @Many(select = "com.zsh.mapper.OrderMapper.findByUid")
            )
    })
    List<User> findUserAndOrder();


    @Select("select * from tb_user")
    @Results({
            @Result(id = true,column = "id" ,property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "password" ,property = "password"),
            @Result(column = "birthday" ,property = "birthday"),
            @Result(
                    property = "roleList",
                    column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.zsh.mapper.RoleMapper.selectRoleByUid")
            )

    })
    List<User> findUserAndRole();
}
