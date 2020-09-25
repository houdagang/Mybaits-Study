package com.gang.dao;

import com.gang.pojo.User03;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 14:36
 */
public interface UserDao {

    //查列表
    List<User03> queryUserList();

    //查单个
    User03 getUserById(Integer id);

    //新增
    int insertUser(User03 user);

    //修改
    int updateUser(User03 user);

    //删除
    int deleteUser(int id);

    int plInsertUser(List<User03> list);
}
