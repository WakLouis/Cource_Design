import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 封装一个添加了滚动条的JTextArea
 * 
 */

class ScrollView{
    JTextArea jt = new JTextArea();
    JScrollPane js = new JScrollPane(jt);
    ScrollView(){
        jt.setBorder (BorderFactory.createLineBorder(Color.GRAY,3));
        jt.setEditable(false);
        js.setBounds(5,100,575,250);
        js.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
   
}

/**
 * 端口扫描线程
 */

class Scanning extends Thread{

    public void run(){
        Program.Breaksign = 0;
        Program.Output.jt.setText("");

        String tmp = Program.LPortInput.getText();
        int L = Integer.parseInt(tmp);
        tmp = Program.RPortInput.getText();
        int R = Integer.parseInt(tmp);

        JFrame Tip = new JFrame();
        Tip.setTitle("提示");

        Tip.setSize(350,100);
        Tip.setLocation(550,300);
        JLabel Text = new JLabel();
        Tip.add(Text);
        Text.setSize(300,70);

        //判断端口输入是否正确
        if(L > R || L < 0 || L > 65535 || R < 0 || R > 65535){
            Tip.setVisible(true);
            Text.setText("<html><body><p align=\"left\">区间不存在，请重新设置端口区间!<br>端口范围为:0-65535</p></body></html>");
            Program.LPortInput.setText("");
            Program.RPortInput.setText("");
            Tip.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return;
        }

        Program.ScanningIP = Program.IP4Input.getText();

        //存放开放端口数组
        ArrayList<Integer>List = new ArrayList<>();

        //开始扫描端口
        for(int i = L;i <= R;i++){
            //被用户停止判断
            if(Program.Breaksign == 1){
                Tip.setVisible(true);
                Text.setText("<html><body><p align=\"left\">端口扫描已被用户终止！</p></body></html>");
                Program.LPortInput.setText("");
                Program.RPortInput.setText("");
                Tip.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                break;
            }
            if(Program.check(Program.ScanningIP,i)){
                Program.Output.jt.append(Program.ScanningIP + ":" + i + "(Open)\n");
                Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
                List.add(i);
            }
            else {
                Program.Output.jt.append(Program.ScanningIP + ":" + i + "(Close)\n");
                Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
            }
        }

        //扫描结束总结
        Program.Output.jt.append("_________________________________\n"+Program.ScanningIP+"下开放的端口有:\n");
        Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
        for(int i = 0;i <= List.size();i++){
            Program.Output.jt.append(List.get(i)+" ");
        }
    }
}

/**
 * 程序入口
 */
public class Program {

    static JFrame MainFrame;        //主面板JFrame
    static JTextField LPortInput;   //左端口输入框
    static JTextField RPortInput;   //右端口输入框
    static ScrollView Output;       //带滚动条的JTextArea
    static JTextField IP4Input;     //地址输入框
    static String ScanningIP;       //地址字符串
    static int Breaksign = 0;       //扫描终止标志
    
    /**
     * 检查端口开放状态
     * @param now 当前需检测IP地址
     * @param Port 端口号
     * @return 返回是否开放状态，1：开放 0：关闭
     */
    public static boolean check(String now,int Port){
        Socket socket = new Socket();
        try{
            socket.connect(new InetSocketAddress(now,Port),5);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        finally{
            try{
                socket.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 创建Annotation说明按钮打开的界面
     */
    public static void Annotation(){
        //创建AFrame图形界面
        JFrame AFrame = new JFrame("说明");
        AFrame.setLayout(new FlowLayout());
        AFrame.setVisible(true);
        AFrame.setSize(350,100);
        AFrame.setLocation(550,300);
        AFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MainFrame.setEnabled(false);

        //创建文本Text
        JLabel Text = new JLabel("",JLabel.CENTER);
        AFrame.add(Text);//将Text添加至AFrame图层
        Text.setSize(300,70);
        // Text.setLocation(100,100);
        Text.setText("<html><body><p align=\"left\">使用方法：输入IP地址与端口范围即可检测端口状态</p></body></html>");

        AFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                MainFrame.setEnabled(true);
            }
        });

        return;
    }

    /**
     * 主面板初始化
     */
    public static void MainFrameInit(){

        //设置窗口可见
        MainFrame.setVisible(true);

        //关闭窗口自动结束进程
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置图形界面尺寸
        MainFrame.setSize(600,400);

        //设置启动时位置
        MainFrame.setLocation(400,200);

        //创建容器CMain并设置无布局
        Container CMain = MainFrame.getContentPane();
        CMain.setLayout(null);
    //    CMain.setLayout(new FlowLayout(0));
        
        //创建AnnoButton按钮
        JButton AnnoButton = new JButton("说明");
        //.setMargin()设置边距
        AnnoButton.setMargin(new Insets(2,2,2,2));
    //    AnnoButton.setBorderPainted(false);
        AnnoButton.setContentAreaFilled(false);
        AnnoButton.setBounds(5,5,40,25);
        AnnoButton.setFocusPainted(false);
        AnnoButton.setFont(new Font("宋体",Font.PLAIN,12));
        CMain.add(AnnoButton);

        //重写ActionListener
        AnnoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Annotation();
            }
        });
        
