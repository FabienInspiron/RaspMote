package client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class UtilIP {
	public static String findAttribut(String msg, String prefixe, String suffixe) throws Exception 
    {         
        Scanner sc = new Scanner(msg);
         
        while (sc.hasNextLine()) 
        { 
            String line = sc.nextLine(); 
             
            int a = line.indexOf(prefixe); 
            if (a!=-1) 
            { 
                int b = line.indexOf(suffixe,a); 
                if (b!=-1) 
                { 
                    sc.close(); 
                    return line.substring(a+prefixe.length(),b); 
                } 
            } 
        } 
         
        sc.close(); 
        return null; 
    }
	
	public static String attribut(String noti, String balise) throws Exception{
		return findAttribut(noti, "<"+balise+">", "</"+balise+">");
	}
	
	/**
	 * Get public ip adress
	 * @return
	 */
	public static String getIpPublic(){
		try {
			return findIP("http://votreip.free.fr/","<title>IP : ","</title>");
		} catch (Exception e) {
			try {
				return findIP("http://www.monip.org/","<BR>IP : ","<br>");
			} catch (Exception e1) {
				return "localhost";
			}
		}
	}
	
	/**
	 * Get an ip adress 
	 * @param site
	 * @param prefixe
	 * @param suffixe
	 * @return
	 * @throws Exception
	 */
	public static String findIP(String site, String prefixe, String suffixe) throws Exception 
    {         
        Scanner sc = new Scanner(new URL(site).openStream()); 
         
        while (sc.hasNextLine()) 
        { 
            String line = sc.nextLine(); 
             
            int a = line.indexOf(prefixe); 
            if (a!=-1) 
            { 
                int b = line.indexOf(suffixe,a); 
                if (b!=-1) 
                { 
                    sc.close(); 
                    return line.substring(a+prefixe.length(),b); 
                } 
            } 
        } 
         
        sc.close(); 
        return null; 
    } 
	
	/**
	 * Optain all IP adress of the computer
	 * 
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static List<String> getHost() throws UnknownHostException, SocketException{
		List<String> ret = new ArrayList<String>();
		
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
        for (; n.hasMoreElements();)
        {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();)
                {
                        InetAddress addr = a.nextElement();
                        
                        if(addr.getHostAddress().indexOf(':') == -1)
                        	ret.add(addr.getHostAddress());
                        
                        if(addr.getHostAddress().indexOf("127.0.0.1") == -1)
                        	ret.add(addr.getHostAddress());
                }
        }
        
        return ret;
	}
	
	/***
	 * Optain ip adress of the computer
	 * @return
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 */
	public static String getIP() throws UnknownHostException, SocketException{
		List<String> list = getHost();

		if (isBehindRouter()) {
			for (String string : list) {
				if (string.indexOf("192") != -1)
					return string;
			}
		} else {
			return getIpPublic();
		}
		
		return "192.168.1.1";
	}
	
	/**
	 * Return true if the host is behind a router
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static boolean isBehindRouter() throws UnknownHostException, SocketException{
		String ip = UtilIP.getIpPublic();
		List<String> list = getHost();
		return !list.contains(ip);
	}
}
