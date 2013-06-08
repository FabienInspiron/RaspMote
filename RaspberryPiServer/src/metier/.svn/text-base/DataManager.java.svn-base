package metier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DataManager {
	private static DataManager instance = new DataManager();
	
	public static final int START_PORT = 9990;
	
	private static int LastId;
	
	/**
	 * List of serveur client
	 */
	List<Adress> list_server; 
	
	/**
	 * List of outlet
	 */
	List<Outlet> list_outlet;
	
	/**
	 * File where outlets are serialized
	 */
	File file_outlet = new File("outlets.xml");
	
	private DataManager() {
		list_server = new ArrayList<Adress>();
		list_outlet = new ArrayList<Outlet>();
		LastId = 0;
	}

	/**
	 * Return singleton instance
	 * @return
	 */
	public static DataManager getInstance() {
		return instance;
	}
	
	/**
	 * Get outlets from xml file
	 */
	private void loadOutlet(){
		try {
			XStream xstream = new XStream(new DomDriver());
			FileInputStream fis = new FileInputStream(file_outlet);
			list_outlet = (List<Outlet>) xstream.fromXML(fis);
		} catch (Exception e) {
			list_outlet = new ArrayList<Outlet>();
		} 
	}
	
	/**
	 * Serialize outlets list
	 */
	public void storeOutlet(){
		try {
			XStream xstream = new XStream(new DomDriver());
			FileOutputStream fos = new FileOutputStream(file_outlet);
			xstream.toXML(list_outlet, fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a server to notify
	 * @param url
	 */
	public void addServer(Adress at) {
		list_server.add(at);
	}
	
	/**
	 * Remove a server to notify
	 * @param url
	 */
	public void removeServer(String url) {
		list_server.remove(url);
	}
	
	public List<Adress> getListClient() {
		return list_server;
	}
	
	/**
	 * Add an outlet
	 * @param outl
	 */
	public int addOutlet(Outlet outl){
		outl.id = LastId;
		LastId++;
		list_outlet.add(outl);
		//storeOutlet();
		
		notifyAllClients(Messages.addOutlet(outl));
		return outl.id;
	}
	
	/**
	 * Remove an outlet
	 * @param id
	 */
	public void removeOutlet(int id) {
		Iterator<Outlet> it = list_outlet.iterator();
		while(it.hasNext()) {
			Outlet o = it.next();
			if(o.id == id) {
				it.remove();
				break;
			}
		}
	}
	
	/**
	 * Return the outlet designed by id
	 * @param id
	 * @return
	 */
	public Outlet getOutlet(int id){
		for (Outlet o : list_outlet) {
			if(o.getID() == id)
				return o;
		}
		
		return null;
	}
	
	public List<Outlet> getListOutlet() {
		return list_outlet;
	}

	
	/**
	 * Add a timer to an outlet
	 * @param id_outlet
	 * @param timer
	 */
	public void setTimer(int id_outlet, int timer) {
		System.out.println("set_timer");
		
		notifyAllClients("");
	}
	
	/**
	 * Change state of an outlet to true
	 * @param id_outlet
	 */
	public void switch_on(int id_outlet) {
		System.out.println("switch_on");
		
		Outlet o = getOutlet(id_outlet);
		if(o != null)
			o.switch_on();
		
		notifyAllClients(Messages.outletChange(getOutlet(id_outlet)));
	}
	
	/**
	 * Change state of an outlet to false
	 * @param id_outlet
	 */
	public void switch_off(int id_outlet) {
		System.out.println("switch_off");
		
		Outlet o = getOutlet(id_outlet);
		if(o != null)
			o.switch_on();
		
		notifyAllClients(Messages.outletChange(getOutlet(id_outlet)));
	}
	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	private void notifyAllClients(String msg) {
		if(msg == null)
			return;
		
		// Temporare list
		List<Adress> list_client_tmp = new ArrayList<Adress>(list_server);
				
		for(Adress u : list_client_tmp) {
			try
	        {
				Socket socket = new Socket(u.getHost(), u.getPort());

		        PrintWriter pred = new PrintWriter(
		                             new BufferedWriter(
		                                new OutputStreamWriter(socket.getOutputStream())),
		                             true);

		        /**
		         * Delete all the cariage return because the are used to
		         * put the end of a socket.
		         */
		        msg = msg.trim();
		        msg = msg.replaceAll("\\n", "");
		        msg = msg.replaceAll("\\r\\n", ""); 
		        msg += "\n";
		        
		        pred.println(msg);
		        
		        pred.flush();
		        pred.close();
		        
		        socket.close();
	        }catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Return a free port available for the host
	 * This méthode try to found the open port and avoid it
	 * @param host
	 * @return
	 */
	public int getFreePort(String host) {
		int port = START_PORT;
		
		boolean boucle = true;
		
		/**
		 * Test if a port is free
		 */
		while (boucle){
			try{
				Socket socket = new Socket(host, port);
				boucle = true;
				port++;
				socket.close();
			}catch (Exception e) { 
				boucle = false;
			}
		}
		
		System.out.println("free port found : " + port);
		return port;
	}
}
