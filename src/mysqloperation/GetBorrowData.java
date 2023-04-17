package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class GetBorrowData {
    Connection connection = null;
    // 防注入
    public static PreparedStatement preparedStatement;
    public void database(Vector data, ArrayList <String> require) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "SELECT * FROM borrow";

            //处理额外的条件
            int mark = 0; 
            for (int i = 0; i < 8; ++ i) 
              if (require.get(i).length() > 0) mark ++; 
            ArrayList <String> tmp = new ArrayList<String>();
            tmp.add("cid");
            tmp.add("cname");
            tmp.add("bid");
            tmp.add("bname");
            tmp.add("borrowtime");
            tmp.add("returntime");
            tmp.add("mid");
            tmp.add("mname");
            if (mark >= 1) {
                mark = 0; 
                sql += " WHERE "; 
                for (int i = 0; i < 8; ++ i) 
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
            columnNames.add("cname");
            columnNames.add("bid");
            columnNames.add("bname");
            columnNames.add("borrowtime");
            columnNames.add("returntime");
            columnNames.add("mid");
            columnNames.add("mname");

            while (resultSet.next()) {
                Vector<String> vString = new Vector<String>();

                vString.addElement(resultSet.getString("cid"));
                vString.addElement(resultSet.getString("cname"));
                vString.addElement(resultSet.getString("bid"));
                vString.addElement(resultSet.getString("bname"));
                vString.addElement(resultSet.getString("borrowtime"));
                vString.addElement(resultSet.getString("returntime"));
                vString.addElement(resultSet.getString("mid"));
                vString.addElement(resultSet.getString("mname"));

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
