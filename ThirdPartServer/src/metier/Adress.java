package metier;

public class Adress {
	String host;
	int port;
	
	public Adress(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}
