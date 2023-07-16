package com.init.mybatis_9.test;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.init.mybatis_9.bean.Employee;
import com.init.mybatis_9.bean.Empstatus;
import com.init.mybatis_9.bean.OrcalePage;
import com.init.mybatis_9.dao.EmployeeMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class MybatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_9/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void Test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Page<Object> page = PageHelper.startPage(4, 1);
            List<Employee> emps = mapper.getEmps();
            //后面加一个参数表示连续显示多少页
            PageInfo<Employee> info = new PageInfo<>(emps, 5);
            for (Employee employee : emps) {
                System.out.println(employee);
            }
            /*System.out.println("当前页码："+page.getPageNum());
            System.out.println("总记录数："+page.getTotal());
            System.out.println("每页的记录数："+page.getPageSize());
            System.out.println("总页码："+page.getPages());*/
            System.out.println("当前页码：" + info.getPageNum());
            System.out.println("总记录数：" + info.getTotal());
            System.out.println("每页的记录数：" + info.getPageSize());
            System.out.println("总页码：" + info.getPages());
            System.out.println("是否是第一页：" + info.isIsFirstPage());
            System.out.print("连续显示的页码：" + "\t");
            int[] nums = info.getNavigatepageNums();
            for (int num : nums) {
                System.out.print(num + "\t");
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //可以执行批量操作的sqlsession
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            for (int i = 0; i < 10; i++) {
                String gender = ((i & 1) == 0 ? "0" : "1");
                StringBuffer uuid = new StringBuffer(UUID.randomUUID().toString().substring(0, 5));
                mapper.addEmp(new Employee(null, uuid.toString(), gender, uuid.append("@qq.com").toString()));
            }
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testProcedure() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            OrcalePage orcalePage = new OrcalePage();
            orcalePage.setStart(1);
            orcalePage.setEnd(5);
            mapper.getPageByProdure(orcalePage);
            System.out.println("总记录数：" + orcalePage.getCount());
            System.out.println("查出的数据：" + orcalePage.getEmps());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testEnum() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            /* Employee employee=new Employee(null,"Amazing","0","Amazing@qq.com");
             mapper.addEmp(employee);
            sqlSession.commit();
            System.out.println("保存成功："+employee.getId());*/
            Employee empById = mapper.getEmpById(31);
            System.out.println("员工的状态码：" + empById.getEmpstatus());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Mybatis在处理枚举时候默认保存是枚举的名字，就是利用了TypeHandler
     */
    @Test
    public void testEnumUse() {
        Empstatus empstatus = Empstatus.LOGOUT;
        System.out.println("枚举的索引：" + empstatus.ordinal());
        System.out.println("枚举的名字：" + empstatus.name());
        System.out.println("枚举的状态码：" + empstatus.getCode());
        System.out.println("枚举的提示信息：" + empstatus.getMsg());
    }
}
