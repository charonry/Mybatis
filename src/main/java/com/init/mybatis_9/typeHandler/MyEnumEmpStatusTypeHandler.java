package com.init.mybatis_9.typeHandler;

import com.init.mybatis_9.bean.Empstatus;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现TypeHandler接口，或者继承BaseTypeHandler
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<Empstatus> {
    /**
     * 定义当前数据如何保存到数据库中
     *
     * @param preparedStatement
     * @param i
     * @param empstatus
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Empstatus empstatus, JdbcType jdbcType)
            throws SQLException {
        String code = String.valueOf(empstatus.getCode());
        System.out.println("要保存的状态码是：" + code);
        preparedStatement.setString(i, code);
    }

    @Override
    public Empstatus getResult(ResultSet resultSet, String columnName) throws SQLException {
        //需要从数据库中拿到枚举状态码返回的一个枚举对象
        int code = resultSet.getInt(columnName);
        System.out.println("从数据库中获取的状态码：" + code);
        Empstatus status = Empstatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public Empstatus getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        //需要从数据库中拿到枚举状态码返回的一个枚举对象
        int code = resultSet.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码：" + code);
        Empstatus status = Empstatus.getEmpStatusByCode(code);
        return status;
    }

    @Override
    public Empstatus getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        //需要从数据库中拿到枚举状态码返回的一个枚举对象
        int code = callableStatement.getInt(columnIndex);
        System.out.println("从数据库中获取的状态码：" + code);
        Empstatus status = Empstatus.getEmpStatusByCode(code);
        return status;
    }
}
