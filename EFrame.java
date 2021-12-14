import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EFrame extends JFrame{
    EFrame(){
        Program.MainFrame.setEnabled(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("警告");
        setSize(250,100);
        setLocation(500,300);
        JLabel Text = new JLabel();
        add(Text);
        Text.setSize(200,70);
        Text.setText("<html><body><p align=\"left\">请等待上次检测结束再进行下次检测!</p></body></html>");

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                Program.MainFrame.setEnabled(true);
            }
        });
    }
}
