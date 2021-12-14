import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tip extends JFrame{
    JLabel Text = new JLabel();
    Tip(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("提示");
        setSize(350,100);
        setLocation(550,300);
        add(Text);
        Text.setSize(300,70);
    }
}
