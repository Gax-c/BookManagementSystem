package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class GetCardData {
    Connection connection = null;
    // 防注入
    public static PreparedStatement preparedStatement;
    public void database(Vector data, ArrayList <String> require) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "SELECT * FROM card";

            //处理额外的条件
            int mark = 0; 
            for (int i = 0; i < 4; ++ i) 
              if (require.get(i).length() > 0) mark ++; 
            ArrayList <String> tmp = new ArrayList<String>();
            tmp.add("cid");
            tmp.add("name");
            tmp.add("unit");
            tmp.add("type");
            if (mark >= 1) {
                mark = 0; 
                sql += " WHERE "; 
                for (int i = 0; i < 4; ++ i) 
                  if (require.get(i).length() > 0) {
                    if (mark == 0) {
                        mark = 1; 
                        sql += tmp.get(i) + "='" + require.get(i) + "'"; 
                    }
                    else {
                        sql += " AND "; 
                        sql += tmp.get(i) + "='" + require.get(i) + "'"; 
                    }
                  }
            }
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("cid");
            columnNames.add("name");
            columnNames.add("unit");
            columnNames.add("type");

            while (resultSet.next()) {
                Vector<String> vString = new Vector<String>();

                vString.addElement(resultSet.getString("cid"));
                vString.addElement(resultSet.getString("name"));
                vString.addElement(resultSet.getString("unit"));
                if (resultSet.getString("type").equals("S")) vString.addElement("学生");
                else vString.addElement("教师");

                data.add(vString);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if(connection != null){
                    connection.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
