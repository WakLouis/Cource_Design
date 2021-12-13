import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

interface GetAddress{

	/**
	 * 获取本机IP地址
	 * @return 返回本机IP地址
	 */

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
