package endpoint;
 
import javax.xml.ws.Endpoint;

import local.ThirdPartServerImpl;

 
public class ThirdPartPublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:9998/ws/thirdpartpublisher", new ThirdPartServerImpl());
	   System.out.println("Server created");
    }
}