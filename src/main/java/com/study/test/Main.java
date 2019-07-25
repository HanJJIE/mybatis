package com.study.test;

import com.study.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
    }
}
