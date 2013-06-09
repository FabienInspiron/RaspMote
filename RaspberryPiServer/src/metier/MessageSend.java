package metier;

import java.sql.Date;
import java.sql.Time;

/**
 * Class that contains the message which is send as xml format to the client
 * @author belli
 *
 */
public class MessageSend {
	String type;
	Outlet out;
	Adress emitConsumer;
	Date date;
	int timeStampe;
	
	public MessageSend(String type, Outlet out, int timeStampe) {
		super();
		this.type = type;
		this.out = out;
		this.timeStampe = timeStampe;
	}
	
	public void setConsumer(String host, int port) {
		Adress a = new Adress(host, port);
		emitConsumer = a;
	}
}
