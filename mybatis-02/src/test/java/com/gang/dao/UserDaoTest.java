package com.gang.dao;

import com.gang.utils.MybatisUtils02;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import src.main.java.com.gang.pojo.User02;

import java.util.List;

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
        SqlSession sqlSession = MybatisUtils02.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
            //UserDao userDao = sqlSession.getMapper(UserDao.class);
            //List<User> userList = userDao.queryUserList();

            //方式二：
            List<User02> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");

            for(User02 user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

}
