package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SearchPassword {
    public ArrayList<String> userid;
    public ArrayList<String> userpw;

    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;

    public void database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "SELECT mid,password FROM manager";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            userid = new ArrayList<>();
            userpw = new ArrayList<>();

            while (resultSet.next()) {
                userid.add(resultSet.getString(1));
                userpw.add(resultSet.getString(2));
            }
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
    }
}
