package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DeleteBorrow {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;
    
    public int DeleteBorrowInfo(String cid, String bid) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");

            //检查借书记录是否存在
            String sql = "SELECT * FROM borrow WHERE cid=? and bid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cid); 
            preparedStatement.setString(2, bid); 
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() == false) return 0; 

            //delete
            sql = "DELETE FROM borrow WHERE cid=? and bid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cid);
            preparedStatement.setString(2,bid);

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
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                throw new RuntimeException(e);
            }
        }
        return 1;
    }
}
