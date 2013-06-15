package metier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "IRaspberryPiAdmin", namespace ="http://ws/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "full_outlet", propOrder = {
    "comNb",
    "nbId"}
)
public class FullOutlet extends Outlet {
	public String comNb;
	public String nbId;
	
	public void init (int id, boolean state, String name, String room, String comNb, String nbId) {
		super.init(id, state, name, room);
		this.comNb = comNb;
		this.nbId = nbId;
	}
	
	/**
	 * switch the outlet on
	 */
	public void switch_on(){
		state = true;
		// TODO execute commande
	}
	
	/**
	 * switch the outlet off
	 */
	public void switch_off(){
		state = false;
		// TODO execute commande
	}
	
	/**
	 * Get the id_outlet
	 * @return
	 */
	public int getID(){
		return id;
	}
	
	public static String OutletToString(Outlet o) {
		return "Outlet [id=" + o.id + ", state=" + o.state + ", room=" + o.room
				+ ", name=" + o.name + "]";
	}
	
	@Override
	public String toString() {
		return "Outlet " + id + " : name = " +name + " room = " + room + " state = " + state;
	}
	
	public void switch_outlet() {
		if(state)
			switch_on();
		else
			switch_off();
	}
}
