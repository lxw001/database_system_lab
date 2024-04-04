import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Data数据类，用于生成数据，将数据插入数据库，删除所有表中数据
 */
public class Data {
    private ArrayList<String> SnoList = new ArrayList<>();//学号列表
    private String[] Ssexlist = {"男", "女"};//待选性别列表
    private ArrayList<String> ChineseNameList = new ArrayList<>();//所有待选中文名字列表
    private ArrayList<String> CourseNameList = new ArrayList<>();//所有课程名列表
    private ArrayList<Student> StudentList = new ArrayList<>();//学生表
    private ArrayList<Course> CourseList = new ArrayList<>();//课程表
    private ArrayList<SC> SCList = new ArrayList<>();//选课表
    //生成数据
    public  void generateData() {
        //构造学号，4个学年（2019-2022），90个班，每个班30个学生
        for (int i = 2019; i <= 2022; i++)
            for (int j = 1; j <= 90; j++)
                for (int k = 1; k <= 30; k++)
                    SnoList.add(i + String.format("%02d", j) + String.format("%02d", k));
        //学生总数10800
        Integer Ssum = 4 * 90 * 30;

        //读取文件，得到学生名列表和课程名列表，便于之后随机从中产生
        try {
            String str;
            FileInputStream file1 = new FileInputStream("Dataset/Chinese_Names.txt");
            FileInputStream file2 = new FileInputStream("Dataset/courses_zh.txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(file1, "utf-8"));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(file2, "utf-8"));
            while ((str = br1.readLine()) != null) {
                ChineseNameList.add(str);
            }
            br1.close();

            while ((str = br2.readLine()) != null) {
                CourseNameList.add(str);
            }
            br2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Random rand = new Random();
        //先生成Student表数据
        // Student(S# char(8) Primary key, Sname char(10), Ssex char(2), Sage integer, SClass char(6))
        //年龄在15-35之间，姓名在百家姓中随机选择并结合学号构造，性别分男女，
        for (int i = 0; i < SnoList.size(); i++) {
            String Sno = SnoList.get(i);
            String Sname = ChineseNameList.get(rand.nextInt(ChineseNameList.size()));
            String Ssex = Ssexlist[rand.nextInt(2)];
            int Sage = rand.nextInt(21) + 15;
            String Sclass = String.format("%c%c", Sno.charAt(4), Sno.charAt(5));//由于学号构造规则，前四位为年号，第5、6位为班级号
            Student stu = new Student(Sno, Sname, Ssex, Sage, Sclass);
            StudentList.add(stu);
        }
        //再生成Course表
        //Course(C# char(4) Primary key, Cname char(30), Credit float(1) , Chours integer, T# char(3))
        //课程号分学科，学时在8-192之间且为8的整数倍，学分依据学时换算即每16学时为1学分，即0.5-12学时（以0.5递增）
        //  每个学生学习课程按学年有16、32、48、64门按学年递增分布，
        for (int i = 0; i < CourseNameList.size(); i++) {
            String Cno = String.format("%04d", i + 1);
            String Cname = CourseNameList.get(i);
            int Chours = 0;
            do {
                Chours = rand.nextInt(185) + 8;
            } while (Chours % 8 != 0);
            float Credit = (float) (Chours / 16.0);
            String Tno = String.format("%03d", rand.nextInt(999) + 1);
            Course course = new Course(Cno, Cname, Credit, Chours, Tno);
            CourseList.add(course);
        }

        //最后生成SC表
        // SC(S# char(8) REFERENCES student S#, C# char(4) REFERENCES course C#, Score float(1))
        //SC表的学号与课程号均应出现于Student表和Course表，每个学生学习课程按学年有16、32、48、64门按学年递增分布，学生姓名和课程名依学号、课程号构造加随机产生等。
        for (int i = 0; i < SnoList.size(); i++) {
            String Sno = SnoList.get(i);
            String year = String.format("%c%c%c%c", Sno.charAt(0), Sno.charAt(1), Sno.charAt(2), Sno.charAt(3));
            int courseSum = CourseNameList.size();
            //16门课
            if (year.equals("2019")) {
                for (int j = 0; j < 16; j++) {
                    String Cno = String.format("%04d", rand.nextInt(courseSum) + 1);
                    float Score = (float) (Math.random() * 100);
                    SC sc = new SC(Sno, Cno, Score);
                    SCList.add(sc);
                }
            }
            //32门课
            if (year.equals("2020")) {
                for (int j = 0; j < 32; j++) {
                    String Cno = String.format("%04d", rand.nextInt(courseSum) + 1);
                    float Score = (float) (Math.random() * 100);
                    SC sc = new SC(Sno, Cno, Score);
                    SCList.add(sc);
                }
            }
            //48门课
            if (year.equals("2021")) {
                for (int j = 0; j < 48; j++) {
                    String Cno = String.format("%04d", rand.nextInt(courseSum) + 1);
                    float Score = (float) (Math.random() * 100);
                    SC sc = new SC(Sno, Cno, Score);
                    SCList.add(sc);
                }
            }
            //64门课
            if (year.equals("2022")) {
                for (int j = 0; j < 64; j++) {
                    String Cno = String.format("%04d", rand.nextInt(courseSum) + 1);
                    float Score = (float) (Math.random() * 100);
                    SC sc = new SC(Sno, Cno, Score);
                    SCList.add(sc);
                }
            }
        }
    }
    //将生成的数据插入到数据库中
    public  void InitDatabase(Connection con) throws SQLException {
        String student_insert_sql = "insert into Student values (?,?,?,?,?)";
        String course_insert_sql = "insert into Course values (?,?,?,?,?)";
        String sc_insert_sql = "insert into SC values (?,?,?)";
        PreparedStatement ps1 = con.prepareStatement(student_insert_sql);
        PreparedStatement ps2 = con.prepareStatement(course_insert_sql);
        PreparedStatement ps3 = con.prepareStatement(sc_insert_sql);
        //将记录插入到Student表中
        for (int i = 0; i < StudentList.size(); i++) {
            Student stu = StudentList.get(i);
            ps1.setObject(1, stu.getSno());
            ps1.setObject(2, stu.getSname());
            ps1.setObject(3, stu.getSsex());
            ps1.setObject(4, stu.getSage());
            ps1.setObject(5, stu.getSclass());
            ps1.addBatch();
        }
        ps1.executeBatch();
        ps1.clearBatch();
        System.out.println("Student表记录插入完成");
        //将记录添加Course表
        for (int i = 0; i < CourseList.size(); i++) {
            Course course = CourseList.get(i);
            ps2.setObject(1, course.getCno());
            ps2.setObject(2, course.getCname());
            ps2.setObject(3, course.getCredit());
            ps2.setObject(4, course.getChours());
            ps2.setObject(5, course.getTno());
            ps2 .addBatch();;
        }
        ps2.executeBatch();
        ps2.clearBatch();
        System.out.println("Course记录插入完成");
        //将记录插入SC表
        for (int i = 0; i < SCList.size(); i++) {
            SC sc = SCList.get(i);
            ps3.setObject(1, sc.getSno());
            ps3.setObject(2, sc.getCno());
            ps3.setObject(3, sc.getScore());
            ps3.addBatch();;
        }
        ps3.executeBatch();
        ps3.clearBatch();
        System.out.println("SC表记录插入完成");
        ps1.close();
        ps2.close();
        ps3.close();
    }
    //删除所有表中数据
    public  void deleteData(Connection con) throws SQLException {
        PreparedStatement pr1=con.prepareStatement("delete from student");
        PreparedStatement pr2=con.prepareStatement("delete from sc");
        PreparedStatement pr3=con.prepareStatement("delete from course");
        pr1.execute();
        System.out.println("成功删除Student表中数据");
        pr2.execute();
        System.out.println("成功删除Course表中数据");
        pr3.execute();
        System.out.println("成功删除SC表中数据");
        pr1.close();
        pr2.close();
        pr3.close();
    }
}
