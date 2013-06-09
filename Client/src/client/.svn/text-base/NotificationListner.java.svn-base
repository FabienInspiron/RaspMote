package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NotificationListner implements Runnable {
	
	int port;
	
	public NotificationListner(int port) {
		this.port = port;
	}

	/**
	 * Lauch a listener on a socket
	 */
	@Override
	public void run() {
		ServerSocket listener = null;
        	try {
				listener = new ServerSocket(port);
				while (true) {
					Socket socket = listener.accept();
					
					DataInputStream in = new DataInputStream(
							socket.getInputStream());
					BufferedReader d = new BufferedReader(
							new InputStreamReader(in));

					String msg = d.readLine();
					System.out.println("Notification reçu : "+ msg);
					
					d.close();
					in.close();
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
