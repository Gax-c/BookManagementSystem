package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class SearchManage {
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
            String sql = "SELECT * FROM manager WHERE mid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, valueAt);

            HashMap<String, String> map = new HashMap<>();
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String mid = result.getString(1);
                String password = result.getString(2);
                String name = result.getString(3);
                String phone = result.getString(4);
                map.put("mid", mid);
                map.put("password", password);
                map.put("name", name);
                map.put("phone", phone);
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
