package metier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Class for the outlet
 * @author Belli, Gorrieri
 *
 */
@XmlRootElement(name = "IRaspberryPi", namespace ="http://ws/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outlet", propOrder = {
    "id",
    "state",
    "room",
    "name"}
)
public class Outlet {
	
	/**
	 * Identification of the outlet
	 */
	public int id;
	
	/**
	 * Save the state of the outlet
	 */
	public boolean state;
	
	/**
	 * Name and room of the outlet
	 */
	public String room;
	
	public String name;
	
	public void init(int id, boolean state, String name, String room){
		this.id = id;
		this.state = state;
		this.name = name;
		this.room = room;
	}
}
