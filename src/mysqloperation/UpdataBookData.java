package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class UpdataBookData {
    public int result;

    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;

    public int updataBookInfo(String bid, String type, String name, String press, String year, String author, String price, String total, String stock) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "UPDATE book SET bid=?, type=?, name=?, press=?, year=?, author=?, price=?, total=?, stock=? WHERE bid=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bid);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, press);
            preparedStatement.setInt(5, Integer.parseInt(year));
            preparedStatement.setString(6, author);
            preparedStatement.setDouble(7, Double.parseDouble(price));
            preparedStatement.setInt(8, Integer.parseInt(total));
            preparedStatement.setInt(9, Integer.parseInt(stock));
            preparedStatement.setString(10, bid);
            result = preparedStatement.executeUpdate();
        } catch (Exception e) { 
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
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                throw new RuntimeException(e);
            }
        }
        return 1; 
    }
}
