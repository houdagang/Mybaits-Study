![Mybatis](https://mybatis.org/images/mybatis-logo.png)

# 1.简介

## 1.1什么是Mybatis

* MyBatis 是一款优秀的持久层框架
* 它支持自定义 SQL、存储过程以及高级映射
* MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作
* MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录
* MyBatis 本是[apache](https://baike.baidu.com/item/apache/6265)的一个开源项目[iBatis](https://baike.baidu.com/item/iBatis), 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。
* 2013年11月迁移到Github。

## 1.2如何获得Mybatis

* maven仓库

  ```<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.5</version>
  </dependency>
  ```

* [GitHub](https://github.com/mybatis/mybatis-3)

  * https://github.com/mybatis/mybatis-3/releases

* [中文文档](https://mybatis.org/mybatis-3/zh/getting-started.html)

## 1.3持久化

**数据持久化**

* 持久化：将程序的数据在持久状态和瞬时状态转化的过程
* 内&emsp;存:   断电即失
* 数据库：io文件持久化

**为什么需要持久化？**

- 有一些对象，不能让他丢失。
- 内存太贵了

## 1.4持久层

Dao层，Service层，Controller层。。。。

* 完成持久化工作的代码块
* 层界线十分明显

## 1.5为什么需要Mybatis

- 传统的JDBC代码太繁琐了，为了简化，从而出现了框架
- 帮助我们将数据存入数据库
- 使用的人多
- 不用也可以，只是它更容易上手**（技术没有高低之分）**
- 优点
  - 简单：本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件易于学习
  - 灵活：mybatis不会对应用程序或者数据库的现有设计强加任何影响。sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
  - 解除sql与程序代码的耦合：通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
  - 提供映射标签，支持对象与数据库的orm字段关系映射
  - 提供对象关系映射标签，支持对象关系组建维护
  - 提供xml标签，支持编写动态sql。

# 2.第一个Mybatis程序

思路：搭建环境-->导入Mybatis-->编写代码-->测试

## 2.1搭建环境

```
CREATE DATABASE `mybatis`;
USE `mybatis`;
#创建 USER 表
CREATE TABLE `USER`(
	`id` int(20) not null PRIMARY KEY,
	`name` varchar(30) default null,
	`pwd` varchar(30) default null
)ENGINE=INNODB DEFAULT CHARSET=utf8;
#新增数据
INSERT INTO `USER` (`id`,`name`,`pwd`) values
(1,'张三','123456'),
(2,'李四','123456'),
(3,'王五','123456');
```

## 2.2新建项目

1.新建普通的maven项目

2.pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- 父项目 -->
    <groupId>com.xiaogang</groupId>
    <artifactId>Mybatis-Study</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>mybatis-01</module>
    </modules>

    <!-- 版本的统一管理 -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <lombok.version>1.16.18</lombok.version>
    </properties>

    <!--依赖-->
    <dependencyManagement>
        <dependencies>

            <!-- mysql -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.21</version>
            </dependency>

            <!-- mybatis -->
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
                <version>3.5.5</version>
            </dependency>

            <!-- junit -->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.13</version>
            </dependency>

            <!-- lombok -->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
                <optional>true</optional>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <resources>
            <resource>
                <directory>${basedir}/src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.config</include>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <resource>
                <directory>${basedir}/src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.config</include>
                    <include>**/*.xml</include>
                    <include>**/*.xls</include>
                </includes>
            </resource>
        </resources>
    </build>
</project>
```

## 2.3创建一个模块

删除src，新建一个modul **(mybatis-01)**

添加配置文件

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- 核心配置文件 -->
<configuration>

    <environments default="development">
        <environment id="development">
            <!-- 事务管理 -->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf8&amp;connectTimeout=1000&amp;socketTimeout=3000&amp;autoReconnect=true&amp;useUnicode=true&amp;useSSL=false&amp;serverTimezone=UTC"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="com/gang/dao/UserMapper.xml"/>
    </mappers>

</configuration>
```



新建一个工具类

```java
package com.gang.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ProjectName : Mybatis-Study
 * @作者 : 侯小刚
 * @描述 : 使用sqlSessionFactory --> sqlSession
 * @创建日期 : 2020-09-21 13:27
 */
public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try{
            //使用mybatis第一步获取sqlSessionFactory对象(官方代码)
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
    // SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。
    // 你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

}

```

## 2.4编写代码

- 实体类

  ```java
  package com.gang.pojo;
  
  import lombok.AllArgsConstructor;
  import lombok.Data;
  import lombok.NoArgsConstructor;
  import lombok.ToString;
  
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
  public class User {
  
      private int id;
      private String name;
      private String pwd;
  
  }
  ```

- Dao接口

  ```
  public interface UserDao {
  
      List<User> queryUserList();
  
  }
  ```

- 接口实现类由原来的UserDaoImpl转变成一个Mapper配置文件

  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!-- 命名空间：绑定一个对应的Dao/Mapper接口 -->
  <mapper namespace="com.gang.dao.UserDao">
  
      <select id="queryUserList" resultType="com.gang.pojo.User">
          select * from user
      </select>
  
  </mapper>
  ```

- 测试

  ```
  package com.gang.dao;
  
  import com.gang.pojo.User;
  import com.gang.utils.MybatisUtils;
  import org.apache.ibatis.session.SqlSession;
  import org.junit.Test;
  
  import java.util.List;
  
  /**
   * @ProjectName : Mybatis-Study
   * @作者 : 侯小刚
   * @描述 :
   * @创建日期 : 2020-09-21 14:48
   */
  public class UserDaoTest {
  
      @Test
      public void test() {
  
          //第一步：获得sqlsession对象
          SqlSession sqlSession = MybatisUtils.getSqlSession();
          try {
              //执行sql
              //方式一：getMapper
             /* UserDao userDao = sqlSession.getMapper(UserDao.class);
              List<User> userList = userDao.queryUserList();*/
  
              //方式二：
              List<User> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");
  
              for(User user : userList) {
                  System.out.println(user);
              }
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              //关闭SqlSession
              sqlSession.close();
          }
      }
  }
  
  ```

  

# 3.CRUD

## 1. namespace

mapper中namespace中的包名要和Dao/mapper接口的包名一致。

## 2.select

选择查询语句；

- id：就是对应的namespace中的方法名

- parameterType：参数类型

- resultType：返回值类型

  ```
  <select id="queryUserList" resultType="com.gang.pojo.User">
          select * from user
      </select>
  
      <select id="getUserById" parameterType="int" resultType="com.gang.pojo.User">
          select * from user where id = #{_parameter}
      </select>
  ```

  

## 3.insert

- id：就是对应的namespace中的方法名
- parameterType：参数类型

```
<insert id="insertUser" parameterType="com.gang.pojo.User">
        insert into user(id, name, pwd) values (#{id},#{name},#{pwd})
    </insert>
```

## 4.update

- id：就是对应的namespace中的方法名
- parameterType：参数类型

```
<update id="updateUser" parameterType="com.gang.pojo.User">
        update user set
            name = #{name},
            pwd = #{pwd}
        where id = #{id}
    </update>
```

## 5.delete

- id：就是对应的namespace中的方法名
- parameterType：参数类型

```
<delete id="deleteUser" parameterType="int">
    delete from user where id = #{_parameter}
</delete>
```

## 6.纠察排错

> 这里要写路径，用 /

![image-20200922091146364](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200922091146364.png)

> mapper中的namespace理论上可以写任意不重复的东西，但是要采用mybatis动态代理的接口，就需要namespace中的值与对应的接口全路径一致

![image-20200922091439391](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200922091439391.png)

```java
//当与接口路径一致的时候
UserDao userDao = sqlSession.getMapper(UserDao.class);
List<User> userList = userDao.queryUserList();

//当随便命名的时候
List<User> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList");
```

# 4.配置解析

## 1.核心配置文件

> mybatis.xml  **MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息**
>
> ***当增加配置信息的时候，标签的顺序必须按照下面的顺序进行排序***

```
configuration（配置）
properties（属性）
settings（设置）
typeAliases（类型别名）
typeHandlers（类型处理器）
objectFactory（对象工厂）
plugins（插件）
environments（环境配置）
environment（环境变量）
transactionManager（事务管理器）
dataSource（数据源）
databaseIdProvider（数据库厂商标识）
mappers（映射器）
```

## 2.属性（properties)

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件（properties）中配置这些属性，也可以在 properties 元素的子元素中设置。

1.引用properties，及对应的jdbc.properties中的代码

```
<properties resource="jdbc.properties"/>
<environments default="development">
        <environment id="development">
            <!-- 事务管理 -->
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
```

```
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
username=root
password=123456
```

2.在properties中配置（**在xml中&必须转义**）

```
<properties>
    <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf8&amp;connectTimeout=1000&amp;socketTimeout=3000&amp;autoReconnect=true&amp;useUnicode=true&amp;useSSL=false&amp;serverTimezone=UTC"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</properties>
```

3.在建立sqlSession对象的时候进行传参

```
static {
        try{
            //使用mybatis第一步获取sqlSessionFactory对象(官方代码)
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = MybatisUtils02.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,"development",properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

> 如果一个属性在不只一个地方进行了配置，那么，MyBatis 将按照下面的顺序来加载：
>
> - 首先读取在 properties 元素体内指定的属性。
> - 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
> - 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。

## 3.设置（setting）

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。 下表描述了设置中各项设置的含义、默认值等。

| 设置名                           | 描述                                                         | 有效值                                                       | 默认值                                                |
| :------------------------------- | :----------------------------------------------------------- | :----------------------------------------------------------- | :---------------------------------------------------- |
| cacheEnabled                     | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     | true \| false                                                | true                                                  |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false                                                | false                                                 |
| aggressiveLazyLoading            | 开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 `lazyLoadTriggerMethods`)。 | true \| false                                                | false （在 3.4.1 及之前的版本中默认为 true）          |
| multipleResultSetsEnabled        | 是否允许单个语句返回多结果集（需要数据库驱动支持）。         | true \| false                                                | true                                                  |
| useColumnLabel                   | 使用列标签代替列名。实际表现依赖于数据库驱动，具体可参考数据库驱动的相关文档，或通过对比测试来观察。 | true \| false                                                | true                                                  |
| useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要数据库驱动支持。如果设置为 true，将强制使用自动生成主键。尽管一些数据库驱动不支持此特性，但仍可正常工作（如 Derby）。 | true \| false                                                | False                                                 |
| autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示关闭自动映射；PARTIAL 只会自动映射没有定义嵌套结果映射的字段。 FULL 会自动映射任何复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                                          | PARTIAL                                               |
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或未知属性类型）的行为。`NONE`: 不做任何反应`WARNING`: 输出警告日志（`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'` 的日志等级必须设置为 `WARN`）`FAILING`: 映射失败 (抛出 `SqlSessionException`) | NONE, WARNING, FAILING                                       | NONE                                                  |
| defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（PreparedStatement）； BATCH 执行器不仅重用语句还会执行批量更新。 | SIMPLE REUSE BATCH                                           | SIMPLE                                                |
| defaultStatementTimeout          | 设置超时时间，它决定数据库驱动等待数据库响应的秒数。         | 任意正整数                                                   | 未设置 (null)                                         |
| defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                                   | 未设置 (null)                                         |
| defaultResultSetType             | 指定语句默认的滚动策略。（新增于 3.5.2）                     | FORWARD_ONLY \| SCROLL_SENSITIVE \| SCROLL_INSENSITIVE \| DEFAULT（等同于未设置） | 未设置 (null)                                         |
| safeRowBoundsEnabled             | 是否允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为 false。 | true \| false                                                | False                                                 |
| safeResultHandlerEnabled         | 是否允许在嵌套语句中使用结果处理器（ResultHandler）。如果允许使用则设置为 false。 | true \| false                                                | True                                                  |
| mapUnderscoreToCamelCase         | 是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。 | true \| false                                                | False                                                 |
| localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地缓存将仅用于执行语句，对相同 SqlSession 的不同查询将不会进行缓存。 | SESSION \| STATEMENT                                         | SESSION                                               |
| jdbcTypeForNull                  | 当没有为参数指定特定的 JDBC 类型时，空值的默认 JDBC 类型。 某些数据库驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量，常用值：NULL、VARCHAR 或 OTHER。              | OTHER                                                 |
| lazyLoadTriggerMethods           | 指定对象的哪些方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                                       | equals,clone,hashCode,toString                        |
| defaultScriptingLanguage         | 指定动态 SQL 生成使用的默认脚本语言。                        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
| defaultEnumTypeHandler           | 指定 Enum 使用的默认 `TypeHandler` 。（新增于 3.4.5）        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.type.EnumTypeHandler                |
| callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这在依赖于 Map.keySet() 或 null 值进行初始化时比较有用。注意基本类型（int、boolean 等）是不能设置成 null 的。 | true \| false                                                | false                                                 |
| returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回 `null`。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集（如集合或关联）。（新增于 3.4.2） | true \| false                                                | false                                                 |
| logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                                   | 未设置                                                |
| logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | 未设置                                                |
| proxyFactory                     | 指定 Mybatis 创建可延迟加载对象所用到的代理工具。            | CGLIB \| JAVASSIST                                           | JAVASSIST （MyBatis 3.3 以上）                        |
| vfsImpl                          | 指定 VFS 的实现                                              | 自定义 VFS 的实现的类全限定名，以逗号分隔。                  | 未设置                                                |
| useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的项目必须采用 Java 8 编译，并且加上 `-parameters` 选项。（新增于 3.4.1） | true \| false                                                | true                                                  |
| configurationFactory             | 指定一个提供 `Configuration` 实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为`static Configuration getConfiguration()` 的方法。（新增于 3.2.3） | 一个类型别名或完全限定类名。                                 | 未设置                                                |
| shrinkWhitespacesInSql           | Removes extra whitespace characters from the SQL. Note that this also affects literal strings in SQL. (Since 3.5.5) | true \| false                                                | false                                                 |

## 4.类型别名（typeAliases）

类型别名可以为Java类型设置一个缩写的名字（别名），意在降低冗余的全限定类名书写。

1.typeAlias标签

```
<typeAliases>
    <typeAlias type="src.main.java.com.gang.pojo.User02" alias="u"/>
</typeAliases>
```

```
<select id="queryUserList" resultType="u">
    select * from user
</select>
```

2.package标签

* 则别名就是类名，一般为类名首字母小写。

```
<typeAliases>
    <package name="src.main.java.com.gang.pojo"/>
</typeAliases>
```

```
<select id="queryUserList" resultType="user02">
    select * from user
</select>
```

* 还有就是可以更改类名的别名，需要用到@Alias()注解

```
@Alias("u02")
public class User02 {
}
```

```
<typeAliases>
    <package name="src.main.java.com.gang.pojo"/>
</typeAliases>
```

```
<select id="queryUserList" resultType="u02">
    select * from user
</select>
```

Mybaits提供了一些常用的类的别名。

| 别名       | 映射的类型 |
| :--------- | :--------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |
| object     | Object     |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |

## 5.环境配置（environments）

MyBatis 可以配置成适应多种环境,**但每个 SqlSessionFactory 实例只能选择一种环境**

```
<environments default="development">
    <environment id="development">
        <!-- 事务管理 -->
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="${driver}"/>
            <property name="url" value="${url}"/>
            <property name="username" value="${username}"/>
            <property name="password" value="${password}"/>
        </dataSource>
    </environment>

    <environment id="test">
        <!-- 事务管理 -->
        <transactionManager type="JDBC"/>
        <!-- 数据源 -->
        <dataSource type="POOLED">
            <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?characterEncoding=utf8&amp;connectTimeout=1000&amp;socketTimeout=3000&amp;autoReconnect=true&amp;useUnicode=true&amp;useSSL=false&amp;serverTimezone=UTC"/>
            <property name="username" value="root"/>
            <property name="password" value="123456"/>
        </dataSource>
    </environment>
</environments>
```

- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。
- 事务管理器的配置（比如：type="JDBC"）。
- 数据源的配置（比如：type="POOLED"）。

## 6.映射器（mappers）

我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。

```
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
</mappers>
```

```
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
</mappers>
```

```
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
</mappers>
```

```
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

如果使用接口实现类和包扫描的方式进行映射，需要注意：

- 接口和他的Mapper配置文件必须同名。
- 接口和他的Mapper配置文件必须在同一个包下。

## 7.生命周期和作用域

![image-20200922145926293](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200922145926293.png)

生命周期和作用域是至关重要的，因为错误的使用会导致非常严重的并发问题。

**SqlSessionFactoryBuilder**

- 这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

**SqlSessionFactory**

- 可以想象成数据库连接池。

- SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

**SqlSession**

- 每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。

- 用完之后需要关闭

# 5.映射文件

## 1.select

| 属性            | 描述                                                         |
| :-------------- | :----------------------------------------------------------- |
| `id`            | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType` | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| parameterMap    | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `resultType`    | 期望从这条语句中返回结果的类全限定名或别名。 注意，如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身的类型。 resultType 和 resultMap 之间只能同时使用一个。 |
| `resultMap`     | 对外部 resultMap 的命名引用。结果映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂的映射问题都能迎刃而解。 resultType 和 resultMap 之间只能同时使用一个。 |
| `flushCache`    | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。 |
| `useCache`      | 将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。 |
| `timeout`       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `fetchSize`     | 这是一个给驱动的建议值，尝试让驱动程序每次批量返回的结果行数等于这个设置值。 默认值为未设置（unset）（依赖驱动）。 |
| `statementType` | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `resultSetType` | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖数据库驱动）。 |
| `databaseId`    | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |
| `resultOrdered` | 这个设置仅针对嵌套结果 select 语句：如果为 true，将会假设包含了嵌套结果集或是分组，当返回一个主结果行时，就不会产生对前面结果集的引用。 这就使得在获取嵌套结果集的时候不至于内存不够用。默认值：`false`。 |
| `resultSets`    | 这个设置仅适用于多结果集的情况。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |

## 2.insert,update,delete

| 属性               | 描述                                                         |
| :----------------- | :----------------------------------------------------------- |
| `id`               | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType`    | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| `parameterMap`     | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `flushCache`       | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：（对 insert、update 和 delete 语句）true。 |
| `timeout`          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `statementType`    | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `useGeneratedKeys` | （仅适用于 insert 和 update）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系型数据库管理系统的自动递增字段），默认值：false。 |
| `keyProperty`      | （仅适用于 insert 和 update）指定能够唯一识别对象的属性，MyBatis 会使用 getGeneratedKeys 的返回值或 insert 语句的 selectKey 子元素设置它的值，默认值：未设置（`unset`）。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `keyColumn`        | （仅适用于 insert 和 update）设置生成键值在表中的列名，在某些数据库（像 PostgreSQL）中，当主键列不是表中的第一列的时候，是必须设置的。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `databaseId`       | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |

- 对于Mysql和SQL server数据库中有自增的id，可以使用useGeneratedKeys=true，keyProperty=id来返回自增的id（**返回到参数对象中**），如果自增不是第一列的情况，还需要用keyColumn来指定列名。

- 批量新增的情况

  ```xml
  <insert id="plInsertUser" parameterType="u03" useGeneratedKeys="true"
          keyProperty="id">
      insert into user(name,pwd) values
      <foreach collection="list" item="item" separator=",">
          (#{item.name},#{item.pwd})
      </foreach>
  </insert>
  ```

  上面执行出来的sql是这样的

  insert into user(name,pwd) values (?,?) , (?,?) , (?,?)

  并且将生成的id返回到对应的参数对象、列表中。

- 对于Oracle数据库不支持自动生成主键的情况，可以如下操作。

  ```
  <insert id="insertAuthor">
    <selectKey keyProperty="id" resultType="int" order="BEFORE">
      select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
    </selectKey>
    insert into Author
      (id, username, password, email,bio, favourite_section)
    values
      (#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
  </insert>
  ```

  其中，selectKey的元素属性

  | 属性            | 描述                                                         |
  | :-------------- | :----------------------------------------------------------- |
  | `keyProperty`   | `selectKey` 语句结果应该被设置到的目标属性。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
  | `keyColumn`     | 返回结果集中生成列属性的列名。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
  | `resultType`    | 结果的类型。通常 MyBatis 可以推断出来，但是为了更加准确，写上也不会有什么问题。MyBatis 允许将任何简单类型用作主键的类型，包括字符串。如果生成列不止一个，则可以使用包含期望属性的 Object 或 Map。 |
  | `order`         | 可以设置为 `BEFORE` 或 `AFTER`。如果设置为 `BEFORE`，那么它首先会生成主键，设置 `keyProperty` 再执行插入语句。如果设置为 `AFTER`，那么先执行插入语句，然后是 `selectKey` 中的语句 - 这和 Oracle 数据库的行为相似，在插入语句内部可能有嵌入索引调用。 |
  | `statementType` | 和前面一样，MyBatis 支持 `STATEMENT`，`PREPARED` 和 `CALLABLE` 类型的映射语句，分别代表 `Statement`, `PreparedStatement` 和 `CallableStatement` 类型。 |


## 3.sql

这个元素可以用来定义可重用的 SQL 代码片段，以便在其它语句中使用。参数可以静态地（在加载的时候）确定下来，并且可以在不同的 include 元素中定义不同的参数值，其中include的refid就是sql的id。

```
<sql id="sometable">
  ${prefix}Table
</sql>

<sql id="someinclude">
  from
    <include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
  select
    field1, field2, field3
  <include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
  </include>
</select>
```

上面的语句执行起来就是

select  field1, field2, field3 from SomeTable

## 4.参数

简单的对象，基本数据类型参数，除了可以直接引用参数名之外，还可以指定一个特殊的类型处理器。比如：

```
select * from user where id = #{_parameter,javaType=int,jdbcType=NUMERIC}
```

其中，jdbcType包括以下类型

```
ARRAY(2003),
BIT(-7),
TINYINT(-6),
SMALLINT(5),
INTEGER(4),
BIGINT(-5),
FLOAT(6),
REAL(7),
DOUBLE(8),
NUMERIC(2),
DECIMAL(3),
CHAR(1),
VARCHAR(12),
LONGVARCHAR(-1),
DATE(91),
TIME(92),
TIMESTAMP(93),
BINARY(-2),
VARBINARY(-3),
LONGVARBINARY(-4),
NULL(0),
OTHER(1111),
BLOB(2004),
CLOB(2005),
BOOLEAN(16),
CURSOR(-10),
UNDEFINED(-2147482648),
NVARCHAR(-9),
NCHAR(-15),
NCLOB(2011),
STRUCT(2002),
JAVA_OBJECT(2000),
DISTINCT(2001),
REF(2006),
DATALINK(70),
ROWID(-8),
LONGNVARCHAR(-16),
SQLXML(2009),
DATETIMEOFFSET(-155),
TIME_WITH_TIMEZONE(2013),
TIMESTAMP_WITH_TIMEZONE(2014);
```

## 5.字符替换

默认情况下，使用#{}的参数语法，就像PreparedStatement的参数占位符？一样，这样既防止了sql注入，而且更安全，更迅速。但是有的时候，也需要不转义的字符串，此时就用到了${}，它会被直接替换掉。

## 6.结果映射

**resultType**：一般都是简单的HashMap，对象（可以是对象地址，可以是对象[别名](#4.类型别名（typeAliases）)），基本数据类型。（不做过多解释）

**resultMap**：当对象中的参数属性与表中的参数属性不一一对应的时候，或者其他情况下（一对多，多对一），可能需要用到。用法如下。

```
<resultMap id="userResultMap" type="User">
  <id property="id" column="user_id" />
  <result property="username" column="user_name"/>
  <result property="password" column="hashed_password"/>
</resultMap>
```

```
<select id="selectUsers" resultMap="userResultMap">
  select user_id, user_name, hashed_password
  from some_table
  where id = #{id}
</select>
```

其中，resultMap标签中的id对应select标签中的resultMap，type对应的实体类的路径或者别名。

result标签中的property对应的是对象中的属性名称，column对应的是查询sql中的列名。

id标签中的属性与result中的相同，但是*id* 元素对应的属性会被标记为对象的标识符，标记出id属性，可以提高整体的性能

| 属性          | 描述                                                         |
| :------------ | :----------------------------------------------------------- |
| `property`    | 映射到列结果的字段或属性。如果 JavaBean 有这个名字的属性（property），会先使用该属性。否则 MyBatis 将会寻找给定名称的字段（field）。 无论是哪一种情形，你都可以使用常见的点式分隔形式进行复杂属性导航。 比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。 |
| `column`      | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 |
| `javaType`    | 一个 Java 类的全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之后的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可以为空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的全限定名，或者是类型别名。 |

如果类中的部分属性名和表中的列名相同，则可以不用写对应的名称。

## 7.高级结果映射

如果世界都像上面那样简单就好了

```
<!-- 非常复杂的结果映射 -->
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
    <arg column="blog_name" javaType="string"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```

- constructor - 用于在实例化类时，注入结果到构造方法中
  - `idArg` - ID 参数；标记出作为 ID 的结果可以帮助提高整体性能
  - `arg` - 将被注入到构造方法的一个普通结果
- `id` – 一个 ID 结果；标记出作为 ID 的结果可以帮助提高整体性能
- `result` – 注入到字段或 JavaBean 属性的普通结果
- association – 一个复杂类型的关联；许多结果将包装成这种类型
  - 嵌套结果映射 – 关联可以是 `resultMap` 元素，或是对其它结果映射的引用
- collection – 一个复杂类型的集合
  - 嵌套结果映射 – 集合可以是 `resultMap` 元素，或是对其它结果映射的引用
- discriminator – 使用结果值来决定使用哪个resultMap
  - case – 基于某些值的结果映射
    - 嵌套结果映射 – `case` 也是一个结果映射，因此具有相同的结构和元素；或者引用其它的结果映射

ResultMap的属性列表

| 属性          | 描述                                                         |
| :------------ | :----------------------------------------------------------- |
| `id`          | 当前命名空间中的一个唯一标识，用于标识一个结果映射。         |
| `type`        | 类的完全限定名, 或者一个类型别名（关于内置的类型别名，可以参考上面的表格）。 |
| `autoMapping` | 如果设置这个属性，MyBatis 将会为本结果映射开启或者关闭自动映射。 这个属性会覆盖全局的属性 autoMappingBehavior。默认值：未设置（unset）。 |

构造参数中的属性

| 属性          | 描述                                                         |
| :------------ | :----------------------------------------------------------- |
| `column`      | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 |
| `javaType`    | 一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可能存在空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。 |
| `select`      | 用于加载复杂类型属性的映射语句的 ID，它会从 column 属性中指定的列检索数据，作为参数传递给此 select 语句。具体请参考关联元素。 |
| `resultMap`   | 结果映射的 ID，可以将嵌套的结果集映射到一个合适的对象树中。 它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的 `ResultSet`。这样的 `ResultSet` 将会将包含重复或部分数据重复的结果集。为了将结果集正确地映射到嵌套的对象树中，MyBatis 允许你 “串联”结果映射，以便解决嵌套结果集的问题。想了解更多内容，请参考下面的关联元素。 |
| `name`        | 构造方法形参的名字。从 3.4.3 版本开始，通过指定具体的参数名，你可以以任意顺序写入 arg 元素。参看上面的解释。 |

# 6.日志

## 1.日志工厂

如果一个数据库操作，出现了异常，我们需要排错，就需要用到日志。

![image-20200922160153625](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200922160153625.png)

- STDOUT_LOGGING
- LOG4J

**STDOUT_LOGGING标准日志输出**

```
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

![image-20200922161741163](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200922161741163.png)

## 2.LOG4J配置

什么是LOG4J？

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件，甚至是套接口服务器、[NT](https://baike.baidu.com/item/NT/3443842)的事件记录器、[UNIX](https://baike.baidu.com/item/UNIX) [Syslog](https://baike.baidu.com/item/Syslog)[守护进程](https://baike.baidu.com/item/守护进程/966835)等
- 我们也可以控制每一条日志的输出格式
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程
- 通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码。

1.导入log4j的包

2.新增log4j.properties，粗略写下配置。

```xml
log4j.rootLogger=DEBUG,CONSOLE

# console
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.Threshold=DEBUG
log4j.appender.CONSOLE.Target=System.out
log4j.appender.CONSOLE.Encoding=UTF-8
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=[demo] %-5p %d{yyyy-MM-dd HH\:mm\:ss} - %C.%M(%L)[%t] - %m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

怎么使用Log4j？

1.import org.apache.log4j.Logger;

2.static Logger logger = Logger.getLogger(this.class);

3.常用的三种日志级别

```
logger.info("");
logger.debug("");
logger.error("");
```

配合着配置，可以以自定义的格式，输出到控制台或者规定的目录文件中（此处不过多介绍）

# 7.分页

**为什么需要分页？**

减少数据的处理量。

## 1.Limit分页（MYSQL），Rownum(Oracle)

java部分

```
@Test
    public void queryUserListWithPager() {
        SqlSession sqlSession = MybatisUtils04.getSqlSession();
        try {
            Map<String,Integer> map = new HashMap<>();
            map.put("startIndex",0);
            map.put("pageSize",5);
            List<User04> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserListWithPager",map);
            System.out.println(userList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭SqlSession
            sqlSession.close();
        }
    }
```

xml部分

```
<select id="queryUserListWithPager" parameterType="map" resultType="u04">
    select * from user
    order by id
    limit #{startIndex},#{pageSize}
</select>
```

## 2.RowBounds实现分页

java代码部分

```
//RowBound分页查询
@Test
public void queryUserListWithPager1() {
    SqlSession sqlSession = MybatisUtils04.getSqlSession();
    try {
        Map<String,Integer> map = new HashMap<>();
        //通过java代码层面实现分页
        List<User04> userList = sqlSession.selectList("com.gang.dao.UserDao.queryUserList",map,
                new RowBounds(0,5));
        System.out.println(userList);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}
```

xml部分

```
<select id="queryUserList" resultType="u04">
    select * from user
</select>
```

## 3.分页插件

https://pagehelper.github.io/

![image-20200923143244325](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200923143244325.png)

# 8.使用注解开发

## 1.面向接口编程

- 为什么采用面向接口编程？

**根本原因：解耦，可拓展，提高复用，分层开发中，上层不用管具体的实现，大家都遵守的共同的标准，使得开发变得容易，规范性更好**

在一个面向对象的系统中，系统的各种功能是由许许多多的不同对象协作完成的。在这种情况下，各个对象内部是如何实现自己的，对系统设计人员来讲就不那么重要了；

而各个对象之间的协作关系则成为系统设计的关键。小到不同类之间的通信，大到各模块之间的交互，在系统设计之初都是要着重考虑的，这也是系统设计的主要工作内容。面向接口编程就是按照这种思想来编程的。

- 关于接口的理解

  接口从更深层次的理解，应该是定义（规范，约束）与实现（名实分离的原则）的分离。

  接口的本身反映了系统设计人员对系统的抽象理解。

  接口应有两类：

  - 第一类时对一个个体的抽象，它可对应为一个抽象体（abstract class）
  - 第二类是对一个个体某一方面的抽象，即形成一个抽象面（interface）

  一=个个体有可能有多个抽象面。抽象体与抽象面是有区别的。

- 三个面向区别

  面向对象是指，我们考虑问题时，以对象为单位，考虑它的属性及方法。

  面向过程是指，我们考虑问题时，以一个具体的流程（事务过程）为单位，考虑它的实现。

  接口设计与非接口设计是针对复用技术而言的，与面向对象（过程）不是一个问题，更多的体现就是对系统整体的架构

## 2.使用注解开发

```
public interface UserMapper {
    @Select("select * from user")
    List<User05> queryUsers();
}
```

```
<!--绑定接口-->
<mappers>
    <mapper class="com.gang.dao.UserMapper"/>
</mappers>
```

测试

```
@Test
public void test() {

    //第一步：获得sqlsession对象
    SqlSession sqlSession = MybatisUtils05.getSqlSession();
    try {
        //执行sql
        //方式一：getMapper
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        List<User05> userList = userDao.queryUsers();

        for(User05 user : userList) {
            System.out.println(user);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}
```

使用注解来映射简单语句会使代码显得更加简洁，但对于稍微复杂一点的语句，Java 注解不仅力不从心，还会让你本就复杂的 SQL 语句更加混乱不堪。 因此，如果你需要做一些很复杂的操作，最好用 XML 来映射语句。

本质：反射机制实现

底层：动态代理。

## 3.CRUD

java

```
@Select("select * from user where id = #{id}")
User05 getUserById(@Param("id") int id2);

@Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
int insertUser(User05 user);

@Update("update user set name = #{name},pwd = #{pwd} where id = #{id}")
int updateUser(User05 user);

@Delete("delete from user where id = #{id}")
int deleteUser(@Param("id") int id);
```

测试

```
@Test
public void testSelectOne() {
    //第一步：获得sqlsession对象
    SqlSession sqlSession = MybatisUtils05.getSqlSession();
    try {
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        User05 user = userDao.getUserById(5);
        System.out.println(user);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}

@Test
public void testInsert() {
    //第一步：获得sqlsession对象
    SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
    try {
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        User05 user = new User05();
        user.setId(5);
        user.setName("李光");
        user.setPwd("546467");
        userDao.insertUser(user);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}

@Test
public void testUpdate() {
    //第一步：获得sqlsession对象
    SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
    try {
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        User05 user = new User05();
        user.setId(5);
        user.setName("李光");
        user.setPwd("123456");
        userDao.updateUser(user);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}

@Test
public void testDelete() {
    //第一步：获得sqlsession对象
    SqlSession sqlSession = MybatisUtils05.getSqlSession(true);
    try {
        //执行sql
        //方式一：getMapper
        UserMapper userDao = sqlSession.getMapper(UserMapper.class);
        userDao.deleteUser(5);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //关闭SqlSession
        sqlSession.close();
    }
}
```

**关于@Param()注解**

- 基本类型的参数或者String类型，需要加上。
- 引用类型不用加
- 如果只有一个基本类型的话，可以忽略（不建议）
- sql中引用的就是@Param中的名称

# 9.Lombok

ProjectLombok是一个java库，它可以自动插入到编辑器和构建工具中，增强java的性能。

永远不要再写另一个getter或equals方法，只要有一个注释，你的类就有一个功能齐全的构建器，自动记录变量，等等。

1.在IDEA中安装lombok插件。

2.在项目中导入包

```
<!-- lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.16.18</version>
    <optional>true</optional>
</dependency>
```

3.在类或者属性上加注解

- @Data:无参构造，get,set,tostring,hashcode,equals
- @AllArgsConstructor
- @NoArgsConstructor