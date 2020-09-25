package com.gang.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-24 9:26
 */
public class AutoProxy implements InvocationHandler {

    private Object tar;

    public Object bing(Object tar) {
        this.tar = tar;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(),tar.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("我是动态代理");
        result = method.invoke(tar,args);
        System.out.println("动态代理执行完毕");
        return result;
    }

}
