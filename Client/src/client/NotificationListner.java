package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println(out);
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        finally {
            try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
