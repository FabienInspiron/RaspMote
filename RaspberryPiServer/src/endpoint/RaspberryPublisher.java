package endpoint;

import java.net.SocketException;
import java.net.UnknownHostException;

import javax.rmi.CORBA.Util;
import javax.xml.ws.Endpoint;

import ws.RaspberryPiAdminServerImpl;
import ws.RaspberryPiServerImpl;
import metier.*;

public class RaspberryPublisher {
	private static String DOMAINE = "127.0.0.1";
	private static int PORT_SERVER = 9990;
	private static int PORT_SERVER_ADMIN = 9991;
	
	private static String serverURL = "http://" + DOMAINE
			+ ":"+ PORT_SERVER +"/ws/raspberry";
	private static String adminURL = "http://" + DOMAINE + ":9997/ws/admin";

	/**
	 * Lauch programm with options
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String addressIP = "127.0.0.1";
		
		try {
			addressIP = UtilIP.getIP();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int portServer = PORT_SERVER;
		int portServerAdmin = PORT_SERVER_ADMIN;
		
//		if(args[4].equals("${opt}")){
//			if(args.length > 0 )
//				addressIP = args[0];
//			
//			if(args.length > 2) {
//				portServer = Integer.parseInt(args[1]);
//				portServerAdmin = Integer.parseInt(args[2]);
//			}
//		}
//		
//		if(args[4].equals("public")){
//			addressIP = getIPpublic();
//		}
//		
//		if(args[4].equals("private")){
//			addressIP = getIPprivate();
//		}
		
		String serverURL = "http://" + addressIP + ":"+ portServer +"/ws/raspberry";
		String serverURAdminL = "http://" + addressIP + ":"+ portServerAdmin +"/ws/raspberry";
		
		RaspberryPiServerImpl rasp = new RaspberryPiServerImpl();

		Endpoint.publish(serverURL, rasp);
		System.out.println("RaspberryPi server lauch succesful at : " + serverURL + "?wsdl");

		
		RaspberryPiAdminServerImpl raspAdmin = new RaspberryPiAdminServerImpl();

		Endpoint.publish(serverURAdminL, raspAdmin);
	
		raspAdmin.add_outlet("Salle a manger", "TV", "tutu", "A");
		raspAdmin.add_outlet("Salle a manger", "Lampe", "tutu", "A");
		raspAdmin.add_outlet("Salle a manger", "Four", "tutu", "A");
		raspAdmin.add_outlet("Salle de bains", "Seche cheveux", "tutu", "A");
		raspAdmin.add_outlet("Salle de bains", "Lampe", "tutu", "A");

		System.out.println("ServerAdmin lauch succesful at : " + serverURAdminL + "?wsdl");

	}
}