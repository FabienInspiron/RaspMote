package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import metier.DataManager;
import metier.Outlet;

@WebService(endpointInterface = "ws.IRaspberryPiAdmin")
public class RaspberryPiAdminServerImpl implements IRaspberryPiAdmin{

	DataManager dataManager;
	
	/**
	 * Normal contructor
	 */
	public RaspberryPiAdminServerImpl() {
		dataManager = DataManager.getInstance();
	}

	@Override
	@WebMethod
	public int add_outlet(String room, String name) {
		Outlet o = new Outlet();
		o.name = name;
		o.room = room;
		return dataManager.addOutlet(o);
	}

	@Override
	@WebMethod
	public void remove_outlet(int id) {
		dataManager.removeOutlet(id);
	}

	@Override
	@WebMethod
	public Outlet get_outlet(int id) {
		return dataManager.getOutlet(id);
	}

	@Override
	@WebMethod
	public void update_outlet(int id, String name, String room, boolean state) {
		dataManager.updateOutlet(id, name, room, state);
	}
}
