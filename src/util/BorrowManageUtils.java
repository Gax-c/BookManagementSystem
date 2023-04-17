package util;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import mysqloperation.GetBorrowData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BorrowManageUtils extends Box {
    JFrame frame = null;
    final int WIDTH = 1200;
    final int HEIGHT = 600;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    // 声明数据模型
    private DefaultTableModel tableModel;

    public BorrowManageUtils(JFrame frame) {
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

        JButton SearchBtn = new JButton("查找");
        JButton AddBtn = new JButton("借书");
        JButton UpdateBtn = new JButton("修改记录");
        JButton DeleteBtn = new JButton("还书");

        //响应查找
        SearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SearchBorrowDialog(frame, "查找", true, new ActionDoneListenerUtils() {
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

        //响应借书
        AddBtn.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddBorrowDialog(frame, "借书", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 8; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //响应修改
        UpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取当前选中的行号,返回行号，未选中则返回-1
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请选择要修改的条目");
                    return;
                }

                //获取该行的信息
                String cid = (String) tableModel.getValueAt(selectedRow, 0);
                String cname = (String) tableModel.getValueAt(selectedRow, 1);
                String bid = (String) tableModel.getValueAt(selectedRow, 2);
                String bname = (String) tableModel.getValueAt(selectedRow, 3);
                String borrow = (String) tableModel.getValueAt(selectedRow, 4);
                String ret = (String) tableModel.getValueAt(selectedRow, 5);
                String mid = (String) tableModel.getValueAt(selectedRow, 6);
                String mname = (String) tableModel.getValueAt(selectedRow, 7);

                //执行更新
                try {
                    new UpdataBorrowDialog(frame, "更新记录", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 8; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }, cid, cname, bid, bname, borrow, ret, mid, mname).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //响应还书
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new DeleteBorrowDialog(frame, "还书", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 8; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        BtnPanel.add(SearchBtn); 
        BtnPanel.add(AddBtn); 
        BtnPanel.add(UpdateBtn); 
        BtnPanel.add(DeleteBtn); 

        this.add(BtnPanel); 

        // 组装表格
        // 存储标题
        String[] ts = {"卡号", "姓名", "书号", "书名", "借书日期", "还书日期", "管理员ID", "管理员姓名"};
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
        for (int i = 0; i < 8; ++ i) require.add(""); 
        requestData(require);
    }

    //获取借阅记录信息
    public void requestData(ArrayList<String> require) {
        //清空tableData
        tableData.clear();

        GetBorrowData GetBorrowData = new GetBorrowData();
        GetBorrowData.database(tableModel.getDataVector(), require);

        //刷新表格
        tableModel.fireTableDataChanged();
    }
}
