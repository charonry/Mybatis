package com.init.mybatis_7.test;


import com.init.mybatis_7.bean.Employee;
import com.init.mybatis_7.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_7/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、获取sqlSessionFactory对象:
     * 解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
     * 注意：【MappedStatement】：代表一个增删改查的详细信息
     * <p>
     * 2、获取sqlSession对象
     * 返回一个DefaultSQlSession对象，包含Executor和Configuration;
     * 这一步会创建Executor对象；
     * <p>
     * 3、获取接口的代理对象（MapperProxy）
     * getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
     * 代理对象里面包含了，DefaultSqlSession（Executor）
     * 4、执行增删改查方法
     * <p>
     * 总结：
     * 1、根据配置文件（全局，sql映射）初始化出Configuration对象
     * 2、创建一个DefaultSqlSession对象，
     * 他里面包含Configuration以及
     * Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
     * 3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
     * 4、MapperProxy里面有（DefaultSqlSession）；
     * 5、执行增删改查方法：
     * 1）、调用DefaultSqlSession的增删改查（Executor）；
     * 2）、会创建一个StatementHandler对象。
     * （同时也会创建出ParameterHandler和ResultSetHandler）
     * 3）、调用StatementHandler预编译参数以及设置参数值;
     * 使用ParameterHandler来给sql设置参数
     * 4）、调用StatementHandler的增删改查方法；
     * 5）、ResultSetHandler封装结果
     * 注意：
     * 四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
     *
     * @throws IOException
     */

    @Test
    public void Test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper);
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }
}
