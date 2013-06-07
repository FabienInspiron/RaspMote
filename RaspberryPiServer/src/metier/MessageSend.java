package metier;

/**
 * Class that contains the message which is send as xml format to the client
 * @author belli
 *
 */
public class MessageSend {
	String type;
	Outlet out;
	
	public MessageSend(String type, Outlet out) {
		super();
		this.type = type;
		this.out = out;
	}
}
