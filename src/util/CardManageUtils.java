package util;

import java.util.ArrayList;
import java.util.Map;
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

import mysqloperation.DeleteCard;
import mysqloperation.GetCardData;
import mysqloperation.SearchCard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardManageUtils extends Box {
    JFrame frame = null;
    final int WIDTH = 1200;
    final int HEIGHT = 600;

    private JTable table;
    private Vector<String> titles;
    private Vector<Vector> tableData;
    // 声明数据模型
    private DefaultTableModel tableModel;

    public CardManageUtils(JFrame frame) {
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
        JButton AddBtn = new JButton("添加");
        JButton UpdateBtn = new JButton("修改");
        JButton DeleteBtn = new JButton("删除");

        //响应搜索
        SearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SearchCardDialog(frame, "搜索借书证", true, new ActionDoneListenerUtils() {
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
                    new AddCardDialog(frame, "添加借书证", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 4; ++ i) require.add(""); 
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

                // 获取 cid
                String valueAt = (String) tableModel.getValueAt(selectedRow, 0);

                // 通过cid查询借书证信息
                SearchCard SearchCard = new SearchCard();
                Map<String, String> map = SearchCard.database(valueAt);
                String cid = map.get("cid");
                String name = map.get("name");
                String unit = map.get("unit");
                String type = map.get("type");

                //执行更新
                try {
                    new UpdataCardDialog(frame, "更新借书证", true, new ActionDoneListenerUtils() {
                        @Override
                        public void done(ArrayList<String> result) {
                            ArrayList<String> require = new ArrayList<String>();
                            for (int i = 0; i < 4; ++ i) require.add(""); 
                            requestData(require);
                        }
                    }, cid, name, unit, type).setVisible(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //响应删除
        DeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteCard DeleteCard = new DeleteCard();

                //获取当前选中的行号，返回行号，未选中则-1
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "请选择要删除的条目");
                }
                //获取cid
                String valueAt = (String) tableModel.getValueAt(selectedRow, 0);

                int result = JOptionPane.showConfirmDialog(frame, "确定要删除卡号为" + valueAt + "的借书证吗？", "警告", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) { 
                    //通过cid删除借书证信息
                    DeleteCard.DeleteCardInfoById(valueAt);

                    ArrayList<String> require = new ArrayList<String>();
                    for (int i = 0; i < 4; ++ i) require.add(""); 

                    //重新获取借书证信息
                    requestData(require);
                    JOptionPane.showMessageDialog(frame, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
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
        String[] ts = {"卡号", "姓名", "单位", "类别"};
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
        for (int i = 0; i < 4; ++ i) require.add(""); 
        requestData(require);
    }

    //获取借书证信息
    public void requestData(ArrayList<String> require) {
        //清空tableData
        tableData.clear();

        GetCardData CardManage = new GetCardData();
        CardManage.database(tableModel.getDataVector(), require);

        //刷新表格
        tableModel.fireTableDataChanged();
    }
}
