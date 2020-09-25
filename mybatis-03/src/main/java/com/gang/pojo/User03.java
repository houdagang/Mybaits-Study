package com.gang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 13:47
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("u03")
public class User03 {

    private int id;
    private String name;
    private String pwd;

}
