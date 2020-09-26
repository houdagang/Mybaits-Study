package com.gang.pojo;

import lombok.Data;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020/9/26 21:02
 */
@Data
public class Student {

    private String name;
    private int id;
    private int tid;

    private Teacher teacher;

}
