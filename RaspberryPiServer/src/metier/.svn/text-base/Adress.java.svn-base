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
	
	/**
	 * Get an ip adress 
	 * @param site
	 * @param prefixe
	 * @param suffixe
	 * @return
	 * @throws Exception
	 */
	public static String findIP(String site, String prefixe, String suffixe) throws Exception 
    {         
        Scanner sc = new Scanner(new URL(site).openStream()); 
         
        while (sc.hasNextLine()) 
        { 
            String line = sc.nextLine(); 
             
            int a = line.indexOf(prefixe); 
            if (a!=-1) 
            { 
                int b = line.indexOf(suffixe,a); 
                if (b!=-1) 
                { 
                    sc.close(); 
                    return line.substring(a+prefixe.length(),b); 
                } 
            } 
        } 
         
        sc.close(); 
        return null; 
    } 
	
	/**
	 * Get public ip adress
	 * @return
	 */
	public static String getIpPublic(){
		try {
			return findIP("http://votreip.free.fr/","<title>IP : ","</title>");
		} catch (Exception e) {
			try {
				return findIP("http://www.monip.org/","<BR>IP : ","<br>");
			} catch (Exception e1) {
				return "localhost";
			}
		}
	}
	
	@Override
	public boolean equals(Object arg0) {
		Adress add = (Adress)arg0;
		if(!host.equals(add.getHost()))
				return false;
		
		if(port == add.getPort())
			return false;
		
		return true;
	}
	
	
}
