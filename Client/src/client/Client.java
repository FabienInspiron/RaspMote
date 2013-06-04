package client;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import ws.IThirdPartyServer;
import ws.ThirdPartServerImplService;


/**
 * wsimport -keep http://localhost:9999/ws/thirdpartpublisher?wsdl
 * @author belli
 *
 */
public class Client {
	public static final String PORT_SUBSCRIBE = "9090";
	public static final String IP_SUBSCRIBE = "127.0.0.1";
	
	public static void main(String[] args) {
		 
		IThirdPartyServer serv = null;
		ThirdPartServerImplService thirdPart = null;
		
		try {
			// recuperation d'un reference sur le serveur
			thirdPart = new ThirdPartServerImplService();
			serv = thirdPart.getThirdPartServerImplPort();
		} catch(Exception e){
			System.err.println("Impossible de se connecter au serveur");
			return;
		}
		
		/**
		 * Ecouter le serveur
		 */
		Thread d = new Thread(new NotificationListner(9090));
		d.start();
		
		
		serv.subscribe(34, "http://"+IP_SUBSCRIBE+":"+PORT_SUBSCRIBE+"/");
		
		System.out.println(serv.getListOutlet());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
