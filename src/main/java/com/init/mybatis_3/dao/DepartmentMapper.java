package com.init.mybatis_3.dao;

import com.init.mybatis_3.bean.Department;

public interface DepartmentMapper {
    public Department getDeptById(Integer id);

    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
