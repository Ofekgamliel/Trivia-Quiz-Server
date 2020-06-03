package com.example.ogamliel.myquizproject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.sun.prism.impl.QueuedPixelSource;

public class socketHandler extends Thread {
	Socket incoming;
	Sql sql;
	socketHandler(Socket _in  , Sql sql  )
	{
		incoming=_in;
		this.sql = sql; 
	}
	
	public void run()
	{
		Object obj = null;
		try
		{
		   String selection,high_score_array;
           Gson json = new Gson();
		   ObjectInputStream HighScore_inFromClient = null;
           BufferedReader inFromClient = new BufferedReader(new InputStreamReader(incoming.getInputStream())); 
           DataOutputStream  outToClient = new DataOutputStream (incoming.getOutputStream() );
           HighScore_inFromClient= new ObjectInputStream (incoming.getInputStream());
           selection = inFromClient.readLine();
           System.out.println(selection);
           switch (selection) 
	           {
          		case "1":
                    ArrayList<Questions> question_Array = sql.Question_query();
                    String Gson_questionArray = json.toJson(question_Array);
                    outToClient.writeBytes(Gson_questionArray + "\n");
                    break;
                 case "2":
                	 high_score_array = inFromClient.readLine();
                	 ArrayList<Players> high = new ArrayList<Players>();
                	 high = json.fromJson(high_score_array, new TypeToken<List<Players>>(){}.getType());
                     Players P = new Players(high.get(0).getPlayerName(),high.get(0).getScore());
                     sql.insert_highScore(P);
                     break;
                 case "3":
                     ArrayList<Players> highScore_Array = sql.HighScore_query();
                     String Gson_HighScoreArray = json.toJson(highScore_Array);
                     outToClient.writeBytes(Gson_HighScoreArray + "\n");
                     break;
	           }
		
		}
		catch(IOException e)
		{
			e.getMessage();
		}
		try {
			incoming.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
