package util;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import mysqloperation.GetBookData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewBookManageUtils extends Box {
    JFrame frame = null;
    final int WIDTH = 1200;
    final int HEIGHT = 600;

    //存放排序所需的参数，2表示键值，0表示升序
    static public int[] SortPara = {2, 0}; 

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    // 声明数据模型
    private DefaultTableModel tableModel;

    public ViewBookManageUtils(JFrame frame) {
        // 垂直布局
        super(BoxLayout.Y_AXIS);
        
        //组装视图
        this.frame = frame;
        JPanel BtnPanel = new JPanel();
        Color color = new Color(203, 220, 217);
        BtnPanel.setBackground(color);

        // 最大宽和高
        BtnPanel.setMaximumSize(new Dimension(WIDTH, 40));
        BtnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton SortBtn = new JButton("排序");
        JButton SearchBtn = new JButton("查找");

        //响应排序
        SortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SortBookDialogForView(frame, "书籍排序", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 9; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //响应搜索
        SearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SearchBookDialog(frame, "搜索书籍", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            requestData((ArrayList<String>)result);
                        }
                    }).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        BtnPanel.add(SortBtn); 
        BtnPanel.add(SearchBtn); 

        this.add(BtnPanel); 

        // 组装表格
        // 存储标题
        String[] ts = {"书号", "类别", "书名", "出版社", "年份", "作者", "价格", "总藏书量", "库存"};
        titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        // 存储数据
        tableData = new Vector<>();
        tableModel = new DefaultTableModel(tableData, titles);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //设置同时只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

        //填充到表格
        ArrayList<String> require = new ArrayList<String>();
        for (int i = 0; i < 9; ++ i) require.add(""); 
        requestData(require);
    }

    //获取书籍信息
    public void requestData(ArrayList<String> require) {
        //清空tableData
        tableData.clear();

        GetBookData bookManage = new GetBookData();
        bookManage.database(tableModel.getDataVector(), require, SortPara);

        //刷新表格
        tableModel.fireTableDataChanged();
    }
}
