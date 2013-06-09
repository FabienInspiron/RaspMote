package client;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;

import ws.Outlet;

import local.IThirdPartyServer;
import local.ThirdPartServerImplService;

/**
 * wsimport -keep http://localhost:9999/ws/thirdpartpublisher?wsdl
 * @author Belli, Gorrieri
 *
 */
public class Client {
	public static final String IP_SUBSCRIBE = "127.0.0.1";
	
	// Unique User IDentifier
	//
	private String uuid;

	public static void main(String[] args) {
		 
		IThirdPartyServer serv = null;
		ThirdPartServerImplService thirdPart = null;
		
		try {
			// Server instance
			thirdPart = new ThirdPartServerImplService();
			
			// Stub of web services
			serv = thirdPart.getThirdPartServerImplPort();
			
		} catch(Exception e){
			System.err.println("Connection to the server failed");
			return;
		}
		
		
		int port = serv.subscribe("localhost");
		
		/**
		 * Listner on server events
		 */
		Thread d = new Thread(new NotificationListner(port));
		d.start();

		/*
		List<Outlet> ls = serv.getListOutlet().getItem();
		for(Outlet o : ls) {
			System.out.println(outletToString(o));
		}
		*/
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("switch_on invoke");
//		serv.switchOn(45);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("switch_on invoke");
//		serv.switchOn(45);
		
		serv.setTimer(45, 30, 1);
		
		Scanner sc = new Scanner(System.in);
		sc.next();
    }
	
	public static String outletToString(Outlet o) {
		return "<Outlet  id=" + o.getId() + ", state=" + o.isState() + ", room=" + o.getRoom()
				+ ", name=" + o.getName() + ">";
	}
}