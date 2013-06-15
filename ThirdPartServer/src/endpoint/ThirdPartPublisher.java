package endpoint;

import javax.xml.ws.Endpoint;

import local.ThirdPartServerImpl;

public class ThirdPartPublisher {

	public static final String IP = "192.168.1.17"; //Adress.getIpPublic();
	public static final String PORT = "9998";
	public static final String PATH = "/ws/thirdpartpublisher";
	
	public static void main(String[] args) {
		
		
		ThirdPartServerImpl server = new ThirdPartServerImpl();
                
                String IPl = IP;
                String ADRESSE = "http://" + IPl + ":" + PORT + PATH;

		Endpoint.publish(ADRESSE, server);
		System.out.println("Service publié a l'adresse : "+ ADRESSE);
		System.out.println("Theard server created ...");
	}
}
