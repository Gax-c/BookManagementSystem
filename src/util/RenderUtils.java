package util;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class RenderUtils extends DefaultTreeCellRenderer {
    private ImageIcon RootIcon = null;
    private ImageIcon ManagerUserManageIcon = null;
    private ImageIcon BookManageIcon = null;
    private ImageIcon CardManageIcon = null;
    private ImageIcon BorrowManageIcon = null; 

    public RenderUtils() {
        RootIcon = new ImageIcon("src/images/RootIcon.png");
        ManagerUserManageIcon = new ImageIcon("src/images/ManagerUserManageIcon.png");
        BookManageIcon = new ImageIcon("src/images/BookManageIcon.png");
        CardManageIcon = new ImageIcon("src/images/CardManageIcon.png");
        BorrowManageIcon = new ImageIcon("src/images/BorrowManageIcon.png");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // 使用默认绘制
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        ImageIcon image = null;
        if (row == 0) image = RootIcon;
        else if (row == 1) image = ManagerUserManageIcon; 
        else if (row == 2) image = BookManageIcon; 
        else if (row == 3) image = CardManageIcon; 
        else if (row == 4) image = BorrowManageIcon; 
        this.setIcon(image);
        return this;
    }
}
