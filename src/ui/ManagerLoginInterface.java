package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import util.*;

public class ManagerLoginInterface {
    JFrame frame = new JFrame("管理员登录");

    final int WIDTH = 800;
    final int HEIGHT = 600;
    
    public void init() throws Exception {
        //设置窗口居中
        frame.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        //设置窗口大小固定
        frame.setResizable(false);

        //设置logo图标
        frame.setIconImage(ImageIO.read(new File("src/images/logo.png")));

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setLayout(null);
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT); 

        JLabel title_label = new JLabel("管 理 员 登 录");
        title_label.setFont(new Font("微软雅黑", Font.BOLD, 30)); 
        title_label.setBounds(WIDTH / 2 - 100, HEIGHT / 5 - 20, 300, 50); 

        //用户名输入
        JLabel uLabel = new JLabel("用户名：");
        JTextField uField = new JTextField(15);
        uLabel.setBounds(WIDTH / 2 - 150, HEIGHT / 3, 50, 50);
        uField.setBounds(WIDTH / 2 - 100, HEIGHT / 3 + 10, 200, 30);

        //密码输入
        JLabel pLabel = new JLabel("密    码：");
        JPasswordField pField = new JPasswordField(15);
        pLabel.setBounds(WIDTH / 2 - 150, HEIGHT / 2 - 40, 50, 50);
        pField.setBounds(WIDTH / 2 - 100, HEIGHT / 2 - 30, 200, 30);

        //返回主界面按钮
        JButton back_botton = new JButton("返回主界面");
        back_botton.setBounds(50, 30, 100, 30);
        back_botton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainInterface().init(); 
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        //登录按钮
        JButton login_botton = new JButton("登录");
        login_botton.setBounds(WIDTH / 2 - 60, HEIGHT * 2 / 3 - 70, 100, 30);
        login_botton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入的内容
                String username = uField.getText().trim();
                String password = new String(pField.getPassword()).trim();

                // 初始化 Login
                LoginUtils login = new LoginUtils();
                login.loginVer(username);

                if (username.equals("admin") && password.equals("123456")) {
                    JOptionPane.showMessageDialog(frame, "root登录成功！");
                    try {
                        new ManagerInterface().init(); 
                    }catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    frame.dispose();
                }
                else if (login.contains == true && password.equals(login.password)) {
                    JOptionPane.showMessageDialog(frame, "管理员" + username + "登录成功！");
                    try {
                        new ManagerInterface().init();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    frame.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "用户名或密码错误！");
                }
            }
        });

        //添加元素
        BackGroundPanel.add(title_label);
        BackGroundPanel.add(uLabel); 
        BackGroundPanel.add(uField); 
        BackGroundPanel.add(pLabel); 
        BackGroundPanel.add(pField); 
        BackGroundPanel.add(back_botton); 
        BackGroundPanel.add(login_botton); 

        frame.add(BackGroundPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
