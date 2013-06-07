package ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import metier.Messages;
import metier.Outlet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@WebService(endpointInterface = "ws.IRaspberryPi")
public class RaspberryPiServerImpl implements IRaspberryPi{

	List<String> list_server; 
	List<Outlet> list_outlet;
	
	/**
	 * Normal contructor
	 */
	public RaspberryPiServerImpl() {
		list_server = new ArrayList<String>();
		list_outlet = new ArrayList<Outlet>();
		Outlet o = new Outlet();
		o.init(0, false, "prise1", "salle1");
		list_outlet.add(o);
		Outlet o1 = new Outlet();
		o1.init(1, false, "prise2", "salle1");
		list_outlet.add(o1);
		for(Outlet ou : list_outlet) {
			System.out.println(Outlet.OutletToString(ou));
		}
		//loadOutlet();
	}
	
	@Override
	public void subscribe(int id, String url) {
		list_server.add(url);
	}
	
	/**
	 * Notify to all the client in list_clients
	 * @param notify
	 */
	private void notifyAllClients(String msg) {
		System.out.println("Notify ");
		
		for(String u : list_server) {
			try
	        {
				URL url = new URL(u);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.connect();
                PrintWriter writer = new PrintWriter(connection.getOutputStream());
                
                msg += "\n";
                
                writer.println("test"+"\n");
                
                System.out.print(msg);
                writer.flush();
                writer.close();
                
				 /*
				URL url = new URL(u);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.connect();
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write("nom=dupont");
				writer.flush();
				  */
				
	        }catch(Exception e){e.printStackTrace();}
		}
		
		System.out.println("ici");
	}

	@Override
	public void switch_on(int id_outlet) {
		System.out.println("switch_on");
		
		Outlet o = getOutlet(id_outlet);
		if(o != null)
			o.switch_on();
		
		notifyAllClients(Messages.outletChange(getOutlet(id_outlet)));
	}

	@Override
	public void switch_off(int id_outlet) {
		System.out.println("switch_off");
		
		Outlet o = getOutlet(id_outlet);
		if(o != null)
			o.switch_off();
		
		notifyAllClients(Messages.outletChange(getOutlet(id_outlet)));
	}

	@Override
	public void setTimer(int id_outlet, int timer) {
		System.out.println("set_timer");
		
		notifyAllClients("");
	}

	@Override
	public Outlet[] getListOutlet() {
		Outlet[] t = new Outlet[list_outlet.size()];
		Outlet[] t2 = list_outlet.toArray(t);
		System.out.println(list_outlet);
		return t2;
	}
	
	/**
	 * Add an outlet
	 * @param outl
	 */
	public void addOutlet(Outlet outl){
		list_outlet.add(outl);
		storeOutlet();
		
		notifyAllClients(Messages.addOutlet(outl));
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
	
	public String getListOutletXML(){
		XStream xstream = new XStream(new DomDriver());
		return xstream.toXML(list_outlet);
	}
	
	File file_outlet = new File("outlets.xml");
	
	/**
	 * Sérialiser une personne dans un fichier
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
	 * Déserialiser les tweets
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
}
