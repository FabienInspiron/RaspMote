package local;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This listener notify all the clients when he receive a notification from the RaspberryPi server
 * @author belli
 *
 */
public class NotificationListner implements Runnable {
	
	int port;
	ThirdPartServerImpl third;
	
	public NotificationListner(int port, ThirdPartServerImpl third) {
		this.port = port;
		this.third = third;
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
					DataInputStream in = new DataInputStream(
							socket.getInputStream());
					BufferedReader d = new BufferedReader(
							new InputStreamReader(in));

					System.out.println("Message reçu : " + d.readLine());
                    third.notifyAllClients("");
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
