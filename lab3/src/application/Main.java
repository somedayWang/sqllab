package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	static Connection conn;
	static Statement stmt;
	static String cname;
	static int cid;
	static Timestamp wtime;
	static int money = 1;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("loginwindow.fxml"));
			primaryStage.setTitle("欢迎工作");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.setHeight(325);
			primaryStage.setWidth(375);
			primaryStage.show();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			final String USER = "root";
			final String PASS = "whdhr";
			final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=lab3";
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接失败");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
