package endpoint;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Endpoint;

import metier.Messages;
import metier.Outlet;

import ws.RaspberryPiServerImpl;

 
public class RaspberryPublisher{
 
	public static final String ADRESSE = "http://localhost:9999/ws/raspberry";
	
	public static void main(String[] args) {
	   RaspberryPiServerImpl rasp = new RaspberryPiServerImpl();
	   Endpoint.publish(ADRESSE, rasp);
	   System.out.println("Lancement du serveur raspberry PI... ");  
	   System.out.println("Service publié à l'adresse : "+ADRESSE);
    }
}