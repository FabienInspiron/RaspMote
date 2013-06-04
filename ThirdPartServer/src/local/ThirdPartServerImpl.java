package local;
 
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import ws.IRaspberryPi;
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
	 * Constructeur normal
	 */
	public ThirdPartServerImpl() {
		list_clients =  new ArrayList<String>();
		
		/**
		 * Recuperation d'une instance sur le serveur raspberry Pi
		 */
		RaspberryPiServerImplService ra = new RaspberryPiServerImplService();
		rasp = ra.getRaspberryPiServerImplPort();
		
		/**
		 * Souscription au serveur du raspberry Pi
		 */
		rasp.subscribe(78, "http://localhost:9090/");
		
	}
	
	@Override
	public boolean subscribe(int id, String url) {
		return list_clients.add(url);
	}

	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	private void notifyAll(String msg) {
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
	
	/**
	 * Notify a client
	 * @param client
	 */
	private void notify(String client, String msg){
		URL url;
		try {
			url = new URL(msg);
	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	        connection.setDoOutput(true);
	        connection.connect();
	        PrintWriter writer = new PrintWriter(connection.getOutputStream());
	        writer.println(msg);
	        writer.flush();
	        writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void switch_on(int id_outlet) {
		rasp.switchOn(id_outlet);
		notifyAll();
	}

	@Override
	public void switch_off(int id_outlet) {
		rasp.switchOff(id_outlet);
		notifyAll();
	}

	@Override
	public void setTimer(int id_outlet, int timer) {
		rasp.setTimer(id_outlet, timer);
		notifyAll();
	}

	@Override
	public String getListOutlet() {
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
 
	
}