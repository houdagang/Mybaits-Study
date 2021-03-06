package com.gang.pojo;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020/9/26 21:02
 */
@Data
public class Teacher {

    private String name;
    private int id;

    private List<Student> list;
}
