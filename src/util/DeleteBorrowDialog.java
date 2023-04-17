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

import mysqloperation.DeleteBorrow;

public class DeleteBorrowDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 450;
    private ActionDoneListenerUtils listener;

    public DeleteBorrowDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
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

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("还书");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cid = cidfield.getText().trim();
                String bid = bidfield.getText().trim(); 
                if (cid.length() == 0 || bid.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "请将信息填写完整！");
                }
                else {
                    //执行删除
                    DeleteBorrow DeleteBorrow = new DeleteBorrow();
                    int result = DeleteBorrow.DeleteBorrowInfo(cid, bid);

                    //接口回调 刷新表格
                    actionDoneListener.done(null);

                    if (result == 1) JOptionPane.showMessageDialog(frame, "还书成功！");
                    else if (result == 0) JOptionPane.showMessageDialog(frame, "借书记录不存在！");
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
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }
}
