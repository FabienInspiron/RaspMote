package metier;

import javax.swing.ImageIcon;

/**
 * Class for the outlet
 * @author belli
 *
 */
public class Outlet {
	
	/**
	 * Identification of the outlet
	 */
	int id_outlet;
	
	/**
	 * Save the state of the outlet
	 */
	boolean state;
	
	/**
	 * Name and room of the outlet
	 */
	String room;
	String name;
	
	/**
	 * Image of the outlet
	 */
	ImageIcon image;

	/**
	 * Level of privileges of each outlet
	 */
	int privileges;
	
	/**
	 * Normal contructor
	 * @param id_outlet
	 * @param room
	 * @param name
	 * @param image
	 */
	public Outlet(int id_outlet, String room, String name, ImageIcon image) {
		super();
		this.id_outlet = id_outlet;
		this.room = room;
		this.name = name;
		this.image = image;
		this.state = false;
	}	
	
	/**
	 * switch the outlet on
	 */
	public void switch_on(){
		state = false;
	}
	
	/**
	 * switch the outlet off
	 */
	public void switch_off(){
		state = true;
	}
	
	/**
	 * Get the id_outlet
	 * @return
	 */
	public int getID(){
		return id_outlet;
	}
}
