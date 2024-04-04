import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class GUI extends JFrame {
    public void init() throws SQLException, ClassNotFoundException {
        //定义窗口,水平布局分为左右两个垂直分布的box,分别为boxLeft和boxRight
        JFrame jframe = new JFrame("My Database");
        jframe.setBounds(0, 0, 1000, 600);//设置窗口宽度和高度
        Box boxLeft = Box.createVerticalBox();
        Box boxRight = Box.createVerticalBox();

        /*
         *  下面制作出窗口左边的box，boxLeft
         */
        //先制作选择数据库组件
        Box selectDB = Box.createHorizontalBox();
        JTextField selectDBText = new JTextField("请选择数据库");
        selectDBText.setBackground(Color.green);
        selectDB.add(selectDBText);
        //添加下拉框
        JComboBox<String> dbNameComboBox = new JComboBox<>();
        JDBCUtils jdbcUtils = new JDBCUtils();
        ArrayList<String> list = jdbcUtils.getDBName();
        for (String s : list) {
            dbNameComboBox.addItem(s);
        }
        dbNameComboBox.setSelectedIndex(-1);
        selectDB.add(dbNameComboBox);

        /*
            下面制作boxLeft的左边3个表(对应3个boxList1,leftList2,leftList3)
         */
        Box leftList1 = Box.createHorizontalBox();
        Box leftList2 = Box.createHorizontalBox();
        Box leftList3 = Box.createHorizontalBox();
        //表一
        JTextField leftList1Text = new JTextField("表1");
        leftList1Text.setBackground(Color.green);
        JComboBox<String> tableNameCombox1 = new JComboBox<>();
        JButton load1 = new JButton("装载");
        leftList1.add(leftList1Text);
        leftList1.add(tableNameCombox1);
        leftList1.add(load1);
        Box leftList1Area = Box.createHorizontalBox();
        JTable leftTableContent1 = new JTable();
        JScrollPane leftJScrollPane1 = new JScrollPane(leftTableContent1);
        leftList1Area.add(leftJScrollPane1);
        //第一个装载button事件监听
        load1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //每次点击后需要先清空上一个装载的表的内容
                DefaultTableModel model = new DefaultTableModel();
                leftTableContent1.setModel(model);
                ArrayList<ArrayList<Object>> list;
                try {
                    JDBCUtils jdbcUtils1 = new JDBCUtils();
                    list = jdbcUtils1.load((String) dbNameComboBox.getSelectedItem(), (String) tableNameCombox1.getSelectedItem());
                    jdbcUtils1.con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                int columns = list.get(0).size();
                //Jtable要用addColumn加表头，再用addRow加数据
                for (int i = 0; i < columns; i++) {
                    model.addColumn(list.get(0).get(i));
                }
                Object[][] lines = new Object[list.size() - 1][columns];
                for (int i = 0; i < list.size() - 1; i++) {
                    for (int j = 0; j < columns; j++) {
                        lines[i][j] = list.get(i + 1).get(j);
                    }
                    model.addRow(lines[i]);
                }
            }
        });
        //表2
        JTextField leftlist2Text = new JTextField("表2");
        leftlist2Text.setBackground(Color.green);
        JComboBox<String> tableNameCombox2 = new JComboBox<>();
        JButton load2 = new JButton("装载");
        leftList2.add(leftlist2Text);
        leftList2.add(tableNameCombox2);
        leftList2.add(load2);
        Box leftList2Area = Box.createHorizontalBox();
        JTable leftTableContent2 = new JTable();
        JScrollPane leftJScrollPane2 = new JScrollPane(leftTableContent2);
        leftList2Area.add(leftJScrollPane2);
        //第二个装载button事件监听
        load2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                leftTableContent2.setModel(model);
                ArrayList<ArrayList<Object>> list;
                try {
                    JDBCUtils jdbcUtils1 = new JDBCUtils();
                    list = jdbcUtils1.load((String) dbNameComboBox.getSelectedItem(), (String) tableNameCombox2.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                int columns = list.get(0).size();
                //Jtable要用addColumn加表头，再用addRow加数据
                for (int i = 0; i < columns; i++) {
                    model.addColumn(list.get(0).get(i));
                }
                Object[][] lines = new Object[list.size() - 1][columns];
                for (int i = 0; i < list.size() - 1; i++) {
                    for (int j = 0; j < columns; j++) {
                        lines[i][j] = list.get(i + 1).get(j);
                    }
                    model.addRow(lines[i]);
                }
            }
        });
        //表3
        JTextField list3Text1 = new JTextField("表3");
        list3Text1.setBackground(Color.green);
        JComboBox<String> tableNameCombox3 = new JComboBox<>();
        JButton load3 = new JButton("装载");
        leftList3.add(list3Text1);
        leftList3.add(tableNameCombox3);
        leftList3.add(load3);
        Box leftList3Area = Box.createHorizontalBox();
        JTable leftTableContent3 = new JTable();
        JScrollPane leftJScrollPane3 = new JScrollPane(leftTableContent3);
        leftList3Area.add(leftJScrollPane3);
        //第三个装载button事件监听
        load3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                leftTableContent3.setModel(model);
                ArrayList<ArrayList<Object>> list;
                try {
                    JDBCUtils jdbcUtils1 = new JDBCUtils();
                    list = jdbcUtils1.load((String) dbNameComboBox.getSelectedItem(), (String) tableNameCombox3.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                int columns = list.get(0).size();
                //Jtable要用addColumn加表头，再用addRow加数据
                for (int i = 0; i < columns; i++) {
                    model.addColumn(list.get(0).get(i));
                }
                Object[][] lines = new Object[list.size() - 1][columns];
                for (int i = 0; i < list.size() - 1; i++) {
                    for (int j = 0; j < columns; j++) {
                        lines[i][j] = list.get(i + 1).get(j);
                    }
                    model.addRow(lines[i]);
                }
            }
        });
        //选择数据库和三个表一起加入左边的大盒子
        boxLeft.add(selectDB);
        boxLeft.add(leftList1);
        boxLeft.add(leftList1Area);
        boxLeft.add(leftList2);
        boxLeft.add(leftList2Area);
        boxLeft.add(leftList3);
        boxLeft.add(leftList3Area);

