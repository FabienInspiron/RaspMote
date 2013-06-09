package local;
 
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import ws.OutletArray; 

@WebService
@SOAPBinding(style = Style.RPC)
public interface IThirdPartyServer{
 
	/**
	 * Subscribe to a server
	 * @param host is you
	 * @return
	 */
	@WebMethod int subscribe(String host);
	
	/**
	 * Switch the light on
	 * @param id_outlet identification of the outlet
	 */
	@WebMethod void switch_on(int id_outlet);
	
	/**
	 * Switch the light off
	 * @param id_outlet identification of the outlet
	 */
	@WebMethod void switch_off(int id_outlet);
	
	/**
	 * Put a timer to the outlet number id_outlet
	 * @param id_outlet the outlet 
	 * @param timer the time in second
	 */
	@WebMethod void setTimer(int id_outlet, int timer, int mode);
	
	/**
	 * Return the list of the outlet
	 */
	@WebMethod OutletArray getListOutlet();
	
	/**
	 * Switch all the outlet on
	 */
	@WebMethod void swith_on_all();
	
	/**
	 * switch all the outlet off
	 */
	@WebMethod void swith_off_all();
 
	/**
	 * Simulate a presence with a thread
	 * @param outlet
	 * @param timeMax
	 */
	@WebMethod void simulatePresence(int outlet, int timeMax);
	
	@WebMethod void stopPresenceSimulator(int outlet);
	
}