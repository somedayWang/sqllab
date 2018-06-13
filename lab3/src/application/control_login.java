package application;


import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class control_login implements Initializable {
   @FXML
   private Button btn_loginin,btn_signin;
   @FXML
   private TextField text_user,text_pw;
   
   static Stage win_cash,win_register;
   
   
   @Override
   public void initialize(URL location, ResourceBundle resources) {

	   
	   
   }
   
   public void towin_cash() {
		try {
			Stage temp=(Stage)btn_loginin.getScene().getWindow();
			temp.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("cashwindow.fxml"));	
			
			Parent root = loader.load();	
			win_cash.setTitle("Welcome to working time!");
			win_cash.setScene(new Scene(root));
			win_cash.setHeight(530);
			win_cash.setWidth(807);
			win_cash.setResizable(false);
			
			win_cash.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("open failed");
		}
	}
   
   public void fun_login(ActionEvent event) {
	   String sql;
	   sql="select * from CASHIER "
	   		+ "where C_NAME = '"+text_user.getText()
	   		+"' and C_PW = '"+text_pw.getText().toString()+"'";
	   try
	   {
		   ResultSet rs = Main.stmt.executeQuery(sql);
		   //System.out.println("login success");
		   if(rs.next()==false)	System.out.println("username or pw is mistake");
		   else
		   {
			   int cid=rs.getInt("CID");
			   System.out.println("CID = "+cid+" login success");
			   win_cash=new Stage();
			   Main.cname=new String(rs.getString("C_NAME").trim());
			   this.towin_cash();
		   }
		   rs.close();
	   }catch(Exception e)
	   {
		   e.printStackTrace();
		   System.out.println("login failed");
	   }
	   
   }
   
   public void fun_register(ActionEvent event) {
	   try
	   {
		    win_register=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("registerwindow.fxml"));	
			win_register.setTitle("Resign your account!");
			win_register.setScene(new Scene(root));
			//win_register.setResizable(false);  
			win_register.show();
	   }catch(Exception e)
	   {
		   e.printStackTrace();
		   System.out.println("login failed");
	   }
	   
   }

}