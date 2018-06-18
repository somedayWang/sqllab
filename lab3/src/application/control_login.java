package application;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
	private Button btn_loginin, btn_signin;
	@FXML
	private TextField text_user, text_pw;

	static Stage win_cash, win_register;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.money = 1;
	}

	public void towin_cash() {
		try {
			Stage temp = (Stage) btn_loginin.getScene().getWindow();
			temp.close();
			FXMLLoader loader;
			if (Main.cid > 100)
				loader = new FXMLLoader(getClass().getResource("cashwindow.fxml"));
			else {
				loader = new FXMLLoader(getClass().getResource("backwindow.fxml"));
			}
			Parent root = loader.load();
			win_cash.setTitle("Welcome to working time!");
			win_cash.setScene(new Scene(root));
			win_cash.setHeight(530);
			win_cash.setWidth(807);
			win_cash.setResizable(false);
			JOptionPane.showMessageDialog(null, "登陆成功 欢迎工作  " + Main.cname, "登陆成功", JOptionPane.PLAIN_MESSAGE);
			win_cash.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("open failed");
		}
	}

	public void fun_login(ActionEvent event) {
		String sql;

		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			CallableStatement crs = Main.conn.prepareCall("{? = call dbo.loginca(?,?,?,?)}");
			crs.registerOutParameter(1, java.sql.Types.INTEGER);
			crs.setString(2, text_user.getText().trim());
			crs.setString(3, text_user.getText().trim());
			crs.setString(4, text_pw.getText().trim());
			crs.setString(5, time.toString());
			crs.execute();
			if (crs.getInt(1) == 1) {
				JOptionPane.showMessageDialog(null, "不存在该员工，请重新登陆", "登陆失败", JOptionPane.ERROR_MESSAGE);
			} else if (crs.getInt(1) == 2) {
				JOptionPane.showMessageDialog(null, "密码错误，请确认用户和密码", "登陆失败", JOptionPane.ERROR_MESSAGE);
			}
			int id = crs.getInt(1);
			crs.close();
			sql = "select C_NAME from CASHIER " + "where CID = " + Integer.toString(id);
			ResultSet rs = Main.stmt.executeQuery(sql);
			rs.next();
			Main.wtime = time;
			Main.cid = id;
			Main.cname = new String(rs.getString("C_NAME").trim());
			rs.close();
			win_cash = new Stage();
			this.towin_cash();
			// System.out.println("login success");
			// if (rs.next() == false) {
			// System.out.println("username or pw is mistake");
			// JOptionPane.showMessageDialog(null, "不存在该员工，请重新登陆", "登陆失败",
			// JOptionPane.ERROR_MESSAGE);
			// } else {
			// int cid = rs.getInt("CID");
			// System.out.println("CID = " + cid + " login success");
			// win_cash = new Stage();
			// Main.cname = new String(rs.getString("C_NAME").trim());
			// Main.cid = rs.getInt("CID");
			// this.towin_cash();
			// }
			// rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}

	}

	public void fun_register(ActionEvent event) {
		try {
			win_register = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("registerwindow.fxml"));
			win_register.setTitle("Resign your account!");
			win_register.setScene(new Scene(root));
			// win_register.setResizable(false);
			win_register.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}

	}

}