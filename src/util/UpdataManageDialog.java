package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mysqloperation.UpdataManageData;

public class UpdataManageDialog extends JDialog {
    // 定义宽高
    final int WIDTH = 600;
    final int HEIGHT = 400;

    private JTextField idfield;
    private JTextField pwfield;
    private JTextField namefield;
    private JTextField phonefield;

    private ActionDoneListenerUtils listener;
    
    public UpdataManageDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils listener, String mid, String password, String name, String phone) throws Exception {
        super(frame, title, isModel);
        this.listener = listener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("管理员ID：");
        idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //密码box 
        Box pwbox = Box.createHorizontalBox(); 
        JLabel pwlabel = new JLabel("密码：");
        pwfield = new JTextField(15);
        pwbox.add(pwlabel); 
        pwbox.add(Box.createHorizontalStrut(15));
        pwbox.add(pwfield); 

        //姓名box
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("姓名：");
        namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield);
        
        //联系方式box
        Box phonebox = Box.createHorizontalBox(); 
        JLabel phonelabel = new JLabel("联系方式：");
        phonefield = new JTextField(15);
        phonebox.add(phonelabel); 
        phonebox.add(Box.createHorizontalStrut(15));
        phonebox.add(phonefield);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("更新");
        JButton BackBtn = new JButton("取消");

        // 将选中内容填充
        idfield.setText(mid);
        idfield.setEditable(false);
        pwfield.setText(password);
        namefield.setText(name);
        phonefield.setText(phone);

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = infoInPut();
                if (result == 1) JOptionPane.showMessageDialog(frame, "更新成功");
                dispose();
            }
        });

        BackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        bBox.add(AddBtn);
        bBox.add(Box.createHorizontalStrut(120));
        bBox.add(BackBtn);

        //整合整个界面
        tot_box.add(Box.createVerticalStrut(40));
        tot_box.add(idbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(pwbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(namebox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(phonebox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }

    public int infoInPut() {
        // 获取用户录入信息
        String mid = idfield.getText().trim();
        String password = pwfield.getText().trim();
        String name = namefield.getText().trim();
        String phone = phonefield.getText().trim();

        // 调用更新方法
        UpdataManageData UpdataManageData = new UpdataManageData();
        int result = UpdataManageData.updataManageInfo(mid, password, name, phone);

        // 接口回调 刷新表格
        listener.done(null);
        return result; 
    }
}
