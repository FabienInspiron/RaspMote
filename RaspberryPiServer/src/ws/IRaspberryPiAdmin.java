package ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import metier.Outlet;
import metier.FullOutlet;


@WebService
@SOAPBinding(style = Style.RPC)
public interface IRaspberryPiAdmin {
	/**
	 * Subscribe to a server
	 * @param url your own url
	 * @return new Id
	 */
	@WebMethod int add_outlet(String room, String name, String numCom, String numOutlet);
	
	/**
	 * Switch the light on
	 * @param id_outlet identification of the outlet
	 */
	@WebMethod void remove_outlet(int id);
	
	@WebMethod FullOutlet get_outlet(int id);
	
	@WebMethod FullOutlet[] get_list_outlet();
	
	@WebMethod void update_outlet(int id, String name, String room, boolean state, String numCom, String numOutlet);
}
