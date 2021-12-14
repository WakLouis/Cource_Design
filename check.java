import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

interface check {
    /**
     * 检查端口开放状态
     * @param now 当前需检测IP地址
     * @param Port 端口号
     * @return 返回是否开放状态，1：开放 0：关闭
     */
    
    public static boolean Check(String now,int Port){

        Socket socket = new Socket();
        try{
            socket.connect(new InetSocketAddress(now,Port),Scanning.Time+10);
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
}
