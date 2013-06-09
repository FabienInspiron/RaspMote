package client;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import local.IThirdPartyServer;
import local.ThirdPartServerImplService;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ws.Outlet;

/**
 * wsimport -keep http://localhost:9999/ws/thirdpartpublisher?wsdl
 * @author Belli, Gorrieri
 *
 */
public class Client {
	public static final String IP_SUBSCRIBE = "127.0.0.1";
	
	public static int timeStamp = 0;
	
	// Unique User IDentifier
	//
	private String uuid;

	public static void main(String[] args) {
		 
		IThirdPartyServer serv = null;
		ThirdPartServerImplService thirdPart = null;
		Client client = new Client();
		
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
		Thread d = new Thread(new NotificationListner(port, client));
		d.start();

		
//		List<Outlet> ls = serv.getListOutlet().getItem();
//		for(Outlet o : ls) {
//			System.out.println(outletToString(o));
//		}
//		
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
		
		serv.setTimer(45, 3, 1);
		serv.setTimer(45, 4, 1);
		serv.setTimer(45, 5, 1);
		serv.setTimer(45, 6, 1);
		
		Scanner sc = new Scanner(System.in);
		sc.next();
    }
	
	public static String outletToString(Outlet o) {
		return "<Outlet  id=" + o.getId() + ", state=" + o.isState() + ", room=" + o.getRoom()
				+ ", name=" + o.getName() + ">";
	}
	
	public void analyseNotification(String not) throws Exception{
		String ret = findAttribut(not, "<timeStampe>", "</timeStampe>");
		int val = Integer.parseInt(ret);
		checkTimeStamp(val);
	}
	
	public static String findAttribut(String msg, String prefixe, String suffixe) throws Exception 
    {         
        Scanner sc = new Scanner(msg);
         
        while (sc.hasNextLine()) 
        { 
            String line = sc.nextLine(); 
             
            int a = line.indexOf(prefixe); 
            if (a!=-1) 
            { 
                int b = line.indexOf(suffixe,a); 
                if (b!=-1) 
                { 
                    sc.close(); 
                    return line.substring(a+prefixe.length(),b); 
                } 
            } 
        } 
         
        sc.close(); 
        return null; 
    } 
	
	public void checkTimeStamp(int value){
		if(timeStamp == 0)
			timeStamp = value;
		
		if(timeStamp++ != value){
			System.out.println("Erreur, un message non reçu");
		}
	}
}