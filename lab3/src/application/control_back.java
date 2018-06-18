package application;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class control_back implements Initializable {

	@FXML
	private Button btn_updatepro;

	@FXML
	private Button btn_selectca;

	@FXML
	private Button btn_updateca;

	@FXML
	private Button btn_selectpro;

	@FXML
	private Button btn_insertpro;

	@FXML
	private Button btn_deletepro;

	@FXML
	private Button btn_insertca;

	@FXML
	private Button btn_deleteca;

	@FXML
	private Button btn_insert, btn_update, btn_delete, btn_select;
	@FXML
	public Label lab_cashier, lab_stime, lab_ntime, lab_total, lab_bufftotal;
	@FXML
	private TextField text_addpid, text_addpname, text_addnum;
	@FXML
	public TableView<sixproperty> table_tab2, table_tab3, table_tab4;
	@FXML
	public TableView<sixproperty> table_tab1;
	@FXML
	private TableColumn<sixproperty, String> tab1_c1, tab1_c2, tab1_c3, tab1_c4, tab1_c5, tab1_c6;
	@FXML
	private TableColumn<sixproperty, String> tab2_c1, tab2_c2, tab2_c3, tab2_c4, tab2_c5;
	@FXML
	private TableColumn<sixproperty, String> tab3_c1, tab3_c2, tab3_c3, tab3_c4, tab3_c5, tab3_c6;
	@FXML
	private TableColumn<sixproperty, String> tab4_c1, tab4_c2, tab4_c3, tab4_c4, tab4_c5, tab4_c6, tab4_c7, tab4_c8;

	// static control_input controlofinput;
	// the entry of the class
	public static double buff, total, bufftotal;
	public static Boolean isvip = false;
	public static String vname;
	public static int vid;
	static Stage win_addpro;

	static Date time;
	static Timer timer;
	static ObservableList<sixproperty> tab1_list = FXCollections.observableArrayList();
	static ObservableList<sixproperty> tab2_list = FXCollections.observableArrayList();
	static ObservableList<sixproperty> tab3_list = FXCollections.observableArrayList();
	static ObservableList<sixproperty> tab4_list = FXCollections.observableArrayList();
	// static List<String> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).
		inti_tab();
		lab_cashier.setText(Main.cname);
		// time=new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MM.dd HH:mm:ss");
		lab_stime.setText(ft.format(new Date()).trim());
		// lab_ntime.setText(ft.format(new Date()).trim());

		// fresh_vip();
		autoupdatetime();

	}

	// tools ����

	// Initialize about
	public void autoupdatetime() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							SimpleDateFormat ft = new SimpleDateFormat("MM.dd HH:mm:ss");
							lab_ntime.setText(ft.format(new Date()).trim());
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("update failed");
						}
					}
				});
			}
		}, 1000, 1000);

	}

	public void inti_tab() {
		// tab1_list=FXCollections.observableArrayList();
		// tab2_list=FXCollections.observableArrayList();
		// tab3_list=FXCollections.observableArrayList();
		// tab4_list=FXCollections.observableArrayList();

		// table_tab1.setEditable(true);
		tab1_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
		tab1_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
		tab1_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
		tab1_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
		tab1_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
		tab1_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
		// tab1_c7.setCellValueFactory(new PropertyValueFactory<>("CG"));
		table_tab1.setItems(tab1_list);
		// table_tab1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		table_tab2.setEditable(false);
		tab2_c1.setCellValueFactory(new PropertyValueFactory<>("CA"));
		tab2_c2.setCellValueFactory(new PropertyValueFactory<>("CB"));
		tab2_c3.setCellValueFactory(new PropertyValueFactory<>("CC"));
		tab2_c4.setCellValueFactory(new PropertyValueFactory<>("CD"));
		tab2_c5.setCellValueFactory(new PropertyValueFactory<>("CE"));
		// tab2_c6.setCellValueFactory(new PropertyValueFactory<>("CF"));
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
		// tab4_c7.setCellValueFactory(new PropertyValueFactory<>("CG"));
		// tab4_c8.setCellValueFactory(new PropertyValueFactory<>("CH"));
		table_tab4.setItems(tab4_list);

	}

	// tab_cash
	public void pro_insert(ActionEvent event) {
		String pname, unit, price, remaining, total;
		pname = new String(
				JOptionPane.showInputDialog(null, "������¼����Ʒ������,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
		unit = new String(
				JOptionPane.showInputDialog(null, "������¼����Ʒ�ļ�����λ,ע�ⲻҪ����5����", "���뵥λ", JOptionPane.PLAIN_MESSAGE));
		price = new String(JOptionPane.showInputDialog(null, "������¼����Ʒ�ĵ���,ʹ��Ӣ�ķ��� . �ָ�С�����ֺ���������, ���֧��С�������λ", "���뵥��",
				JOptionPane.PLAIN_MESSAGE));
		remaining = new String(JOptionPane.showInputDialog(null, "������¼����Ʒ�Ĵ����", "������", JOptionPane.PLAIN_MESSAGE));
		total = "��Ʒ���ƣ�" + pname + "\n��Ʒ��λ��" + unit + "\n��Ʒ��λ��" + price + "\n��Ʒʣ�ࣺ" + remaining;
		if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
			System.out.println(total);
			try {
				CallableStatement crs = Main.conn.prepareCall("{? = call dbo.insertpro(?,?,?,?)}");
				crs.registerOutParameter(1, java.sql.Types.INTEGER);
				crs.setString(2, pname.trim());
				crs.setString(3, unit.trim());
				crs.setString(4, price.trim());
				crs.setString(5, remaining.trim());
				crs.execute();
				if (crs.getInt(1) <= 0) {
					System.out.println("vid failed");
					JOptionPane.showMessageDialog(null,
							"¼��ʧ��,����Ʒ���ƺ͵�λ�Ѵ���,��Ӧ��Ʒ��Ϊ" + Integer.toString(crs.getInt(1) + 100000), "¼��ʧ��",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "¼��ɹ�����Ʒ��Ϊ" + Integer.toString(crs.getInt(1)), "¼��ɹ�",
							JOptionPane.PLAIN_MESSAGE);
				}
				crs.close();
				fresh_pro();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void pro_update(ActionEvent event) {
		String pid, pname, unit, price, remaining, total;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ����ѡ������Ʒ���в���������Ϊ�����Ӧid����", "ѡ��Ŀ��", JOptionPane.OK_OPTION) == 0) {
			if (table_tab2.getSelectionModel().getSelectedCells().size() != 0) {
				pid = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCA().trim();
				pname = new String(
						JOptionPane.showInputDialog(null, "������¼����Ʒ������,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
				unit = new String(
						JOptionPane.showInputDialog(null, "������¼����Ʒ�ļ�����λ,ע�ⲻҪ����5����", "���뵥λ", JOptionPane.PLAIN_MESSAGE));
				price = new String(JOptionPane.showInputDialog(null, "������¼����Ʒ�ĵ���,ʹ��Ӣ�ķ��� . �ָ�С�����ֺ���������, ���֧��С�������λ",
						"���뵥��", JOptionPane.PLAIN_MESSAGE));
				remaining = new String(
						JOptionPane.showInputDialog(null, "������¼����Ʒ�Ĵ����", "������", JOptionPane.PLAIN_MESSAGE));
				total = "��Ʒ���ƣ�" + pname + "\n��Ʒ��λ��" + unit + "\n��Ʒ��λ��" + price + "\n��Ʒʣ�ࣺ" + remaining;
				if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
					System.out.println(total);
					try {
						String sql;
						sql = "update PRODUCT set PID = " + pid.trim() + " , P_NAME = '" + pname.trim() + "' , UNIT = '"
								+ unit + "' , P_PRICE = " + price + " , REMAINING = " + remaining + " where PID = "
								+ pid.trim();
						Main.stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, total, "�޸ĳɹ�", JOptionPane.PLAIN_MESSAGE);
						fresh_pro();
						return;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, total, "�޸�ʧ��", JOptionPane.PLAIN_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "δѡ���κ���", "ѡ�����", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			pid = new String(JOptionPane.showInputDialog(null, "�������޸���Ʒ��id", "����id", JOptionPane.PLAIN_MESSAGE));
			for (int i = 0; i < tab2_list.size(); i++) {
				if (tab2_list.get(i).getCA().compareTo(pid.trim()) == 0) {
					pname = new String(JOptionPane.showInputDialog(null, "��������Ʒ������,ע�ⲻҪ����15����", "��������",
							JOptionPane.PLAIN_MESSAGE));
					unit = new String(JOptionPane.showInputDialog(null, "��������Ʒ�ļ�����λ,ע�ⲻҪ����5����", "���뵥λ",
							JOptionPane.PLAIN_MESSAGE));
					price = new String(JOptionPane.showInputDialog(null, "��������Ʒ�ĵ���,ʹ��Ӣ�ķ��� . �ָ�С�����ֺ���������, ���֧��С�������λ",
							"���뵥��", JOptionPane.PLAIN_MESSAGE));
					remaining = new String(
							JOptionPane.showInputDialog(null, "��������Ʒ�Ĵ����", "������", JOptionPane.PLAIN_MESSAGE));
					total = "��Ʒ���ƣ�" + pname + "\n��Ʒ��λ��" + unit + "\n��Ʒ��λ��" + price + "\n��Ʒʣ�ࣺ" + remaining;
					if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
						System.out.println(total);
						try {
							String sql;
							sql = "update PRODUCT set PID = " + pid.trim() + " , P_NAME = '" + pname.trim()
									+ "' , UNIT = '" + unit + "' , P_PRICE = " + price + " , REMAINING = " + remaining
									+ " where PID = " + pid.trim();
							Main.stmt.executeUpdate(sql);
							JOptionPane.showMessageDialog(null, total, "�޸ĳɹ�", JOptionPane.PLAIN_MESSAGE);
							fresh_pro();
							return;
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, total, "�޸�ʧ��", JOptionPane.PLAIN_MESSAGE);
						}
					}
					fresh_pro();
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid����Ʒ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			fresh_pro();
		}

	}

	public void pro_delete(ActionEvent event) {
		String pid, pname, unit, price, remaining, total;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ����ѡ������Ʒ���в���������Ϊ�����Ӧid����", "ѡ��Ŀ��", JOptionPane.OK_OPTION) == 0) {
			if (table_tab2.getSelectionModel().getSelectedCells().size() != 0) {
				pid = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCA().trim();
				pname = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCB().trim();
				unit = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCC().trim();
				price = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCD().trim();
				remaining = tab2_list.get(table_tab2.getSelectionModel().getSelectedCells().get(0).getRow()).getCE()
						.trim();
				total = "��Ʒ���ƣ�" + pname + "\n��Ʒ��λ��" + unit + "\n��Ʒ��λ��" + price + "\n��Ʒʣ�ࣺ" + remaining;
				if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
					System.out.println(total);
					try {
						String sql;
						sql = "delete PRODUCT where PID = " + pid.trim();
						Main.stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, total, "ɾ���ɹ�", JOptionPane.PLAIN_MESSAGE);
						fresh_pro();
						return;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, total, "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "δѡ���κ���", "ѡ�����", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			pid = new String(JOptionPane.showInputDialog(null, "������ɾ����Ʒ��id", "����id", JOptionPane.PLAIN_MESSAGE));
			for (int i = 0; i < tab2_list.size(); i++) {
				if (tab2_list.get(i).getCA().compareTo(pid.trim()) == 0) {
					pname = tab2_list.get(i).getCB().trim();
					unit = tab2_list.get(i).getCC().trim();
					price = tab2_list.get(i).getCD().trim();
					remaining = tab2_list.get(i).getCE().trim();
					total = "��Ʒ���ƣ�" + pname + "\n��Ʒ��λ��" + unit + "\n��Ʒ��λ��" + price + "\n��Ʒʣ�ࣺ" + remaining;
					if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
						System.out.println(total);
						try {
							String sql;
							sql = "delete PRODUCT where PID = " + pid.trim();
							Main.stmt.executeUpdate(sql);
							JOptionPane.showMessageDialog(null, total, "ɾ���ɹ�", JOptionPane.PLAIN_MESSAGE);
							fresh_pro();
							return;
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, total, "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
						}
					}
					fresh_pro();
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid����Ʒ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			fresh_pro();
		}

	}

	public void pro_select(ActionEvent event) {
		fresh_pro();
		String cid, cname;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ�ѡ��id��ѯ������Ϊ���Ʋ�ѯ", "ѡ���ѯ��ʽ", JOptionPane.OK_OPTION) == 0) {
			cid = new String(JOptionPane.showInputDialog(null, "��������Ʒid", "����ID", JOptionPane.PLAIN_MESSAGE));
			// table_tab1.getSelectionModel().select(0);
			for (int i = 0; i < tab2_list.size(); i++) {
				if (tab2_list.get(i).getCA().compareTo(cid.trim()) == 0) {
					table_tab2.getSelectionModel().select(i);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid����Ʒ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			cname = new String(
					JOptionPane.showInputDialog(null, "��������Ʒ����,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
			// table_tab1.getSelectionModel().select(0);
			for (int i = 0; i < tab2_list.size(); i++) {
				if (tab2_list.get(i).getCB().compareTo(cname.trim()) == 0) {
					table_tab2.getSelectionModel().select(i);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧ���Ƶ���Ʒ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	@FXML
	void ca_insert(ActionEvent event) {
		String cid, cname, cpw, cphone, total;
		cid = new String(
				JOptionPane.showInputDialog(null, "����������Աid,����0-100���Ϊ����Ա�˺�", "����ID", JOptionPane.PLAIN_MESSAGE));
		cname = new String(JOptionPane.showInputDialog(null, "����������Ա����,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
		cpw = new String(JOptionPane.showInputDialog(null, "����������,ע�ⲻҪ����30���ַ�", "��������", JOptionPane.PLAIN_MESSAGE));
		cphone = new String(JOptionPane.showInputDialog(null, "������Ԥ���ֻ���", "������", JOptionPane.PLAIN_MESSAGE));
		total = "����Աid��" + cid + "\n����Ա���ƣ�" + cname + "\n���룺" + cpw + "\nԤ���ֻ��ţ�" + cphone;
		if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
			System.out.println(total);
			try {
				CallableStatement crs = Main.conn.prepareCall("{? = call dbo.insertca(?,?,?,?)}");
				crs.registerOutParameter(1, java.sql.Types.INTEGER);
				crs.setString(2, cid.trim());
				crs.setString(3, cname.trim());
				crs.setString(4, cpw.trim());
				crs.setString(5, cphone.trim());
				crs.execute();
				if (crs.getInt(1) == 1) {
					System.out.println("vid failed");
					JOptionPane.showMessageDialog(null, "¼��ʧ��,������Ա��Ż��������ѱ�ռ��", "¼��ʧ��", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "����Ա¼��ɹ�", "¼��ɹ�", JOptionPane.PLAIN_MESSAGE);
				}
				crs.close();
				fresh_cashier();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

	}

	@FXML
	void ca_select(ActionEvent event) {
		fresh_cashier();
		String cid, cname;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ�ѡ��id��ѯ������Ϊ���Ʋ�ѯ", "ѡ���ѯ��ʽ", JOptionPane.OK_OPTION) == 0) {
			cid = new String(
					JOptionPane.showInputDialog(null, "����������Աid,����0-100���Ϊ����Ա�˺�", "����ID", JOptionPane.PLAIN_MESSAGE));
			// table_tab1.getSelectionModel().select(0);
			for (int i = 0; i < tab1_list.size(); i++) {
				if (tab1_list.get(i).getCA().compareTo(cid.trim()) == 0) {
					table_tab1.getSelectionModel().select(i);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid������Ա", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			cname = new String(
					JOptionPane.showInputDialog(null, "����������Ա����,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
			// table_tab1.getSelectionModel().select(0);
			for (int i = 0; i < tab1_list.size(); i++) {
				if (tab1_list.get(i).getCB().compareTo(cname.trim()) == 0) {
					table_tab1.getSelectionModel().select(i);
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧ���Ƶ�����Ա", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	@FXML
	void ca_update(ActionEvent event) {
		String cid, cname, cpw, cphone, total;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ����ѡ������Ʒ���в���������Ϊ�����Ӧid����", "ѡ��Ŀ��", JOptionPane.OK_OPTION) == 0) {
			if (table_tab1.getSelectionModel().getSelectedCells().size() != 0) {
				cid = tab1_list.get(table_tab1.getSelectionModel().getSelectedCells().get(0).getRow()).getCA().trim();
				cname = new String(
						JOptionPane.showInputDialog(null, "����������Ա����,ע�ⲻҪ����15����", "��������", JOptionPane.PLAIN_MESSAGE));
				cpw = new String(
						JOptionPane.showInputDialog(null, "����������,ע�ⲻҪ����30���ַ�", "��������", JOptionPane.PLAIN_MESSAGE));
				cphone = new String(JOptionPane.showInputDialog(null, "������Ԥ���ֻ���", "������", JOptionPane.PLAIN_MESSAGE));
				total = "����Աid��" + cid + "\n����Ա���ƣ�" + cname + "\n���룺" + cpw + "\nԤ���ֻ��ţ�" + cphone;
				if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
					System.out.println(total);
					try {
						String sql;
						sql = "update CASHIER set  C_NAME = '" + cname.trim() + "' , C_PW = '" + cpw.trim()
								+ "' , C_PHONE = '" + cphone.trim() + "' where CID = " + cid.trim();
						Main.stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, total, "�޸ĳɹ�", JOptionPane.PLAIN_MESSAGE);
						fresh_cashier();
						return;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, total, "�޸�ʧ��", JOptionPane.PLAIN_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "δѡ���κ���", "ѡ�����", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			cid = new String(JOptionPane.showInputDialog(null, "�������޸���Ʒ��id", "����id", JOptionPane.PLAIN_MESSAGE));
			for (int i = 0; i < tab1_list.size(); i++) {
				if (tab1_list.get(i).getCA().compareTo(cid.trim()) == 0) {
					cname = new String(JOptionPane.showInputDialog(null, "����������Ա����,ע�ⲻҪ����15����", "��������",
							JOptionPane.PLAIN_MESSAGE));
					cpw = new String(
							JOptionPane.showInputDialog(null, "����������,ע�ⲻҪ����30���ַ�", "��������", JOptionPane.PLAIN_MESSAGE));
					cphone = new String(
							JOptionPane.showInputDialog(null, "������Ԥ���ֻ���", "������", JOptionPane.PLAIN_MESSAGE));
					total = "����Աid��" + cid + "\n����Ա���ƣ�" + cname + "\n���룺" + cpw + "\nԤ���ֻ��ţ�" + cphone;
					if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
						System.out.println(total);
						try {
							String sql;
							sql = "update CASHIER set  C_NAME = '" + cname.trim() + "' , C_PW = '" + cpw.trim()
									+ "' , C_PHONE = '" + cphone.trim() + "' where CID = " + cid.trim();
							Main.stmt.executeUpdate(sql);
							JOptionPane.showMessageDialog(null, total, "�޸ĳɹ�", JOptionPane.PLAIN_MESSAGE);
							fresh_cashier();
							return;
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, total, "�޸�ʧ��", JOptionPane.PLAIN_MESSAGE);
						}
					}
					fresh_cashier();
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid������Ա", "�޸�ʧ��", JOptionPane.ERROR_MESSAGE);
			fresh_cashier();
		}

	}

	@FXML
	void ca_delete(ActionEvent event) {
		String cid, cname, cpw, cphone, total;
		if (JOptionPane.showConfirmDialog(null, "�Ƿ����ѡ������Ʒ���в���������Ϊ�����Ӧid����", "ѡ��Ŀ��", JOptionPane.OK_OPTION) == 0) {
			if (table_tab1.getSelectionModel().getSelectedCells().size() != 0) {
				cid = tab1_list.get(table_tab1.getSelectionModel().getSelectedCells().get(0).getRow()).getCA().trim();
				cname = tab1_list.get(table_tab1.getSelectionModel().getSelectedCells().get(0).getRow()).getCB().trim();
				cpw = tab1_list.get(table_tab1.getSelectionModel().getSelectedCells().get(0).getRow()).getCC().trim();
				cphone = tab1_list.get(table_tab1.getSelectionModel().getSelectedCells().get(0).getRow()).getCD()
						.trim();
				total = "����Աid��" + cid + "\n����Ա���ƣ�" + cname + "\n���룺" + cpw + "\nԤ���ֻ��ţ�" + cphone;
				if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
					System.out.println(total);
					try {
						String sql;
						if (Integer.parseInt(cid.trim()) != 0)
							sql = "delete CASHIER where CID = " + cid.trim();
						else {
							JOptionPane.showMessageDialog(null, "����ɾ��0�Ź���Ա", "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
							return;
						}
						Main.stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, total, "ɾ���ɹ�", JOptionPane.PLAIN_MESSAGE);
						fresh_cashier();
						return;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, total, "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "δѡ���κ���", "ѡ�����", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			cid = new String(JOptionPane.showInputDialog(null, "������ɾ����Ʒ��id", "����id", JOptionPane.PLAIN_MESSAGE));
			for (int i = 0; i < tab1_list.size(); i++) {
				if (tab1_list.get(i).getCA().compareTo(cid.trim()) == 0) {
					cname = tab1_list.get(i).getCB().trim();
					cpw = tab1_list.get(i).getCC().trim();
					cphone = tab1_list.get(i).getCD().trim();
					total = "����Աid��" + cid + "\n����Ա���ƣ�" + cname + "\n���룺" + cpw + "\nԤ���ֻ��ţ�" + cphone;
					if (JOptionPane.showConfirmDialog(null, total, "ȷ��������Ϣ�Ƿ���ȷ", JOptionPane.OK_CANCEL_OPTION) == 0) {
						System.out.println(total);
						try {
							String sql;
							if (Integer.parseInt(cid.trim()) != 0)
								sql = "delete CASHIER where CID = " + cid.trim();
							else {
								JOptionPane.showMessageDialog(null, "����ɾ��0�Ź���Ա", "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
								return;
							}
							Main.stmt.executeUpdate(sql);
							JOptionPane.showMessageDialog(null, total, "ɾ���ɹ�", JOptionPane.PLAIN_MESSAGE);
							fresh_cashier();
							return;
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, total, "ɾ��ʧ��", JOptionPane.PLAIN_MESSAGE);
						}
					}
					fresh_cashier();
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "δ�ҵ���Ӧid����Ʒ", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			fresh_cashier();
		}

	}

	public void reset_one(ActionEvent event) {
		List<Integer> row = new ArrayList<Integer>();
		for (int i = table_tab1.getSelectionModel().getSelectedCells().size() - 1; i >= 0; i--) {
			row.add(table_tab1.getSelectionModel().getSelectedCells().get(i).getRow());
		}
		for (int i = 0; i < row.size(); i++) {
			tab1_list.remove((int) row.get(i));
		}
		for (int i = 0; i < tab1_list.size(); i++) {
			control_cashwin.tab1_list.get(i).setCA(Integer.toString(i + 1));
		}
		table_tab1.refresh();
	}

	public void fresh_cashier() {
		String sql;
		sql = "select * from CASHIER";
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the product table is null");
			else {
				tab1_list.clear();
				if (rs.getString("W_DATETIME") != null) {
					tab1_list.add(new sixproperty(rs.getString("CID"), rs.getString("C_NAME").trim(),
							rs.getString("C_PW").trim(), rs.getString("C_PHONE").trim(),
							rs.getString("W_DATETIME").substring(0, 19), " "));
				} else {
					tab1_list.add(new sixproperty(rs.getString("CID"), rs.getString("C_NAME").trim(),
							rs.getString("C_PW").trim(), rs.getString("C_PHONE").trim(), " ", " "));
				}
				while (rs.next()) {
					if (rs.getString("W_DATETIME") != null) {
						tab1_list.add(new sixproperty(rs.getString("CID"), rs.getString("C_NAME").trim(),
								rs.getString("C_PW").trim(), rs.getString("C_PHONE").trim(),
								rs.getString("W_DATETIME").substring(0, 19), " "));
					} else {
						tab1_list.add(new sixproperty(rs.getString("CID"), rs.getString("C_NAME").trim(),
								rs.getString("C_PW").trim(), rs.getString("C_PHONE").trim(), " ", " "));
					}

				}
				// table_tab2.setItems(tab2_list);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fresh failed");
		}
		// }
		// });
	}

	// tab_product
	public void fresh_pro() {
		String sql;
		sql = "select * from PRODUCT";
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the product table is null");
			else {
				tab2_list.clear();
				tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME").trim(), rs.getString("UNIT"),
						rs.getString("P_PRICE"), rs.getString("REMAINING"), " "));
				while (rs.next()) {
					tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME").trim(),
							rs.getString("UNIT"), rs.getString("P_PRICE"), rs.getString("REMAINING"), " "));
				}
				// table_tab2.setItems(tab2_list);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fresh failed");
		}
		// }
		// });
	}

	// tab_vip
	public void fresh_vip() {
		String sql;
		sql = "select * from VIP";
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the vip table is null");
			else {
				tab3_list.clear();
				// ObservableList<sixproperty> tab3_list=FXCollections.observableArrayList();
				// sixproperty temp=new sixproperty(rs.getString("VID"),
				// rs.getString("V_NAME"),
				// rs.getString("V_PHONE"),
				// rs.getString("V_NAME"),
				// rs.getString("T_NUM"),
				// rs.getString("V_NAME"));
				if (Integer.parseInt(rs.getString("OVERTAG")) == 0) {
					tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"), rs.getString("V_PHONE"),
							"��", rs.getString("T_NUM"), rs.getString("PAST_DATETIME").substring(0, 19)));
				} else {
					tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"), rs.getString("V_PHONE"),
							"��", rs.getString("T_NUM"), rs.getString("PAST_DATETIME").substring(0, 19)));
				}
				// tab3_list.add(temp);
				while (rs.next()) {
					if (Integer.parseInt(rs.getString("OVERTAG")) == 0) {
						tab3_list.add(
								new sixproperty(rs.getString("VID"), rs.getString("V_NAME"), rs.getString("V_PHONE"),
										"��", rs.getString("T_NUM"), rs.getString("PAST_DATETIME").substring(0, 19)));
					} else {
						tab3_list.add(
								new sixproperty(rs.getString("VID"), rs.getString("V_NAME"), rs.getString("V_PHONE"),
										"��", rs.getString("T_NUM"), rs.getString("PAST_DATETIME").substring(0, 19)));
					}
				}
				// table_tab3.setItems(tab3_list);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fresh failed");
		}
		// }
		// });
	}

	// tab_log
	public void fresh_log() {
		String sql;
		sql = "select * from TRADE order by T_DATETIME";
		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		try {
			ResultSet rs = Main.stmt.executeQuery(sql);
			if (rs.next() == false)
				System.out.println("the TRADE table is null");
			else {
				tab4_list.clear();
				// ObservableList<sixproperty> tab3_list=FXCollections.observableArrayList();
				// sixproperty temp=new sixproperty(rs.getString("VID"),
				// rs.getString("V_NAME"),
				// rs.getString("V_PHONE"),
				// rs.getString("V_NAME"),
				// rs.getString("T_NUM"),
				// rs.getString("V_NAME"));
				tab4_list.add(new sixproperty(rs.getString("CID"), rs.getString("T_DATETIME").substring(0, 19),
						rs.getString("PID"), rs.getString("P_NAME").trim(), rs.getString("T_NUM"),
						rs.getString("T_PRICE")));
				while (rs.next()) {
					tab4_list.add(new sixproperty(rs.getString("CID"), rs.getString("T_DATETIME").substring(0, 19),
							rs.getString("PID"), rs.getString("P_NAME").trim(), rs.getString("T_NUM"),
							rs.getString("T_PRICE")));
				}

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fresh failed");
		}
		// }
		// });
	}
}