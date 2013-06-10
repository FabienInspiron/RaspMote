package client;

import interfaceGraphique.displayOutlet;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import ws.OutletArray;

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
	
	private NotificationListner listner;
	
	private static IThirdPartyServer serv;
	
	public HashMap<Integer, Outlet> list_outlet;
	
	public displayOutlet dispOut;
	
	public Client() {
		try {
			listner = new NotificationListner(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serv.subscribe("localhost",listner.port);
		
		/**
		 * Listner on server events
		 */
		
		listner.start();
		
		list_outlet = new HashMap<Integer, Outlet>();
		
		
		OutletArray tab = serv.getListOutlet();
		updateList(tab.getItem());
		
		/**
		 * Graphique display
		 */
		dispOut = new displayOutlet(this, serv);
	}

	public static void main(String[] args) {
		 
		serv = null;
		ThirdPartServerImplService thirdPart = null;
		
		try {
			// Server instance
			thirdPart = new ThirdPartServerImplService();
			
			// Stub of web services
			serv = thirdPart.getThirdPartServerImplPort();
			
		} catch(Exception e){
			System.err.println("Connection to the server failed");
			return;
		}

		Client client = new Client();
    }
	
	/**
	 * Analyse a notification
	 * @param not
	 * @throws Exception
	 */
	public void analyseNotification(String not) throws Exception{
		
		/**
		 * Check time stampe
		 */
		String ret = Util.attribut(not, "timeStampe");
		int val = Integer.parseInt(ret);
		checkTimeStamp(val);
		
		String mode = Util.attribut(not, "type");
		if(mode.equals("CHANGE")){
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_outlet.get(id_out).setName(Util.attribut(not, "name"));
			list_outlet.get(id_out).setRoom(Util.attribut(not, "room"));
			list_outlet.get(id_out).setState(Boolean.parseBoolean(Util.attribut(not, "state")));
		}
		
		dispOut.dispose();
		dispOut = new displayOutlet(this, serv);
	}
	
	/**
	 * Check for the time stampe
	 * @param value
	 */
	public void checkTimeStamp(int value){
		if(timeStamp == 0)
			timeStamp = value;
		
		if(timeStamp++ != value){
			System.out.println("Erreur, un message non reçu");
		}
	}
	
	/**
	 * Update the entire list
	 * @param list
	 */
	public void updateList(List<Outlet> list){
		for (Outlet outlet : list) {
			list_outlet.put(outlet.getId(), outlet);
		}
	}
	
	public HashMap<Integer, Outlet> getList(){
		return list_outlet;
	}
	
	public List<Outlet> getOutlet(){
		return new ArrayList<Outlet>(list_outlet.values());
	}
}