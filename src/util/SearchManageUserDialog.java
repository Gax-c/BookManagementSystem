package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchManageUserDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 450;
    private ActionDoneListenerUtils listener;

    public SearchManageUserDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
        super(frame, title, isModel);
        this.listener = actionDoneListener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("管理员ID：");
        JTextField idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //密码box 
        Box pwbox = Box.createHorizontalBox(); 
        JLabel pwlabel = new JLabel("密码：");
        JTextField pwfield = new JTextField(15);
        pwbox.add(pwlabel); 
        pwbox.add(Box.createHorizontalStrut(15));
        pwbox.add(pwfield); 

        //姓名box
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("姓名：");
        JTextField namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield);
        
        //联系方式box
        Box phonebox = Box.createHorizontalBox(); 
        JLabel phonelabel = new JLabel("联系方式：");
        JTextField phonefield = new JTextField(15);
        phonebox.add(phonelabel); 
        phonebox.add(Box.createHorizontalStrut(15));
        phonebox.add(phonefield);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("确定");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ManagerId = idfield.getText().trim();
                String ManagePw = pwfield.getText().trim(); 
                String ManageName = namefield.getText().trim(); 
                String ManagePhone = phonefield.getText().trim(); 
                
                //接口回调 刷新表格
                ArrayList<String> require = new ArrayList<String>(); 
                require.add(ManagerId); 
                require.add(ManagePw); 
                require.add(ManageName); 
                require.add(ManagePhone); 
                actionDoneListener.done(require);
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
}
