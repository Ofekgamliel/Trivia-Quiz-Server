package com.example.ogamliel.myquizproject;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.Connection;


public class Sql {

	private static Connection connect; 
	
	public static void insert_highScore(Players p ){	
		String sqlInsert = "insert into trivia.high_score (name,score) values (?,?)";
		
		try {
			PreparedStatement pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, p.getPlayerName());
			pst.setString(2, p.getScore());
		
			pst.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Players> HighScore_query(){
		ArrayList<Players> player = new ArrayList<Players>();
		PreparedStatement statement = null;
		
		try {
			statement = connect.prepareStatement("SELECT * FROM trivia.high_score order by high_score.score +0 desc");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
		        player.add(new Players(result.getString(1),result.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return player;
	}
	
	public static ArrayList<Questions> Question_query(){
		ArrayList<Questions> question = new ArrayList<Questions>();
		PreparedStatement statement = null;
		
		try {
			statement = connect.prepareStatement("SELECT * FROM trivia.ques");
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
		        question.add(new Questions(result.getString(1),result.getString(2),
		        		result.getString(3),result.getString(4),result.getString(5),
		        		result.getString(6),result.getString(7),result.getString(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return question;
	}
	
	public static void connection()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("SQL driver working");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ConectingToSQL ()
	{
		
		connection();
		String host = "jdbc:mysql://localhost:3306/trivia";
		String username = "root";
		String password = "1234";
		
		
		try {
			 connect = (Connection) DriverManager.getConnection(host, username, password);
		System.out.println("SQL connection initiated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("problem");
		}
		
		
		
	}
	
	
}
