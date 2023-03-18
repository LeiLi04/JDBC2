package com.atguigu3.preparedstatement.crud;

import com.atguigu3.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class PreparedStatementUpdate {

    @Test
    public void testCommonUpdate(){
//		String sql = "delete from customers where id = ?";
//		update(sql,3);

        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql,"DD","2");
    }

    /**
     * @Description 通用的对数据库增删改查的操作
     * Author:LeiLi
     * Date: 2022-10-20
     * @param sql
     * @param args
     */
    public void update(String sql, Object... args) {
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
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4 close
            JDBCUtils.closeResource(connection, ps);
        }
    }

}
