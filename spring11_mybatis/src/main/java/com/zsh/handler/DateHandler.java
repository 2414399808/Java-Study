package com.zsh.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;


public class DateHandler extends BaseTypeHandler<Date> {

//将java类型转换成数据库需要的类型
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        long time = date.getTime();
        preparedStatement.setLong(i,time);
    }

    //将数据库中类型转换成java类型
    //String 参数 要转换的字段的名称

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        //获得结果集中需要的数据
        long birthday = resultSet.getLong(s);
        Date date = new Date(birthday);
        return date;
    }
    //将数据库中类型转换成java类型
    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        long birthday = resultSet.getLong(i);
        Date date = new Date(birthday);
        return date;
    }
    //将数据库中类型转换成java类型
    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        long birthday = callableStatement.getLong(i);
        Date date = new Date(birthday);
        return date;
    }
}
