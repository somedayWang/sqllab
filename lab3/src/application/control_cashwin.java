package application;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class control_cashwin implements Initializable {

   @FXML
   private Button btn_addpro,btn_reset,btn_commitadd;
   @FXML
   public Label lab_cashier,lab_stime,lab_ntime;
   @FXML
   private TextField text_addpid,text_addpname,text_addnum;
   @FXML
   public TableView<sixproperty> table_tab2,table_tab3;
   @FXML
   public TableView<eightproperty> table_tab1,table_tab4;
   @FXML
   private TableColumn<eightproperty,String> tab1_c1,tab1_c2,tab1_c3,tab1_c4,tab1_c5,tab1_c6,tab1_c7;
   @FXML
   private TableColumn<sixproperty,String> tab2_c1,tab2_c2,tab2_c3,tab2_c4,tab2_c5,tab2_c6;
   @FXML
   private TableColumn<sixproperty,String> tab3_c1,tab3_c2,tab3_c3,tab3_c4,tab3_c5,tab3_c6;
   @FXML
   private TableColumn<eightproperty,String> tab4_c1,tab4_c2,tab4_c3,tab4_c4,tab4_c5,tab4_c6,tab4_c7,tab4_c8;
   
   
  // static control_input controlofinput;
   //the entry of the class
   static Stage win_addpro;
   static Date time;
   static Timer timer;
   public static ObservableList<eightproperty> tab1_list=FXCollections.observableArrayList();
   static ObservableList<sixproperty> tab2_list=FXCollections.observableArrayList();
   static ObservableList<sixproperty> tab3_list=FXCollections.observableArrayList();
   static ObservableList<eightproperty> tab4_list=FXCollections.observableArrayList();
   //static List<String> list;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
	  
       // TODO (don't really need to do anything here).
	   inti_tab();
	   lab_cashier.setText(Main.cname);
	   //time=new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("MM.dd HH:mm:ss");
		lab_stime.setText(ft.format(new Date()).trim());
		//lab_ntime.setText(ft.format(new Date()).trim()); 
	   //fresh_vip();
		autoupdatetime();

   }
   //Initialize about
   public void autoupdatetime() {  
	   timer = new Timer();
	   timer.schedule(new TimerTask() {  
	    public void run() { 
	     
	     Platform.runLater(new Runnable() 
	     {  
	      @Override 
	      public void run() {
	      try{  
	    	  SimpleDateFormat ft = new SimpleDateFormat ("MM.dd HH:mm:ss");
	    	  lab_ntime.setText(ft.format(new Date()).trim()); 
	      }catch(Exception e){
		    	e.printStackTrace();
				System.out.println("update failed");    
	      		}
	    }
	    });
	    }
	   }, 1000, 1000); 

	}  
   
   
   public void inti_tab() {
//	   tab1_list=FXCollections.observableArrayList();
//	   tab2_list=FXCollections.observableArrayList();
//	   tab3_list=FXCollections.observableArrayList();
//	   tab4_list=FXCollections.observableArrayList();
	   
	   table_tab1.setEditable(false);
	   tab1_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
	   tab1_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
	   tab1_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
	   tab1_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
	   tab1_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
	   tab1_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
	   tab1_c7.setCellValueFactory(new PropertyValueFactory<>("CG"));
	   
	   table_tab1.setItems(tab1_list);
	   
	   table_tab2.setEditable(false);
	   tab2_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
	   tab2_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
	   tab2_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
	   tab2_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
	   tab2_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
	   tab2_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
	   table_tab2.setItems(tab2_list);
	   
	   table_tab3.setEditable(false);
	   tab3_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
	   tab3_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
	   tab3_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
	   tab3_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
	   tab3_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
	   tab3_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
	   table_tab3.setItems(tab3_list);
	   
	   table_tab4.setEditable(false);
	   tab4_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
	   tab4_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
	   tab4_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
	   tab4_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
	   tab4_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
	   tab4_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
	   tab4_c7.setCellValueFactory(new PropertyValueFactory<>("CG"));
	   tab4_c8.setCellValueFactory(new PropertyValueFactory<>("CH"));
	   table_tab4.setItems(tab4_list);
	   
   }
   
   
   //tab_cash
   		
   public void add_product(ActionEvent event){
	   try {
		win_addpro=new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("inputwindow.fxml"));	
		//controlofinput=loader.getController();
		/*controlofinput.btn_commitadd.setOnAction(new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		    	lab_cashier.setText("2333");
		    }
		});*/
	
		
		Parent root=loader.load();
		
//		controlofinput.setcontrolofcashwin();	
	/*	Platform.runLater(new Runnable() {
			@Override
			public void run() {
		   try {

				
		   }catch(Exception e) {
			   e.printStackTrace();
			   System.out.println("haiyahaoqia");
		   }
			}
		});*/
		//loader
		win_addpro.setTitle("Welcome to working time!");
		win_addpro.setScene(new Scene(root));
		
		win_addpro.show();
		
	   	
	   } catch(Exception e) {
			e.printStackTrace();
			System.out.println("add failed");
		}
   }

   public void add_product_table()
   {
	   table_tab1.setItems(control_cashwin.tab1_list);
   }
  
   //tab_product
   public void fresh_pro() {
	   String sql;
	   sql="select * from PRODUCT";
	   	Platform.runLater(new Runnable() {
			@Override
			public void run() {
		   try {
			   ResultSet rs = Main.stmt.executeQuery(sql);
			   if(rs.next()==false)	System.out.println("the product table is null");
			   else
			   {
				   tab2_list.clear();
				   tab2_list.add(new sixproperty(rs.getString("PID"),
						   rs.getString("P_NAME"),
						   rs.getString("UNIT"),
						   rs.getString("P_PRICE"),
						   rs.getString("REMAINING"),
						   rs.getString("PID")
						   ));
				   while(rs.next())
				   {
					 
					   tab2_list.add(new sixproperty(rs.getString("PID"),
							   rs.getString("P_NAME"),
							   rs.getString("UNIT"),
							   rs.getString("P_PRICE"),
							   rs.getString("REMAINING"),
							   rs.getString("PID")
							   ));
				   }
//				   table_tab2.setItems(tab2_list);
			   }
			   rs.close();
		   }catch(Exception e) {
			   e.printStackTrace();
			   System.out.println("fresh failed");
		   }
			}
	});
   }
   
   
   //tab_vip
   public void fresh_vip() {
	   String sql;
	   sql="select * from VIP";
	   Platform.runLater(new Runnable() {
			@Override
			public void run() {
		   try {
			   ResultSet rs = Main.stmt.executeQuery(sql);
			   if(rs.next()==false)	System.out.println("the vip table is null");
			   else
			   {
				   tab3_list.clear();
				   //ObservableList<sixproperty> tab3_list=FXCollections.observableArrayList();
//				   sixproperty temp=new sixproperty(rs.getString("VID"),
//						   rs.getString("V_NAME"),
//						   rs.getString("V_PHONE"),
//						   rs.getString("V_NAME"),
//						   rs.getString("T_NUM"),
//						   rs.getString("V_NAME"));
				   tab3_list.add(new sixproperty(rs.getString("VID"),
						   rs.getString("V_NAME"),
						   rs.getString("V_PHONE"),
						   rs.getString("V_NAME"),
						   rs.getString("T_NUM"),
						   rs.getString("V_NAME")
						   ));
				  // tab3_list.add(temp);
				   while(rs.next())
				   {
					 
					   tab3_list.add(new sixproperty(rs.getString("VID"),
							   rs.getString("V_NAME"),
							   rs.getString("V_PHONE"),
							   rs.getString("V_NAME"),
							   rs.getString("T_NUM"),
							   rs.getString("V_NAME")
							   ));
				   }
				   table_tab3.setItems(tab3_list);
				   
			   }
			   rs.close();
		   }catch(Exception e) {
			   e.printStackTrace();
			   System.out.println("fresh failed");
		   }
			}
	});
   }
   
   
   //tab_log

}