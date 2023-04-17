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

import mysqloperation.AddBorrow;

public class AddBorrowDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 450;
    private ActionDoneListenerUtils listener;

    public AddBorrowDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
        super(frame, title, isModel);
        this.listener = actionDoneListener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //cid box
        Box cidbox = Box.createHorizontalBox(); 
        JLabel cidlabel = new JLabel("卡号：");
        JTextField cidfield = new JTextField(15);
        cidbox.add(cidlabel); 
        cidbox.add(Box.createHorizontalStrut(15));
        cidbox.add(cidfield); 

        //bid box
        Box bidbox = Box.createHorizontalBox(); 
        JLabel bidlabel = new JLabel("书号：");
        JTextField bidfield = new JTextField(15);
        bidbox.add(bidlabel); 
        bidbox.add(Box.createHorizontalStrut(15));
        bidbox.add(bidfield);

        //borrow box
        Box borrowbox = Box.createHorizontalBox(); 
        JLabel borrowlabel = new JLabel("借书日期：");
        JTextField borrowfield = new JTextField(15);
        borrowbox.add(borrowlabel); 
        borrowbox.add(Box.createHorizontalStrut(15));
        borrowbox.add(borrowfield); 

        //return box
        Box returnbox = Box.createHorizontalBox(); 
        JLabel returnlabel = new JLabel("还书日期：");
        JTextField returnfield = new JTextField(15);
        returnbox.add(returnlabel); 
        returnbox.add(Box.createHorizontalStrut(15));
        returnbox.add(returnfield); 

        //mid box
        Box midbox = Box.createHorizontalBox(); 
        JLabel midlabel = new JLabel("管理员ID：");
        JTextField midfield = new JTextField(15);
        midbox.add(midlabel); 
        midbox.add(Box.createHorizontalStrut(15));
        midbox.add(midfield); 

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("借书");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid = cidfield.getText().trim();
                String bid = bidfield.getText().trim(); 
                String borrow = borrowfield.getText().trim(); 
                String ret = returnfield.getText().trim(); 
                String mid = midfield.getText().trim(); 
                if (cid.length() == 0 || bid.length() == 0 || borrow.length() == 0 || ret.length() == 0 || mid.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "请将信息填写完整！");
                }
                else {
                    //执行插入
                    AddBorrow AddBorrow = new AddBorrow();
                    int result = AddBorrow.addBorrowInfoToDatabase(cid, bid, borrow, ret, mid);

                    //接口回调 刷新表格
                    actionDoneListener.done(null);
                    if (result == 1) JOptionPane.showMessageDialog(frame, "添加成功！");
                    else if (result == 0) JOptionPane.showMessageDialog(frame, "该书库存不足！");
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
        tot_box.add(cidbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(bidbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(borrowbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(returnbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(midbox); 
        tot_box.add(Box.createVerticalStrut(25));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }
}
