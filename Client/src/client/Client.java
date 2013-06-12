package client;

import interfaceGraphique.displayOutlet;
import interfaceGraphique.mainWindow;
import interfaceGraphique.timerSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import local.IThirdPartyServer;
import local.ThirdPartServerImplService;
import ws.Outlet;
import ws.OutletArray;

/**
 * wsimport -keep http://localhost:9999/ws/thirdpartpublisher?wsdl
 * 
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

	public List<Integer> list_timer;

	public List<Integer> list_presence;

	public displayOutlet dispOut;

	public Client() {
		try {
			listner = new NotificationListner(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		serv.subscribe("localhost", listner.port);

		/**
		 * Listner on server events
		 */

		listner.start();

		list_outlet = new HashMap<Integer, Outlet>();
		list_presence = new ArrayList<Integer>();

		OutletArray tab = serv.getListOutlet();
		updateList(tab.getItem());
		list_timer = new ArrayList<Integer>();

		/**
		 * Graphique display
		 */
		 dispOut = new displayOutlet(this, serv);
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
		}

		if (mode.equals("TIMER")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_timer.add(new Integer(id_out));

			list_outlet.get(id_out).setState(
					Boolean.parseBoolean(Util.attribut(not, "state")));
		}

		if (mode.equals("PRESENCE")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));
			list_presence.add(new Integer(id_out));

			list_outlet.get(id_out).setState(
					Boolean.parseBoolean(Util.attribut(not, "state")));
		}

		if (mode.equals("STOP-PRESENCE")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));

			if (list_presence.contains(new Integer(id_out)))
				list_presence.remove(new Integer(id_out));
		}

		if (mode.equals("STOP-TIMER")) {
			int id_out = Integer.parseInt(Util.attribut(not, "id"));

			if (list_timer.contains(new Integer(id_out)))
				list_timer.remove(new Integer(id_out));
		}

		if (mode.equals("LIST")) {
			updateList();
		}

		dispOut.maj_outlets();

		// dispOut.dispose();
		// dispOut = new displayOutlet(this, serv);
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

	/**
	 * Update the entire list
	 * 
	 * @param list
	 */
	public void updateList(List<Outlet> list) {
		for (Outlet outlet : list) {
			list_outlet.put(outlet.getId(), outlet);
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
}