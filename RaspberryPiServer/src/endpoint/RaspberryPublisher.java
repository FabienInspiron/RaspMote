package endpoint;
 
import javax.xml.ws.Endpoint;

import ws.RaspberryPiAdminServerImpl;
import ws.RaspberryPiServerImpl;

 
public class RaspberryPublisher{
	private static String DOMAINE = "localhost"; //"raspmote.no-ip.org";
	private static String serverURL = "http://"+DOMAINE+":9999/ws/raspberry";
	private static String adminURL = "http://"+DOMAINE+":9997/ws/admin";

 
	public static void main(String[] args) {
	   RaspberryPiServerImpl rasp = new RaspberryPiServerImpl();
	   
	   Endpoint.publish(serverURL, rasp);
	   System.out.println("Lauch raspberry pi server... ");
	   System.out.println("Publication of the wsdl : "+ serverURL+"?wsdl");
	   
	   RaspberryPiAdminServerImpl raspAdmin = new RaspberryPiAdminServerImpl();
	   
	   Endpoint.publish(adminURL, raspAdmin);
	   System.out.println("Lauch raspberry pi admin... ");
	   
	   System.out.println("Lauch service here : "+serverURL);
    }
}