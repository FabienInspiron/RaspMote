package metier;

/**
 * Class that contains the message which is send as xml format to the client
 * @author belli
 *
 */
public class MessageSend {
	String type;
	Outlet out;
	Adress emitConsumer;
	
	public MessageSend(String type, Outlet out) {
		super();
		this.type = type;
		this.out = out;
	}
	
	public void setConsumer(String host, int port) {
		Adress a = new Adress(host, port);
		emitConsumer = a;
	}
}
