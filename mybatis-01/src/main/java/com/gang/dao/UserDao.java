package com.gang.dao;

import com.gang.pojo.User;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 14:36
 */
public interface UserDao {

    //查列表
    List<User> queryUserList();

    //查单个
    User getUserById(Integer id);

    //新增
    int insertUser(User user);

    //修改
    int updateUser(User user);

    //删除
    int deleteUser(int id);

    //批量新增
    int plInsertUser(List<User> list);
}
