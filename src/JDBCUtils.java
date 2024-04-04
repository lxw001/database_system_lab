import java.sql.*;
import java.util.ArrayList;

public class JDBCUtils {
    public String URL = "jdbc:mysql://localhost:3306/";
    public String USER = "root";
    public String PASSWORD = "666666";
    public Connection con = null;

    //默认连接是对sct数据库批处理的连接
    public JDBCUtils() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(URL + "SCT?rewriteBatchedStatements=true", USER, PASSWORD);
    }

    public ArrayList<Student> queryStudent(Connection con, String sqlString) throws SQLException {
        ArrayList<Student> studentList = new ArrayList<>();
        PreparedStatement pr1 = con.prepareStatement(sqlString);
        ResultSet resultSet1 = pr1.executeQuery();
        while (resultSet1.next()) {
            String Sno = resultSet1.getNString(1);
            String Sname = resultSet1.getNString(2);
            String Ssex = resultSet1.getNString(3);
            int Sage = resultSet1.getInt(4);
            String Sclass = resultSet1.getNString(5);
            studentList.add(new Student(Sno, Sname, Ssex, Sage, Sclass));
        }
        pr1.close();
        resultSet1.close();
        con.close();
        return studentList;
    }

    public ArrayList<Course> queryCourse(Connection con, String sqlString) throws SQLException {
        ArrayList<Course> courseList = new ArrayList<>();
        PreparedStatement pr2 = con.prepareStatement(sqlString);
        ResultSet resultSet2 = pr2.executeQuery();
        while (resultSet2.next()) {
            String Cno = resultSet2.getNString(1);
            String Cname = resultSet2.getNString(2);
            float Credit = resultSet2.getFloat(3);
            int Chours = resultSet2.getInt(4);
            String Tno = resultSet2.getNString(5);
            courseList.add(new Course(Cno, Cname, Credit, Chours, Tno));
        }
        pr2.close();
        resultSet2.close();
        con.close();
        return courseList;
    }

    public ArrayList<SC> querySC(Connection con, String sqlString) throws SQLException {
        ArrayList<SC> scList = new ArrayList<>();
        PreparedStatement pr3 = con.prepareStatement(sqlString);
        ResultSet resultSet3 = pr3.executeQuery();
        while (resultSet3.next()) {
            String Sno = resultSet3.getNString(1);
            String Cno = resultSet3.getNString(2);
            float Score = resultSet3.getFloat(3);
            scList.add(new SC(Sno, Cno, Score));
        }
        pr3.close();
        resultSet3.close();
        con.close();
        return scList;
    }

    public ArrayList<String> getDBName() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            while (rs.next()) {
                String databaseName = rs.getString(1);
                //System.out.println(databaseName);
                list.add(databaseName);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getTableNames(String dbName) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW Tables");
            while (rs.next()) {
                String tableName = rs.getString(1);
                //System.out.println(tableName);
                list.add(tableName);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getColumnNames(String dbName, String tableName) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getColumns(null, null, tableName, null);
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            //System.out.println(columnName);
            list.add(columnName);
        }
        rs.close();
        connection.close();
        return list;
    }

    public ArrayList<ArrayList<Object>> load(String dbName, String tableName) throws SQLException {
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        String sqlString = "select * from " + tableName;
        Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
        PreparedStatement pr2 = connection.prepareStatement(sqlString);
        ResultSet rs = pr2.executeQuery();
        ResultSetMetaData rs_metaData = rs.getMetaData();
        ArrayList<Object> temp = new ArrayList<>();
        int count = rs_metaData.getColumnCount();
        //二维列表第0行用来存储表头（即列名）
        for (int i = 1; i <= count; i++) {
            temp.add(rs_metaData.getColumnName(i));
        }
        list.add(temp);
        while (rs.next()) {
            ArrayList<Object> temp2 = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                temp2.add(rs.getObject(i));
            }
            list.add(temp2);
        }
        rs.close();
        pr2.close();
        connection.close();
        return list;
    }
    public ArrayList<ArrayList<Object>> query(String dbName,String sqlString) throws SQLException {
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
        PreparedStatement pr2 = connection.prepareStatement(sqlString);
        ResultSet rs = pr2.executeQuery();
        ResultSetMetaData rs_metaData = rs.getMetaData();
        ArrayList<Object> temp = new ArrayList<>();
        int count = rs_metaData.getColumnCount();
        //二维列表第0行用来存储表头（即列名）
        for (int i = 1; i <= count; i++) {
            temp.add(rs_metaData.getColumnName(i));
        }
        list.add(temp);
        while (rs.next()) {
            ArrayList<Object> temp2 = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                temp2.add(rs.getObject(i));
            }
            list.add(temp2);
        }
        rs.close();
        pr2.close();
        connection.close();
        return list;
    }

    //得到对某一个特定数据库的连接
    public Connection getConnection(String dbName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
        return connection;
    }

}


