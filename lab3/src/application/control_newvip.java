package application;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class control_newvip implements Initializable {

	@FXML
	private Button btn_commit, btn_cancel, btn_revive;
	@FXML
	private TextField text_name, text_phone;

	// public static int money = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).
		// check_1000.setEnabled(false);

	}

	public void commit(ActionEvent event) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		if (text_name.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "名字不能为空", "请重新输入", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (text_phone.getText().trim().trim().length() != 11) {
			JOptionPane.showMessageDialog(null, "请输入11位合法手机号码", "请重新输入", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			// int money;
			if (JOptionPane.showConfirmDialog(null, "确认收款50元或单笔交易满1000元", "确认", JOptionPane.OK_OPTION) == 0) {
				CallableStatement crs = Main.conn.prepareCall("{? = call dbo.newvip(?,?,?,?,?)}");
				crs.registerOutParameter(1, java.sql.Types.INTEGER);
				crs.setString(2, text_name.getText().trim());
				crs.setString(3, text_phone.getText().trim());
				crs.setString(4, time.toString());
				crs.setString(5, Integer.toString(Main.cid).trim());
				// if (check_1000.getState())
				// money = 0;
				// else
				// money = 1;
				crs.setString(6, Integer.toString(Main.money).trim());
				crs.execute();
				if (crs.getInt(1) <= 0) {
					System.out.println("vid failed");
					JOptionPane.showMessageDialog(null,
							"办理失败,该姓名和手机号已经存在,对应卡号为" + Integer.toString(crs.getInt(1) + 100000), "办理失败",
							JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "办理成功，会员卡号为" + Integer.toString(crs.getInt(1)), "办理成功",
							JOptionPane.PLAIN_MESSAGE);
				}
				crs.close();
				Main.money = 1;
				Stage temp = (Stage) text_name.getScene().getWindow();
				temp.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("add failed");
		}
	}

	public void cancel(ActionEvent event) {
		Stage temp = (Stage) text_name.getScene().getWindow();
		temp.close();
	}

	public void revive(ActionEvent event) {
		try {
			String vid = JOptionPane.showInputDialog(null, "请输入要激活的会员卡号,并收款50元", "激活会员卡", JOptionPane.PLAIN_MESSAGE);
			if (vid.length() == 0)
				return;
			if (JOptionPane.showConfirmDialog(null, "确认收款50元或单笔交易满1000元", "确认", JOptionPane.OK_OPTION) == 0) {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				try {
					// int money;
					CallableStatement crs = Main.conn.prepareCall("{? = call dbo.revivevip(?,?,?,?)}");
					crs.registerOutParameter(1, java.sql.Types.INTEGER);
					crs.setString(2, vid.trim());
					crs.setString(3, time.toString());
					crs.setString(4, Integer.toString(Main.cid).trim());
					// if (check_1000.getState())
					// money = 0;
					// else
					// money = 1;
					crs.setString(5, Integer.toString(Main.money).trim());
					crs.execute();
					if (crs.getInt(1) == 0) {
						System.out.println("revive sucess");
						JOptionPane.showMessageDialog(null, "激活成功,消费额度已清零", "激活状态", JOptionPane.PLAIN_MESSAGE);
						crs.close();
						Main.money = 1;
						Stage temp = (Stage) text_name.getScene().getWindow();
						temp.close();
						return;
					} else if (crs.getInt(1) == 1) {
						System.out.println("already revived");
						JOptionPane.showMessageDialog(null, "激活失败,该会员卡仍未失效", "激活状态", JOptionPane.ERROR_MESSAGE);
					} else {
						System.out.println("vid error");
						JOptionPane.showMessageDialog(null, "激活失败,请输入正确的会员卡号", "激活状态", JOptionPane.ERROR_MESSAGE);
					}
					Stage temp = (Stage) text_name.getScene().getWindow();
					temp.show();
					crs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("add failed");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("input failed");
			Stage temp = (Stage) text_name.getScene().getWindow();
			temp.show();
		}
	}
}
// When user click on myButton
// this method will be called.