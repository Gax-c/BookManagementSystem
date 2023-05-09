package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AddBookBash {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;
    
    public int addBookBashInfoToDatabase(String str) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            
            String[] attr = str.split(","); 

            //去除第一个'('
            attr[0] = attr[0].substring(1); 
            
            //去除最后一个')'
            attr[7] = attr[7].substring(0, attr[7].length() - 1); 

            String sql = "SELECT * FROM book WHERE bid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, attr[0].trim()); 
            ResultSet resultSet = preparedStatement.executeQuery();


            if (!resultSet.next()) {
                sql = "INSERT INTO book VALUES(?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, attr[0].trim());
                preparedStatement.setString(2, attr[1].trim());
                preparedStatement.setString(3, attr[2].trim());
                preparedStatement.setString(4, attr[3].trim());
                preparedStatement.setInt(5, Integer.parseInt(attr[4].trim()));
                preparedStatement.setString(6, attr[5].trim());
                preparedStatement.setDouble(7, Double.parseDouble(attr[6].trim()));
                preparedStatement.setInt(8, Integer.parseInt(attr[7].trim()));
                preparedStatement.setInt(9, Integer.parseInt(attr[7].trim()));
            }
            else {
                int preStock = resultSet.getInt("stock"); 
                int preTotal = resultSet.getInt("total"); 
                sql = "UPDATE book SET total=?, stock=? WHERE bid=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, preTotal + Integer.parseInt(attr[7].trim()));
                preparedStatement.setInt(2, preStock + Integer.parseInt(attr[7].trim()));
                preparedStatement.setString(3, attr[0].trim());
                preparedStatement.executeUpdate();
            }

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
