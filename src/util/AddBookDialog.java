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

import mysqloperation.AddBook;

public class AddBookDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 500;
    private ActionDoneListenerUtils listener;

    public AddBookDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
        super(frame, title, isModel);
        this.listener = actionDoneListener; 
        this.setBounds((ScreenUtils.GetScreenWidth() - WIDTH) / 2, (ScreenUtils.GetScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);

        JPanel BackGroundPanel = new JPanel(); 
        BackGroundPanel.setBounds(0, 0, WIDTH, HEIGHT);

        //总的box 
        Box tot_box = Box.createVerticalBox(); 

        //id box
        Box idbox = Box.createHorizontalBox(); 
        JLabel idlabel = new JLabel("书号：");
        JTextField idfield = new JTextField(15);
        idbox.add(idlabel); 
        idbox.add(Box.createHorizontalStrut(15));
        idbox.add(idfield); 

        //type box 
        Box typebox = Box.createHorizontalBox(); 
        JLabel typelabel = new JLabel("类别：");
        JTextField typefield = new JTextField(15);
        typebox.add(typelabel); 
        typebox.add(Box.createHorizontalStrut(15));
        typebox.add(typefield); 

        //书名box
        Box namebox = Box.createHorizontalBox(); 
        JLabel namelabel = new JLabel("书名：");
        JTextField namefield = new JTextField(15);
        namebox.add(namelabel); 
        namebox.add(Box.createHorizontalStrut(15));
        namebox.add(namefield);
        
        //出版社box
        Box pubbox = Box.createHorizontalBox(); 
        JLabel publabel = new JLabel("出版社：");
        JTextField pubfield = new JTextField(15);
        pubbox.add(publabel); 
        pubbox.add(Box.createHorizontalStrut(15));
        pubbox.add(pubfield);

        //年份box
        Box yearbox = Box.createHorizontalBox(); 
        JLabel yearlabel = new JLabel("出版年份：");
        JTextField yearfield = new JTextField(15);
        yearbox.add(yearlabel); 
        yearbox.add(Box.createHorizontalStrut(15));
        yearbox.add(yearfield); 

        //作者box
        Box authorbox = Box.createHorizontalBox(); 
        JLabel authorlabel = new JLabel("作者：");
        JTextField authorfield = new JTextField(15);
        authorbox.add(authorlabel); 
        authorbox.add(Box.createHorizontalStrut(15));
        authorbox.add(authorfield);

        //price box
        Box pricebox = Box.createHorizontalBox(); 
        JLabel pricelabel = new JLabel("价格：");
        JTextField pricefield = new JTextField(15);
        pricebox.add(pricelabel); 
        pricebox.add(Box.createHorizontalStrut(15));
        pricebox.add(pricefield);

        //total box
        Box totalbox = Box.createHorizontalBox(); 
        JLabel totallabel = new JLabel("总藏书量：");
        JTextField totalfield = new JTextField(15);
        totalbox.add(totallabel); 
        totalbox.add(Box.createHorizontalStrut(15));
        totalbox.add(totalfield);

        //stock box
        Box stockbox = Box.createHorizontalBox(); 
        JLabel stocklabel = new JLabel("库存：");
        JTextField stockfield = new JTextField(15);
        stockbox.add(stocklabel); 
        stockbox.add(Box.createHorizontalStrut(15));
        stockbox.add(stockfield);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("添加");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid = idfield.getText().trim();
                String type = typefield.getText().trim();
                String name = namefield.getText().trim(); 
                String press = pubfield.getText().trim(); 
                String year = yearfield.getText().trim(); 
                String author = authorfield.getText().trim();
                String price = pricefield.getText().trim();
                String total = totalfield.getText().trim(); 
                String stock = stockfield.getText().trim();  
                if (bid.length() == 0 || type.length() == 0 || name.length() == 0 || press.length() == 0 || year.length() == 0 || author.length() == 0 || price.length() == 0 || total.length() == 0 || stock.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "请将信息填写完整！");
                }
                else {
                    //执行插入
                    AddBook AddBook = new AddBook();
                    int result = AddBook.addBookInfoToDatabase(bid, type, name, press, year, author, price, total, stock);

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
}
