package com.init.mybatis_5.test;

import com.init.mybatis_5.bean.Employee;
import com.init.mybatis_5.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_5/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 两级缓存：
     * 一级缓存：（本地缓存）：sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
     * 与数据库同一次会话期间查询到的数据会放在本地缓存中。
     * 以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库；
     * <p>
     * 一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 1、sqlSession不同。
     * 2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 4、sqlSession相同，手动清除了一级缓存（缓存清空）
     * <p>
     * 二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
     * 工作机制：
     * 1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 3、sqlSession===EmployeeMapper==>Employee
     * DepartmentMapper===>Department
     * 不同namespace查出的数据会放在自己对应的缓存中（map）
     * 效果：数据会从二级缓存中获取
     * 查出的数据都会被默认先放在一级缓存中。
     * 只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 使用：
     * 1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 2）、去mapper.xml中配置使用二级缓存：
     * <cache></cache>
     * 3）、我们的POJO需要实现序列化接口
     * <p>
     * 和缓存有关的设置/属性：
     * 1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     * 2）、每个select标签都有useCache="true"：
     * false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     * 3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     * 增删改执行完成后就会清楚缓存；
     * 测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     * 查询标签：flushCache="false"：
     * 如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     * 4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     * 5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     * STATEMENT：可以禁用一级缓存；
     * <p>
     * 第三方缓存整合：
     * 1）、导入第三方缓存包即可；
     * 2）、导入与第三方缓存整合的适配包；官方有；
     * 3）、mapper.xml中使用自定义缓存
     * <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     *
     * @throws IOException
     */

    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            mapper.getEmpById(1);
            Employee emp01 = mapper.getEmpById(1);
            System.out.println(emp01);
            mapper.addEmp(new Employee(null, "jax", "1", "jax@qq.com"));
            sqlSession.commit();
            System.out.println("数据添加成功");
            Employee emp02 = mapper.getEmpById(1);
            System.out.println("是否没有重新封装：" + (emp01 == emp02));
          /*  SqlSession sqlSession2=sqlSessionFactory.openSession();
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            Employee emp022 = mapper2.getEmpById(1);
            System.out.println("是否没有重新封装："+(emp01==emp022));
            sqlSession2.close();*/
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSecondLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            Employee emp01 = mapper.getEmpById(1);
            System.out.println(emp01);
            sqlSession.close();
            Employee emp02 = mapper2.getEmpById(1);
            System.out.println(emp02);
            sqlSession2.close();
        } finally {

        }
    }
}
