package com.init.mybatis_4.test;


import com.init.mybatis_4.bean.Department;
import com.init.mybatis_4.bean.Employee;
import com.init.mybatis_4.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MybatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_4/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(1, "m", null, null);
            /*List<Employee> emps = mapper.getEmpsByCondtionIf(employee);
            for(Employee emp:emps){
                System.out.println(emp);
            }*/
            /*List<Employee> emps = mapper.getEmpsByCondtionTrim(employee);
            for(Employee emp:emps){
                System.out.println(emp);
            }*/
            /*List<Employee> emps = mapper.getEmpsByCondtionChoose(employee);
            for(Employee emp:emps){
                System.out.println(emp);
            }*/
            Employee employee1 = new Employee(1, "Admin", null, "Admin@qq.com");
            mapper.updateEmp(employee1);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 4));
            for (Employee emp : emps) {
                System.out.println(emp);
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(null, "jax", "0", "jax@qq.com", new Department(1)));
            employees.add(new Employee(null, "minth", "1", "minth@qq.com", new Department(2)));
            employees.add(new Employee(null, "taylor", "0", "taylor@qq.com", new Department(2)));
            mapper.addEmps(employees);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInnerParameter() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee();
            employee.setLastName("o");
            List<Employee> employees = mapper.getEmpsTestInnerParameter(employee);
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        } finally {
            sqlSession.close();
        }
    }
}
