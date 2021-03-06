package com.gang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 : 使用sqlSessionFactory --> sqlSession
 * @创建日期 : 2020-09-21 13:27
 */
public class MybatisUtils07 {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try{
            //使用mybatis第一步获取sqlSessionFactory对象(官方代码)
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = MybatisUtils07.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,"development",properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
    // SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。
    // 你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。

    public static SqlSession getSqlSession(boolean flag) {
        return sqlSessionFactory.openSession(flag);
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

}
