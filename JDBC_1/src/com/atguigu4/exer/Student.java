package com.atguigu4.exer;

import com.atguigu3.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Student {
    //Attributes
    private int flowID;
    private int type;
    private String iDCard;
    private String examCard;
    private String studentName;
    private String location;
    private int grade;

    public static void main(String[] args) {
        System.out.println("请选择您要输入的类型");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        Scanner s=new Scanner(System.in);
        String selection = s.next();
        if("a".equalsIgnoreCase(selection)) {
            System.out.println("请输入准考证号");
            String examCard=s.next();
            String sql="select FlowID flowID,Type type,IDCard iDCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where examCard=?";
            Student student = queryCommon(Student.class, sql, examCard);
            System.out.println(student);
        }else if("b".equalsIgnoreCase(selection)) {
            System.out.println("请输入身份证号");
            String iDCard=s.next();
            String sql="select FlowID flowID,Type type,IDCard iDCard,ExamCard examCard,StudentName studentName,Location location,Grade grade from examstudent where idCard=?";
            Student student = queryCommon(Student.class, sql, iDCard);
            System.out.println(student);
        }else{
            System.out.println("别nm瞎几把乱写");
        }

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
    //Constructor
    public Student() {
    }

    public Student(int flowID, int type, String iDCard, String examCard, String studentName, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.iDCard = iDCard;
        this.examCard = examCard;
        this.studentName = studentName;
        this.location = location;
        this.grade = grade;
    }

    @Override
    public String toString() {
        System.out.println("======查询结果========");
        return info();
    }

    private String info() {
        return "流水号："+flowID+"\n四级/六级"+type+"\n身份证号"+iDCard+"\n准考证号"+examCard+"\n学生姓名"+studentName+"\n区域"+location+"\n成绩"+grade;
    }
    //Get&Set

    public int getFlowID() {
        return flowID;
    }

    public void setFlowID(int flowID) {
        this.flowID = flowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getiDCard() {
        return iDCard;
    }

    public void setiDCard(String iDCard) {
        this.iDCard = iDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
