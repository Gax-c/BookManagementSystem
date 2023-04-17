package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SortBookDialogForView extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 450;
    private ActionDoneListenerUtils listener;

    public SortBookDialogForView(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
        super(frame, title, isModel);
        this.listener = actionDoneListener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //key box
        Box keybox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("排序键值：");
        JComboBox<String> cmb = new JComboBox<String>();
        cmb.addItem("书名");
        cmb.addItem("书号");
        cmb.addItem("类别");
        cmb.addItem("出版社");
        cmb.addItem("年份");
        cmb.addItem("作者");
        cmb.addItem("总藏书量");
        cmb.addItem("库存");
        keybox.add(idlabel); 
        keybox.add(Box.createHorizontalStrut(15));
        keybox.add(cmb); 

        //排列顺序box 
        Box showbox = Box.createHorizontalBox();
        JLabel showlabel = new JLabel("排列顺序：");
        JRadioButton asc = new JRadioButton("升序", true);
        JRadioButton desc = new JRadioButton("降序", false);
        ButtonGroup eGroup = new ButtonGroup();
        eGroup.add(asc);
        eGroup.add(desc);
        showbox.add(showlabel);
        showbox.add(Box.createHorizontalStrut(20));
        showbox.add(asc);
        showbox.add(desc);
        showbox.add(Box.createHorizontalStrut(70));

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("确定");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = cmb.getSelectedIndex();
                ViewBookManageUtils.SortPara[0] = selected; 
                ViewBookManageUtils.SortPara[1] = eGroup.isSelected(asc.getModel()) ? 0 : 1;

                //接口回调 刷新表格
                actionDoneListener.done(null);

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
        tot_box.add(keybox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(showbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }
}
