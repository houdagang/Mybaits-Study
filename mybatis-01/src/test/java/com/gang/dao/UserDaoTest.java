package com.gang.dao;

import com.gang.pojo.User;
import com.gang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
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
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
           /* UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> userList = userDao.queryUserList();*/

            //方式二：
            List<User> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");

            for(User user : userList) {
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
    public void testSelect() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user = userDao.getUserById(3);
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //新增(需要提交事务)
    @Test
    public void testInsert() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user = new User(4,"小刚","123456");
            int res = userDao.insertUser(user);
            if(res > 0) {
                //提交事务
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //批量新增
    @Test
    public void testPlInsert() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user1 = new User(4,"小刚1","123456");
            User user2 = new User(5,"小刚2","123456");
            User user3 = new User(6,"小刚3","123456");
            List<User> list = new ArrayList<User>();
            list.add(user1);
            list.add(user2);
            list.add(user3);
            int res = userDao.plInsertUser(list);
            System.out.println(list);
            if(res > 0) {
                //提交事务
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //修改(需要提交事务)
    @Test
    public void testUpdate() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User user = new User(4,"小刚","654321");
            int res = userDao.updateUser(user);
            if(res > 0) {
                //提交事务
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //删除(需要提交事务)
    @Test
    public void testDelete() {
        //第一步：获得sqlsession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            int res = userDao.deleteUser(4);
            if(res > 0) {
                //提交事务
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }
}
