import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class annotation {
    public static void Annotation(){
        //创建AFrame图形界面
        JFrame AFrame = new JFrame("说明");
        AFrame.setLayout(new FlowLayout());
        AFrame.setVisible(true);
        AFrame.setSize(350,100);
        AFrame.setLocation(550,300);
        AFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Program.MainFrame.setEnabled(false);

        //创建文本Text
        JLabel Text = new JLabel("",JLabel.CENTER);
        AFrame.add(Text);//将Text添加至AFrame图层
        Text.setSize(300,70);
        // Text.setLocation(100,100);
        Text.setText("<html><body><p align=\"left\">使用方法:输入IP地址与端口范围即可检测端口状态<br>若在使用过程中发现BUG可反馈至:1139128923@qq.com</p></body></html>");

        AFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Program.MainFrame.setEnabled(true);
            }
        });

        return;
    }
}
