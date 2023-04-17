package util;
import javax.swing.*;
import java.awt.*;

public class BackGroundUtils extends JPanel {
    // 声明图片
    private Image BackGround;

    public BackGroundUtils(Image BackGround) {
        this.BackGround = BackGround;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制背景
        g.drawImage(BackGround, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
