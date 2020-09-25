package com.gang.dao.impl;

import com.gang.dao.Subject;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-24 9:20
 */
public class HandProxy implements Subject {

    SubjectImpl SubjectImpl = new SubjectImpl();
    @Override
    public void doSomething() {
        System.out.println("我是静态代理类");
        SubjectImpl.doSomething();
    }
}
