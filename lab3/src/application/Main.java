package application;

import java.sql.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class Main extends Application {
	static Connection conn;
	static Statement stmt;
	static String cname;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("loginwindow.fxml"));	
			primaryStage.setTitle("Welcome to someday's cashier system!");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			final String USER = "root";
			final String PASS = "whdhr";
			final String DB_URL= "jdbc:sqlserver://localhost:1433;databaseName=lab3";
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt=conn.createStatement();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("¡¨Ω” ß∞‹");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
