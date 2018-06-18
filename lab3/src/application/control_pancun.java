package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class control_pancun implements Initializable {

	@FXML
	private Button btn_xiaban, btn_huanban;
	@FXML
	public TableView<sixproperty> table_tab2;
	@FXML
	public Label lab_wtime, lab_ltime, lab_cid, lab_cname, lab_tradenum, lab_trademoney, lab_vipnum, lab_vipmoney;
	@FXML
	private TableColumn<sixproperty, String> tab2_c1, tab2_c2, tab2_c3, tab2_c4, tab2_c5;

	static ObservableList<sixproperty> tab2_list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inti_tab();
		lab_trademoney.setText("0");
		lab_tradenum.setText("0");
		lab_vipmoney.setText("0");
		lab_vipnum.setText("0");
		fresh_list();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String sql;
		sql = "update CLOGS set L_DATETIME = '" + time.toString() + "' where W_DATETIME = '" + Main.wtime.toString()
				+ "' and CID = " + Integer.toString(Main.cid);
		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		lab_cid.setText(Integer.toString(Main.cid));
		lab_cname.setText((Main.cname));
		lab_wtime.setText(Main.wtime.toString().substring(0, 19));
		lab_ltime.setText(time.toString().substring(0, 19));
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

		String sql;
		sql = "select * from CLOGS where W_DATETIME = '" + Main.wtime.toString() + "' and CID = "
				+ Integer.toString(Main.cid) + " order by PID";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					int vipnum = 0, vipmoney = 0;
					// double money;
					ResultSet rs = Main.stmt.executeQuery(sql);
					if (rs.next() == false)
						System.out.println("the product table is null");
					else {
						while (rs.getString("PID") == null) {
							if (rs.getString("P_NAME").trim().compareTo("单笔总计") == 0) {
								lab_tradenum.setText(rs.getString("P_NUM").trim());
								lab_trademoney.setText(rs.getString("P_PRICE").trim());
							} else if (rs.getString("P_NAME").trim().compareTo("激活失效会员") == 0) {
								vipnum = vipnum + rs.getInt("P_NUM");
								vipmoney = vipmoney + rs.getInt("P_NUM") * rs.getInt("P_PRICE");

							} else if (rs.getString("P_NAME").trim().compareTo("办理会员") == 0) {
								vipnum = vipnum + rs.getInt("P_NUM");
								vipmoney = vipmoney + rs.getInt("P_NUM") * rs.getInt("P_PRICE");
							}
							rs.next();
						}
						lab_vipnum.setText(Integer.toString(vipnum));
						lab_vipmoney.setText(Integer.toString(vipmoney));
						tab2_list.clear();
						tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME").trim(),
								rs.getString("P_PRICE"), rs.getString("P_NUM"),
								Double.toString(rs.getDouble("P_PRICE") * rs.getDouble("P_NUM")), " "));
						while (rs.next()) {
							tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME").trim(),
									rs.getString("P_PRICE"), rs.getString("P_NUM"),
									Double.toString(rs.getDouble("P_PRICE") * rs.getDouble("P_NUM")), " "));
						}
						// table_tab2.setItems(tab2_list);
					}
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fresh failed");
				}
			}
		});

	}

	public void xiaban() {
		Stage temp = (Stage) btn_xiaban.getScene().getWindow();
		JOptionPane.showMessageDialog(null, "辛苦了，享受你的下班时间", "你是自由的了", JOptionPane.PLAIN_MESSAGE);
		temp.close();
	}

	public void huanban() {
		try {
			JOptionPane.showMessageDialog(null, "让我们看看是哪位幸运儿继续工作", "开始工作", JOptionPane.PLAIN_MESSAGE);
			Stage win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginwindow.fxml"));
			Parent root = loader.load();
			win_addpro.setTitle("准备上班");
			win_addpro.setScene(new Scene(root));
			win_addpro.show();
			Stage temp = (Stage) btn_xiaban.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
