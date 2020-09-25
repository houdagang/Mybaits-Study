package com.gang.dao;

import com.gang.pojo.User04;
import com.gang.utils.MybatisUtils04;
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
        SqlSession sqlSession = MybatisUtils04.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
           /* UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> userList = userDao.queryUserList();*/

            //方式二：
            List<User04> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");

            for(User04 user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //limit分页查询
    @Test
    public void queryUserListWithPager() {
        SqlSession sqlSession = MybatisUtils04.getSqlSession();
        try {
            Map<String,Integer> map = new HashMap<>();
            map.put("startIndex",0);
            map.put("pageSize",5);
            List<User04> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserListWithPager",map);
            System.out.println(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

    //RowBound分页查询
    @Test
    public void queryUserListWithPager1() {
        SqlSession sqlSession = MybatisUtils04.getSqlSession();
        try {
            Map<String,Integer> map = new HashMap<>();
            //通过java代码层面实现分页
            List<User04> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList",map,
                    new RowBounds(0,5));
            System.out.println(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }

}
