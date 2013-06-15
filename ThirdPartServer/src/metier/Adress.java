package metier;

import java.net.URL;
import java.util.Scanner;

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
	
	@Override
	public boolean equals(Object arg0) {
		Adress add = (Adress)arg0;
		if(!host.equals(add.getHost()))
				return false;
		
		if(port != add.getPort())
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret += host + ":";
		ret += port;
		
		return ret;
	}
	
}
