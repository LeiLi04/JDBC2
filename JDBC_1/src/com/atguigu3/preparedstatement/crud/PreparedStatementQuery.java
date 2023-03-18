package com.atguigu3.preparedstatement.crud;

import com.atguigu3.bean.Customer;
import com.atguigu3.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PreparedStatementQuery {

    @Test
    public void testPreparedStatement() throws Exception {
        String sql="select id,name,email from customers where id=?";
        Customer customer=queryCommon(Customer.class,sql,12);
        System.out.println(customer);
    }

    /**
     * @Description: 针对不同的表的通用的查询操作，返回表中的一条数据
     * @autor LeiLi
     * @date 2022-10-20
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T queryCommon(Class<T> clazz,String sql, Object... args) {
        //1 get connection
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            //2 creat preparedStatement
            ps = connection.prepareStatement(sql);
            //2.1 occupied sign
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //3 ps execute sql
            ResultSet rs = ps.executeQuery();
            //4 ps get MetaData
            ResultSetMetaData md= rs.getMetaData();
            //5 Through ResultSet to get Column value, and get column numbers & colum name by MetaData
            if(rs.next()){
                T t = clazz.newInstance();
                for(int i=0;i<md.getColumnCount();i++){
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = md.getColumnLabel(i + 1);
                    // 通过反射得到实例
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close
            JDBCUtils.closeResource(connection, ps);
        }
        return null;
    }
}
