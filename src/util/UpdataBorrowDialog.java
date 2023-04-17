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

import mysqloperation.UpdataBorrowData;

public class UpdataBorrowDialog extends JDialog {
    // 定义宽高
    final int WIDTH = 600;
    final int HEIGHT = 450;

    private JTextField cidfield;
    private JTextField cnamefield;
    private JTextField bidfield;
    private JTextField bnamefield;
    private JTextField borrowfield;
    private JTextField returnfield;
    private JTextField midfield;
    private JTextField mnamefield;


    private ActionDoneListenerUtils listener;
    
    public UpdataBorrowDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils listener, String cid, String cname, String bid, String bname, String borrow, String ret, String mid, String mname) throws Exception {
        super(frame, title, isModel);
        this.listener = listener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //cid box
        Box cidbox = Box.createHorizontalBox(); 
        JLabel cidlabel = new JLabel("卡号：");
        cidfield = new JTextField(15);
        cidbox.add(cidlabel); 
        cidbox.add(Box.createHorizontalStrut(15));
        cidbox.add(cidfield); 

        //cname box 
        Box cnamebox = Box.createHorizontalBox(); 
        JLabel cnamelabel = new JLabel("姓名：");
        cnamefield = new JTextField(15);
        cnamebox.add(cnamelabel); 
        cnamebox.add(Box.createHorizontalStrut(15));
        cnamebox.add(cnamefield);

        //bid box
        Box bidbox = Box.createHorizontalBox(); 
        JLabel bidlabel = new JLabel("书号：");
        bidfield = new JTextField(15);
        bidbox.add(bidlabel); 
        bidbox.add(Box.createHorizontalStrut(15));
        bidbox.add(bidfield);
        
        //bname box
        Box bnamebox = Box.createHorizontalBox(); 
        JLabel bnamelabel = new JLabel("出版社：");
        bnamefield = new JTextField(15);
        bnamebox.add(bnamelabel); 
        bnamebox.add(Box.createHorizontalStrut(15));
        bnamebox.add(bnamefield);

        //borrow box
        Box borrowbox = Box.createHorizontalBox(); 
        JLabel borrowlabel = new JLabel("借书日期：");
        borrowfield = new JTextField(15);
        borrowbox.add(borrowlabel); 
        borrowbox.add(Box.createHorizontalStrut(15));
        borrowbox.add(borrowfield); 

        //return box
        Box returnbox = Box.createHorizontalBox(); 
        JLabel returnlabel = new JLabel("还书日期：");
        returnfield = new JTextField(15);
        returnbox.add(returnlabel); 
        returnbox.add(Box.createHorizontalStrut(15));
        returnbox.add(returnfield); 

        //mid box
        Box midbox = Box.createHorizontalBox(); 
        JLabel midlabel = new JLabel("管理员ID：");
        midfield = new JTextField(15);
        midbox.add(midlabel); 
        midbox.add(Box.createHorizontalStrut(15));
        midbox.add(midfield);

        //mname box
        Box mnamebox = Box.createHorizontalBox(); 
        JLabel mnamelabel = new JLabel("管理员姓名：");
        mnamefield = new JTextField(15);
        mnamebox.add(mnamelabel); 
        mnamebox.add(Box.createHorizontalStrut(15));
        mnamebox.add(mnamefield);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("更新");
        JButton BackBtn = new JButton("取消");

        // 将选中内容填充
        cidfield.setText(cid);
        cidfield.setEditable(false);
        cnamefield.setText(cname); 
        cnamefield.setEditable(false);
        bidfield.setText(bid); 
        bidfield.setEditable(false);
        bnamefield.setText(bname); 
        bnamefield.setEditable(false);
        borrowfield.setText(borrow); 
        borrowfield.setEditable(false);
        returnfield.setText(ret); 
        midfield.setText(mid); 
        midfield.setEditable(false);
        mnamefield.setText(mname); 
        mnamefield.setEditable(false);

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = infoInPut(ret);
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
        tot_box.add(cidbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(cnamebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bidbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bnamebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(borrowbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(returnbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(midbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(mnamebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }

    public int infoInPut(String ret) {
        // 获取用户录入信息
        String cid = cidfield.getText().trim();
        String cname = cnamefield.getText().trim();
        String bid = bidfield.getText().trim();
        String bname = bnamefield.getText().trim();
        String borrow = borrowfield.getText().trim();
        String ret_ = returnfield.getText().trim();
        String mid = midfield.getText().trim();
        String mname = mnamefield.getText().trim();

        // 调用更新方法
        UpdataBorrowData UpdataBorrowData = new UpdataBorrowData();
        int result = UpdataBorrowData.updataBorrowInfo(cid, cname, bid, bname, borrow, ret, mid, mname, ret_); 

        // 接口回调 刷新表格
        listener.done(null);
        return result; 
    }
}