//--------------------------------------------------------------------------------
        //然后制作水平方向上右边的box，boxRight，boxRight为垂直布局

        //5个子box都为垂直布局
        Box rightList1 = Box.createVerticalBox();
        Box rightList2 = Box.createVerticalBox();
        Box rightList3 = Box.createVerticalBox();
        Box makeSqlArea = Box.createVerticalBox();
        Box executeSqlArea = Box.createVerticalBox();
        /*
            3个list
        */
        Box tempBox11 = Box.createHorizontalBox();
        Box tempBox12 = Box.createVerticalBox();
        JTextField rightText1 = new JTextField("请选择待查询的表1");
        rightText1.setBackground(Color.green);
        tempBox11.add(rightText1);
        JComboBox<String> tableNameCombox4 = new JComboBox<>();
        tempBox11.add(tableNameCombox4);
        rightList1.add(tempBox11);
        JScrollPane rightScrollPane1 = new JScrollPane(tempBox12);
        rightList1.add(rightScrollPane1);

        Box tempBox21 = Box.createHorizontalBox();
        Box tempBox22 = Box.createVerticalBox();
        JTextField rightText2 = new JTextField("请选择待查询的表2");
        rightText2.setBackground(Color.green);
        tempBox21.add(rightText2);
        JComboBox<String> tableNameCombox5 = new JComboBox<>();
        tempBox21.add(tableNameCombox5);
        rightList2.add(tempBox21);
        JScrollPane rightScrollPane2 = new JScrollPane(tempBox22);
        rightList2.add(rightScrollPane2);

        Box tempBox31 = Box.createHorizontalBox();
        Box tempBox32 = Box.createVerticalBox();
        JTextField rightText3 = new JTextField("请选择待查询的表3");
        rightText3.setBackground(Color.green);
        tempBox31.add(rightText3);
        JComboBox<String> tableNameCombox6 = new JComboBox<>();
        tempBox31.add(tableNameCombox6);
        rightList3.add(tempBox31);
        JScrollPane rightScrollPane3 = new JScrollPane(tempBox32);
        rightList3.add(rightScrollPane3);
        /*
         *      makeSqlArea
         */
        JButton makeSQL = new JButton("构造SQL语句");
        makeSqlArea.add(makeSQL);
        TextArea sqlText = new TextArea("");
        sqlText.setBackground(Color.ORANGE);
        makeSqlArea.add(sqlText);

        JButton executeQuery = new JButton("执行SQL语句");
        makeSqlArea.add(executeQuery);
        TextArea runInfo = new TextArea("运行说明：");
        runInfo.setBackground(Color.ORANGE);
        makeSqlArea.add(runInfo);
        //给构造SQL语句的button加监听事件
        makeSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sqlText.setText("");
                runInfo.setText("运行说明");
                //一个tempbox装了多个小box（数量为表中的列名），每个小box两个组件（一个复选框，另一个是后面紧跟的文本框，用来得到用户的输入）
                //首先得到3个小boxlist
                Component[] boxesList1 = tempBox12.getComponents();
                Component[] boxesList2 = tempBox22.getComponents();
                Component[] boxesList3 = tempBox32.getComponents();
                ArrayList<String> tableNameList = new ArrayList<>();
                ArrayList<String> columnNameList = new ArrayList<>();//存储所有的表名+列名字段
                ArrayList<String> selectedColumnNameList = new ArrayList<>();//存储被选中的表名+列名
                ArrayList<String> conditionList = new ArrayList<>();
                StringBuilder str0 = new StringBuilder("");
                StringBuilder str1 = new StringBuilder("");
                StringBuilder str2 = new StringBuilder("");
                //循环遍历每个小box，得到里面的jcheckbox组件和用户输入的文本
                //添加下拉框选中的表名
                tableNameList.add((String) tableNameCombox4.getSelectedItem());
                tableNameList.add((String) tableNameCombox5.getSelectedItem());
                tableNameList.add((String) tableNameCombox6.getSelectedItem());
                for (int i = 0; i < boxesList1.length; i++) {
                    Component[] boxes1 = ((Box) boxesList1[i]).getComponents();
                    JCheckBox checkBox = (JCheckBox) (boxes1[0]);
                    JTextField text = (JTextField) (boxes1[1]);
                    String columnName = checkBox.getText();
                    String condition = text.getText();
                    //添加表名+列名字段，用来为唯一标识条件
                    if (columnName.contains("#")) {
                        columnNameList.add("t1.`" + columnName + "`");
                    } else columnNameList.add("t1." + columnName);
                    conditionList.add(condition);
                    //当被选中时，将字段名加入到selectedColumnNameList中
                    if (checkBox.isSelected()) {
                        if (columnName.contains("#")) {
                            selectedColumnNameList.add("t1.`" + columnName + "`");
                        } else selectedColumnNameList.add("t1." + columnName);
                    }

                }

                for (int i = 0; i < boxesList2.length; i++) {
                    Component[] boxes2 = ((Box) boxesList2[i]).getComponents();
                    JCheckBox checkBox = (JCheckBox) (boxes2[0]);
                    JTextField text = (JTextField) (boxes2[1]);
                    String columnName = checkBox.getText();
                    String condition = text.getText();
                    if (columnName.contains("#")) {
                        columnNameList.add("t2.`" + columnName + "`");
                    } else columnNameList.add("t2." + columnName);
                    conditionList.add(condition);
                    //当被选中时，将字段名加入到selectedColumnNameList中
                    if (checkBox.isSelected()) {
                        if (columnName.contains("#")) {
                            selectedColumnNameList.add("t2.`" + columnName + "`");
                        } else selectedColumnNameList.add("t2." + columnName);
                    }
                }

                for (int i = 0; i < boxesList3.length; i++) {
                    Component[] boxes3 = ((Box) boxesList3[i]).getComponents();
                    JCheckBox checkBox = (JCheckBox) (boxes3[0]);
                    JTextField text = (JTextField) (boxes3[1]);
                    String columnName = checkBox.getText();
                    String condition = text.getText();
                    if (columnName.contains("#")) {
                        columnNameList.add("t3.`" + columnName + "`");
                    } else columnNameList.add("t3." + columnName);
                    conditionList.add(condition);
                    //当被选中时，将字段名加入到selectedColumnNameList中
                    if (checkBox.isSelected()) {
                        if (columnName.contains("#")) {
                            selectedColumnNameList.add("t3.`" + columnName + "`");
                        } else selectedColumnNameList.add("t3." + columnName);
                    }
                }

                for (int i = 0; i < tableNameList.size(); i++) {
                    if(tableNameList.get(i)==null) continue;
                    str1.append(tableNameList.get(i) + " as t" + (i + 1));
                    if (i + 1 < tableNameList.size()) str1.append(",");
                }
                if(str1.toString().endsWith(",")) str1.delete(str1.length()-1,str1.length());

                for (int i = 0; i < selectedColumnNameList.size(); i++) {
                    str0.append(selectedColumnNameList.get(i));
                    if (i + 1 < selectedColumnNameList.size()) str0.append(",");
                }

                for (int i = 0; i < columnNameList.size(); i++) {
                    //如果这一行条件为空或者为初始条件，则不加入条件，直接跳至下一个列
                    if (conditionList.get(i).toString().equals("") || conditionList.get(i).toString().equals("请输入条件"))
                        continue;

                    str2.append(columnNameList.get(i) + " like '" + conditionList.get(i) + "'");
                    if (i + 1 < columnNameList.size()) {
                        str2.append(" and ");
                    }
                }
                if (str2.toString().endsWith("and ")) str2.delete(str2.length() - 4, str2.length());
                if (str0.toString().equals("")) {
                    //一个都不勾默认select *
                    if (str2.toString().equals("")) {
                        String SQL = "select * " + str0 + "\n" + "from " + str1 + ";";
                        sqlText.setText(SQL);
                    } else {
                        String SQL = "select * " + str0 + "\n" + "from " + str1 + "\n" + "where " + str2 + ";";
                        sqlText.setText(SQL);
                    }
                } else {
                    if (str2.toString().equals("")) {
                        String SQL = "select  " + str0 + "\n" + "from " + str1 + ";";
                        sqlText.setText(SQL);
                    } else {
                        String SQL = "select  " + str0 + "\n" + "from " + str1 + "\n" + "where " + str2 + ";";
                        sqlText.setText(SQL);
                    }
                }
            }
        });
        /*
         *       executeSalArea
         */
        JTextField queryResultText = new JTextField("检索结果");
        queryResultText.setBackground(Color.green);
        executeSqlArea.add(queryResultText);
        JTable queryResult = new JTable();
        JScrollPane rightJScrollPane = new JScrollPane(queryResult);
        executeSqlArea.add(rightJScrollPane);
        /*
         *      把上面5个小box放入boxright。。。由于右边放不下这么多，把executeSqlArea放入左边的box了
         */
        boxRight.add(rightList1);
        boxRight.add(rightList2);
        boxRight.add(rightList3);
        boxRight.add(makeSqlArea);
        boxLeft.add(executeSqlArea);

        /*
         *      下面是一些组件的监听器
         */
        executeQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                queryResult.setModel(model);
                ArrayList<ArrayList<Object>> list;
                try {
                    JDBCUtils jdbcUtils1 = new JDBCUtils();
                    list = jdbcUtils1.query((String) dbNameComboBox.getSelectedItem(), sqlText.getText());
                    jdbcUtils1.con.close();
                } catch (SQLException ex) {
                    runInfo.setText(ex.getMessage());
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                int columns = list.get(0).size();
                //Jtable要用addColumn加表头，再用addRow加数据
                for (int i = 0; i < columns; i++) {
                    model.addColumn(list.get(0).get(i));
                }
                Object[][] lines = new Object[list.size() - 1][columns];
                for (int i = 0; i < list.size() - 1; i++) {
                    for (int j = 0; j < columns; j++) {
                        lines[i][j] = list.get(i + 1).get(j);
                    }
                    model.addRow(lines[i]);
                }
                runInfo.setText("查询成功");
            }
        });
        //为flase用来屏蔽其他的事件监听器
        JTextField flag = new JTextField();
        flag.setText("flase");
        dbNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag.setText("flase");
                //每次选择完数据库后先清空左边3个表里的内容
                leftTableContent1.setModel(new DefaultTableModel());
                leftTableContent2.setModel(new DefaultTableModel());
                leftTableContent3.setModel(new DefaultTableModel());
                String selectedDbName = (String) dbNameComboBox.getSelectedItem(); // 获取用户选择的数据库名称
                JDBCUtils jdbcUtils1;//对某一个数据库的连接
                try {
                    jdbcUtils1 = new JDBCUtils();
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<String> tableNames; // 根据数据库名称获取表名列表
                try {
                    tableNames = jdbcUtils1.getTableNames(selectedDbName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                tableNameCombox1.removeAllItems();
                tableNameCombox2.removeAllItems();
                tableNameCombox3.removeAllItems();
                tableNameCombox4.removeAllItems();
                tableNameCombox5.removeAllItems();
                tableNameCombox6.removeAllItems();

                tempBox12.removeAll();
                tempBox22.removeAll();
                tempBox32.removeAll();
                //将该数据库里所有表名添加到下拉框里
                for (String s : tableNames) {
                    tableNameCombox1.addItem(s);
                    tableNameCombox2.addItem(s);
                    tableNameCombox3.addItem(s);
                    tableNameCombox4.addItem(s);
                    tableNameCombox5.addItem(s);
                    tableNameCombox6.addItem(s);
                }
                tableNameCombox1.setSelectedIndex(-1);
                tableNameCombox2.setSelectedIndex(-1);
                tableNameCombox3.setSelectedIndex(-1);
                tableNameCombox4.setSelectedIndex(-1);
                tableNameCombox5.setSelectedIndex(-1);
                tableNameCombox6.setSelectedIndex(-1);
                flag.setText("true");
            }
        });

        tableNameCombox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag.getText().equals("true")) {
                    tempBox12.removeAll();
                    ArrayList<String> columnNameList4; // 根据数据库名和表名获得其中的列名
                    try {
                        JDBCUtils jdbcUtils2 = new JDBCUtils();
                        columnNameList4 = jdbcUtils2.getColumnNames((String) dbNameComboBox.getSelectedItem(),
                                (String) tableNameCombox4.getSelectedItem());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    for (String s : columnNameList4) {
                        Box box = Box.createHorizontalBox();
                        JCheckBox jCheckBox = new JCheckBox(s);
                        JTextField jTextField = new JTextField("请输入条件");
                        jTextField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                // 当文本框获得焦点时，清空文本框
                                jTextField.setText("");
                            }
                        });
                        box.add(jCheckBox);
                        box.add(jTextField);
                        tempBox12.add(box);
                    }
                    boxRight.revalidate();
                    boxRight.repaint();
                }
            }
        });

        tableNameCombox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag.getText().equals("true")) {
                    tempBox22.removeAll();
                    ArrayList<String> columnNameList5; // 根据数据库名和表名获得其中的列名
                    try {
                        JDBCUtils jdbcUtils2 = new JDBCUtils();
                        columnNameList5 = jdbcUtils2.getColumnNames((String) dbNameComboBox.getSelectedItem(),
                                (String) tableNameCombox5.getSelectedItem());
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    for (String s : columnNameList5) {
                        Box box = Box.createHorizontalBox();
                        JCheckBox jCheckBox = new JCheckBox(s);
                        JTextField jTextField = new JTextField("请输入条件");
                        jTextField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                // 当文本框获得焦点时，清空文本框
                                jTextField.setText("");
                            }
                        });
                        box.add(jCheckBox);
                        box.add(jTextField);
                        tempBox22.add(box);
                    }
                    boxRight.revalidate();
                    boxRight.repaint();
                }
            }
        });

        tableNameCombox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag.getText().equals("true")) {
                    tempBox32.removeAll();
                    ArrayList<String> columnNameList4; // 根据数据库名和表名获得其中的列名
                    try {
                        JDBCUtils jdbcUtils2 = new JDBCUtils();
                        columnNameList4 = jdbcUtils2.getColumnNames((String) dbNameComboBox.getSelectedItem(),
                                (String) tableNameCombox6.getSelectedItem());
                    } catch (SQLException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    for (String s : columnNameList4) {
                        Box box = Box.createHorizontalBox();
                        JCheckBox jCheckBox = new JCheckBox(s);
                        JTextField jTextField = new JTextField("请输入条件");
                        jTextField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                // 当文本框获得焦点时，清空文本框
                                jTextField.setText("");
                            }
                        });
                        box.add(jCheckBox);
                        box.add(jTextField);
                        tempBox32.add(box);
                    }
                    boxRight.revalidate();
                    boxRight.repaint();
                }
            }
        });

        Box box = Box.createHorizontalBox();
        box.add(boxLeft);
        box.add(boxRight);
        jframe.add(box);
        //设置窗口可见
        jframe.setLocationRelativeTo(null);//窗口居中
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
