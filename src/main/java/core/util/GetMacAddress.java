package core.util;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 * 类名称：GetMacAddress 类描述： 获取mac和ip地址 创建人： 邹猛 创建时间：2015年4月27日 下午12:47:51 修改人：
 * 修改时间：2015年4月27日 下午12:47:51 修改备注：
 */
public class GetMacAddress {

    /**
     * 
     * @param args
     * 
     * @throws UnknownHostException
     * 
     * @throws SocketException
     */

    public static void localMac(String ip) throws UnknownHostException,
            SocketException {
        // 得到IP，输出PC-201309011313/122.206.73.83
        // InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip);
        // String mac=getMac(ip);
        // return mac;
    }

    public static String localIp() throws UnknownHostException, SocketException {
        // 得到IP，输出PC-201309011313/122.206.73.83
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip);
        String local_Ip = ip.toString();
        return local_Ip;
    }
    /*
     * private static String getLocalMac(InetAddress ia) throws SocketException
     * { //获取网卡，获取地址 byte[] mac =
     * NetworkInterface.getByInetAddress(ia).getHardwareAddress();
     * System.out.println("mac数组长度："+mac.length); StringBuffer sb = new
     * StringBuffer(""); for(int i=0; i<mac.length; i++) { if(i!=0) {
     * sb.append("-"); } //字节转换为整数 int temp = mac[i]&0xff; String str =
     * Integer.toHexString(temp); System.out.println("每8位:"+str);
     * if(str.length()==1) { sb.append("0"+str); }else { sb.append(str); } }
     * System.out.println("本机MAC地址:"+sb.toString().toUpperCase()); return
     * sb.toString(); }
     */

}
