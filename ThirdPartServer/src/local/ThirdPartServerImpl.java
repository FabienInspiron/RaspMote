package local;
 
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import metier.Adress;
import metier.User;
import net.java.dev.jaxb.array.IntArray;
import ws.IRaspberryPi;
import ws.OutletArray;
import ws.RaspberryPiServerImplService;
 
@WebService(endpointInterface = "local.IThirdPartyServer")
public class ThirdPartServerImpl implements IThirdPartyServer{
	
	public static final int START_PORT = 9990;
	
	/**
	 * List of all the subscribers
	 */
	private List<Adress> list_clients;
	
	/**
	 * References on the raspberry pi server
	 * @param args
	 */
	private IRaspberryPi rasp;
	
	
	/**
	 * List of the users
	 */
	private List<User> users;
	
	
	private int port_ecoute;
	
	private NotificationListner listner;
	
	/**
	 * Normal constructor
	 */
	public ThirdPartServerImpl() {
		list_clients =  new ArrayList<Adress>();
		users = new ArrayList<User>();
		
		// Launch listner
		try {
			listner = new NotificationListner(this);
		} catch(IOException e) {
			System.err.println("Impossible de lancer le listner !!");
		}
		
		/**
		 * Get an instance of the Rapsberry Pi server
		 */
		RaspberryPiServerImplService ra = new RaspberryPiServerImplService();
		rasp = ra.getRaspberryPiServerImplPort();
		
		/**
		 * Subscribe to the notification system
		 */
		rasp.subscribe("localhost", listner.port);
		listner.start();
	}
	
	@Override
	public int subscribe(String host, int port) {
		Adress insock = new Adress(host, port);
		
		if(!list_clients.contains(insock)){
			list_clients.add(insock);
			System.out.println("Ajout de " + insock);
		}
		
		return 1;
	}

	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	public void notifyAllClients(String msg) {
		if(msg == null) return;
		
		System.out.println("Notification...");
		
		// Temporare list
		List<Adress> list_client_tmp = new ArrayList<Adress>(list_clients);
		
		for(Adress u : list_client_tmp) {	
			try
	        {	
				System.out.println("Notification vers : " + u.getHost() +":"+ u.getPort());
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
	
	@Override
	public void switch_on(int id_outlet) {
		rasp.switchOn(id_outlet);
	}

	@Override
	public void switch_off(int id_outlet) {
		rasp.switchOff(id_outlet);
	}

	@Override
	public void setTimer(int id_outlet, int timer, int mode) {
		rasp.setTimer(id_outlet, timer, mode);
	}

	@Override
	public OutletArray getListOutlet() {
		return rasp.getListOutlet();
	}

	@Override
	public void swith_on_all() {
		// TODO Auto-generated method stub
	}

	@Override
	public void swith_off_all() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Create a new user
	 * @param username
	 * @param password
	 */
	public void sign_in(String username, String password){
		User u = new User(username, password);
		users.add(u);
	}
	
	/**
	 * Check if the user is present
	 * @param username
	 * @param password
	 */
	public User sign_up(String username, String password){
		User us = new User(username, password);
		
		for (User u : users) {
			if(u.equals(us))
				return u;
		}
		
		return null;
	}
	
	public int getPortEcoute(){
		return port_ecoute;
	}

	@Override
	public void simulatePresence(int outlet, int timeMax) {
		rasp.setPresenceSimulator(outlet, timeMax);
	}

	@Override
	public void stopPresenceSimulator(int outlet) {
		rasp.stopPresenceSimulator(outlet);
	}

	@Override
	public void switch_outlet(int idOutlet) {
		rasp.switchOutlet(idOutlet);
	}

	@Override
	public float getTimer(int idOutlet) {
		rasp.getTimer(idOutlet);
		return 0;
	}

	@Override
	public boolean isPresence(int idOutlet) {
		return rasp.isPresence(idOutlet);
	}

	@Override
	public void stopTimer(int id_outlet) {
		rasp.stopTimer(id_outlet);
	}

	@Override
	public IntArray getListTimer() {
		return rasp.getListTimer();
	}

	@Override
	public IntArray getListPresence() {
		return rasp.getListPresence();
	}
}