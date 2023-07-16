package com.init.mybatis_9.dao;

import com.init.mybatis_9.bean.Employee;
import com.init.mybatis_9.bean.OrcalePage;

import java.util.List;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> getEmps();

    public boolean addEmp(Employee employee);

    public void getPageByProdure(OrcalePage page);
}
