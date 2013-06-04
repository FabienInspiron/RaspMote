package ws;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface = "ws.IRaspberryPi")
public class RaspberryPiServerImpl implements IRaspberryPi{

	List<String> list_server = new ArrayList<String>();
	
	@Override
	public void subscribe(int id, String url) {
		list_server.add(url);
	}
	
	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	private void notifyAll(String msg) {
		for(String u : list_server) {
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
		System.out.println("switch_on");
	}

	@Override
	public void switch_off(int id_outlet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimer(int id_outlet, int timer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getListOutlet() {
		return "list";
	}

}
