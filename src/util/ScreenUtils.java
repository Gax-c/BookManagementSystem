package util; 

import java.awt.*;

public class ScreenUtils {
    //获取电脑屏幕宽度
    public static int GetScreenWidth(){
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    //获取电脑屏幕高度
    public static int GetScreenHeight(){
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}