package application;


import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application{
	 
	 @Override
	 public void start(Stage primaryStage) throws Exception {
	  primaryStage.setTitle("测试用");
	  //测试带有自动清除按钮的TextField
	//  primaryStage.setScene(testClearableTextField());
	  //测试动态匹配下拉列表
	  primaryStage.setScene(testAutoCompletionBingdingTextField());
	  primaryStage.show();
	 }
	 
	 public static void main(String[] args) {
	  launch(args);
	 }
	 
	 public Scene testClearableTextField(){
	  //带清除按钮的文本框
	  TextField textClearable= TextFields.createClearableTextField();
	  StackPane root = new StackPane();
	  root.getChildren().add(textClearable);
	  Scene scene = new Scene(root,300,300);
	  return scene;
	 }
	 
	 public Scene testAutoCompletionBingdingTextField(){
	  List<String> list = new ArrayList<String>();
	  list.add("123456");
	  list.add("123567");
	  list.add("123678");
	  list.add("456123");
	  list.add("456234");
	  list.add("456345");
	  list.add("789456");
	  list.add("788567");
	  list.add("123444");
	  list.add("123445");
	  list.add("123555");
	  list.add("123556");
	  list.add("123666");
	  list.add("123667");
	  list.add("123677");
	  list.add("456777");
	  list.add("456788");
	  list.add("456888");
	  list.add("456889");
	  list.add("456999");
	  list.add("789444");
	  list.add("789455");
	  list.add("789466");
	  list.add("789566");
	  list.add("123745");
	  list.add("123756");
	  list.add("123765");
	  list.add("123764");
	  list.add("123546");
	  list.add("456213");
	  list.add("456231");
	  list.add("123879");
	  list.add("123897");
	  list.add("123978");
	  list.add("123987");
	  TextField textField = new TextField();
	  AutoCompletionBinding<String> textAutoBingding = TextFields.bindAutoCompletion(textField, FXCollections.observableArrayList(list));
	  textAutoBingding.setVisibleRowCount(3);
	  StackPane root = new StackPane();
	  root.getChildren().add(textField);
	  Scene scene = new Scene(root,300,800);
	  return scene;
	 }
	}

