package application;

import java.net.URL;
import java.sql.CallableStatement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class control_register implements Initializable {
	@FXML
	private Button btn_commit;
	@FXML
	private TextField lab_name, lab_phone;
	@FXML
	private PasswordField lab_yanzheng, lab_pw;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).

	}

	public void commit(ActionEvent event) {

		if (lab_name.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "名字不能为空", "请重新输入", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (lab_phone.getText().trim().trim().length() != 11) {
			JOptionPane.showMessageDialog(null, "请输入11位合法手机号码", "请重新输入", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (lab_pw.getText().trim().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "请重新输入", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			// int money;
			CallableStatement crs = Main.conn.prepareCall("{? = call dbo.newca(?,?,?,?)}");
			crs.registerOutParameter(1, java.sql.Types.INTEGER);
			crs.setString(2, lab_name.getText().trim());
			crs.setString(3, lab_pw.getText().trim());
			crs.setString(4, lab_phone.getText().trim());
			crs.setString(5, lab_yanzheng.getText().trim());
			crs.execute();
			if (crs.getInt(1) <= 0) {
				System.out.println("vid failed");
				JOptionPane.showMessageDialog(null, "申请失败,该姓名和密码已经存在,对应员工号为" + Integer.toString(crs.getInt(1) + 10000),
						"申请失败", JOptionPane.ERROR_MESSAGE);
			} else if (crs.getInt(1) == 1) {
				JOptionPane.showMessageDialog(null, "申请失败，验证信息错误,请核实验证信息", "申请成功", JOptionPane.ERROR_MESSAGE);
				crs.close();
				return;
			} else {
				JOptionPane.showMessageDialog(null, "申请成功，员工号为" + Integer.toString(crs.getInt(1)), "申请成功",
						JOptionPane.PLAIN_MESSAGE);
			}
			crs.close();
			Stage temp = (Stage) btn_commit.getScene().getWindow();
			temp.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("add failed");
		}
	}
}
