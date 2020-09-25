package com.gang.pojo;

import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-21 13:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("u05")
@Builder
public class User05 {

    private int id;
    private String name;
    private String pwd;

}
