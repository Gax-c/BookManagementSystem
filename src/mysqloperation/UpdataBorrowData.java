package mysqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class UpdataBorrowData {
    public int result;

    // 数据库连接变量 （联系）
    public static Connection connection;

    // 防注入（预处理语句；编报表）
    public static PreparedStatement preparedStatement;

    public int updataBorrowInfo(String cid, String cname, String bid, String bname, String borrow, String ret, String mid, String mname, String ret_) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?characterEncoding=GBK", "root", "123456");
            String sql = "UPDATE borrow SET returntime=? WHERE cid=? and cname=? and bid=? and bname=? and borrowtime=? and returntime=? and mid=? and mname=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ret_);
            preparedStatement.setString(2, cid);
            preparedStatement.setString(3, cname);
            preparedStatement.setString(4, bid);
            preparedStatement.setString(5, bname);
            preparedStatement.setString(6, borrow);
            preparedStatement.setString(7, ret);
            preparedStatement.setString(8, mid);
            preparedStatement.setString(9, mname);
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
