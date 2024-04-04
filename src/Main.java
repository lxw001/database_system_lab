import com.mysql.cj.jdbc.CallableStatement;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Main {
    public static final String URL = "jdbc:mysql://localhost:3306/SCT?rewriteBatchedStatements=true";
    public static final String USER = "root";
    public static final String PASSWORD = "666666";
//    public static ArrayList<Student> StudentList = new ArrayList<>();//学生表
//    public static ArrayList<Course> CourseList = new ArrayList<>();//课程表
//    public static ArrayList<SC> SCList = new ArrayList<>();//选课表

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动程序
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD); // 连接数据库

//        Data data=new Data();
//        data.generateData();//生成数据
//        data.InitDatabase(con);//将数据插入数据库
//        data.deleteData(con);//将所有表中数据删除（生成数据时这条用不上）

        //con.close();
        GUI gui=new GUI();
        gui.init();
    }

}
