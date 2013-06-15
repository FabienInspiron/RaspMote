package ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import metier.DataManager;
import metier.Outlet;
import metier.FullOutlet;

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
	public int add_outlet(String room, String name, String numCom, String numOutlet) {
		FullOutlet o = new FullOutlet();
		o.init(-1, false, name, room, numCom, numOutlet);
		return dataManager.addOutlet(o);
	}

	@Override
	@WebMethod
	public void remove_outlet(int id) {
		dataManager.removeOutlet(id);
	}

	@Override
	@WebMethod
	public FullOutlet get_outlet(int id) {
		return dataManager.getOutletComplet(id);
	}

	@Override
	@WebMethod
	public void update_outlet(int id, String name, String room, boolean state, String comNb, String nbId) {
		dataManager.updateOutlet(id, name, room, state, comNb, nbId);
	}

	@Override
	@WebMethod
	public FullOutlet[] get_list_outlet() {
		FullOutlet[] t = new FullOutlet[dataManager.getListOutletComplet().size()];
		return dataManager.getListOutletComplet().toArray(t);
	}
}
