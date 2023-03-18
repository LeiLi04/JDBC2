package com.atguigu4.exer;

import com.atguigu3.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Exer2Test {
    @Test
    public void testExer2Test(){
//    请输入考生的详细信息
//
        Scanner scanner = new Scanner(System.in);
//    Type:
        System.out.println("请输入考试类型");
        int type = scanner.nextInt();
//    IDCard:
        System.out.println("请输入IDCard");
        String idCard = scanner.next();
//    ExamCard:
        System.out.println("请输入ExamCard");
        String examCard = scanner.next();
//    StudentName:
        System.out.println("请输入studentName");
        String studentName = scanner.next();
//    Location:
        System.out.println("请输入location");
        String location = scanner.next();
//    Grade:
        System.out.println("请输入grade");
        int grade = scanner.nextInt();
        //执行sql
        String sql="insert into examstudent(type,IDCard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
        int insertCounts=update(sql,type,idCard,examCard,studentName,location,grade);
//    信息录入成功!
        if(insertCounts>0){
            System.out.println("Succesfully");
        }else{
            System.out.println("failed");
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
