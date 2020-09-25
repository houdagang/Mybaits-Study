package com.gang.dao;

import com.gang.pojo.User05;
import com.gang.utils.MybatisUtils05;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 14:48
 */
public class UserDaoTest {

    @Test
    public void test() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils05.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            List<User05> userList = userDao.queryUsers();

            for(User05 user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectOne() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils05.getSqlSession();
        try {
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User05 user = userDao.getUserById(5);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
        try {
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User05 user = new User05();
            user.setId(5);
            user.setName("李光");
            user.setPwd("546467");
            userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
        try {
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            User05 user = new User05();
            user.setId(5);
            user.setName("李光");
            user.setPwd("123456");
            userDao.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testDelete() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
        try {
            //执行sql
            //方式一：getMapper
            UserMapper userDao = sqlSession.getMapper(UserMapper.class);
            userDao.deleteUser(5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }
}
