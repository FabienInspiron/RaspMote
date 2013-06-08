package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style = Style.RPC)
public interface IRaspberryPiAdmin {
	/**
	 * Subscribe to a server
	 * @param url your own url
	 * @return new Id
	 */
	@WebMethod int add_outlet(String room, String name);
	
	/**
	 * Switch the light on
	 * @param id_outlet identification of the outlet
	 */
	@WebMethod void remove_outlet(int id);
}
