package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class GetBookData {
    Connection connection = null;
    // 防注入
    public static PreparedStatement preparedStatement;
    public void database(Vector data, ArrayList <String> require, int[] SortPara) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "SELECT * FROM book";

            //处理额外的条件
            int mark = 0; 
            for (int i = 0; i < 9; ++ i) 
              if (require.get(i).length() > 0) mark ++; 
            ArrayList <String> tmp = new ArrayList<String>();
            tmp.add("bid");
            tmp.add("type");
            tmp.add("name");
            tmp.add("press");
            tmp.add("year1");
            tmp.add("year2");
            tmp.add("author");
            tmp.add("price1");
            tmp.add("price2");
            if (mark >= 1) {
                mark = 0; 
                sql += " WHERE "; 
                for (int i = 0; i < 9; ++ i) 
                  if (require.get(i).length() > 0) {
                    if (mark == 0) {
                        mark = 1; 
                        if (tmp.get(i) == "year1") sql += "year" + ">=" + require.get(i); 
                        else if (tmp.get(i) == "year2") sql += "year" + "<=" + require.get(i); 
                        else if (tmp.get(i) == "price1") sql += "price" + ">=" + require.get(i); 
                        else if (tmp.get(i) == "price2") sql += "price" + "<=" + require.get(i); 
                        else sql += tmp.get(i) + "='" + require.get(i) + "'"; 
                    }
                    else {
                        sql += " AND "; 
                        if (tmp.get(i) == "year1") sql += "year" + ">=" + require.get(i); 
                        else if (tmp.get(i) == "year2") sql += "year" + "<=" + require.get(i); 
                        else if (tmp.get(i) == "price1") sql += "price" + ">=" + require.get(i); 
                        else if (tmp.get(i) == "price2") sql += "price" + "<=" + require.get(i); 
                        else sql += tmp.get(i) + "='" + require.get(i) + "'"; 
                    }
                  }
            }

            sql += " ORDER BY "; 
            if (SortPara[0] == 0) sql += "bid"; 
            else if (SortPara[0] == 1) sql += "type"; 
            else if (SortPara[0] == 2) sql += "name";
            else if (SortPara[0] == 3) sql += "press"; 
            else if (SortPara[0] == 4) sql += "year";
            else if (SortPara[0] == 5) sql += "author";   
            else if (SortPara[0] == 6) sql += "price";
            else if (SortPara[0] == 7) sql += "total";
            else if (SortPara[0] == 8) sql += "stock";

            if (SortPara[1] == 0) sql += " ASC"; 
            else if (SortPara[1] == 1) sql += " DESC"; 

            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            Vector<String> columnNames = new Vector<String>();
            columnNames.add("bid");
            columnNames.add("type");
            columnNames.add("name");
            columnNames.add("press");
            columnNames.add("year");
            columnNames.add("author");
            columnNames.add("price");
            columnNames.add("total");
            columnNames.add("stock");

            while (resultSet.next()) {
                Vector<String> vString = new Vector<String>();

                vString.addElement(resultSet.getString("bid"));
                vString.addElement(resultSet.getString("type"));
                vString.addElement(resultSet.getString("name"));
                vString.addElement(resultSet.getString("press"));
                vString.addElement(resultSet.getString("year"));
                vString.addElement(resultSet.getString("author"));
                vString.addElement(resultSet.getString("price"));
                vString.addElement(resultSet.getString("total"));
                vString.addElement(resultSet.getString("stock"));

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
