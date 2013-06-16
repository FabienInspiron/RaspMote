package endpoint;

import java.io.ObjectInputStream.GetField;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import local.ThirdPartServerImpl;
import metier.UtilIP;

public class ThirdPartPublisher {

	public static final String IP = "192.168.1.17";
	public static final String PORT = "9998";
	public static final String PATH = "/ws/thirdpartpublisher";

	public static void main(String[] args) {

		ThirdPartServerImpl server = new ThirdPartServerImpl();

		String IPl = null;
		try {
			IPl = UtilIP.getIP();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ADRESSE = "http://" + IPl + ":" + PORT + PATH;

		Endpoint.publish(ADRESSE, server);
		System.out.println("Service publié a l'adresse : " + ADRESSE);
		System.out.println("Theard server created ...");
	}
}
