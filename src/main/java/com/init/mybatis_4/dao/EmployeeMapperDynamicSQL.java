package com.init.mybatis_4.dao;

import com.init.mybatis_4.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    public List<Employee> getEmpsByCondtionIf(Employee employee);

    public List<Employee> getEmpsByCondtionTrim(Employee employee);

    public List<Employee> getEmpsByCondtionChoose(Employee employee);

    public void updateEmp(Employee employee);

    public List<Employee> getEmpsByConditionForeach(@Param("ids") List<Integer> ids);

    public void addEmps(@Param("employees") List<Employee> employees);

    public List<Employee> getEmpsTestInnerParameter(Employee employee);
}
