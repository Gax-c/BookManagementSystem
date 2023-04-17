package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import util.RenderUtils;
import util.ScreenUtils;
import util.ViewBookManageUtils;

public class ViewBookInterface {
    JFrame frame = new JFrame("图书查看");

    final int WIDTH = 1200;
    final int HEIGHT = 800;
    
    public void init() throws Exception {
        //设置窗口居中
        frame.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        //设置窗口大小固定
        frame.setResizable(false);

        //设置logo图标
        frame.setIconImage(ImageIO.read(new File("src/images/logo.png")));

        // 设置菜单栏
        JMenuBar MenuBar = new JMenuBar();
        JMenu menu = new JMenu("操作");
        JMenuItem cancellationItem = new JMenuItem("注销");
        JMenuItem exitItem = new JMenuItem("退出");

        //注销响应
        cancellationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "点击“确定”按钮，将退出此账户并返回到登录界面!\n确定继续吗?", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        new MainInterface().init();
                        frame.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "错误:" + ex.getMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        //退出响应
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(cancellationItem);
        menu.add(exitItem);
        MenuBar.add(menu); 
        frame.setJMenuBar(MenuBar);

        // 设置分割面板
        JSplitPane SplitPane = new JSplitPane();

        // 支持连续布局
        SplitPane.setContinuousLayout(true);

        // 初始显示位置
        SplitPane.setDividerLocation(200);

        // 设置分割条宽度
        SplitPane.setDividerSize(7);
        
        // 设置左侧内容
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("系统管理");
        DefaultMutableTreeNode BookManage = new DefaultMutableTreeNode("图书查看");
        
        root.add(BookManage); 

        Color color = new Color(203, 220, 217);
        JTree tree = new JTree(root);
        RenderUtils Render = new RenderUtils();
        Render.setBackgroundNonSelectionColor(color);
        Render.setBackgroundSelectionColor(new Color(140, 140, 140));
        tree.setCellRenderer(Render);
        tree.setBackground(color);
        SplitPane.setLeftComponent(tree);

        //相应树组件事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                SplitPane.setRightComponent(new ViewBookManageUtils(frame));
                SplitPane.setDividerLocation(200);
            }
        });
        
        tree.setSelectionRow(1);

        frame.add(SplitPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
