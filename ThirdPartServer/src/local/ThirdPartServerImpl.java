package local;
 
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import metier.User;
import ws.IRaspberryPi;
import ws.OutletArray;
import ws.RaspberryPiServerImplService;
 
@WebService(endpointInterface = "local.IThirdPartyServer")
public class ThirdPartServerImpl implements IThirdPartyServer{

	/**
	 * List of all the subscribers
	 */
	private List<String> list_clients;
	
	/**
	 * References on the raspberry pi server
	 * @param args
	 */
	private IRaspberryPi rasp;
	
	
	/**
	 * List of the users
	 */
	private List<User> users;
	
	/**
	 * Normal constructor
	 */
	public ThirdPartServerImpl() {
		list_clients =  new ArrayList<String>();
		users = new ArrayList<User>();
		
		/**
		 * Get an instance of the Rapsberry Pi server
		 */
		RaspberryPiServerImplService ra = new RaspberryPiServerImplService();
		rasp = ra.getRaspberryPiServerImplPort();
		
		/**
		 * Subscribe to the notification system
		 */
		rasp.subscribe(78, "http://localhost:9090/");
	}
	
	@Override
	public boolean subscribe(int id, String url) {
		if(!list_clients.contains(url))
			return list_clients.add(url);
		else
			return false;
	}

	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	public void notifyAllClients(String msg) {
		System.out.println("NotifyAll " + msg);
		
		for(String u : list_clients) {
			try
	        {
				URL url = new URL(u);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                connection.connect();
                PrintWriter writer = new PrintWriter(connection.getOutputStream());
                writer.println(msg);
                writer.flush();
                writer.close();
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
	public void setTimer(int id_outlet, int timer) {
		rasp.setTimer(id_outlet, timer);
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
}