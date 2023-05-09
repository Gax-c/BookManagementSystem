package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class DeleteCard {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;

    public void DeleteCardInfoById(String cid) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            
            //归还相关的库存
            String sql = "SELECT * FROM borrow WHERE cid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bid = resultSet.getString("bid"); 
                sql = "SELECT * FROM book WHERE bid=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bid);
                ResultSet resultSet2 = preparedStatement.executeQuery();
                resultSet2.next(); 
                int preStock = resultSet2.getInt("stock"); 

                sql = "UPDATE book SET stock=? WHERE bid=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, preStock + 1);
                preparedStatement.setString(2, bid);
                preparedStatement.executeUpdate();
            }

            sql = "DELETE FROM card WHERE cid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cid);
            preparedStatement.executeUpdate(); 
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        } finally {
            try{
                if(connection != null){
                    connection.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
