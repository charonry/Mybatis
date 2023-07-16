package com.init.mybatis_3.dao;

import com.init.mybatis_3.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public long addEmp(Employee employee);

    public boolean updateEmp(Employee employee);

    public void deleteEmp(Integer id);

    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public List<Employee> getEmpsByLastNameLike(@Param("lastName") String lastName);

    //返回一条记录map；key就是列名，值就是对应的值
    public Map<String, Object> getEmpByIdReturnMap(Integer id);

    //多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
    //@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("id")
    public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);
}
