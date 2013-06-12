package metier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ws.IRaspberryPi;

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
	HashMap<Integer, Outlet> list_outlet;
	
	/**
	 * List timer
	 */
	HashMap<Integer, Thread> list_timer;
	
	
	/**
	 * List of PresenceSimulatorSThread
	 */
	HashMap<Integer, ThreadPresenceSimulator> list_presence_simulator;
	
	/**
	 * File where outlets are serialized
	 */
	File file_outlet = new File("outlets.xml");
	
	private DataManager() {
		list_server = new ArrayList<Adress>();
		list_outlet = new HashMap<Integer, Outlet>();
		list_timer = new HashMap<Integer, Thread>();
		list_presence_simulator = new HashMap<Integer, ThreadPresenceSimulator>();
		
		loadOutlet();
		
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
		System.out.println("chargement");
		try {
			XStream xstream = new XStream(new DomDriver());
			FileInputStream fis = new FileInputStream(file_outlet);
			list_outlet = (HashMap<Integer, Outlet>) xstream.fromXML(fis);
		} catch (Exception e) {
			System.out.println("erreur chargemenr");
			list_outlet = new HashMap<Integer, Outlet>();
		} 
	}
	
	/**
	 * Serialize outlets list
	 */
	public void storeOutlet(){
		System.out.println("save");
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
		if(!list_server.contains(at))
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
	 * Get an outlet
	 * @param id
	 * @return
	 */
	public Outlet getOutlet(int id) {
		return list_outlet.get(id);
	}
	
	/**
	 * Add an outlet
	 * @param outl
	 */
	public int addOutlet(Outlet outl){
		outl.id = LastId;
		LastId++;
		list_outlet.put(new Integer(outl.id), outl);
		storeOutlet();
		
		notifyAllClients(Messages.addOutlet(outl));
		return outl.id;
	}
	
	/**
	 * Update an outlet
	 * @param id
	 * @param name
	 * @param room
	 * @param state
	 */
	public void updateOutlet(int id, String name, String room, boolean state) {
		Outlet o = getOutlet(id);
		o.name = name;
		o.room = room;
		o.state = state;
		
		storeOutlet();
	}
	
	/**
	 * Remove an outlet
	 * @param id
	 */
	public void removeOutlet(int id) {
		list_outlet.remove(id);
	}
	
	/**
	 * Return the hashmap
	 * @return
	 */
	public HashMap<Integer, Outlet> getHashOutlet() {
		return list_outlet;
	}
	
	/**
	 * Return a list of outlet
	 * @return
	 */
	public ArrayList<Outlet> getListOutlet() {
		return new ArrayList<Outlet>(list_outlet.values());
	}

	/**
	 * Return a list of the integer outlet
	 * @return
	 */
	public ArrayList<Integer> getListOutletKey() {
		return new ArrayList<Integer>(list_outlet.keySet());
	}

	
	/***
	 * Add a timer to an outlet
	 * @param id_outlet
	 * @param rasp
	 * @param timer in secondes
	 * @param mode
	 */
	public void setTimer(int id_outlet, IRaspberryPi rasp, int timer, int mode) {
		TimerLauch lauch = new TimerLauch(id_outlet,rasp,timer,mode);
		Thread t = new Thread(lauch);
		t.start();
		
		list_timer.put(new Integer(id_outlet), t);
		
		notifyAllClients(Messages.outletTimer(list_outlet.get(id_outlet), lauch.getTime()));
	}
	
	/**
	 * Change state of an outlet to true
	 * @param id_outlet
	 */
	public void switch_on(int id_outlet) {
		System.out.println("switch_on " + id_outlet);
		
		Outlet o = list_outlet.get(new Integer(id_outlet));
		
		if(o != null){
			o.switch_on();
		}
		
		notifyAllClients(Messages.outletChange(o));
	}
	
	/**
	 * Change state of an outlet to false
	 * @param id_outlet
	 */
	public void switch_off(int id_outlet) {
		System.out.println("switch_off" + id_outlet);
		
		Outlet o = list_outlet.get(id_outlet);
		if(o != null)
			o.switch_off();
		
		notifyAllClients(Messages.outletChange(o));
	}
	/**
	 * Notify all clients in list_clients
	 * @param notify
	 */
	private void notifyAllClients(String msg) {
		if(msg == null)
			return;
		
		System.out.println("Notification...");
		
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
		         * Delete all the carriage return because the are used to
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
		        
		        storeOutlet();
	        }catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Lauch presence simulator ans put it in the list
	 * @param numOutlet
	 * @param rasp
	 * @param time
	 */
	public void simulatePresence(int numOutlet,IRaspberryPi rasp, int time){
		ThreadPresenceSimulator presence = new ThreadPresenceSimulator(numOutlet, rasp, time);
		list_presence_simulator.put(new Integer(numOutlet), presence);
		
		Thread t = new Thread(presence);
		t.start();
		
		notifyAllClients(Messages.outletPresence(list_outlet.get(numOutlet)));
	}
	
	/**
	 * Stop the simulation
	 * @param numOutlet is he outlet to stop
	 */
	public void stopSimulatePresence(int numOutlet){
		ThreadPresenceSimulator theThread = list_presence_simulator.get(new Integer(numOutlet));
		
		if(theThread!=null){
			theThread.stop();
		
			// Del frome the list
			list_presence_simulator.remove(new Integer(numOutlet));
		}
		
		notifyAllClients(Messages.outletSTOPPresence(list_outlet.get(numOutlet)));
	}
	
	/**
	 * Stop a timer and remove from the list
	 * @param numOutlet
	 */
	public void stopTimer(int numOutlet){
		Thread t = list_timer.get(new Integer(numOutlet));
		t.stop();
		
		list_timer.remove(new Integer(numOutlet));
		
		notifyAllClients(Messages.outletSTOPTimer(list_outlet.get(numOutlet)));
	}
	
	public void afficherListe(){
		List<Outlet> l = getListOutlet();
		for (Outlet o : l) {
			System.out.println(o);
		}
	}
	
	public void switchOutlet(int idOutlet){
		System.out.println("switch");
		Outlet o = list_outlet.get(new Integer(idOutlet));
		
		if(o != null){
			o.switch_outlet();
		}
		
		notifyAllClients(Messages.outletChange(o));
	}
	
	public float getTimer(int idOutlet) {
		Thread t = list_timer.get(new Integer(idOutlet));
		System.out.println("get timer");
		
		if (t != null)
			return 0;
		else
			return 0;
	}
	
	public boolean isPresence(int idOutlet) {
		return list_presence_simulator.containsKey(idOutlet);
	}
	
	public Integer[] getListTimer(){
		return (Integer[]) list_timer.keySet().toArray();
	}
	
	public Integer[] getListPresence(){
		return (Integer[]) list_presence_simulator.keySet().toArray();
	}
}
