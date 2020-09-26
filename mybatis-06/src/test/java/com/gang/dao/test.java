package com.gang.dao;

import com.gang.pojo.Student;
import com.gang.pojo.Teacher;
import com.gang.utils.MybatisUtils06;
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
        SqlSession sqlSession = MybatisUtils06.getSqlSession();
        try {
            //执行sql
            //方式一：getMapper
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            List<Student> list = studentMapper.queryStudents2();
            for(Student student : list) {
                System.out.println(student);
            }

            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            Teacher teacher = teacherMapper.getTeacher();
            System.out.println(teacher);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }


    }

}
