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
public class NotificationListner extends Thread {
	
	int port;
	ThirdPartServerImpl third;
	ServerSocket listener;
	
	public NotificationListner(ThirdPartServerImpl third) throws IOException {
		this.third = third;
		listener = new ServerSocket(0);
		port = listener.getLocalPort();
	}

	/**
	 * Lauch a listener on a socket
	 */
	@Override
	public void run() {
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                	
					DataInputStream in = new DataInputStream(
							socket.getInputStream());
					BufferedReader d = new BufferedReader(
							new InputStreamReader(in));

					String msg = d.readLine();
					
                    third.notifyAllClients(msg);
                    
                    d.close();
                    in.close();
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
