package com.init.mybatis_5.dao;

import com.init.mybatis_3.bean.Employee;

import java.util.List;

public interface EmployeeMapperPlus {
    public Employee getEmpById(Integer id);

    public Employee getEmpAndDeptById(Integer id);

    public Employee getEmpByIdStep(Integer id);

    public List<Employee> getEmpsByDeptIdStep(Integer deptId);

    public Employee getEmpByIdStepDiscriminator(Integer id);
}
