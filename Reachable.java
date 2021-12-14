import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Reachable {
    /**
     * 检测Host能否连通
     * @param Host 需要检测的IP地址
     * @return 返回"-1" --连接超时.返回其他为连接该IP的延迟
     */
    public static String isHostReachable(String Host){
        BufferedReader read = null;
        try{
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("ping " + Host);
            InputStreamReader reader = new InputStreamReader(process.getInputStream(),"GB2312");
            read = new BufferedReader(reader);
            
            StringBuffer sb = new StringBuffer();
            String line = null;
            while((line  = read.readLine()) != null){
                //Program.Output.jt.append("1\n");
                sb.append(line);
            }
            if(!sb.toString().contains("平均")){
                return "-1";
            }
            else{
                //Program.Output.jt.append(sb.toString() + "\n");
                return sb.toString().substring(sb.toString().lastIndexOf("平均")+5, sb.length());
            }
        }
        catch(UnknownHostException e1){
            e1.printStackTrace();
        }
        catch(IOException e2){
            e2.printStackTrace();
        }
        return "-1";
    }
}
