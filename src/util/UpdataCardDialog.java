package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import mysqloperation.UpdataCardData;

public class UpdataCardDialog extends JDialog {
    // 定义宽高
    final int WIDTH = 600;
    final int HEIGHT = 400;

    private JTextField idfield;
    private JTextField namefield;
    private JTextField unitfield;
    
    private JRadioButton teacher;
    private JRadioButton student;
    private ButtonGroup eGroup;

    private ActionDoneListenerUtils listener;
    
    public UpdataCardDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils listener, String cid, String name, String unit, String type) throws Exception {
        super(frame, title, isModel);
        this.listener = listener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("卡号：");
        idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //name box 
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("姓名：");
        namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield);  

        //unit box
        Box unitbox = Box.createHorizontalBox(); 
        JLabel unitlabel = new JLabel("单位：");
        unitfield = new JTextField(15);
        unitbox.add(unitlabel); 
        unitbox.add(Box.createHorizontalStrut(15));
        unitbox.add(unitfield);
        
        //type box
        Box typebox = Box.createHorizontalBox();
        JLabel typelabel = new JLabel("类别：");
        if (type.equals("S")) {
            teacher = new JRadioButton("教师", false);
            student = new JRadioButton("学生", true);
        }
        else {
            teacher = new JRadioButton("教师", true);
            student = new JRadioButton("学生", false);
        }
        eGroup = new ButtonGroup();
        eGroup.add(teacher);
        eGroup.add(student);
        typebox.add(typelabel);
        typebox.add(Box.createHorizontalStrut(20));
        typebox.add(teacher);
        typebox.add(student);
        typebox.add(Box.createHorizontalStrut(70));

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("更新");
        JButton BackBtn = new JButton("取消");

        // 将选中内容填充
        idfield.setText(cid);
        idfield.setEditable(false);
        namefield.setText(name);
        unitfield.setText(unit);

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
        tot_box.add(namebox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(unitbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(typebox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }

    public int infoInPut() {
        // 获取用户录入信息
        String cid = idfield.getText().trim();
        String name = namefield.getText().trim();
        String unit = unitfield.getText().trim();
        String type = eGroup.isSelected(teacher.getModel()) ? "T" : "S";

        // 调用更新方法
        UpdataCardData UpdataCardData = new UpdataCardData();
        int result = UpdataCardData.UpdataCardInfo(cid, name, unit, type);

        // 接口回调 刷新表格
        listener.done(null);
        return result; 
    }
}
