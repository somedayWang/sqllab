package application;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class control_list implements Initializable {

	@FXML
	private Button btn_commit, btn_cancel;
	@FXML
	public TableView<sixproperty> table_tab2;
	@FXML
	public Label lab_cid, lab_vid, lab_ntime, lab_total, lab_bufftotal;
	@FXML
	private TableColumn<sixproperty, String> tab2_c1, tab2_c2, tab2_c3, tab2_c4, tab2_c5;

	// private double buff, total, bufftotal;
	// public static Boolean isvip = false;

	static ObservableList<sixproperty> tab2_list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inti_tab();
		fresh_list();
		lab_cid.setText(Integer.toString(Main.cid));
		SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		lab_ntime.setText(ft.format(new Date()).trim());
		if (control_cashwin.isvip)
			lab_vid.setText(control_cashwin.vname);
		else
			lab_vid.setText("非会员");
		lab_total.setText(Double.toString(control_cashwin.total));
		lab_bufftotal.setText(Double.toString(control_cashwin.bufftotal));

	}

	public void inti_tab() {
		table_tab2.setEditable(false);
		tab2_c1.setResizable(false);
		tab2_c2.setResizable(false);
		tab2_c3.setResizable(false);
		tab2_c4.setResizable(false);
		tab2_c5.setResizable(false);
		tab2_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
		tab2_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
		tab2_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
		tab2_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
		tab2_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
		// tab2_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
		table_tab2.setItems(tab2_list);
	}

	public void fresh_list() {

		tab2_list.clear();
		for (int i = 0; i < control_cashwin.tab1_list.size(); i++) {
			tab2_list.add(new sixproperty(control_cashwin.tab1_list.get(i).getCA(),
					control_cashwin.tab1_list.get(i).getCC(), control_cashwin.tab1_list.get(i).getCE(),
					control_cashwin.tab1_list.get(i).getCF(), control_cashwin.tab1_list.get(i).getCG(), " "));
		}

	}

	public void commitandupdate(ActionEvent event) {
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			for (int i = 0; i < control_cashwin.tab1_list.size(); i++) {
				CallableStatement crs = Main.conn.prepareCall("{? = call dbo.sellpro(?,?,?,?,?)}");
				crs.registerOutParameter(1, java.sql.Types.INTEGER);
				crs.setString(2, control_cashwin.tab1_list.get(i).getCB());
				crs.setString(3, control_cashwin.tab1_list.get(i).getCF());
				crs.setString(4, Integer.toString(Main.cid));
				if (control_cashwin.isvip)
					crs.setString(5, "1");
				else
					crs.setString(5, "0");
				crs.setString(6, time.toString());
				crs.execute();
				if (crs.getInt(1) == 1)
					System.out.println("error sell failed");
				crs.close();
			}
			CallableStatement crs = Main.conn.prepareCall("{? = call dbo.totalmoney(?,?,?)}");
			crs.registerOutParameter(1, java.sql.Types.INTEGER);
			crs.setString(2, lab_bufftotal.getText().trim());
			crs.setString(3, Integer.toString(Main.cid));
			crs.setString(4, time.toString());
			crs.execute();
			crs.close();
			if (control_cashwin.isvip) {
				crs = Main.conn.prepareCall("{? = call dbo.upodatevip(?,?,?)}");
				crs.registerOutParameter(1, java.sql.Types.INTEGER);
				crs.setString(2, lab_bufftotal.getText().trim());
				crs.setString(3, Integer.toString(control_cashwin.vid));
				crs.setString(4, time.toString());
				crs.execute();
				if (crs.getInt(1) == -1) {
					JOptionPane.showMessageDialog(null, "会员卡自动续期一年", "积分信息", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "会员卡成功积分", "积分信息", JOptionPane.PLAIN_MESSAGE);
				}
				crs.close();
				control_cashwin.isvip = false;
				JOptionPane.showMessageDialog(null, "交易完成，正在打印小票", "交易完成", JOptionPane.CLOSED_OPTION);
				control_cashwin.tab1_list.clear();
				control_cashwin.isvip = false;
				Stage temp = (Stage) btn_commit.getScene().getWindow();
				temp.close();
				return;
			}
			System.out.println("OK");
			if (control_cashwin.bufftotal >= 1000 && control_cashwin.isvip == false) {
				// System.out.println(JOptionPane.showConfirmDialog(null, "交易完成，正在打印小票", "交易完成",
				// JOptionPane.OK_OPTION));
				if (JOptionPane.showConfirmDialog(null, "交易完成，正在打印小票，单笔交易满1000，是否免费办理会员卡", "会员卡办理",
						JOptionPane.OK_OPTION) == 0) {
					System.out.println("我要办");
					try {
						Main.money = 0;
						Stage win_addpro = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("newvipwindow.fxml"));
						Parent root = loader.load();
						win_addpro.setTitle("单笔消费满1000，免费办理vip");
						win_addpro.setScene(new Scene(root));
						win_addpro.show();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("ifvip failed");
					}
				} else {
					System.out.println("我不要");
					Main.money = 1;
				}
			} else
				JOptionPane.showMessageDialog(null, "交易完成，正在打印小票", "交易完成", JOptionPane.CLOSED_OPTION);
			control_cashwin.tab1_list.clear();
			control_cashwin.isvip = false;
			Stage temp = (Stage) btn_commit.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("sell failed");
		}
	}

	public void cancelandclose(ActionEvent event) {
		Stage temp = (Stage) btn_commit.getScene().getWindow();
		temp.close();
	}

}
