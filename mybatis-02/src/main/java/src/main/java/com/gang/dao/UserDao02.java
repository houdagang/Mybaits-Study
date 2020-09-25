package src.main.java.com.gang.dao;

import src.main.java.com.gang.pojo.User02;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 14:36
 */
public interface UserDao02 {

    //查列表
    List<User02> queryUserList();

    //查单个
    User02 getUserById(Integer id);

    //新增
    int insertUser(User02 user);

    //修改
    int updateUser(User02 user);

    //删除
    int deleteUser(int id);

}
