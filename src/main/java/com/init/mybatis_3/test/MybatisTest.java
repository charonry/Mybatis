package com.init.mybatis_3.test;


import com.init.mybatis_3.bean.Department;
import com.init.mybatis_3.bean.Employee;
import com.init.mybatis_3.dao.DepartmentMapper;
import com.init.mybatis_3.dao.EmployeeMapper;
import com.init.mybatis_3.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class MybatisTest {

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis_3/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void Test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpByIdAndLastName(1, "tom");
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //获取到的SqlSession不会自动提交数据
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
            //添加
            /*Employee employee=new Employee(null,"jerry","0","jerry@qq.com");
            employeeMapper.addEmp(employee);
            System.out.println(employee.getId());*/
            //更新
           /* Employee employee=new Employee(2,"jerry","1","jeery@qq.com");
            Boolean flag=employeeMapper.updateEmp(employee);
            System.out.println(flag);*/
            //删除
            /*employeeMapper.deleteEmp(2);*/
            //手动提交
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> employees = mapper.getEmpsByLastNameLike("ry");
            System.out.println(mapper.getClass());
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            /*Map<String,Object> map=mapper.getEmpByIdReturnMap(1);
            System.out.println(map);*/
            Map<String, Employee> map = mapper.getEmpByLastNameLikeReturnMap("ry");
            System.out.println(map);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
          /*  Employee employee=mapper.getEmpById(1);
            System.out.println(employee);
            System.out.println(mapper.getClass());;*/
            /*Employee empAndDept = mapper.getEmpAndDeptById(1);
            System.out.println(empAndDept);
            System.out.println(empAndDept.getDept());*/
            Employee empByIdStep = mapper.getEmpByIdStep(1);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            /*Department deptByIdPlus = mapper.getDeptByIdPlus(1);
            System.out.println(deptByIdPlus);
            System.out.println(deptByIdPlus.getEmps());*/
            /*Department deptByIdStep = mapper.getDeptByIdStep(1);
            System.out.println(deptByIdStep);
            System.out.println(deptByIdStep.getEmps());*/
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void Test07() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee empByIdStepDiscriminator = mapper.getEmpByIdStepDiscriminator(1);
            System.out.println(empByIdStepDiscriminator);
            System.out.println(empByIdStepDiscriminator.getDept());
        } finally {
            sqlSession.close();
        }
    }
}
