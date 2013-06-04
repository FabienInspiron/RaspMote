package endpoint;
 
import javax.xml.ws.Endpoint;

import ws.RaspberryPiServerImpl;

 
public class RaspberryPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:9999/ws/raspberry", new RaspberryPiServerImpl());
	   System.out.println("Lancement du serveur raspberry PI... ");
    }
	
}