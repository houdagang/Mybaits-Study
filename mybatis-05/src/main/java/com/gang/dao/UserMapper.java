package com.gang.dao;

import com.gang.pojo.User05;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-23 15:00
 */
public interface UserMapper {

    @Select("select * from user")
    List<User05> queryUsers();

    @Select("select * from user where id = #{id}")
    User05 getUserById(@Param("id") int id2);

    @Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
    int insertUser(User05 user);

    @Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
    int updateUser(User05 user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);

}
