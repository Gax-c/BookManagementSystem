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

import mysqloperation.UpdataBookData;

public class UpdataBookDialog extends JDialog {
    // 定义宽高
    final int WIDTH = 600;
    final int HEIGHT = 500;

    private JTextField idfield;
    private JTextField typefield;
    private JTextField namefield;
    private JTextField pubfield;
    private JTextField yearfield;
    private JTextField authorfield;
    private JTextField pricefield;
    private JTextField totalfield;
    private JTextField stockfield;

    private ActionDoneListenerUtils listener;
    
    public UpdataBookDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils listener, String bid, String type, String name, String press, String year, String author, String price, String total, String stock) throws Exception {
        super(frame, title, isModel);
        this.listener = listener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("书号：");
        idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //type box 
        Box typebox = Box.createHorizontalBox(); 
        JLabel typelabel = new JLabel("类别：");
        typefield = new JTextField(15);
        typebox.add(typelabel); 
        typebox.add(Box.createHorizontalStrut(15));
        typebox.add(typefield);

        //书名box
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("书名：");
        namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield);
        
        //出版社box
        Box pubbox = Box.createHorizontalBox(); 
        JLabel publabel = new JLabel("出版社：");
        pubfield = new JTextField(15);
        pubbox.add(publabel); 
        pubbox.add(Box.createHorizontalStrut(15));
        pubbox.add(pubfield);

        //年份box
        Box yearbox = Box.createHorizontalBox(); 
        JLabel yearlabel = new JLabel("出版年份：");
        yearfield = new JTextField(15);
        yearbox.add(yearlabel); 
        yearbox.add(Box.createHorizontalStrut(15));
        yearbox.add(yearfield); 

        //作者box
        Box authorbox = Box.createHorizontalBox(); 
        JLabel authorlabel = new JLabel("作者：");
        authorfield = new JTextField(15);
        authorbox.add(authorlabel); 
        authorbox.add(Box.createHorizontalStrut(15));
        authorbox.add(authorfield);

        //price box
        Box pricebox = Box.createHorizontalBox(); 
        JLabel pricelabel = new JLabel("价格：");
        pricefield = new JTextField(15);
        pricebox.add(pricelabel); 
        pricebox.add(Box.createHorizontalStrut(15));
        pricebox.add(pricefield);

        //total box
        Box totalbox = Box.createHorizontalBox(); 
        JLabel totallabel = new JLabel("总藏书量：");
        totalfield = new JTextField(15);
        totalbox.add(totallabel); 
        totalbox.add(Box.createHorizontalStrut(15));
        totalbox.add(totalfield);

        //stock box
        Box stockbox = Box.createHorizontalBox(); 
        JLabel stocklabel = new JLabel("库存：");
        stockfield = new JTextField(15);
        stockbox.add(stocklabel); 
        stockbox.add(Box.createHorizontalStrut(15));
        stockbox.add(stockfield);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("更新");
        JButton BackBtn = new JButton("取消");

        // 将选中内容填充
        idfield.setText(bid);
        idfield.setEditable(false);
        typefield.setText(type);
        namefield.setText(name);
        pubfield.setText(press);
        yearfield.setText(year);
        authorfield.setText(author);
        pricefield.setText(price);
        totalfield.setText(total);
        stockfield.setText(stock);

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
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(typebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(namebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(pubbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(yearbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(authorbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(pricebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(totalbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(stockbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }

    public int infoInPut() {
        // 获取用户录入信息
        String bid = idfield.getText().trim();
        String type = typefield.getText().trim();
        String name = namefield.getText().trim();
        String press = pubfield.getText().trim();
        String year = yearfield.getText().trim();
        String author = authorfield.getText().trim();
        String price = pricefield.getText().trim();
        String total = totalfield.getText().trim();
        String stock = stockfield.getText().trim();

        // 调用更新方法
        UpdataBookData UpdataBookData = new UpdataBookData();
        int result = UpdataBookData.updataBookInfo(bid, type, name, press, year, author, price, total, stock);

        // 接口回调 刷新表格
        listener.done(null);
        return result; 
    }
}
