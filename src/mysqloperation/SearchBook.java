package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class SearchBook {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;

    public Map<String, String> database(String valueAt) {
        try {
            // 获取数据库连接
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "SELECT * FROM book WHERE bid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, valueAt);

            HashMap<String, String> map = new HashMap<>();
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String bid = result.getString(1);
                String type = result.getString(2);
                String name = result.getString(3);
                String press = result.getString(4);
                String year = result.getString(5);
                String author = result.getString(6);
                String price = result.getString(7);
                String total = result.getString(8);
                String stock = result.getString(9);
                map.put("bid", bid);
                map.put("type", type);
                map.put("name", name);
                map.put("press", press);
                map.put("year", year);
                map.put("author", author);
                map.put("price", price);
                map.put("total", total);
                map.put("stock", stock);
            }
            return map;

        } catch (Exception e) {
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
        return null;
    }
}
