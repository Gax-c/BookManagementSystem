package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AddCard {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;
    
    public int addCardInfoToDatabase(String cid, String name, String unit, String type) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "INSERT INTO card VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, unit);
            preparedStatement.setString(4, type);

            preparedStatement.executeUpdate();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            return 0; 
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                throw new RuntimeException(e);
            }
        }
        return 1; 
    }
}
