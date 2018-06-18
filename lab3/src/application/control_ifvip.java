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

public class control_ifvip implements Initializable {

	@FXML
	public Button btn_reset, btn_commit;
	@FXML
	private TextField text_vid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void commit_input(ActionEvent event) {

		String sql;
		sql = "select * from VIP where VID= " + Integer.parseInt(text_vid.getText());
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			CallableStatement crs = Main.conn.prepareCall("{? = call dbo.ifvip(?,?)}");
			crs.registerOutParameter(1, java.sql.Types.INTEGER);
			crs.setString(2, text_vid.getText().toString());
			crs.setString(3, time.toString());
			crs.execute();
			if (crs.getInt(1) == 1) {
				JOptionPane.showMessageDialog(null, "不存在该会员号，请重新输入", "验证失败", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (crs.getInt(1) == 2) {
				if (JOptionPane.showConfirmDialog(null, "该会员卡已过期，是否激活或办理新的会员卡", "验证失败", JOptionPane.OK_OPTION) == 0) {
					try {
						Stage win_addpro = new Stage();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("newvipwindow.fxml"));
						Parent root = loader.load();
						win_addpro.setTitle("A new VIP");
						win_addpro.setScene(new Scene(root));
						win_addpro.show();
						crs.close();
						Stage temp = (Stage) btn_reset.getScene().getWindow();
						temp.close();
						return;
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("ifvip failed");
					}
				} else {
					JOptionPane.showMessageDialog(null, "请重新输入", "验证失败", JOptionPane.ERROR_MESSAGE);
					crs.close();
					return;
				}
			}
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next()) {
				control_cashwin.vname = new String(rs.getString("V_NAME").trim());
				control_cashwin.vid = rs.getInt("VID");
				control_cashwin.isvip = true;
				System.out.println("he is a vip =" + control_cashwin.vname);
				JOptionPane.showConfirmDialog(null, "验证成功 这位是VIP " + control_cashwin.vname, "验证成功",
						JOptionPane.PLAIN_MESSAGE);
				Stage temp = (Stage) btn_reset.getScene().getWindow();
				temp.close();
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}

	}

	public void reset_input(ActionEvent event) {
		text_vid.clear();
	}

}
