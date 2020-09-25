package com.gang.dao;

import com.gang.utils.MybatisUtils03;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.gang.pojo.User03;

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
        SqlSession sqlSession = MybatisUtils03.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
           /* UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> userList = userDao.queryUserList();*/

            //方式二：
            List<User03> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");

            for(User03 user : userList) {
                System.out.println(user);
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
        SqlSession sqlSession = MybatisUtils03.getSqlSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            User03 user1 = new User03(4,"小刚1","123456");
            User03 user2 = new User03(5,"小刚2","123456");
            User03 user3 = new User03(6,"小刚3","123456");
            List<User03> list = new ArrayList<User03>();
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
}
