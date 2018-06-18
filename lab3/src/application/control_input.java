package application;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;
import org.controlsfx.control.textfield.TextFields;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class control_input implements Initializable {

	@FXML
	public Button btn_reset, btn_commitadd;
	@FXML
	private TextField text_addpid, text_addpname, text_addnum;
	@FXML
	private Label lab_num;

	// public List<int,String> list_pro=new ArrayList<>();
	public static int remain;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lab_num.setText(null);
		// TODO (don't really need to do anything here).
		remain = 1;
		// text_addnum{
		// @Override
		// public void replaceText(int start, int end, String text) {
		// if (!text.matches("[a-z]")) {
		// super.replaceText(start, end, text);
		// }
		// }
		//
		// @Override
		// public void replaceSelection(String text) {
		// if (!text.matches("[a-z]")) {
		// super.replaceSelection(text);
		// }
		// }
		// };
		autocomplete_pid();
		autocomplete_pname();

	}
	// public void test(ActionEvent event)
	// {
	// // win_cash.
	// controlofcashwin.lab_cashier.setText("23333");
	// }
	// public void setcontrolofcashwin()
	// {
	// //controlofcashwin=temp;
	// }

	public void commit_input(ActionEvent event) {
		String sql;
		sql = "select * from PRODUCT where PID=" + text_addpid.getText() + " and P_NAME= '" + text_addpname.getText()
				+ "'";

		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next()) {
				int price = rs.getInt("P_PRICE");
				int num = Integer.parseInt(text_addnum.getText());
				int total = price * num;
				System.out.println(control_cashwin.tab1_list.size());
				for (int i = 0; i < control_cashwin.tab1_list.size(); i++) {
					if (control_cashwin.tab1_list.get(i).getCB().compareTo(text_addpid.getText()) == 0) {
						num = num + Integer.parseInt(control_cashwin.tab1_list.get(i).getCF());
						total = price * num;
						control_cashwin.tab1_list.get(i).setCF(Integer.toString(num));
						control_cashwin.tab1_list.get(i).setCG(Integer.toString(total));
						control_cashwin.tab1_list.set(i, control_cashwin.tab1_list.get(i));
						return;
					}
				}
				control_cashwin.tab1_list.add(new eightproperty(Integer.toString(control_cashwin.tab1_list.size() + 1),
						text_addpid.getText(), text_addpname.getText(), rs.getString("UNIT").trim(),
						rs.getString("P_PRICE").trim(), Integer.toString(num), Integer.toString(total), " "));
				// control_cashwin.table_tab1.setItems(control_cashwin.tab1_list);
			} else {
				JOptionPane.showMessageDialog(null, "不存在该产品，请重新输入", "添加失败", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}

	}

	public void reset_input(ActionEvent event) {
		text_addpid.clear();
		text_addpname.clear();
		text_addnum.setText("1");
	}

	// public void changenum(ActionEvent event) {
	//
	// int num = Integer.parseInt(text_addnum.getText().trim());
	// if (num <= 0)
	// num = 1;
	// if (num >= remain)
	// num = remain;
	// text_addnum.setText(Integer.toString(num));
	//
	// }

	public void add(ActionEvent event) {
		int num = Integer.parseInt(text_addnum.getText().trim());
		num++;
		if (num <= 0)
			num = 1;
		if (num >= remain)
			num = remain;
		text_addnum.setText(Integer.toString(num));
	}

	public void minus(ActionEvent event) {
		int num = Integer.parseInt(text_addnum.getText().trim());
		num--;
		if (num <= 0)
			num = 1;
		if (num >= remain)
			num = remain;
		text_addnum.setText(Integer.toString(num));
	}

	public void autocomplete_pid() {
		String sql;
		sql = "select PID from PRODUCT";
		// text_addpid.setText("123");
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		try {
			List<String> list;
			list = new ArrayList<String>();
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the product table is null");
			else {
				int pid = rs.getInt("PID");
				list.add(Integer.toString(pid));
				while (rs.next()) {
					pid = rs.getInt("PID");
					list.add(Integer.toString(pid));
				}
			}
			rs.close();
			AutoCompletionBinding<String> textAutoBingding = TextFields.bindAutoCompletion(text_addpid,
					FXCollections.observableArrayList(list));
			textAutoBingding.setVisibleRowCount(5);
			textAutoBingding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {
				@Override
				public void handle(AutoCompletionEvent<String> event) {
					// TODO Auto-generated method stub
					try {
						String sql;
						sql = "select P_NAME,REMAINING from PRODUCT where PID = " + text_addpid.getText();
						ResultSet rs = Main.stmt.executeQuery(sql);
						rs.next();
						text_addpname.setText(rs.getString("P_NAME").trim());
						remain = rs.getInt("REMAINING");
						lab_num.setText(Integer.toString(remain));
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("login failed");
						text_addpname.clear();
						remain = 1;
						lab_num.setText(null);
					}
				}
			});
			// textAutoBingding.setUserInput(list.toString());
			// text_addpid.setAccessibleText(list);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}
		// }
		// });

	}

	public void autowithpid() {
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		try {
			String sql;
			sql = "select P_NAME,REMAINING from PRODUCT where PID = " + text_addpid.getText();
			ResultSet rs = Main.stmt.executeQuery(sql);
			rs.next();
			text_addpname.setText(rs.getString("P_NAME").trim());
			remain = rs.getInt("REMAINING");
			lab_num.setText(Integer.toString(remain));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
			text_addpname.clear();
			remain = 1;
			lab_num.setText(null);
		}
		// }
		//
		// });

	}

	public void autocomplete_pname() {
		String sql;
		sql = "select P_NAME from PRODUCT";
		// text_addpid.setText("123");
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		try {
			List<String> list;
			list = new ArrayList<String>();
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the product table is null");
			else {
				String pname = rs.getString("P_NAME").trim();
				list.add(pname);
				while (rs.next()) {
					pname = rs.getString("P_NAME").trim();
					list.add(pname);
				}
			}
			rs.close();
			AutoCompletionBinding<String> textAutoBingding = TextFields.bindAutoCompletion(text_addpname,
					FXCollections.observableArrayList(list));
			textAutoBingding.setVisibleRowCount(5);
			textAutoBingding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<String>>() {

				@Override
				public void handle(AutoCompletionEvent<String> event) {
					// TODO Auto-generated method stub
					try {
						String sql;
						sql = "select PID,REMAINING from PRODUCT where P_NAME = '" + text_addpname.getText().trim()
								+ "'";
						ResultSet rs = Main.stmt.executeQuery(sql);
						rs.next();
						text_addpid.setText(rs.getString("PID").trim());
						remain = rs.getInt("REMAINING");
						lab_num.setText(Integer.toString(remain));
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("login failed");
						text_addpid.clear();
						remain = 1;
						lab_num.setText(null);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
		}
		// }
		// });

	}

	public void autowithpname() {
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// TODO Auto-generated method stub
		try {
			String sql;
			sql = "select PID,REMAINING from PRODUCT where P_NAME = '" + text_addpname.getText().trim() + "'";
			ResultSet rs = Main.stmt.executeQuery(sql);
			rs.next();
			text_addpid.setText(rs.getString("PID").trim());
			remain = rs.getInt("REMAINING");
			lab_num.setText(Integer.toString(remain));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login failed");
			text_addpid.clear();
			remain = 1;
			lab_num.setText(null);
		}
		// }
		//
		// });
	}
}
