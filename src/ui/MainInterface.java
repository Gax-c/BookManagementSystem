package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import util.*;

public class MainInterface {
    JFrame frame = new JFrame("图书管理系统");

    final int WIDTH = 800;
    final int HEIGHT = 600;

    public void init() throws Exception {
        //设置窗口居中
        frame.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        //设置窗口大小固定
        frame.setResizable(false);

        //设置logo图标
        frame.setIconImage(ImageIO.read(new File("src/images/logo.png")));

        //设置背景图片
        BackGroundUtils BackGroundPanel = new BackGroundUtils(ImageIO.read(new File("src/images/background.jpg"))); 
        BackGroundPanel.setLayout(null);
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT); 

        JLabel title_label = new JLabel("图 书 管 理 系 统");
        title_label.setFont(new Font("微软雅黑", Font.BOLD, 30)); 
        title_label.setBounds(WIDTH / 2 - 110, HEIGHT / 5, 300, 50); 

        //创建按钮
        JButton view_button = new JButton("查看图书");
        JButton admin_button = new JButton("管理员登录");
        view_button.setBounds(WIDTH / 3 - 50, HEIGHT / 2, 100, 30);
        admin_button.setBounds(WIDTH * 2 / 3 - 50, HEIGHT / 2, 100, 30);

        //设置view按钮监听
        view_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ViewBookInterface().init();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        //设置admin按钮监听
        admin_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ManagerLoginInterface().init();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });

        BackGroundPanel.add(title_label); 
        BackGroundPanel.add(view_button);
        BackGroundPanel.add(admin_button);

        frame.add(BackGroundPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
