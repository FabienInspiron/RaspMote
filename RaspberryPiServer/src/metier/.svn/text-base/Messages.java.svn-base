package metier;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Messages which are send with notifications
 * @author belli
 *
 */
public class Messages {
	
	/**
	 * Xstream to send data
	 */
	public static XStream xstream = new XStream(new DomDriver());

	public static int time = 0;
	
	/**
	 * Construct the notify xml query to send
	 * @param out
	 * @return
	 */
	public static String addOutlet(Outlet out) {
		MessageSend ms = new MessageSend("ADD", out, time++);
		return xstream.toXML(ms);
	}

	/**
	 * Remove an outlet
	 * @param out the outlet to remove
	 * @return
	 */
	public static String removeOutlet(Outlet out){
		MessageSend ms = new MessageSend("ADD", out,time++);
		return xstream.toXML(ms);
	}
	
	
	public static String listNeedToBeUpdate(){
		MessageSend ms = new MessageSend("LIST", null,time++);
		return xstream.toXML(ms);
	}
	
	public static String outletChange(Outlet out){
		MessageSend ms = new MessageSend("CHANGE", out,time++);
		return xstream.toXML(ms);
	}
	
	/***
	 * Convert an object 
	 * @param outl
	 * @return
	 */
	public static String toStringOutlet(Outlet outl){
		XStream xstream = new XStream(new DomDriver());
		return xstream.toXML(outl);
	}
}
