import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取本机IP地址
 */
public class GetAddress {
		public static String GetIP4Address(){
			String ip = "0";
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			ip = (String)ip4.getHostAddress();
		} catch (UnknownHostException e) {
			ip = "Not Found IP Address!";
		}	
		return ip;
	}
}
