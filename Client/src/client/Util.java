package client;

import java.util.Scanner;

public class Util {
	public static String findAttribut(String msg, String prefixe, String suffixe) throws Exception 
    {         
        Scanner sc = new Scanner(msg);
         
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
	
	public static String attribut(String noti, String balise) throws Exception{
		return findAttribut(noti, "<"+balise+">", "</"+balise+">");
	}
}
