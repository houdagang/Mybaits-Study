package com.gang.pojo;

import com.gang.dao.AutoProxy;
import com.gang.dao.Subject;
import com.gang.dao.impl.SubjectImpl;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-24 8:53
 */
public class TestFsAndDtdl {

    /**
     * 测试反射
     * 获取所有的属性
     */
    @Test
    public void testFs() throws Exception {
        //获取整个类
        Class c = Class.forName("com.gang.pojo.User05");
        //获取所有的属性
        Field[] fs = c.getDeclaredFields();
        //定义可变长的字符，用来存储属性
        StringBuffer sb = new StringBuffer();
        //通过追加的方法，将每个属性拼接到此字符串中
        //最外边的public定义
        sb.append(Modifier.toString(c.getModifiers()) + " class"
                + c.getSimpleName() + "{\n");
        //里边的每一个属性
        for(Field field : fs) {
            sb.append("\t");//空格
            //获得属性的修饰符，例如public static等等
            sb.append(Modifier.toString(field.getModifiers()) + " ");
            //属性的类型的名字
            sb.append(field.getType().getSimpleName() + " ");
            //属性的名字 + 回车
            sb.append(field.getName() + ";\n");
        }
        sb.append("}");
        System.out.println(sb);
    }

    /**
     * 获取特定属性
     */
    @Test
    public void testFsForGetTdsx() throws Exception{
        //获取类
        Class c = Class.forName("com.gang.pojo.User05");
        //获取id属性
        Field idF = c.getDeclaredField("id");
        //实例化这个类赋给o
        Object o = c.newInstance();
        //打破封装
        idF.setAccessible(true); //使用反射机制可以打破封装性，导致了java对象的属性不安全。
        //给o对象的id属性赋值"110"
        idF.set(o, 110); //set
        //get
        System.out.println(idF.get(o));
    }


    /**
     * 测试动态代理
     */
    @Test
    public void testDtdl() {
        AutoProxy dtdl = new AutoProxy();
        Subject subject = (Subject) dtdl.bing(new SubjectImpl());
        subject.doSomething();
    }

}
