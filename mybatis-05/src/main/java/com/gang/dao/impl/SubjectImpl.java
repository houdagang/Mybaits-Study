package com.gang.dao.impl;

import com.gang.dao.Subject;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-24 9:17
 */
public class SubjectImpl implements Subject {

    @Override
    public void doSomething() {
        System.out.println("我是普通的实现类");
    }
}
