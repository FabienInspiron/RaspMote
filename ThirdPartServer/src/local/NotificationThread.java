package local;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import metier.Adress;

public class NotificationThread implements Runnable {
	
	Adress ip;
	String msg;
	
	public NotificationThread(Adress ip, String msg) {
		this.ip = ip;
		this.msg = msg;
	}
	
	public void run() {
		Socket socket;
		try {
			socket = new Socket(ip.getHost(), ip.getPort());

	        PrintWriter pred = new PrintWriter(
	                             new BufferedWriter(
	                                new OutputStreamWriter(socket.getOutputStream())),
	                             true);

	        msg = msg.trim();
	        msg = msg.replaceAll("\\n", "");
	        msg = msg.replaceAll("\\r\\n", ""); 
	        msg += "\n";
	        
	        pred.println(msg);
	        
	        pred.flush();
	        pred.close();
	        
	        socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
