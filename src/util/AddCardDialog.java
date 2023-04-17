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

import mysqloperation.AddCard;

public class AddCardDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 450;
    private ActionDoneListenerUtils listener;

    public AddCardDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
        super(frame, title, isModel);
        this.listener = actionDoneListener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("卡号：");
        JTextField idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //name box 
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("姓名：");
        JTextField namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield); 

        //unit box
        Box unitbox = Box.createHorizontalBox(); 
        JLabel unitlabel = new JLabel("单位：");
        JTextField unitfield = new JTextField(15);
        unitbox.add(unitlabel); 
        unitbox.add(Box.createHorizontalStrut(15));
        unitbox.add(unitfield);
        
        //type box
        Box typebox = Box.createHorizontalBox();
        JLabel typelabel = new JLabel("类别：");
        JRadioButton teacher = new JRadioButton("教师", true);
        JRadioButton student = new JRadioButton("学生", false);
        ButtonGroup eGroup = new ButtonGroup();
        eGroup.add(teacher);
        eGroup.add(student);
        typebox.add(typelabel);
        typebox.add(Box.createHorizontalStrut(20));
        typebox.add(teacher);
        typebox.add(student);
        typebox.add(Box.createHorizontalStrut(70));

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("添加");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid = idfield.getText().trim();
                String name = namefield.getText().trim(); 
                String unit = unitfield.getText().trim(); 
                String type = eGroup.isSelected(teacher.getModel()) ? "T" : "S";
                if (cid.length() == 0 || name.length() == 0 || unit.length() == 0 || type.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "请将信息填写完整！");
                }
                else {
                    //执行插入
                    AddCard AddCard = new AddCard();
                    int result = AddCard.addCardInfoToDatabase(cid, name, unit, type);

                    //接口回调 刷新表格
                    actionDoneListener.done(null);

                    if (result == 1) JOptionPane.showMessageDialog(frame, "添加成功！");
                    dispose();
                }
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
}
