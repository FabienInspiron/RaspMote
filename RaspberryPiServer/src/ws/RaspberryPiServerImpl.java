package ws;

import javax.jws.WebService;

import metier.Adress;
import metier.DataManager;
import metier.Outlet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@WebService(endpointInterface = "ws.IRaspberryPi")
public class RaspberryPiServerImpl implements IRaspberryPi{
	
	DataManager dataManager;
	
	/**
	 * Normal contructor
	 */
	public RaspberryPiServerImpl() {
		dataManager = DataManager.getInstance();
	}
	
	/**
	 * Subscripte to all notification
	 * return the port to listen
	 */
	@Override
	public int subscribe(String host) {
		int port = dataManager.getFreePort(host);
		
		//InetSocketAddress insock = new InetSocketAddress(host, port);
		Adress add = new Adress(host, port);
		
		dataManager.addServer(add);
		return port;
	}

	@Override
	public void switch_on(int id_outlet) {
		dataManager.switch_on(id_outlet);
	}

	@Override
	public void switch_off(int id_outlet) {
		dataManager.switch_off(id_outlet);
	}

	@Override
	public void setTimer(int id_outlet, int timer, int mode) {
		dataManager.setTimer(id_outlet, this, timer, mode);
	}

	@Override
	public Outlet[] getListOutlet() {
		Outlet[] t = new Outlet[dataManager.getListOutlet().size()];
		return dataManager.getListOutlet().toArray(t);
	}
	
	public String getListOutletXML(){
		XStream xstream = new XStream(new DomDriver());
		return xstream.toXML(dataManager.getListOutlet());
	}

	@Override
	public void setPresenceSimulator(int idOutlet, int timeMax) {
		dataManager.simulatePresence(idOutlet, this, timeMax);
	}

	@Override
	public void stopPresenceSimulator(int idOutlet) {
		dataManager.stopSimulatePresence(idOutlet);	
	}
}
