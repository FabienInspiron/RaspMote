package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import metier.Outlet;


@WebService
@SOAPBinding(style = Style.RPC)
public interface IRaspberryPi {
	/**
	 * Subscribe to a server
	 * @param host of the client
	 * @return
	 */
	@WebMethod int subscribe(String host, int port);
	
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
	@WebMethod public void setTimer(int id_outlet, int timer, int mode);
	
	@WebMethod public void stopTimer(int id_outlet);
	
	/**
	 * Put a presence simulator
	 * @param idOutlet
	 * @param timeMax
	 */
	@WebMethod void setPresenceSimulator(int idOutlet, int timeMax);
	
	@WebMethod void stopPresenceSimulator(int idOutlet);
	
	@WebMethod void switchOutlet(int idOutlet);
	
	/**
	 * Return the list of the outlet
	 */
	@WebMethod Outlet[] getListOutlet();

	/**
	 * Return the list of the outlet in XML Format
	 * @return
	 */
	@WebMethod String getListOutletXML();
	
	/**
	 * Return the end of the timer
	 * @param idOutlet
	 * @return
	 */
	@WebMethod float getTimer(int idOutlet);
	
	@WebMethod boolean isPresence(int idOutlet);
	
	@WebMethod Integer[] getListTimer();
	
	@WebMethod Integer[] getListPresence();
}