        //主要界面创建
        String ip = GetAddress.GetIP4Address();
        JLabel IP4Address = new JLabel("正在检测IP地址...",JLabel.LEFT);
        IP4Address.setFont(new Font("宋体",Font.BOLD,14));
        IP4Address.setSize(200,20);
        IP4Address.setLocation(60,7);
        CMain.add(IP4Address);
        IP4Address.setText("本机IP地址为: " + ip);

        JLabel InputText1 = new JLabel("输入目标IP: ",JLabel.LEFT);
        IP4Input = new JTextField(ip);
        JLabel InputText2 = new JLabel("输入端口范围:",JLabel.LEFT);
        JLabel InputText3 = new JLabel("~",JLabel.LEFT);
        LPortInput = new JTextField();
        RPortInput = new JTextField();

        //限制端口输入仅限数字
        LPortInput.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();				
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
					
				}else{
					e.consume(); //屏蔽掉非法输入
				}
			}
		});

        RPortInput.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                int keyChar = e.getKeyChar();				
				if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){
					
				}else{
					e.consume(); 
				}
            }
        });
        
        CMain.add(InputText1);CMain.add(IP4Input);CMain.add(InputText2);CMain.add(InputText3);CMain.add(LPortInput);CMain.add(RPortInput);
        InputText1.setSize(85,20);
        InputText1.setLocation(5,35);
        InputText1.setFont(new Font("宋体",Font.BOLD,14));
        IP4Input.setSize(100,20);
        IP4Input.setLocation(85,35);
        InputText2.setSize(95,20);
        InputText2.setLocation(190,35);
        InputText2.setFont(new Font("宋体",Font.BOLD,14));
        LPortInput.setSize(50,20);
        LPortInput.setLocation(285,35);
        InputText3.setSize(10,20);
        InputText3.setLocation(337,42);
        InputText3.setFont(new Font("宋体",Font.BOLD,15));
        RPortInput.setSize(50,20);
        RPortInput.setLocation(350,35);

        //创建Start开始按钮
        JButton SButton = new JButton();
        CMain.add(SButton);
        SButton.setSize(50,50);
        SButton.setLocation(6,52);

        ImageIcon icon = new ImageIcon("Start.png");
        icon.getImage();
        Image img = icon.getImage().getScaledInstance(75, 75,Image.SCALE_DEFAULT);
        icon = new ImageIcon(img);
        SButton.setIcon(icon);
        SButton.setToolTipText("开始扫描");

        SButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Scanning t1 = new Scanning();
                t1.start();
            }
        });

        //创建Stop终止按钮
        JButton EButton = new JButton();
        CMain.add(EButton);
        EButton.setSize(50,50);
        EButton.setLocation(56,52);
        
        ImageIcon icon1 = new ImageIcon("Stop.png");
        icon1.getImage();
        Image img1 = icon1.getImage().getScaledInstance(80, 80,Image.SCALE_DEFAULT);
        icon1 = new ImageIcon(img1);
        EButton.setIcon(icon1);
        EButton.setToolTipText("终止");

        //当用户按下EButton终止按钮，Breaksign的值变为1,使循环终止
        EButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Breaksign = 1;
            }
        });

        //创建滚动显示窗
        Output = new ScrollView();
        Output.jt.setSize(1,1);
        Output.jt.setLocation(0,0);
        CMain.add(Output.js);


        return;
    }
    
    public static void main(String args[]){ 

        //创建MainFrame图形界面
        MainFrame = new JFrame("端口扫描器 -by Wake");
        
        //MainFrame初始化
        MainFrameInit();

    }

}
