package endpoint;

import javax.xml.ws.Endpoint;

import local.NotificationListner;
import local.ThirdPartServerImpl;
import metier.Adress;

public class ThirdPartPublisher {

	public static final String IP = Adress.getIpPublic();
	public static final String PORT = "9998";
	public static final String PATH = "/ws/thirdpartpublisher";
	public static final String ADRESSE = "http://" + IP + ":" + PORT + PATH;
	
	public static void main(String[] args) {
		ThirdPartServerImpl server = new ThirdPartServerImpl();
		
		Thread t = new Thread(new NotificationListner(server.getPortEcoute(), server));
		t.start();
		
		Endpoint.publish(ADRESSE, server);
		System.out.println("Service publié a l'adresse : "+ ADRESSE);
		System.out.println("Theard server created ...");
	}
}