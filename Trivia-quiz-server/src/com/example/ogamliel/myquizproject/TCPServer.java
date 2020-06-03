package com.example.ogamliel.myquizproject;
import java.io.*; 
import java.net.*; 

class TCPServer { 
    
  public static void main(String argv[]) throws Exception 
    { 
	  Sql sql = new Sql();
	
	  ServerSocket s = null;
	  sql.ConectingToSQL();
		try {
		    s = new ServerSocket(2222);
		  
		} catch(IOException e) {
		    System.out.println(e);
		    System.exit(1);
		}

		while (true) {
		    Socket incoming = null;
		    
		    try {
			incoming = s.accept();
			
		    } catch(IOException e) {
			System.out.println(e);
			continue;
		    }
		    	
		    new socketHandler(incoming , sql).start();
		    	
		    System.out.println(incoming);
		    
		}
    } 
} 
