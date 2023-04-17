package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import mysqloperation.AddBookBash;
import mysqloperation.DeleteBook;
import mysqloperation.GetBookData;
import mysqloperation.SearchBook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookManageUtils extends Box {
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

    public BookManageUtils(JFrame frame) {
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
        JButton AddBtn = new JButton("添加");
        JButton UpdateBtn = new JButton("修改");
        JButton DeleteBtn = new JButton("删除");
        JButton fileChooserBtn = new JButton("批量导入"); 

        //响应排序
        SortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SortBookDialog(frame, "书籍排序", true, new ActionDoneListenerUtils() {
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

        //响应添加
        AddBtn.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddBookDialog(frame, "添加书籍", true, new ActionDoneListenerUtils() {
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

                // 获取 bid
                String valueAt = (String) tableModel.getValueAt(selectedRow, 0);

                // 通过bid查询书籍信息
                SearchBook SearchBook = new SearchBook();
                Map<String, String> map = SearchBook.database(valueAt);
                String bid = map.get("bid");
                String type = map.get("type");
                String name = map.get("name");
                String press = map.get("press");
                String year = map.get("year");
                String author = map.get("author");
                String price = map.get("price");
                String total = map.get("total");
                String stock = map.get("stock");

                //执行更新
                try {
                    new UpdataBookDialog(frame, "更新书籍信息", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 9; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }, bid, type, name, press, year, author, price, total, stock).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //响应删除
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteBook DeleteBook = new DeleteBook();

                //获取当前选中的行号，返回行号，未选中则-1
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请选择要删除的条目");
                }
                //获取bid
                String valueAt = (String) tableModel.getValueAt(selectedRow, 0);

                int result = JOptionPane.showConfirmDialog(frame, "确定要删除书号为" + valueAt + "的图书吗？", "警告", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { 
                    //通过bid删除书籍信息
                    DeleteBook.DeleteBookInfoById(valueAt);

                    ArrayList<String> require = new ArrayList<String>();
                    for (int i = 0; i < 9; ++ i) require.add(""); 

                    //重新获取书籍信息
                    requestData(require);
                    JOptionPane.showMessageDialog(frame, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        //响应批量导入
        fileChooserBtn.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                //显示一个文件选择器
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.showOpenDialog(frame);
                File file = fileChooser.getSelectedFile();
                //进行显示
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String tempString = null;
                    
                    // 一次读入一行，直到读入null为文件结束
                    int result = 1; 
                    while ((tempString = reader.readLine()) != null) {
                        AddBookBash AddBookBash = new AddBookBash(); 
                        if (AddBookBash.addBookBashInfoToDatabase(tempString) == 0) result = 0;
                    }
                    reader.close();
                    
                    ArrayList<String> require = new ArrayList<String>();
                    for (int i = 0; i < 9; ++ i) require.add(""); 

                    //重新获取书籍信息
                    requestData(require); 
                    if (result == 1) JOptionPane.showMessageDialog(frame, "添加成功！");
                    else JOptionPane.showMessageDialog(frame, "部分书籍添加出现错误，请重新检查！");

                } catch (Exception e1) {
                    e1.printStackTrace();
                } 
            }
        });
        
        BtnPanel.add(SortBtn); 
        BtnPanel.add(SearchBtn); 
        BtnPanel.add(AddBtn); 
        BtnPanel.add(UpdateBtn); 
        BtnPanel.add(DeleteBtn); 
        BtnPanel.add(fileChooserBtn); 

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
