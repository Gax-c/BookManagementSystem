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

public class SearchBookDialog extends JDialog {
    final int WIDTH = 600;
    final int HEIGHT = 500;
    private ActionDoneListenerUtils listener;

    public SearchBookDialog(JFrame frame, String title, boolean isModel, ActionDoneListenerUtils actionDoneListener) throws Exception {
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

        //年份box1
        Box yearbox1 = Box.createHorizontalBox(); 
        JLabel yearlabel1 = new JLabel("出版年份下界：");
        JTextField yearfield1 = new JTextField(15);
        yearbox1.add(yearlabel1); 
        yearbox1.add(Box.createHorizontalStrut(15));
        yearbox1.add(yearfield1); 

        //年份box2
        Box yearbox2 = Box.createHorizontalBox(); 
        JLabel yearlabel2 = new JLabel("出版年份上界：");
        JTextField yearfield2 = new JTextField(15);
        yearbox2.add(yearlabel2); 
        yearbox2.add(Box.createHorizontalStrut(15));
        yearbox2.add(yearfield2); 

        //作者box
        Box authorbox = Box.createHorizontalBox(); 
        JLabel authorlabel = new JLabel("作者：");
        JTextField authorfield = new JTextField(15);
        authorbox.add(authorlabel); 
        authorbox.add(Box.createHorizontalStrut(15));
        authorbox.add(authorfield);

        //price box1
        Box pricebox1 = Box.createHorizontalBox(); 
        JLabel pricelabel1 = new JLabel("价格下界：");
        JTextField pricefield1 = new JTextField(15);
        pricebox1.add(pricelabel1); 
        pricebox1.add(Box.createHorizontalStrut(15));
        pricebox1.add(pricefield1);

        //price box2
        Box pricebox2 = Box.createHorizontalBox(); 
        JLabel pricelabel2 = new JLabel("价格上界：");
        JTextField pricefield2 = new JTextField(15);
        pricebox2.add(pricelabel2); 
        pricebox2.add(Box.createHorizontalStrut(15));
        pricebox2.add(pricefield2);

        // 组装按钮
        Box bBox = Box.createHorizontalBox();
        JButton AddBtn = new JButton("确定");
        JButton BackBtn = new JButton("取消");

        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bid = idfield.getText().trim();
                String type = typefield.getText().trim();
                String name = namefield.getText().trim(); 
                String press = pubfield.getText().trim(); 
                String year1 = yearfield1.getText().trim(); 
                String year2 = yearfield2.getText().trim();
                String author = authorfield.getText().trim();
                String price1 = pricefield1.getText().trim(); 
                String price2 = pricefield2.getText().trim(); 
                
                //接口回调 刷新表格
                ArrayList<String> require = new ArrayList<String>(); 
                require.add(bid); 
                require.add(type); 
                require.add(name); 
                require.add(press); 
                require.add(year1); 
                require.add(year2); 
                require.add(author); 
                require.add(price1); 
                require.add(price2); 
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
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(typebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(namebox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(pubbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(yearbox1); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(yearbox2); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(authorbox); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(pricebox1); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(pricebox2); 
        tot_box.add(Box.createVerticalStrut(15));
        tot_box.add(bBox); 

        BackGroundPanel.add(tot_box); 
        this.add(BackGroundPanel); 
    }
}
