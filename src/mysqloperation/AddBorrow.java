package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AddBorrow {
    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;
    
    public int addBorrowInfoToDatabase(String cid, String bid, String borrow, String ret, String mid) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            
            //检查库存
            String sql = "SELECT * FROM book WHERE bid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bid); 
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next(); 
            int stock = resultSet.getInt("stock"); 
            if (stock <= 0) return 0; 

            //库存减一
            sql = "UPDATE book SET stock=? WHERE bid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stock - 1); 
            preparedStatement.setString(2, bid); 
            preparedStatement.executeUpdate();

            //get cname
            sql = "SELECT * from card WHERE cid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cid); 
            resultSet = preparedStatement.executeQuery();
            resultSet.next(); 
            String cname = resultSet.getString("name"); 

            //get bname
            sql = "SELECT * from book WHERE bid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bid); 
            resultSet = preparedStatement.executeQuery();
            resultSet.next(); 
            String bname = resultSet.getString("name"); 

            //get mname
            sql = "SELECT * from manager WHERE mid=?"; 
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mid); 
            resultSet = preparedStatement.executeQuery();
            resultSet.next(); 
            String mname = resultSet.getString("name"); 

            //insert 
            sql = "INSERT INTO borrow VALUES(?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cid);
            preparedStatement.setString(2,cname);
            preparedStatement.setString(3,bid);
            preparedStatement.setString(4,bname);
            preparedStatement.setString(5,borrow);
            preparedStatement.setString(6,ret);
            preparedStatement.setString(7,mid);
            preparedStatement.setString(8,mname);

            preparedStatement.executeUpdate();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误:" + e.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            return 2; 
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
