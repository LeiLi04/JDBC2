package com.atguigu4.exer;

import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer1Test {
    @Test
    public void testInsert(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username");
        String username = scanner.next();
        System.out.println("Please enter your password");
        String password = scanner.next();
        System.out.println("Please enter your birthday");
        String birthday = scanner.next();
        String sql="insert into customers(name,email,birth)values(?,?,?)";
        int insertCount=update(sql,username,password,birthday);
        if(insertCount>0){
            System.out.println("Success!");
        }   else {
            System.out.println("Failed!");
        }
    }

    public int update(String sql, Object... args) {
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
            /*
             *如果执行的是查询操作，有返回的结果，就是true
             * 如果执行update（增删改），则返回false
             * ps.execute();
             */
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4 close
            JDBCUtils.closeResource(connection, ps);
        }
        return 0;
    }
}
