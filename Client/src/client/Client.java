package client;

import interfaceGraphique.DisplayOutlet;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import local.IThirdPartyServer;
import local.ThirdPartServerImplService;
import ws.Outlet;

/**
 * wsimport -keep http://localhost:9999/ws/thirdpartpublisher?wsdl
 * 
 * @author Belli, Gorrieri
 * 
 */
public class Client {
	public static final String IP_SUBSCRIBE2 = "157.169.102.132";
	public static final String IP_SUBSCRIBE= "192.168.1.17";

	public static int timeStamp = 0;

	// Unique User IDentifier
	//
	private String uuid;

	private NotificationListner listner;

	private static IThirdPartyServer serv;

	public HashMap<Integer, Outlet> list_outlet;

	public List<Integer> list_timer;

	public List<Integer> list_presence;

	public DisplayOutlet dispOut;

	public Client() {
		try {
			listner = new NotificationListner(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ip = null;
		
		try {
			ip = Util.getIP();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serv.subscribe(ip, listner.port);

		/**
		 * Listner on server events
		 */

		listner.start();

		list_outlet = new HashMap<Integer, Outlet>();
		list_presence = new ArrayList<Integer>();

		/**
		 * Stay list up to date
		 */
		updateList();

		list_timer = new ArrayList<Integer>();

		/**
		 * Graphique display
		 */
		 dispOut = new DisplayOutlet(this, serv);
		/* Create and display the form */
		//new mainWindow(this, serv).setVisible(true);
	}

	public static void main(String[] args) {
		
		serv = null;
		ThirdPartServerImplService thirdPart = null;

		try {
			// Server instance
			thirdPart = new ThirdPartServerImplService();

			// Stub of web services
			serv = thirdPart.getThirdPartServerImplPort();

		} catch (Exception e) {
			System.err.println("Connection to the server failed");
			return;
		}

		Client client = new Client();
	}

	/**
	 * Analyse a notification
	 * 
	 * @param not
	 * @throws Exception
	 */
	public void analyseNotification(String not) throws Exception {

		/**
		 * Check time stampe
		 */
		String ret = Util.attribut(not, "timeStampe");
		int val = Integer.parseInt(ret);
		checkTimeStamp(val);

		String mode = Util.attribut(not, "type");
		if (mode.equals("CHANGE")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_outlet.get(id_out).setName(Util.attribut(not, "name"));
			list_outlet.get(id_out).setRoom(Util.attribut(not, "room"));
			list_outlet.get(id_out).setState(
					Boolean.parseBoolean(Util.attribut(not, "state")));

			/**
			 * Stop timer in the outlet
			 */
			if (list_timer.contains(new Integer(id_out)))
				list_timer.remove(new Integer(id_out));
			
			dispOut.createStatusBar(getTimeString() + " - " + "Outlet "+id_out+" change to "+Util.attribut(not, "state")+"...");
			
		}

		if (mode.equals("TIMER")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_timer.add(new Integer(id_out));

			list_outlet.get(id_out).setState(
					Boolean.parseBoolean(Util.attribut(not, "state")));
			
			dispOut.createStatusBar(getTimeString() + " - " + "Timer add...");
		}

		if (mode.equals("PRESENCE")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_presence.add(new Integer(id_out));

			list_outlet.get(id_out).setState(
					Boolean.parseBoolean(Util.attribut(not, "state")));
			
			dispOut.createStatusBar(getTimeString() + " - " + "Presence add on outlet "+id_out+"...");
		}

		if (mode.equals("STOP-PRESENCE")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));

			if (list_presence.contains(new Integer(id_out)))
				list_presence.remove(new Integer(id_out));
			
			dispOut.createStatusBar(getTimeString() + " - " + "Stop presence receive on outlet "+id_out+"...");
		}

		if (mode.equals("STOP-TIMER")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));

			if (list_timer.contains(new Integer(id_out)))
				list_timer.remove(new Integer(id_out));
			
			dispOut.createStatusBar(getTimeString() + " - " + "Stop timer receive on outlet "+id_out+"...");
		}

		if (mode.equals("LIST")) {
			updateList();
			
			dispOut.createStatusBar(getTimeString() + " - " + "List is outdated, need to be update...");
		}
		
		if (mode.equals("ADD")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			String name = Util.attribut(not, "name");
			String room = Util.attribut(not, "room");
			boolean status = Boolean.parseBoolean(Util.attribut(not, "state"));
			
			Outlet o = new Outlet();
			o.setId(id_out);
			o.setName(name);
			o.setRoom(room);
			o.setState(status);
			
			list_outlet.put(id_out, o);
			
			dispOut.createStatusBar(getTimeString() + " - " + "Add "+name+" outlet ...");
		}

		if (mode.equals("DEL")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			String name = Util.attribut(not, "name");
			
			list_outlet.remove(id_out);
			
			dispOut.createStatusBar(getTimeString() + " - " + "DEL"+name+" outlet ...");
		}
		
		dispOut.maj_outlets();

	}

	/**
	 * Check for the time stampe
	 * 
	 * @param value
	 */
	public void checkTimeStamp(int value) {
		if (timeStamp == 0)
			timeStamp = value;

		if (timeStamp++ != value) {
			updateList();
		}
	}

	public HashMap<Integer, Outlet> getList() {
		return list_outlet;
	}

	public List<Outlet> getOutlet() {
		return new ArrayList<Outlet>(list_outlet.values());
	}

	public boolean asTimer(int id_outlet) {
		return list_timer.contains(new Integer(id_outlet));
	}

	public boolean asPresence(int id_outlet) {
		return list_presence.contains(new Integer(id_outlet));
	}

	/**
	 * Error occur, update all list
	 */
	public void updateList() {
		List<Outlet> list = serv.getListOutlet().getItem();
		for (Outlet outlet : list) {
			list_outlet.put(outlet.getId(), outlet);
		}

		list_timer = serv.getListTimer().getItem();
		list_presence = serv.getListPresence().getItem();
	}
	
	public String getTimeString(){
		Calendar calendar = Calendar.getInstance();
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		
		return hours + ":" + minutes;
	}
}