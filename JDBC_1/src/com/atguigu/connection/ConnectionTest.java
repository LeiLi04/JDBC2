package com.atguigu.connection;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        ConnectionTest connectionTest = new ConnectionTest();
        connectionTest.testConnection5();

    }
    @Test
    //方式一
    public void testConnection1(){
        try {
            //1.提供java.sql.Driver接口实现类的对象
            java.sql.Driver driver= new com.mysql.cj.jdbc.Driver();
            //url:http://localhost:8080/gmall/keyboard.jpg
            //jdbc:mysql: protocol
            //localhost:ip address
            //3306, default port of mysql
            //test: test Database
            //2.提供url，指明具体操作的数据
            String url="jdbc:mysql://localhost:3306/jdbc_learn";
            //3.提供Properties的对象，指明用户名和密码
            Properties info=new Properties();
            info.setProperty("user","root");
            info.setProperty("password","99007951");
            //4.调用driver的connect()，获取连接
            Connection conn= driver.connect(url, info);
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //方式二：对方式一的迭代，在如下的程序中不出现第三方的api，使得程序具有更好的可移植性
     public void testConnection2(){
         try {
             //1 获取Driver实现类对象：使用反射[*这里是方法1，2之间唯一的区别]
             Class clazz=Class.forName("com.mysql.cj.jdbc.Driver");
             Driver driver=(Driver)clazz.newInstance();
             //2.提供url，指明具体操作的数据
             String url="jdbc:mysql://localhost:3306/jdbc_learn";
             //3.提供Properties的对象，指明用户名和密码
             Properties info=new Properties();
             info.setProperty("user","root");
             info.setProperty("password","99007951");
             //4.调用driver的connect()，获取连接
             Connection conn= driver.connect(url, info);
             System.out.println(conn);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (InstantiationException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (SQLException e) {
             e.printStackTrace();
         }
         {

         }
     }
     // 方式三：使用DriverManager替换Driver
    @Test
    public void testConnection3 (){
        try {
            //1 获取Driver实现类对象：使用反射[*这里是方法1，2之间唯一的区别]
            Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver=(Driver)clazz.newInstance();
            //2.提供另外三个链接的基本信息
            String url="jdbc://localhost:3306/test";
            String user="root";
            String password="99007951";
            //注册驱动
            DriverManager.registerDriver(driver);
            //获取链接
            Connection conn= DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    // 方式四：可以只是加载驱动，不用显示注册驱动了
    @Test
    public void testConnection4 (){
        try {
            //1.提供另外三个链接的基本信息
            String url="jdbc://localhost:3306/test";
            String user="root";
            String password="99007951";
            //1 获取Driver实现类对象：使用反射[*这里是方法1，2之间唯一的区别]
            Class.forName("com.mysql.cj.jdbc.Driver");
            //相对于方式三，可以省略如下操作
            /*Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver=(Driver)clazz.newInstance();
            //注册驱动
            DriverManager.registerDriver(driver);*/
            //为什么可以省略上述操作呢？
            /*
                在mysql的Driver实现类中，声明了如下操作
                static {
                try {
                    DriverManager.registerDriver(new Driver());
                } catch (SQLException var1) {
                    throw new RuntimeException("Can't register driver!");
                }
            }
             */
            //获取链接
            Connection conn= DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//方式五，将数据库链接需要的4个声明信息放在配置文件中，通过读取配置文件的方式，获取链接
    /*
    这种方式的好处：
        1 实现了数据和代码的分离。实现了解除耦合
        2 如果需要修改配置文件信息，可以避免程序重新打包
     */
    @Test
    public void testConnection5 () throws IOException, ClassNotFoundException, SQLException {
        //1. 读取properties中的4个基本信息
         InputStream is=ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc_1.properties");
         Properties pros=new Properties();
         pros.load(is);
         String user = pros.getProperty("user");
         String password = pros.getProperty("password");
         String url = pros.getProperty("url");
         String driverClass = pros.getProperty("driverClass");
        //2 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3 获取链接
        Connection conn= DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
