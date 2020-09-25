package com.gang.dao;

import com.gang.pojo.User04;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 14:36
 */
public interface UserDao {

    //查列表
    List<User04> queryUserList();

    //查列表with分页
    List<User04> queryUserListWithPager(Map map);
}
