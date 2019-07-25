# MyBatis 持久化框架

## 简介
MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射

## 使用

### 引入依赖
```xml
<!-- mybatis -->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.4.6</version>
</dependency>
<!-- mysql -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.15</version>
</dependency>
```

### 配置文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/study"/>
                <property name="username" value="root"/>
                <property name="password" value="hhj19980221"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
</configuration>
```

### 映射文件 UserMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.study.mapper.UserMapper">
    <select id="selectUser" resultType="com.study.pojo.User">
        SELECT * FROM USER WHERE user_id = #{user_id}
    </select>
</mapper>
```

### POJO 类
```java
public class User {
    private Long user_id;
    private String user_name;
//    get/set and toSting ...    
}

```

### Mapper 接口
```java
public interface UserMapper {
    User select(int user_id);
}
```

### MyBatis 核心 API 使用
```java
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        指定全局配置文件的位置
        String config = "src/main/resources/config/mybatis-config.xml";
//        读取配置文件
        InputStream inputStream = new FileInputStream(config);
//        创建 SqlSessionFactory 会话工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        
//        创建会话
        SqlSession sqlSession = factory.openSession();
//        执行 SQL 语句（全路径类名.方法名, 参数）
        User user = sqlSession.selectOne("com.study.mapper.UserMapper.selectUser", 1);
        
        System.out.println(user);
//        释放资源
        sqlSession.close();
    }
}
```

## MyBatis 缓存机制
### 一级缓存（默认打开）
- 一级缓存是 SqlSession 级别的缓存，在每个 SqlSession 中有一个 HashMap 用于缓存数据，不同的 SqlSession 对象缓存的数据的区域是互相不影响的
- 如果一个 SqlSession 对象执行了 commit 操作，就清空了 SqlSession 对象中的缓存了（避免脏读）

### 二级缓存
- 二级缓存是 Mapper 级别的缓存，多个 SqlSession 去调用同一个 Mapper 中的 SQL 语句时，它们可以公用二级缓存，二级缓存是跨域 Session 对象的
- 二级缓存的区域是