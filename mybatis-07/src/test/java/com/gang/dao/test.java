package com.gang.dao;

import com.gang.pojo.User;
import com.gang.utils.MybatisUtils07;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020/9/26 21:09
 */
public class test {

    @Test
    public void testSelect() {
        SqlSession sqlSession = MybatisUtils07.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user1 = userMapper.getUserById(1);
            System.out.println(user1);
            User user2 = userMapper.getUserById(1);
            System.out.println(user2);
            System.out.println(user1 == user2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }


    @Test
    public void testTwoCache() {
        SqlSession sqlSession = MybatisUtils07.getSqlSession();
        SqlSession sqlSession1 = MybatisUtils07.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
            User user1 = userMapper.getUserById(1);
            System.out.println(user1);
            sqlSession.close();
            User user2 = userMapper1.getUserById(1);
            System.out.println(user2);
            sqlSession1.close();
            System.out.println(user1 == user2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
