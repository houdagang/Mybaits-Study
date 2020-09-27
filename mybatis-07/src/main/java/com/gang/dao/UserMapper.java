package com.gang.dao;

import com.gang.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-27 16:05
 */
public interface UserMapper {

    User getUserById(@Param("id") int id);

    int updateUser(User user);

}
