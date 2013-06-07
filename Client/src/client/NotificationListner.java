package client;

import java.io.DataInputStream;
import java.io.IOException;
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
					System.out.print("Notification reçu <--");
					DataInputStream in = new DataInputStream(socket.getInputStream());
					System.out.println(in.readLine());
					System.out.println("-->");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
