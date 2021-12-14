import java.util.ArrayList;

public class SecureCheck extends Thread{
    Tip tip = new Tip();
    ArrayList<Integer>List = new ArrayList<>();
    int Port[] = {0,20,21,22,23,25,53,69,80,81,82,83,84,85,86,87,88,89,443,8440,8441,8442,8443,8444,8445,8446,8447,8448,8449,8450,
    8080,8081,8082,8083,8084,8085,8086,8087,8088,8089,110,111,2049,137,139,445,143,161,389,512,513,514,873,1194,1352,1433,1521,1500,
    1723,2082,2083,2181,2601,2604,3128,3312,3311,3306,3389,3690,4848,5000,5432,5900,5901,5902,5984,6379,7001,7002,7778,8000,8443,
    8069,9080,9081,9090,9200,9300,11211,27017,27018,50070,50030};
    public void run(){
        Program.Breaksign = 0;
        Program.Output.jt.append("正在进行本机端口安全性检查...\n");
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i = 1;i < Port.length;i++){
            if(Program.Breaksign == 1){
                tip.setVisible(true);
                tip.Text.setText("<html><body><p align=\"left\">端口扫描已被用户终止!</p></body></html>");
                Program.LPortInput.setText("");
                Program.RPortInput.setText("");
                break;
            }
            if(check.Check(new String("127.0.0.1"),Port[i])){
                Program.Output.jt.append("127.0.0.1: " + Port[i] + "(Open)\n");
                Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
                List.add(Port[i]);
            }
            else {
                Program.Output.jt.append("127.0.0.1: " + Port[i] + "(Close)\n");
                Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
            }
        }
        Program.Output.jt.append("检测完毕\n_________________________________\n");
        if(List.size() == 0){
            Program.Output.jt.append("所有检测风险端口均已关闭!\n");
        }
        else{
            Program.Output.jt.append(List.size()+"个风险端口处于监听状态,以下为风险端口,可酌情选择关闭:\n");
            for(int i = 0;i < List.size();i++){
                Program.Output.jt.append(List.get(i) + " ");
                Program.Output.jt.setCaretPosition(Program.Output.jt.getText().length());
            }
        }
        
        return;
    }
}
