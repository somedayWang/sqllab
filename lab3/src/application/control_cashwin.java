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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class control_cashwin implements Initializable {

	@FXML
	private Button btn_addvip, btn_addpro, btn_reset, btn_commitadd, btn_resetadd, btn_resetone, btn_ifvip, btn_xiaban;
	@FXML
	private Button btn_insert, btn_update, btn_delete, btn_select;
	@FXML
	public Label lab_cashier, lab_stime, lab_ntime, lab_total, lab_bufftotal;
	@FXML
	private TextField text_addpid, text_addpname, text_addnum;
	@FXML
	public TableView<sixproperty> table_tab2, table_tab3, table_tab4;
	@FXML
	public TableView<eightproperty> table_tab1;
	@FXML
	private TableColumn<eightproperty, String> tab1_c1, tab1_c2, tab1_c3, tab1_c4, tab1_c5, tab1_c6, tab1_c7;
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
	public static ObservableList<eightproperty> tab1_list = FXCollections.observableArrayList();
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (Main.cid <= 100) {
						btn_insert.setDisable(false);
						btn_update.setDisable(false);
						btn_delete.setDisable(false);
						btn_select.setDisable(false);
					} else {
						btn_insert.setDisable(true);
						btn_update.setDisable(true);
						btn_delete.setDisable(true);
						btn_select.setDisable(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("update failed");
				}
			}
		});

		// fresh_vip();
		autoupdatetime();
		autoupdateprice();
	}

	// tools 函数
	public static int getChineseLength(String name, String endcoding) throws Exception {
		int len = 0; // 定义返回的字符串长度
		int j = 0;
		// 按照指定编码得到byte[]
		byte[] b_name = name.getBytes(endcoding);
		while (true) {
			short tmpst = (short) (b_name[j] & 0xF0);
			if (tmpst >= 0xB0) {
				if (tmpst < 0xC0) {
					j += 2;
					len += 2;
				} else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
					j += 2;
					len += 2;
				} else if (tmpst == 0xE0) {
					j += 3;
					len += 2;
				} else if (tmpst == 0xF0) {
					short tmpst0 = (short) ((b_name[j]) & 0x0F);
					if (tmpst0 == 0) {
						j += 4;
						len += 2;
					} else if ((tmpst0 > 0) && (tmpst0 < 12)) {
						j += 5;
						len += 2;
					} else if (tmpst0 > 11) {
						j += 6;
						len += 2;
					}
				}
			} else {
				j += 1;
				len += 1;
			}
			if (j > b_name.length - 1) {
				break;
			}
		}
		return len;
	}

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
							total = 0;
							for (int i = 0; i < tab1_list.size(); i++) {
								total = total + Integer.parseInt(control_cashwin.tab1_list.get(i).getCG());
							}
							lab_total.setText(Double.toString(total));
							if (isvip)
								buff = 0.9;
							else
								buff = 1.0;
							bufftotal = buff * total;
							lab_bufftotal.setText(Double.toString(bufftotal));

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("update failed");
						}
					}
				});
			}
		}, 1000, 1000);

	}

	public void autoupdateprice() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							total = 0;
							for (int i = 0; i < tab1_list.size(); i++) {
								total = total + Integer.parseInt(control_cashwin.tab1_list.get(i).getCG());
							}
							lab_total.setText(Double.toString(total));
							if (isvip)
								buff = 0.9;
							else
								buff = 1.0;
							bufftotal = buff * total;
							lab_bufftotal.setText(Double.toString(bufftotal));

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("update failed");
						}
					}
				});
			}
		}, 100, 100);

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
		tab1_c7.setCellValueFactory(new PropertyValueFactory<>("CG"));
		table_tab1.setItems(tab1_list);
		table_tab1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
				JOptionPane.showInputDialog(null, "请输入录入商品的名称,注意不要超过15个字", "输入名称", JOptionPane.PLAIN_MESSAGE));
		unit = new String(
				JOptionPane.showInputDialog(null, "请输入录入商品的计量单位,注意不要超过5个字", "输入单位", JOptionPane.PLAIN_MESSAGE));
		price = new String(JOptionPane.showInputDialog(null, "请输入录入商品的单价,使用英文符号 . 分隔小数部分和整数部分, 最大支持小数点后四位", "输入单价",
				JOptionPane.PLAIN_MESSAGE));
		remaining = new String(JOptionPane.showInputDialog(null, "请输入录入商品的存货量", "输入存货", JOptionPane.PLAIN_MESSAGE));
		total = "商品名称：" + pname + "\n商品单位：" + unit + "\n商品单位：" + price + "\n商品单位：" + remaining;
		if (JOptionPane.showConfirmDialog(null, total, "确认以上信息是否正确", JOptionPane.OK_CANCEL_OPTION) == 0) {
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
							"录入失败,该商品名称和单位已存在,对应商品号为" + Integer.toString(crs.getInt(1) + 100000), "录入失败",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "录入成功，商品号为" + Integer.toString(crs.getInt(1)), "录入成功",
							JOptionPane.PLAIN_MESSAGE);
				}
				crs.close();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
	}

	public void pro_update(ActionEvent event) {

	}

	public void pro_delete(ActionEvent event) {

	}

	public void pro_select(ActionEvent event) {

	}

	public void reset_product(ActionEvent event) {
		tab1_list.clear();
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

	public void ifvip(ActionEvent event) {
		try {
			win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ifvipwin.fxml"));
			Parent root = loader.load();
			win_addpro.setTitle("Let 's confirm his vip ID");
			win_addpro.setScene(new Scene(root));
			win_addpro.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ifvip failed");
		}
	}

	public void addvip(ActionEvent event) {
		try {
			// control_newvip.money = 0;
			win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("newvipwindow.fxml"));
			Parent root = loader.load();
			win_addpro.setTitle("A new VIP");
			win_addpro.setScene(new Scene(root));
			Main.money = 1;
			win_addpro.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ifvip failed");
		}
	}

	public void add_product(ActionEvent event) {
		try {
			win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("inputwindow.fxml"));
			// controlofinput=loader.getController();
			/*
			 * controlofinput.btn_commitadd.setOnAction(new EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) {
			 * lab_cashier.setText("2333"); } });
			 */
			Parent root = loader.load();
			// controlofinput.setcontrolofcashwin();
			/*
			 * Platform.runLater(new Runnable() {
			 * 
			 * @Override public void run() { try {
			 * 
			 * 
			 * }catch(Exception e) { e.printStackTrace(); System.out.println("haiyahaoqia");
			 * } } });
			 */
			// loader
			win_addpro.setTitle("Welcome to working time!");
			win_addpro.setScene(new Scene(root));

			win_addpro.show();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("add failed");
		}
	}

	public void commit_inputadd(ActionEvent event) {

		try {
			String sql = new String("select REMAINING from PRODUCT where PID = ");
			int numsize = tab1_list.size(), num;
			for (int i = 0; i < numsize; i++) {
				ResultSet rs = Main.stmt.executeQuery(sql + tab1_list.get(i).getCB());
				rs.next();
				num = rs.getInt("REMAINING");
				if (num < Integer.parseInt(tab1_list.get(i).getCF())) {
					JOptionPane.showMessageDialog(null, "商品存货不足，请确认商品 " + tab1_list.get(i).getCC(), "提交失败",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				rs.close();
			}
			win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("listwindow.fxml"));
			Parent root = loader.load();
			win_addpro.setTitle("打印小票信息");
			win_addpro.setScene(new Scene(root));
			win_addpro.setResizable(false);
			win_addpro.setHeight(367);
			win_addpro.setWidth(477);
			win_addpro.show();

			// String mes=new String(
			// String.format("%-46s", "商品名称")+
			// String.format("%-11s", "商品单价")+
			// String.format("%-11s", "商品数量")+
			// String.format("%-11s", "商品总价")+"\n");
			// Alert alert = new Alert(AlertType.CONFIRMATION);
			// alert.setTitle("打印小票信息");
			// alert.setHeaderText(null);
			// alert.setContentText("I have a great message for you!");

			// alert.showAndWait();
			// String mes=new String();
			// Button btn=new Button("ok");
			// win_addpro=new Stage();
			// win_addpro.setHeight(numsize*20+10);
			// win_addpro.setWidth(500);
			// win_addpro.setTitle("打印小票信息");
			// Label label1=new Label(mes);
			// Scene ssc=new Scene(btn);
			//
			// for(int i=0;i<numsize;i++)
			// {
			// String temp;
			// temp=tab1_list.get(i).getCC();
			// for(int j=60-getChineseLength(tab1_list.get(i).getCC(),"GB2312");j>=0;j--)
			// {
			// temp=temp+" ";
			// }
			// //if(getChineseLength(mes,"GB2312")%4==0) mes=mes+"\t";
			// mes=mes+temp+"\t";
			// mes=mes+String.format("%-15s", tab1_list.get(i).getCE());
			// mes=mes+String.format("%-15s", tab1_list.get(i).getCF());
			// mes=mes+String.format("%-15s", tab1_list.get(i).getCG())+"\n";
			// }
			// System.out.println(mes);
			// mes= new String(mes.getBytes(),"GB2312");
			//
			// //JOptionPane.showMessageDialog(null,new String(mes.getBytes(),"GBK"),
			// "打印小票信息",JOptionPane.PLAIN_MESSAGE);
			// alert.setWidth(500);
			//
			// alert.setContentText(mes);
			// alert.show();
			// win_addpro.setScene(ssc);
			// win_addpro.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed");
		}
	}

	// tab_product
	public void fresh_pro() {
		String sql;
		sql = "select * from PRODUCT";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					ResultSet rs = Main.stmt.executeQuery(sql);
					if (rs.next() == false)
						System.out.println("the product table is null");
					else {
						tab2_list.clear();
						tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME"), rs.getString("UNIT"),
								rs.getString("P_PRICE"), rs.getString("REMAINING"), " "));
						while (rs.next()) {
							tab2_list.add(new sixproperty(rs.getString("PID"), rs.getString("P_NAME"),
									rs.getString("UNIT"), rs.getString("P_PRICE"), rs.getString("REMAINING"), " "));
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

	// tab_vip
	public void fresh_vip() {
		String sql;
		sql = "select * from VIP";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
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
							tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"),
									rs.getString("V_PHONE"), "√", rs.getString("T_NUM"),
									rs.getString("PAST_DATETIME").substring(0, 19)));
						} else {
							tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"),
									rs.getString("V_PHONE"), "×", rs.getString("T_NUM"),
									rs.getString("PAST_DATETIME").substring(0, 19)));
						}
						// tab3_list.add(temp);
						while (rs.next()) {
							if (Integer.parseInt(rs.getString("OVERTAG")) == 0) {
								tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"),
										rs.getString("V_PHONE"), "√", rs.getString("T_NUM"),
										rs.getString("PAST_DATETIME").substring(0, 19)));
							} else {
								tab3_list.add(new sixproperty(rs.getString("VID"), rs.getString("V_NAME"),
										rs.getString("V_PHONE"), "×", rs.getString("T_NUM"),
										rs.getString("PAST_DATETIME").substring(0, 19)));
							}
						}
						// table_tab3.setItems(tab3_list);

					}
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fresh failed");
				}
			}
		});
	}

	// tab_log
	public void fresh_log() {
		String sql;
		sql = "select * from TRADE order by T_DATETIME";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
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
							tab4_list.add(new sixproperty(rs.getString("CID"),
									rs.getString("T_DATETIME").substring(0, 19), rs.getString("PID"),
									rs.getString("P_NAME").trim(), rs.getString("T_NUM"), rs.getString("T_PRICE")));
						}

					}
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fresh failed");
				}
			}
		});
	}
	// xiaban

	public void xiaban(ActionEvent event) {
		try {
			// control_newvip.money = 0;
			win_addpro = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("xiabanlistwindow.fxml"));
			Parent root = loader.load();
			win_addpro.setTitle("准备下班");
			win_addpro.setScene(new Scene(root));
			win_addpro.show();
			Stage temp = (Stage) btn_xiaban.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ifvip failed");
		}
	}
}