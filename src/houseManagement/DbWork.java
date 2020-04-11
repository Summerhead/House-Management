package houseManagement;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang3.RandomStringUtils;

public class DbWork {
	private static Connection conn = null;
	private static boolean verified = false;
	private static String who;
	private static boolean isAdmin = false;
	private static int userID = 6;
	private static int rows;
	private static HashMap<JTextField, Boolean> isInsertedMap = new HashMap<JTextField, Boolean>();
	private static int houseID;
	private static JTextField nameInput01;
	private static JTextField nameInput02;
	private static JTextField nameInput03;
	private static JTextField nameInput04;
	private static JTextField nameInput05;
	private static JTextField nameInput06;
	private static JTextField nameInput07;
	private static JTextField nameInput08;
	private static JTextField nameInput09;
	private static JTextField nameInput010;
	private static JTextField nameInput011;
	private static JTextField nameInput012;
	private static JTextField nameInput013;
	private static JTextField nameInput2;
	private static JTextField nameInput3;
	private static JTextField nameInput4;
	private static JTextField nameInput5;
	private static JTextField nameInput6;
	private static JTextField nameInput7;
	private static JTextField nameInput8;
	
	
	
	public static void dbWork() {
		
	}
	
	
	
	public static void openConnection() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			Class.forName(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/housemanagement?serverTimezone=UTC", "root",
					"beautyofbalance");
			if (conn != null) {
				System.out.println("Connection successful");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in openConnection: " + e);
		}
	}
	
	
	
	public static void closeConnection() {
		try {
			conn.close();
			System.out.println("Connection closed");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in closeConnection: " + e);
		}
	}
	
	
	
	public static void registration(JTextField tfLastName, JTextField tfFirstName, JTextField tfMiddleName,
			JTextField tfEmail, JPasswordField pfPassword1, JPasswordField pfPassword2, JTextField tfProfession,
			JTextField tfIDHouse, CardLayout cardLayout, JPanel cardHolder) {
		String lastName = tfLastName.getText();
		String firstName = tfFirstName.getText();
		String middleName = tfMiddleName.getText();
		String email = tfEmail.getText();
		String password = new String(pfPassword1.getPassword());
		String passwordToCheck = new String(pfPassword2.getPassword());
		String prof = tfProfession.getText();
		int idHouse = 0;
		boolean idHouseLegit = true;
		try {
			idHouse = Integer.parseInt(tfIDHouse.getText());
		}
		catch (Exception e) {
			idHouseLegit = false;
		}
		if ((lastName.isEmpty()) || (firstName.isEmpty()) || (middleName.isEmpty()) || (email.isEmpty())
				|| (password.isEmpty()) || (passwordToCheck.isEmpty()) || (prof.isEmpty())) {
			JOptionPane.showMessageDialog(null, "Пожалуйста, заполните все поля", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			if (password.length() > 7) {
				if (password.equals(passwordToCheck)) {
					try {
						openConnection();
						if (verifyEmail(email)) {
							boolean go_on = false;
							Statement st = conn.createStatement();
							ResultSet rs = st.executeQuery("SELECT id FROM house");
							while (rs.next()) {
								if (idHouse == rs.getInt("id") && idHouseLegit) {
									go_on = true;
									break;
								}
							}
							if (go_on) {
								System.out.println(
										"INSERT INTO request (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + lastName + "', '" + firstName + "', '" + middleName
												+ "', '" + email + "', '" + password + "', '" + prof + "', 0, '"
												+ idHouse + "')");
								st.executeUpdate(
										"INSERT INTO request (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + lastName + "', '" + firstName + "', '" + middleName
												+ "', '" + email + "', '" + password + "', '" + prof + "', 0, '"
												+ idHouse + "')",
										Statement.RETURN_GENERATED_KEYS);
								JOptionPane.showMessageDialog(null, "Заявка отправлена, пожалуйста, ждите подтвеждения",
										"Info", JOptionPane.PLAIN_MESSAGE);
								cardLayout.show(cardHolder, "Start");
								emptyTextFields(tfLastName, tfFirstName, tfMiddleName, tfEmail, pfPassword1,
										pfPassword2, tfProfession, tfIDHouse);
							}
							else {
								JOptionPane.showMessageDialog(null, "Введенный ID дома не существует", "Warning",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Такой Email уже существует, попробуйте снова",
									"Warning", JOptionPane.WARNING_MESSAGE);
						}
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова",
								"Error", JOptionPane.ERROR_MESSAGE);
						System.out.println("Error in registration: " + e);
					}
					closeConnection();
				}
				else {
					JOptionPane.showMessageDialog(null, "Введенные пароли не совпадают", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Пароль не должен быть короче 8 символов", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	
	
	public static boolean verifyEmail(String email) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM worker");
			while (rs.next()) {
				if (rs.getString("email").equals(email)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in verifyEmail: " + e);
		}
		return true;
	}
	
	
	
	public static void authorization(JRadioButton rdbtnUser, JRadioButton rdbtnAdministrator, JTextField tfEmail,
			JPasswordField pfPassword, CardLayout cardLayout, JPanel cardHolder, JButton bLists,
			JButton bChangePassword, JButton bRequests, JButton bRegistration, JButton bLogin, JButton bLogout) {
		String email = tfEmail.getText();
		String password = new String(pfPassword.getPassword());
		if (email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Пожалуйста, заполните все поля", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			if (!rdbtnUser.isSelected() && !rdbtnAdministrator.isSelected()) {
				JOptionPane.showMessageDialog(null, "Пожалуйста, укажите, под какими правами вы хотите войти в систему",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
			else {
				try {
					verifyUser(rdbtnUser, rdbtnAdministrator, email, password);
					if (verified) {
						if (isAdmin) {
							JOptionPane.showMessageDialog(null, "Вы вошли в систему как администратор", "Info",
									JOptionPane.PLAIN_MESSAGE);
							bRequests.setVisible(true);
						}
						else {
							if (who.equals("notadmin")) {
								JOptionPane.showMessageDialog(null,
										"К сожалению, вы не обладаете правами администратора. Вход будет выполнен под правами пользователя",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							if (who.equals("user")) {
								JOptionPane.showMessageDialog(null, "Вы вошли в систему как пользователь", "Info",
										JOptionPane.PLAIN_MESSAGE);
							}
						}
						bLists.setVisible(true);
						bChangePassword.setVisible(true);
						bRegistration.setVisible(false);
						bLogin.setVisible(false);
						bLogout.setVisible(true);
						cardLayout.show(cardHolder, "Start");
						emptyTextFields(tfEmail, pfPassword);
					}
					else {
						JOptionPane.showMessageDialog(null,
								"Не получилось войти в систему, пожалуйста, попробуйте снова", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова",
							"Error", JOptionPane.ERROR_MESSAGE);
					System.out.print("Error in authorization: " + e);
				}
			}
		}
	}
	
	
	
	public static void verifyUser(JRadioButton rdbtnUser, JRadioButton rdbtnAdministrator, String email,
			String password) {
		try {
			openConnection();
			Statement st = conn.createStatement();
			ResultSet rs1 = st.executeQuery("SELECT id, isadmin FROM worker WHERE worker.email = '" + email
					+ "' AND worker.password = '" + password + "'");
			if (rs1.next()) {
				verified = true;
				userID = rs1.getInt("id");
				if (rs1.getInt("isadmin") == 1 && rdbtnAdministrator.isSelected()) {
					isAdmin = true;
					who = "admin";
				}
				if ((rs1.getInt("isadmin") == 0 || rs1.getInt("isadmin") == 1) && rdbtnUser.isSelected()) {
					who = "user";
				}
				if (rs1.getInt("isadmin") == 0 && rdbtnAdministrator.isSelected()) {
					who = "notadmin";
				}
				System.out.println("userID: " + userID + "; is admin? " + isAdmin + "; who? " + who);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in verifyUser: " + e);
		}
		closeConnection();
	}
	
	
	
	public static void emptyTextFields(JTextField... tfs) {
		for (JTextField tf : tfs) {
			tf.setText("");
		}
	}
	
	
	
	public static void deselectCheckBoxes(JCheckBox... chbs) {
		for (JCheckBox chb : chbs) {
			chb.setSelected(false);
		}
	}
	
	
	
	public static Object[][] constructDataTable(JCheckBox chbWorkers, JCheckBox chbDepartments, JCheckBox chbHouses,
			JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills, JCheckBox chbReports) {
		Object[][] listData = null;
		StringBuilder sbFROM;
		StringBuilder sbWHERE;
		if (!chbWorkers.isSelected() && !chbDepartments.isSelected() && !chbHouses.isSelected()
				&& !chbApartments.isSelected() && !chbResidents.isSelected() && !chbBills.isSelected()
				&& !chbReports.isSelected()) {
			System.out.println("No checkBox is selected");
		}
		else {
			openConnection();
			HashMap<Integer, ArrayList<Object>> mapData = new HashMap<Integer, ArrayList<Object>>();
			if (isAdmin) {
				ArrayList<String> allWHERE = new ArrayList<String>();
				allWHERE.add("report.worker = worker.id");
				allWHERE.add("worker.house = house.id");
				allWHERE.add("house.department = department.id");
				allWHERE.add("house.id = apartment.house");
				allWHERE.add("apartment.id = resident.apartment");
				allWHERE.add("resident.id = bill.resident");
				ArrayList<Integer> checkBoxesWHERE = new ArrayList<Integer>();
				if (chbReports.isSelected()) {
					checkBoxesWHERE.add(0);
				}
				if (chbWorkers.isSelected()) {
					checkBoxesWHERE.add(1);
//					checkBoxesWHERE.add(2);
				}
				if (chbHouses.isSelected()) {
					checkBoxesWHERE.add(2);
				}
				if (chbDepartments.isSelected()) {
					if (chbHouses.isSelected() || chbApartments.isSelected() || chbResidents.isSelected()) {
						checkBoxesWHERE.add(2);
					}
					checkBoxesWHERE.add(3);
				}
				if (chbApartments.isSelected()) {
					checkBoxesWHERE.add(4);
				}
				if (chbResidents.isSelected()) {
					checkBoxesWHERE.add(5);
				}
				if (chbBills.isSelected()) {
					checkBoxesWHERE.add(6);
				}
				ArrayList<String> arrWHERE = new ArrayList<String>();
				for (int i = checkBoxesWHERE.get(0); i < checkBoxesWHERE.get(checkBoxesWHERE.size() - 1); i++) {
					if (i == checkBoxesWHERE.get(0)) {
						arrWHERE.add("WHERE ");
						arrWHERE.add(allWHERE.get(i));
					}
					else {
						arrWHERE.add(" AND ");
						arrWHERE.add(allWHERE.get(i));
					}
				}
				sbWHERE = new StringBuilder();
				for (String wh : arrWHERE) {
					sbWHERE.append(wh);
				}
				ArrayList<String> allFROM = new ArrayList<String>();
				allFROM.add("report");
				allFROM.add("worker");
				allFROM.add("house");
				allFROM.add("department");
				allFROM.add("apartment");
				allFROM.add("resident");
				allFROM.add("bill");
				ArrayList<Integer> checkBoxesFROM = new ArrayList<Integer>();
				if (chbReports.isSelected()) {
					checkBoxesFROM.add(0);
				}
				if (chbWorkers.isSelected()) {
					checkBoxesFROM.add(1);
//					checkBoxesWHERE.add(2);
				}
				if (chbHouses.isSelected()) {
					checkBoxesFROM.add(2);
				}
				if (chbDepartments.isSelected()) {
					if (chbHouses.isSelected() || chbApartments.isSelected() || chbResidents.isSelected()) {
						checkBoxesFROM.add(2);
					}
					checkBoxesFROM.add(3);
				}
				if (chbApartments.isSelected()) {
					checkBoxesFROM.add(4);
				}
				if (chbResidents.isSelected()) {
					checkBoxesFROM.add(5);
				}
				if (chbBills.isSelected()) {
					checkBoxesFROM.add(6);
				}
				ArrayList<String> arrFROM = new ArrayList<String>();
				for (int i = checkBoxesFROM.get(0); i < checkBoxesFROM.get(checkBoxesFROM.size() - 1) + 1; i++) {
					if (i == checkBoxesFROM.get(0)) {
						arrFROM.add("FROM ");
						arrFROM.add(allFROM.get(i));
					}
					else {
						arrFROM.add(", ");
						arrFROM.add(allFROM.get(i));
					}
				}
				sbFROM = new StringBuilder();
				for (String fr : arrFROM) {
					sbFROM.append(fr);
				}
			}
			else {
				try {
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(
							"SELECT house.id FROM house, worker WHERE house.id = worker.house AND worker.id = "
									+ userID);
					while (rs.next()) {
						houseID = rs.getInt("house.id");
					}
				}
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова",
							"Error", JOptionPane.ERROR_MESSAGE);
					System.out.print("Error in constructDataTable: " + e);
				}
				ArrayList<String> allWHERE = new ArrayList<String>();
				allWHERE.add("report.worker = worker.id");
				allWHERE.add("worker.house = house.id");
				allWHERE.add("house.department = department.id");
				allWHERE.add("house.id = " + houseID);
				allWHERE.add("house.id = apartment.house");
				allWHERE.add("apartment.id = resident.apartment");
				allWHERE.add("resident.id = bill.resident");
				ArrayList<Integer> checkBoxesWHERE = new ArrayList<Integer>();
				if (chbReports.isSelected()) {
					checkBoxesWHERE.add(0);
					checkBoxesWHERE.add(4);
				}
				if (chbWorkers.isSelected()) {
					checkBoxesWHERE.add(1);
					checkBoxesWHERE.add(4);
				}
				if (chbHouses.isSelected()) {
					checkBoxesWHERE.add(2);
					checkBoxesWHERE.add(4);
				}
				if (chbDepartments.isSelected()) {
					if (chbHouses.isSelected() || chbApartments.isSelected()) {
						checkBoxesWHERE.add(2);
						checkBoxesWHERE.add(5);
					}
					checkBoxesWHERE.add(2);
					checkBoxesWHERE.add(4);
				}
				if (chbApartments.isSelected()) {
					checkBoxesWHERE.add(2);
					checkBoxesWHERE.add(5);
				}
				if (chbResidents.isSelected()) {
					checkBoxesWHERE.add(2);
					checkBoxesWHERE.add(6);
				}
				if (chbBills.isSelected()) {
					checkBoxesWHERE.add(2);
					checkBoxesWHERE.add(7);
				}
				ArrayList<String> arrWHERE = new ArrayList<String>();
				for (int i = checkBoxesWHERE.get(0); i < checkBoxesWHERE.get(checkBoxesWHERE.size() - 1); i++) {
					if (i == checkBoxesWHERE.get(0)) {
						arrWHERE.add("WHERE ");
						arrWHERE.add(allWHERE.get(i));
					}
					else {
						arrWHERE.add(" AND ");
						arrWHERE.add(allWHERE.get(i));
					}
				}
				sbWHERE = new StringBuilder();
				for (String wh : arrWHERE) {
					sbWHERE.append(wh);
				}
				ArrayList<String> allFROM = new ArrayList<String>();
				allFROM.add("report");
				allFROM.add("worker");
				allFROM.add("house");
				allFROM.add("department");
				allFROM.add("apartment");
				allFROM.add("resident");
				allFROM.add("bill");
				ArrayList<Integer> checkBoxesFROM = new ArrayList<Integer>();
				if (chbReports.isSelected()) {
					checkBoxesFROM.add(0);
					checkBoxesFROM.add(3);
				}
				if (chbWorkers.isSelected()) {
					checkBoxesFROM.add(1);
					checkBoxesFROM.add(3);
				}
				if (chbHouses.isSelected()) {
					checkBoxesFROM.add(2);
					checkBoxesFROM.add(3);
				}
				if (chbDepartments.isSelected()) {
					if (chbHouses.isSelected() || chbApartments.isSelected()) {
						checkBoxesFROM.add(2);
						checkBoxesFROM.add(4);
					}
					checkBoxesFROM.add(2);
					checkBoxesFROM.add(3);
				}
				if (chbApartments.isSelected()) {
					checkBoxesFROM.add(2);
					checkBoxesFROM.add(4);
				}
				if (chbResidents.isSelected()) {
					checkBoxesFROM.add(2);
					checkBoxesFROM.add(5);
				}
				if (chbBills.isSelected()) {
					checkBoxesFROM.add(2);
					checkBoxesFROM.add(6);
				}
				ArrayList<String> arrFROM = new ArrayList<String>();
				for (int i = checkBoxesFROM.get(0); i < checkBoxesFROM.get(checkBoxesFROM.size() - 1) + 1; i++) {
					if (i == checkBoxesFROM.get(0)) {
						arrFROM.add("FROM ");
						arrFROM.add(allFROM.get(i));
					}
					else {
						arrFROM.add(", ");
						arrFROM.add(allFROM.get(i));
					}
				}
				sbFROM = new StringBuilder();
				for (String fr : arrFROM) {
					sbFROM.append(fr);
				}
			}
			try {
				if (chbHouses.isSelected()) {
					updateHouses();
				}
				if (chbBills.isSelected()) {
					updateBills();
				}
				Statement st = conn.createStatement();
				System.out.println("SELECT * " + sbFROM + " " + sbWHERE);
				ResultSet rs = st.executeQuery("SELECT * " + sbFROM + " " + sbWHERE);
				int i = 0;
				while (rs.next()) {
					ArrayList<Object> row = new ArrayList<Object>();
					if (chbWorkers.isSelected()) {
						row.add(rs.getInt("worker.id"));
						row.add(rs.getString("worker.last_name"));
						row.add(rs.getString("worker.first_name"));
						row.add(rs.getString("worker.middle_name"));
						row.add(rs.getString("worker.email"));
						row.add(rs.getString("worker.profession"));
						row.add(rs.getInt("worker.isadmin"));
						row.add(rs.getInt("worker.house"));
//						row.add(rs.getString("house.address"));
					}
					if (chbDepartments.isSelected()) {
						row.add(rs.getInt("department.id"));
						row.add(rs.getString("department.name"));
					}
					if (chbHouses.isSelected()) {
						row.add(rs.getInt("house.id"));
						row.add(rs.getString("house.element"));
						row.add(rs.getString("house.address"));
						row.add(rs.getInt("house.number"));
						row.add(rs.getDouble("house.square"));
						row.add(rs.getString("house.status"));
						row.add(rs.getInt("house.num_of_res"));
						row.add(rs.getDouble("house.price"));
						row.add(rs.getInt("house.department"));
//						row.add(rs.getString("department.name"));
					}
					if (chbApartments.isSelected()) {
						row.add(rs.getInt("apartment.id"));
						row.add(rs.getInt("apartment.number"));
						row.add(rs.getDouble("apartment.price"));
						row.add(rs.getInt("apartment.house"));
					}
					if (chbResidents.isSelected()) {
						row.add(rs.getInt("resident.id"));
						row.add(rs.getString("resident.last_name"));
						row.add(rs.getString("resident.first_name"));
						row.add(rs.getString("resident.middle_name"));
						row.add(rs.getInt("resident.apartment"));
//						row.add(rs.getString("house.address"));
					}
					if (chbBills.isSelected()) {
						row.add(rs.getInt("bill.id"));
						row.add(rs.getString("bill.name"));
						row.add(rs.getString("bill.body"));
						row.add(rs.getDouble("bill.charge"));
						row.add(rs.getDate("bill.date_to_pay"));
						row.add(rs.getString("bill.status"));
						try {
							row.add(rs.getDate("bill.date_of_payment"));
						}
						catch (Exception e) {
							row.add(null);
						}
						row.add(rs.getString("bill.form_of_payment"));
						row.add(rs.getInt("bill.resident"));
//						row.add(rs.getString("resident.name"));
					}
					if (chbReports.isSelected()) {
						row.add(rs.getInt("report.id"));
						row.add(rs.getString("report.name"));
						row.add(rs.getString("report.body"));
						row.add(rs.getInt("report.worker"));
//						row.add(rs.getString("house.address"));
					}
					mapData.put(i, row);
					i++;
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.print("Error in constructDataTable: " + e);
			}
			if (mapData.size() != 0) {
				listData = new Object[mapData.size()][mapData.get(0).size()];
				for (int x = 0; x < mapData.size(); x++) {
					for (int y = 0; y < mapData.get(x).size(); y++) {
						listData[x][y] = mapData.get(x).get(y);
					}
				}
			}
			closeConnection();
		}
		return listData;
	}
	
	
	
	public static void updateHouses() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT id FROM house");
			ArrayList<Integer> houseIDs = new ArrayList<Integer>();
			while (rs.next()) {
				houseIDs.add(rs.getInt("id"));
			}
			ArrayList<Integer> countsRes = new ArrayList<Integer>();
			ArrayList<Double> priceHouses = new ArrayList<Double>();
			for (Integer h : houseIDs) {
				Double priceAp = 0.0;
//				System.out.println("SELECT count(*) FROM house, apartment, resident WHERE house.id = apartment.house AND apartment.id = resident.apartment AND house.id = " + i);
				ResultSet rs2 = st.executeQuery(
						"SELECT count(*) FROM house, apartment, resident WHERE house.id = apartment.house AND apartment.id = resident.apartment AND house.id = "
								+ h);
				while (rs2.next()) {
//					System.out.println("count: " + rs2.getInt("count(*)"));
					countsRes.add(rs2.getInt("count(*)"));
				}
				ResultSet rs3 = st.executeQuery(
						"SELECT apartment.price FROM house, apartment WHERE house.id = apartment.house AND house.id = "
								+ h);
				while (rs3.next()) {
					priceAp += rs3.getDouble("apartment.price");
				}
				priceHouses.add(priceAp);
			}
			for (int i = 0; i < countsRes.size(); i++) {
//				System.out.println("UPDATE house SET house.num_of_res = " + i + " WHERE house.id = " + houseIDs.get(x));
				st.executeUpdate("UPDATE house SET house.num_of_res = " + i + ", house.price = " + priceHouses.get(i)
						+ " WHERE house.id = " + houseIDs.get(i));
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in updateHouses: " + e);
		}
	}
	
	
	
	public static void updateBills() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT id FROM resident");
			ArrayList<Integer> residentIDs = new ArrayList<Integer>();
			while (rs.next()) {
				residentIDs.add(rs.getInt("id"));
			}
			rs = st.executeQuery(
					"SELECT CURRENT_DATE-bill.date_to_pay, resident.id FROM resident, bill WHERE resident.id = bill.resident AND (CURRENT_DATE-bill.date_to_pay)/31 < 1");
			while (rs.next()) {
//				System.out.println(rs.getInt("CURRENT_DATE-bill.date_to_pay")/31 - 1);
				residentIDs.remove((Object)rs.getInt("resident.id"));
			}
			ArrayList<Double> chargeServices = new ArrayList<Double>();
			ArrayList<Double> chargeForAp = new ArrayList<Double>();
			for (Integer i : residentIDs) {
				rs = st.executeQuery(
						"SELECT apartment.price FROM apartment, resident WHERE resident.apartment = apartment.id AND resident.id = "
								+ i);
				while (rs.next()) {
					chargeServices.add(rs.getDouble("apartment.price") * 0.7);
					chargeForAp.add(rs.getDouble("apartment.price") * 0.4);
				}
			}
			Calendar calendar = Calendar.getInstance();
			int lastDate = calendar.getActualMaximum(Calendar.DATE);
			calendar.set(Calendar.DATE, lastDate);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	        System.out.println("date: " + dateFormat.format(calendar.getTime()));
			String strLastDay = dateFormat.format(calendar.getTime());
			int q = 0;
			for (Integer i : residentIDs) {
				st.executeUpdate(
						"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('Услуги ЖКХ', 'Сумма к оплате', '"
								+ chargeServices.get(q) + "', '" + strLastDay + "', '" + i + "')");
				st.executeUpdate(
						"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('Налог', 'Сумма к оплате', '"
								+ chargeForAp.get(q) + "', '" + strLastDay + "', '" + i + "')");
				q++;
			}
			rs = st.executeQuery("SELECT id FROM bill");
			ArrayList<Integer> billIDs = new ArrayList<Integer>();
			while (rs.next()) {
//				System.out.println("id: " + rs.getInt("id"));
				billIDs.add(rs.getInt("id"));
			}
			ArrayList<String> statuses = new ArrayList<String>();
			ResultSet rs2 = st.executeQuery("SELECT bill.date_to_pay-CURRENT_DATE, date_of_payment FROM bill");
			while (rs2.next()) {
				int diff = rs2.getInt("bill.date_to_pay-CURRENT_DATE");
				Date date_of_payment = null;
				try {
					date_of_payment = rs2.getDate("date_of_payment");
				}
				catch (Exception e) {
					
				}
				if (date_of_payment != null) {
					statuses.add("Оплачено");
				}
				else {
					if (diff > 0) {
						statuses.add("Не оплачено");
					}
					if (diff <= 0) {
						statuses.add("Задолженность");
					}
				}
			}
			int x = 0;
			for (String s : statuses) {
//				System.out.println("UPDATE bill SET bill.status = '" + s + "' WHERE bill.id = " + billIDs.get(x));
				st.executeUpdate("UPDATE bill SET bill.status = '" + s + "' WHERE bill.id = " + billIDs.get(x));
				x++;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.print("Error in updateBills: " + e);
		}
	}
	
	
	
	public static String[] tableDataHeaders(JCheckBox chbWorkers, JCheckBox chbDepartments, JCheckBox chbHouses,
			JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills, JCheckBox chbReports) {
		ArrayList<String> arrHeaders = new ArrayList<String>();
		if (chbWorkers.isSelected()) {
			arrHeaders.add("ID сотрудника");
			arrHeaders.add("Фамилия сотрудника");
			arrHeaders.add("Имя сотрудника");
			arrHeaders.add("Отчество сотрудника");
			arrHeaders.add("Email сотрудника");
			arrHeaders.add("Профессия сотрудника");
			arrHeaders.add("Привилегии сотрудника");
			arrHeaders.add("ID дома сотрудника");
//			arrHeaders.add("Адресс");
		}
		if (chbDepartments.isSelected()) {
			arrHeaders.add("ID отделения");
			arrHeaders.add("Название отделение");
		}
		if (chbHouses.isSelected()) {
			arrHeaders.add("ID дома");
			arrHeaders.add("Элемент");
			arrHeaders.add("Адрес дома");
			arrHeaders.add("Номер дома");
			arrHeaders.add("Площадь дома");
			arrHeaders.add("Статус дома");
			arrHeaders.add("Количество жильцов");
			arrHeaders.add("Цена всех квартир");
			arrHeaders.add("ID отделения дома");
//			arrHeaders.add("Отделение");
		}
		if (chbApartments.isSelected()) {
			arrHeaders.add("ID квартиры");
			arrHeaders.add("Номер квартиры");
			arrHeaders.add("Цена квартиры за м^2");
			arrHeaders.add("ID дома квартиры");
		}
		if (chbResidents.isSelected()) {
			arrHeaders.add("ID жителя");
			arrHeaders.add("Фамилия жителя");
			arrHeaders.add("Имя жителя");
			arrHeaders.add("Отчество жителя");
			arrHeaders.add("ID квартиры жителя");
		}
		if (chbBills.isSelected()) {
			arrHeaders.add("ID счета");
			arrHeaders.add("Название счета");
			arrHeaders.add("Тело счета");
			arrHeaders.add("Сумма счета");
			arrHeaders.add("Крайний срок оплаты");
			arrHeaders.add("Статус счета");
			arrHeaders.add("Дата оплаты");
			arrHeaders.add("Форма оплаты");
			arrHeaders.add("ID жителя счета");
//			arrHeaders.add("Житель");
		}
		if (chbReports.isSelected()) {
			arrHeaders.add("ID отчета");
			arrHeaders.add("Название отчета");
			arrHeaders.add("Тело отчета");
			arrHeaders.add("ID сотрудника отчета");
//			arrHeaders.add("Адресс");
		}
		String[] strHeaders = new String[arrHeaders.size()];
		for (int i = 0; i < arrHeaders.size(); i++) {
			strHeaders[i] = arrHeaders.get(i);
		}
		return strHeaders;
	}
	
	
	
	public static void updateDataTable(JTable tableData, JCheckBox chbWorkers, JCheckBox chbDepartments,
			JCheckBox chbHouses, JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills,
			JCheckBox chbReports) {
		Object[][] data = constructDataTable(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents,
				chbBills, chbReports);
		String[] headers = tableDataHeaders(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents,
				chbBills, chbReports);
		DefaultTableModel dtm = (DefaultTableModel)tableData.getModel();
		if (data == null) {
			resetTable(tableData);
		}
		else {
			dtm.setDataVector(data, headers);
			TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(dtm);
			int i = 0;
			if (chbWorkers.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 6, new IntComparator());
				trs.setComparator(i + 7, new IntComparator());
				i += 8;
			}
			if (chbDepartments.isSelected()) {
				trs.setComparator(i, new IntComparator());
				i += 2;
			}
			if (chbHouses.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 3, new IntComparator());
				trs.setComparator(i + 4, new IntComparator());
				trs.setComparator(i + 6, new IntComparator());
				trs.setComparator(i + 7, new IntComparator());
				trs.setComparator(i + 8, new IntComparator());
				i += 9;
			}
			if (chbApartments.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 1, new IntComparator());
				trs.setComparator(i + 2, new IntComparator());
				trs.setComparator(i + 3, new IntComparator());
				i += 4;
			}
			if (chbResidents.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 4, new IntComparator());
				i += 5;
			}
			if (chbBills.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 3, new IntComparator());
				trs.setComparator(i + 8, new IntComparator());
				i += 9;
			}
			if (chbReports.isSelected()) {
				trs.setComparator(i, new IntComparator());
				trs.setComparator(i + 3, new IntComparator());
			}
			tableData.setRowSorter(trs);
		}
		isInsertedMap.clear();
	}
	
	private static class IntComparator implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			int res = -1;
			if (o1 instanceof Integer && o2 instanceof Integer) {
				Integer int1 = (Integer)o1;
				Integer int2 = (Integer)o2;
				res = int1.compareTo(int2);
			}
			if (o1 instanceof Double && o2 instanceof Double) {
				Double int1 = (Double)o1;
				Double int2 = (Double)o2;
				res = int1.compareTo(int2);
			}
			return res;
		}
		
		
		
		public boolean equals(Object o2) {
			return this.equals(o2);
		}
	}
	
	
	
	public static void resetTable(JTable dataTable) {
		DefaultTableModel dtm = (DefaultTableModel)dataTable.getModel();
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
	}
	
	
	
//	private static class focusListener2 implements FocusListener {
//		JTable dataTable;
//		JLayeredPane panePromptText;
//		JTextField tf;
//		TableRowSorter<TableModel> sorter;
//		
//		
//		@Override
//		public void focusGained(FocusEvent e) {
//			for (Component c : ((JTextField)e.getSource()).getParent().getParent().getComponents()) {
//				System.out.println(c);
//				if (c instanceof JScrollPane) {
//					System.out.println(dataTable);
//					dataTable = (JTable) ((JScrollPane) c).getViewport().getView();
//				}
//			}
//			panePromptText = (JLayeredPane)((JTextField)e.getSource()).getParent();
//			tf = (JTextField)e.getSource();
//			setDocumentListener(dataTable, panePromptText, tf);
//		}
//		@Override
//		public void focusLost(FocusEvent e) {
////			sorter.setRowFilter(null);
//			for (Component c : ((JTextField)e.getSource()).getParent().getParent().getComponents()) {
//				System.out.println(c);
//				if (c instanceof JScrollPane) {
//					System.out.println(dataTable);
//					dataTable = (JTable) ((JScrollPane) c).getViewport().getView();
//				}
//			}
//			panePromptText = (JLayeredPane)((JTextField)e.getSource()).getParent();
//			tf = (JTextField)e.getSource();
//			setDocumentListener(dataTable, panePromptText, tf);
//		}
//	}
//	
//	
//	
//	public static void setDocumentListener(JTable dataTable, JLayeredPane panePromptText, JTextField tf) {
//		for (Component c : panePromptText.getComponents()) {
//			 if (c instanceof JTextField) {
//				 ((JTextField) c).getDocument().addDocumentListener(new DocumentListener() {
//					 @Override
//					 public void insertUpdate(DocumentEvent e) {
//						 updateFilter(dataTable, panePromptText, tf);
//					 }
//
//					 @Override
//					 public void removeUpdate(DocumentEvent e) {
//						 updateFilter(dataTable, panePromptText, tf);
//					 }
//
//					 @Override
//					 public void changedUpdate(DocumentEvent e) {
//						 updateFilter(dataTable, panePromptText, tf);
//					 }
//				 });
//			 }
//		}
//	}
//	
//	
//	
//	protected static void updateFilter(JTable dataTable, JLayeredPane panePromptText, JTextField tf) {
//		TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>)dataTable.getRowSorter();
//		String text = "(?i)" + tf.getText();
//		int col = -1;
//		System.out.println("tf.getText(): " + tf.getText());
//		int i = 0;
//		if (tf.getText().isEmpty()) {
//			System.out.println("text is empty");
//		}
//		else {
//			System.out.println("not empty: " + tf.getText());
//		}
//		for (Component c : dataTable.getParent().getParent().getParent().getComponents()) {
//			if (c instanceof JLayeredPane) {
//				for (Component c2 : ((JLayeredPane) c).getComponents()) {
//					if (c2 instanceof JLayeredPane) {
//						for (Component c3 : ((JLayeredPane) c2).getComponents()) {
//							if (c3 instanceof JCheckBox) {
//								System.out.println("((JCheckBox) c2).getText(): " + ((JCheckBox) c3).getText());
//								if (((JCheckBox) c3).getText().equals("Сотрудники")) {
//									if (tf.getText().isEmpty()) {
//										System.out.println("text is empty");
//										sorter.setRowFilter(null);
//									}
//									else {
//										boolean empty = true;
//										if (tf.equals(nameInput01)) {
//											empty = false;
//											col = 0;
//										}
//										if (tf.equals(nameInput2)) {
//											empty = false;
//											col = 1;
//										}
//										if (tf.equals(nameInput3)) {
//											empty = false;
//											col = 2;
//										}
//										if (tf.equals(nameInput4)) {
//											empty = false;
//											col = 3;
//										}
//										if (tf.equals(nameInput5)) {
//											empty = false;
//											col = 4;
//										}
//										if (tf.equals(nameInput6)) {
//											empty = false;
//											col = 5;
//										}
//										if (tf.equals(nameInput7)) {
//											empty = false;
//											col = 6;
//										}
//										if (tf.equals(nameInput8)) {
//											empty = false;
//											col = 7;
//										}
//										System.out.println("Empty? " + empty);
//										if (empty) {
//											sorter.setRowFilter(null);
//										}
//										else {
//											sorter.setRowFilter(RowFilter.regexFilter(text, col));
//										}
//									}
//									
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		System.out.println("col: " + col);
//	}
//	 
//	 
//	 
//	 
//	 private static class documentListener implements DocumentListener {
//
//		@Override
//		public void insertUpdate(DocumentEvent e) {
//			updateFilter();
//		}
//
//		@Override
//		public void removeUpdate(DocumentEvent e) {
//			updateFilter();
//		}
//
//		@Override
//		public void changedUpdate(DocumentEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		 
//	 }
	
	public static void updatePromptTable(JCheckBox chbWorkers, JCheckBox chbDepartments, JCheckBox chbHouses,
			JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills, JCheckBox chbReports,
			JLayeredPane lpData, JLayeredPane panePromptText, GridBagConstraints gbc_panePromptText) {
		panePromptText.removeAll();
		lpData.remove(panePromptText);
		if (chbWorkers.isSelected() || chbDepartments.isSelected() || chbHouses.isSelected()
				|| chbApartments.isSelected() || chbResidents.isSelected() || chbBills.isSelected()
				|| chbReports.isSelected()) {
			rows = 0;
			if (chbWorkers.isSelected()) {
				nameInput01 = new JTextField("ID сотрудника");
				nameInput2 = new JTextField("Фамилия сотрудника");
				nameInput3 = new JTextField("Имя сотрудника");
				nameInput4 = new JTextField("Отчество сотрудника");
				nameInput5 = new JTextField("Email сотрудника");
				nameInput6 = new JTextField("Профессия сотрудника");
				nameInput7 = new JTextField("Привилегии сотрудника");
				nameInput8 = new JTextField("ID дома сотрудника");
				panePromptText.add(nameInput01);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				panePromptText.add(nameInput5);
				panePromptText.add(nameInput6);
				panePromptText.add(nameInput7);
				panePromptText.add(nameInput8);
				rows += 8;
			}
			if (chbDepartments.isSelected()) {
				nameInput02 = new JTextField("ID отделения");
				JTextField nameInput2 = new JTextField("Название отделения");
				panePromptText.add(nameInput02);
				panePromptText.add(nameInput2);
				rows += 2;
			}
			if (chbHouses.isSelected()) {
				nameInput03 = new JTextField("ID дома");
				JTextField nameInput2 = new JTextField("Элемент");
				JTextField nameInput3 = new JTextField("Адрес дома");
				JTextField nameInput4 = new JTextField("Номер дома");
				JTextField nameInput5 = new JTextField("Площадь дома");
				JTextField nameInput6 = new JTextField("Статус дома");
				nameInput04 = new JTextField("Количество жильцов");
				nameInput05 = new JTextField("Цена всех квартир");
				JTextField nameInput7 = new JTextField("ID отделения дома");
				panePromptText.add(nameInput03);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				panePromptText.add(nameInput5);
				panePromptText.add(nameInput6);
				panePromptText.add(nameInput04);
				panePromptText.add(nameInput05);
				panePromptText.add(nameInput7);
				rows += 9;
			}
			if (chbApartments.isSelected()) {
				nameInput06 = new JTextField("ID квартиры");
				JTextField nameInput2 = new JTextField("Номер квартиры");
				JTextField nameInput3 = new JTextField("Цена квартиры за м^2");
				JTextField nameInput4 = new JTextField("ID дома квартиры");
				panePromptText.add(nameInput06);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				rows += 4;
			}
			if (chbResidents.isSelected()) {
				nameInput07 = new JTextField("ID жителя");
				JTextField nameInput2 = new JTextField("Фамилия жителя");
				JTextField nameInput3 = new JTextField("Имя жителя");
				JTextField nameInput4 = new JTextField("Отчество жителя");
				JTextField nameInput5 = new JTextField("ID квартиры жителя");
				panePromptText.add(nameInput07);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				panePromptText.add(nameInput5);
				rows += 5;
			}
			if (chbBills.isSelected()) {
				nameInput08 = new JTextField("ID счета");
				JTextField nameInput2 = new JTextField("Название счета");
				JTextField nameInput3 = new JTextField("Тело счета");
				JTextField nameInput4 = new JTextField("Сумма счета");
				nameInput09 = new JTextField("Крайний срок оплаты");
				nameInput010 = new JTextField("Статус счета");
				nameInput011 = new JTextField("Дата оплаты");
				nameInput012 = new JTextField("Форма оплаты");
				JTextField nameInput9 = new JTextField("ID жителя счета");
				panePromptText.add(nameInput08);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				panePromptText.add(nameInput09);
				panePromptText.add(nameInput010);
				panePromptText.add(nameInput011);
				panePromptText.add(nameInput012);
				panePromptText.add(nameInput9);
				rows += 9;
			}
			if (chbReports.isSelected()) {
				nameInput013 = new JTextField("ID отчета");
				JTextField nameInput2 = new JTextField("Название отчета");
				JTextField nameInput3 = new JTextField("Тело отчета");
				JTextField nameInput4 = new JTextField("ID сотрудника отчета");
				panePromptText.add(nameInput013);
				panePromptText.add(nameInput2);
				panePromptText.add(nameInput3);
				panePromptText.add(nameInput4);
				rows += 4;
			}
			for (Component c : panePromptText.getComponents()) {
				if (c instanceof JTextField) {
					c.addFocusListener(new focusListener());
				}
			}
//			bSearch.addMouseListener(new mouseListener());
			panePromptText.setLayout(new GridLayout(0, rows, 0, 0));
			lpData.add(panePromptText, gbc_panePromptText);
		}
	}
	
//	private static class mouseListener implements MouseListener {
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			for (Component c : ((JButton)e.getSource()).getParent().getParent().getComponents()) {
//				if (c instanceof JLayeredPane) {
//					for (Component c2 : ((JLayeredPane) c).getComponents()) {
//						if (c2 instanceof JTextField) {
//							c2.addFocusListener(new focusListener2());
//						}
//					}
//				}
//				c.addFocusListener(new focusListener2());
//			}
//			System.out.println("Say hello");
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	
	private static class focusListener implements FocusListener {
		private String promptText = null;
		private boolean isInserted = false;
		
		
		
		@Override
		public void focusGained(FocusEvent e) {
			if (!isInserted) {
				promptText = ((JTextField)e.getSource()).getText();
				((JTextField)e.getSource()).setText("");
			}
		}
		
		
		
		@Override
		public void focusLost(FocusEvent e) {
			if (((JTextField)e.getSource()).getText().isEmpty()) {
				((JTextField)e.getSource()).setText(promptText);
				isInserted = false;
				isInsertedMap.remove((JTextField)e.getSource());
			}
			else {
				isInserted = true;
				isInsertedMap.remove((JTextField)e.getSource());
			}
			isInsertedMap.put((JTextField)e.getSource(), isInserted);
		}
	}
	
	
	
	public static Object[][] constructTableRequests() {
		Object[][] arrData = null;
		HashMap<Integer, ArrayList<Object>> mapMyOrders = new HashMap<Integer, ArrayList<Object>>();
		ArrayList<Object> listMyOrders = null;
		try {
			openConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM request");
			int i = 0;
			while (rs.next()) {
				listMyOrders = new ArrayList<Object>();
				listMyOrders.add(rs.getInt("request.id"));
				listMyOrders.add(rs.getString("request.last_name"));
				listMyOrders.add(rs.getString("request.first_name"));
				listMyOrders.add(rs.getString("request.middle_name"));
				listMyOrders.add(rs.getString("request.email"));
				listMyOrders.add(rs.getString("request.profession"));
				listMyOrders.add(rs.getInt("request.isadmin"));
				listMyOrders.add(rs.getInt("request.house"));
				mapMyOrders.put(i, listMyOrders);
				i++;
			}
			closeConnection();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in constructTableRequests: " + e);
		}
		if (mapMyOrders.size() != 0) {
			arrData = new Object[mapMyOrders.size()][mapMyOrders.get(0).size()];
			for (int x = 0; x < mapMyOrders.size(); x++) {
				for (int y = 0; y < mapMyOrders.get(0).size(); y++) {
					arrData[x][y] = mapMyOrders.get(x).get(y);
				}
			}
		}
		closeConnection();
		return arrData;
	}
	
	
	
	public static String[] tableRequestsHeaders() {
		String[] arrHeaders = new String[8];
		arrHeaders[0] = "ID заявки";
		arrHeaders[1] = "Фамилия";
		arrHeaders[2] = "Имя";
		arrHeaders[3] = "Отчество";
		arrHeaders[4] = "Email";
		arrHeaders[5] = "Профессия";
		arrHeaders[6] = "Привилегии";
		arrHeaders[7] = "ID дома";
		return arrHeaders;
	}
	
	
	
	public static void updateRequestsTable(JTable tableRequests) {
		DefaultTableModel dtm = (DefaultTableModel)tableRequests.getModel();
		Object[][] data = constructTableRequests();
		String[] headers = tableRequestsHeaders();
		if (data == null) {
			resetTable(tableRequests);
		}
		else {
			dtm.setDataVector(data, headers);
		}
	}
	
	
	
	public static void insert(JCheckBox chbWorkers, JCheckBox chbDepartments, JCheckBox chbHouses,
			JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills, JCheckBox chbReports,
			JLayeredPane paneList, JLayeredPane panePromptText, GridBagConstraints gbc_panePromptText,
			JLayeredPane lpAddDelete, GridBagConstraints gbc_lpAddDelete, JTable tableData) {
		Iterator<Entry<JTextField, Boolean>> it = isInsertedMap.entrySet().iterator();
		boolean go_on = true;
		boolean avoid = false;
		while (it.hasNext()) {
			Map.Entry<JTextField, Boolean> pair = (Map.Entry<JTextField, Boolean>)it.next();
			System.out.println("Key: " + pair.getKey() + "; Value: " + pair.getValue());
			if ((boolean)pair.getValue() == false) {
				if (!pair.getKey().equals(nameInput01) && !pair.getKey().equals(nameInput02)
						&& !pair.getKey().equals(nameInput03) && !pair.getKey().equals(nameInput04)
						&& !pair.getKey().equals(nameInput05) && !pair.getKey().equals(nameInput06)
						&& !pair.getKey().equals(nameInput07) && !pair.getKey().equals(nameInput08)
						&& !pair.getKey().equals(nameInput09) && !pair.getKey().equals(nameInput010)
						&& !pair.getKey().equals(nameInput011) && !pair.getKey().equals(nameInput012)
						&& !pair.getKey().equals(nameInput013)) {
					go_on = false;
					break;
				}
				else {
					avoid = true;
				}
			}
		}
		int optionalRows = 0;
		if (chbWorkers.isSelected()) {
			optionalRows++;
		}
		if (chbDepartments.isSelected()) {
			optionalRows++;
		}
		if (chbHouses.isSelected()) {
			optionalRows += 2;
		}
		if (chbApartments.isSelected()) {
			optionalRows++;
		}
		if (chbResidents.isSelected()) {
			optionalRows++;
		}
		if (chbBills.isSelected()) {
			optionalRows += 4;
		}
		if (chbReports.isSelected()) {
			optionalRows++;
		}
		System.out
				.println("isInsertedMap size: " + isInsertedMap.size() + "; compulsory rows: " + (rows - optionalRows));
		System.out.println("Go on? " + go_on);
		System.out.println("Avoid? " + avoid);
		if (isInsertedMap.size() < rows - optionalRows) {
//			for (Component c : panePromptText.getComponents()) {
//				if (c instanceof JTextField) {
//					isInsertedMap.remove(c);
//				}
//			}
			JOptionPane.showMessageDialog(null, "Пожалуйста, заполните правильно форму", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			if (go_on == false) {
				JOptionPane.showMessageDialog(null, "Пожалуйста, заполните правильно форму", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			else {
				System.out.println("isInsertedMap size: " + isInsertedMap.size() + "; rows: " + rows);
				if (isInsertedMap.size() != rows) {
					avoid = true;
				}
				System.out.println("rows: " + rows);
				String[] values = new String[rows];
				int i = 0;
				for (Component c : panePromptText.getComponents()) {
					if (c instanceof JTextField && !((JTextField)c).getText().isEmpty()) {
						System.out.println("Inserted text: " + ((JTextField)c).getText());
						values[i] = ((JTextField)c).getText();
						System.out.println("i: " + i);
						i++;
					}
				}
				openConnection();
				try {
					Statement st = conn.createStatement();
					i = 0;
					if (chbWorkers.isSelected()) {
						String last_name = values[1];
						String first_name = values[2];
						String middle_name = values[3];
						String email = values[4];
						String password = generatePassword();
						String profession = values[5];
						int isadmin = Integer.valueOf(values[6]);
						int house = Integer.valueOf(values[7]);
						if (isAdmin) {
							if (avoid) {
								System.out.println(
										"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name
												+ "', '" + email + "', '" + password + "', '" + profession + "', '"
												+ isadmin + "', '" + house + "')");
								st.executeUpdate(
										"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name
												+ "', '" + email + "', '" + password + "', '" + profession + "', '"
												+ isadmin + "', '" + house + "')");
							}
							else {
								int id = Integer.valueOf(values[0]);
								System.out.println(
										"INSERT INTO worker (id, last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + id + "', '" + last_name + "', '" + first_name + "', '"
												+ middle_name + "', '" + email + "', '" + password + "', '" + profession
												+ "', '" + isadmin + "', '" + house + "')");
								st.executeUpdate(
										"INSERT INTO worker (id, last_name, first_name, middle_name, email, password, profession, isadmin, house) "
												+ "VALUES ('" + id + "', '" + last_name + "', '" + first_name + "', '"
												+ middle_name + "', '" + email + "', '" + password + "', '" + profession
												+ "', '" + isadmin + "', '" + house + "')");
							}
							JOptionPane.showMessageDialog(null,
									"Пароль нового пользователя: " + password
											+ ". Его можно будет изменить на главной странице.",
									"Info", JOptionPane.PLAIN_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
						i += 8;
					}
					if (chbDepartments.isSelected()) {
						String name = values[i + 1];
						if (isAdmin) {
							if (avoid) {
								System.out.println("INSERT INTO department (name) VALUES ('" + name + "')");
								st.executeUpdate("INSERT INTO department (name) VALUES ('" + name + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println(
										"INSERT INTO department (id, name) VALUES ('" + id + "', '" + name + "')");
								st.executeUpdate(
										"INSERT INTO department (id, name) VALUES ('" + id + "', '" + name + "')");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
						i += 2;
					}
					if (chbHouses.isSelected()) {
						String element = values[i + 1];
						String address = values[i + 2];
						int number = Integer.valueOf(values[i + 3]);
						Double square = Double.valueOf(values[i + 4]);
						String status = values[i + 5];
						int department = Integer.valueOf(values[i + 8]);
						if (isAdmin) {
							if (avoid) {
								System.out.println(
										"INSERT INTO house (element, address, number, square, status, num_of_res, department) "
												+ "VALUES ('" + element + "', '" + address + "', '" + number + "', '"
												+ square + "', '" + status + "', 0, '" + department + "')");
								st.executeUpdate(
										"INSERT INTO house (element, address, number, square, status, num_of_res, department) "
												+ "VALUES ('" + element + "', '" + address + "', '" + number + "', '"
												+ square + "', '" + status + "', 0, '" + department + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println(
										"INSERT INTO house (id, element, address, number, square, status, num_of_res, department) "
												+ "VALUES ('" + id + "', '" + element + "', '" + address + "', '"
												+ number + "', '" + square + "', '" + status + "', 0, '" + department
												+ "')");
								st.executeUpdate(
										"INSERT INTO house (id, element, address, number, square, status, num_of_res, department) "
												+ "VALUES ('" + id + "', '" + element + "', '" + address + "', '"
												+ number + "', '" + square + "', '" + status + "', 0, '" + department
												+ "')");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
						i += 9;
					}
					if (chbApartments.isSelected()) {
						int number = Integer.valueOf(values[i + 1]);
						double price = Double.valueOf(values[i + 2]);
						int house = Integer.valueOf(values[i + 3]);
						if (isAdmin) {
							if (avoid) {
								System.out.println("INSERT INTO apartment (number, price, house) VALUES ('" + number
										+ "', '" + price + "', '" + house + "')");
								st.executeUpdate("INSERT INTO apartment (number, price, house) VALUES ('" + number
										+ "', '" + price + "', '" + house + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println("INSERT INTO apartment (id, number, price, house) VALUES ('" + id
										+ "', '" + number + "', '" + price + "', '" + house + "')");
								st.executeUpdate("INSERT INTO apartment (id, number, price, house) VALUES ('" + id
										+ "', '" + number + "', '" + price + "', '" + house + "')");
							}
						}
						else {
							try {
								boolean match = false;
								ArrayList<Integer> houseIDs = new ArrayList<Integer>();
								st = conn.createStatement();
								ResultSet rs = st.executeQuery(
										"SELECT house.id FROM house, worker WHERE worker.house = house.id AND worker.id = "
												+ userID);
								while (rs.next()) {
									houseIDs.add(rs.getInt("house.id"));
								}
								for (int hid : houseIDs) {
									if (house == hid) {
										match = true;
										if (avoid) {
											System.out.println("INSERT INTO apartment (number, price, house) VALUES ('"
													+ number + "', '" + price + "', '" + house + "')");
											st.executeUpdate("INSERT INTO apartment (number, price, house) VALUES ('"
													+ number + "', '" + price + "', '" + house + "')");
										}
										else {
											int id = Integer.valueOf(values[i]);
											System.out.println(
													"INSERT INTO apartment (id, number, price, house) VALUES ('" + id
															+ "', '" + number + "', '" + price + "', '" + house + "')");
											st.executeUpdate(
													"INSERT INTO apartment (id, number, price, house) VALUES ('" + id
															+ "', '" + number + "', '" + price + "', '" + house + "')");
										}
										break;
									}
								}
								if (!match) {
									JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий",
											"Warning", JOptionPane.WARNING_MESSAGE);
								}
							}
							catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										"Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						i += 4;
					}
					if (chbResidents.isSelected()) {
						String last_name = values[i + 1];
						String first_name = values[i + 2];
						String middle_name = values[i + 3];
						int apartment = Integer.valueOf(values[i + 4]);
						if (isAdmin) {
							if (avoid) {
								System.out.println(
										"INSERT INTO resident (last_name, first_name, middle_name, apartment) VALUES ('"
												+ last_name + "', '" + first_name + "', '" + middle_name + "', '"
												+ apartment + "')");
								st.executeUpdate(
										"INSERT INTO resident (last_name, first_name, middle_name, apartment) VALUES ('"
												+ last_name + "', '" + first_name + "', '" + middle_name + "', '"
												+ apartment + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println(
										"INSERT INTO resident (id, last_name, first_name, middle_name, apartment) VALUES ('"
												+ id + "', '" + last_name + "', '" + first_name + "', '" + middle_name
												+ "', '" + apartment + "')");
								st.executeUpdate(
										"INSERT INTO resident (id, last_name, first_name, middle_name, apartment) VALUES ('"
												+ id + "', '" + last_name + "', '" + first_name + "', '" + middle_name
												+ "', '" + apartment + "')");
							}
						}
						else {
							try {
								boolean match = false;
								ArrayList<Integer> apartmentIDs = new ArrayList<Integer>();
								st = conn.createStatement();
								ResultSet rs = st.executeQuery(
										"SELECT apartment.id FROM apartment, house, worker WHERE worker.house = house.id AND house.id = apartment.house AND worker.id = "
												+ userID);
								while (rs.next()) {
									apartmentIDs.add(rs.getInt("apartment.id"));
								}
								for (int aid : apartmentIDs) {
									if (apartment == aid) {
										match = true;
										if (avoid) {
											System.out.println(
													"INSERT INTO resident (last_name, first_name, middle_name, apartment) VALUES ('"
															+ last_name + "', '" + first_name + "', '" + middle_name
															+ "', '" + apartment + "')");
											st.executeUpdate(
													"INSERT INTO resident (last_name, first_name, middle_name, apartment) VALUES ('"
															+ last_name + "', '" + first_name + "', '" + middle_name
															+ "', '" + apartment + "')");
										}
										else {
											int id = Integer.valueOf(values[i]);
											System.out.println(
													"INSERT INTO resident (id, last_name, first_name, middle_name, apartment) VALUES ('"
															+ id + "', '" + last_name + "', '" + first_name + "', '"
															+ middle_name + "', '" + apartment + "')");
											st.executeUpdate(
													"INSERT INTO resident (id, last_name, first_name, middle_name, apartment) VALUES ('"
															+ id + "', '" + last_name + "', '" + first_name + "', '"
															+ middle_name + "', '" + apartment + "')");
										}
										break;
									}
								}
								if (!match) {
									JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий",
											"Warning", JOptionPane.WARNING_MESSAGE);
								}
							}
							catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										"Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						i += 5;
					}
					if (chbBills.isSelected()) {
						String name = values[i + 1];
						String body = values[i + 2];
						Double charge = Double.valueOf(values[i + 3]);
						Date dateToPay = Date.valueOf(values[i + 4]);
						int resident = Integer.valueOf(values[i + 8]);
						if (isAdmin) {
							if (avoid) {
								System.out.println(
										"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('" + name
												+ "', '" + body + "', '" + charge + "', '" + dateToPay + "', '"
												+ resident + "')");
								st.executeUpdate(
										"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('" + name
												+ "', '" + body + "', '" + charge + "', '" + dateToPay + "', '"
												+ resident + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println(
										"INSERT INTO bill (id, name, body, charge, date_to_pay, resident) VALUES ('"
												+ id + "', '" + name + "', '" + body + "', '" + charge + "', '"
												+ dateToPay + "', '" + resident + "')");
								st.executeUpdate(
										"INSERT INTO bill (id, name, body, charge, date_to_pay, resident) VALUES ('"
												+ id + "', '" + name + "', '" + body + "', '" + charge + "', '"
												+ dateToPay + "', '" + resident + "')");
							}
						}
						else {
							try {
								boolean match = false;
								ArrayList<Integer> residentIDs = new ArrayList<Integer>();
								st = conn.createStatement();
								ResultSet rs = st.executeQuery(
										"SELECT resident.id FROM resident, apartment, house, worker WHERE worker.house = house.id AND house.id = apartment.house AND apartment.id = resident.apartment AND worker.id = "
												+ userID);
								while (rs.next()) {
									residentIDs.add(rs.getInt("resident.id"));
								}
								for (int rid : residentIDs) {
									if (resident == rid) {
										match = true;
										if (avoid) {
											System.out.println(
													"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('"
															+ name + "', '" + body + "', '" + charge + "', '"
															+ dateToPay + "', '" + resident + "')");
											st.executeUpdate(
													"INSERT INTO bill (name, body, charge, date_to_pay, resident) VALUES ('"
															+ name + "', '" + body + "', '" + charge + "', '"
															+ dateToPay + "', '" + resident + "')");
										}
										else {
											int id = Integer.valueOf(values[i]);
											System.out.println(
													"INSERT INTO bill (id, name, body, charge, date_to_pay, resident) VALUES ('"
															+ id + "', '" + name + "', '" + body + "', '" + charge
															+ "', '" + dateToPay + "', '" + resident + "')");
											st.executeUpdate(
													"INSERT INTO bill (id, name, body, charge, date_to_pay, resident) VALUES ('"
															+ id + "', '" + name + "', '" + body + "', '" + charge
															+ "', '" + dateToPay + "', '" + resident + "')");
										}
										break;
									}
								}
								if (!match) {
									JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий",
											"Warning", JOptionPane.WARNING_MESSAGE);
								}
							}
							catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										"Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						i += 9;
					}
					if (chbReports.isSelected()) {
						String name = values[i + 1];
						String body = values[i + 2];
						int worker = Integer.valueOf(values[i + 3]);
						if (isAdmin) {
							if (avoid) {
								System.out.println("INSERT INTO report (name, body, worker) VALUES ('" + name + "', '"
										+ body + "', '" + worker + "')");
								st.executeUpdate("INSERT INTO report (name, body, worker) VALUES ('" + name + "', '"
										+ body + "', '" + worker + "')");
							}
							else {
								int id = Integer.valueOf(values[i]);
								System.out.println("INSERT INTO report (id, name, body, worker) VALUES ('" + id + "', '"
										+ name + "', '" + body + "', '" + worker + "')");
								st.executeUpdate("INSERT INTO report (id, name, body, worker) VALUES ('" + id + "', '"
										+ name + "', '" + body + "', '" + worker + "')");
							}
						}
						else {
							try {
								boolean match = false;
								ArrayList<Integer> workerIDs = new ArrayList<Integer>();
								st = conn.createStatement();
								ResultSet rs = st.executeQuery(
										"SELECT worker.id FROM report, worker WHERE report.worker = worker.id AND worker.id = "
												+ userID);
								while (rs.next()) {
									workerIDs.add(rs.getInt("worker.id"));
								}
								for (int wid : workerIDs) {
									if (worker == wid) {
										match = true;
										if (avoid) {
											System.out.println("INSERT INTO report (name, body, worker) VALUES ('"
													+ name + "', '" + body + "', '" + worker + "')");
											st.executeUpdate("INSERT INTO report (name, body, worker) VALUES ('" + name
													+ "', '" + body + "', '" + worker + "')");
										}
										else {
											int id = Integer.valueOf(values[i]);
											System.out.println("INSERT INTO report (id, name, body, worker) VALUES ('"
													+ id + "', '" + name + "', '" + body + "', '" + worker + "')");
											st.executeUpdate("INSERT INTO report (id, name, body, worker) VALUES ('"
													+ id + "', '" + name + "', '" + body + "', '" + worker + "')");
										}
										break;
									}
								}
								if (!match) {
									JOptionPane.showMessageDialog(null, "К сожалению, у Вас нет таких полномочий",
											"Warning", JOptionPane.WARNING_MESSAGE);
								}
							}
							catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										"Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					for (Component c : panePromptText.getComponents()) {
						if (c instanceof JTextField) {
							System.out.println("Component to delete: " + c);
							isInsertedMap.remove(c);
						}
					}
					updateDataTable(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents,
							chbBills, chbReports);
					updatePromptTable(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills,
							chbReports, paneList, panePromptText, gbc_panePromptText);
				}
				catch (Exception e) {
//					JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Пожалуйста, заполните правильно форму", "Warning",
							JOptionPane.WARNING_MESSAGE);
					System.out.println("Error in insert: " + e);
					e.printStackTrace();
				}
				closeConnection();
			}
		}
	}
	
	
	
	public static String generatePassword() {
		String password = RandomStringUtils.random(8, true, true);
		System.out.println(password);
		return password;
	}
	
	
	
	public static void deleteSelectedRows(JTable tableData, JCheckBox chbWorkers, JCheckBox chbDepartments,
			JCheckBox chbHouses, JCheckBox chbApartments, JCheckBox chbResidents, JCheckBox chbBills,
			JCheckBox chbReports) {
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		for (int index : tableData.getSelectedRows()) {
			selectedRows.add(index);
		}
		if (selectedRows.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Пожалуйста выберите запись для архивации", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			int column = 0;
//			System.out.println("selectedRows.length + 1: " +  selectedRows.length);
			int id = 0;
//			System.out.println("selectedRows[0]: " +  selectedRows[0] + "; selectedRows[selectedRows.length]: " + (selectedRows[selectedRows.length - 1] + 1));
			try {
				openConnection();
				Statement st = conn.createStatement();
				if (chbWorkers.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM worker WHERE worker.id = " + id);
						st.executeUpdate("DELETE FROM worker WHERE worker.id = " + id);
					}
					column += 8;
				}
				if (chbDepartments.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM department WHERE department.id = " + id);
						st.executeUpdate("DELETE FROM department WHERE department.id = " + id);
					}
					column += 2;
				}
				if (chbHouses.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM house WHERE house.id = " + id);
						st.executeUpdate("DELETE FROM house WHERE house.id = " + id);
					}
					column += 5;
				}
				if (chbApartments.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM apartment WHERE apartment.id = " + id);
						st.executeUpdate("DELETE FROM apartment WHERE apartment.id = " + id);
					}
					column += 3;
				}
				if (chbResidents.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM resident WHERE resident.id = " + id);
						st.executeUpdate("DELETE FROM resident WHERE resident.id = " + id);
					}
					column += 5;
				}
				if (chbBills.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM bill WHERE bill.id = " + id);
						st.executeUpdate("DELETE FROM bill WHERE bill.id = " + id);
					}
					column += 5;
				}
				if (chbReports.isSelected()) {
					for (int i = 0; i < selectedRows.size(); i++) {
						id = (int)tableData.getValueAt(selectedRows.get(i), column);
						System.out.println("DELETE FROM report WHERE report.id = " + id);
						st.executeUpdate("DELETE FROM report WHERE report.id = " + id);
					}
				}
				closeConnection();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error in deleteSelectedRows: " + e);
				e.printStackTrace();
			}
		}
	}
	
	
	
//	public static void filter(String query, JTable dataTable) {
//		DefaultTableModel dtm = (DefaultTableModel)dataTable.getModel();
//		TableRowSorter<DefaultTableModel> trs = new TableRowSorter<DefaultTableModel>(dtm);
//		dataTable.setRowSorter(trs);
//		trs.setRowFilter(RowFilter.regexFilter(query));
//	}
//	
//	
//	
//	private static class keyListener implements KeyListener {
//		
//		@Override
//		public void keyTyped(KeyEvent e) {
//			
//			
//		}
//
//		@Override
//		public void keyPressed(KeyEvent e) {
//			
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			String query = ((JTextField)e.getComponent()).getText();
//			System.out.println("Query: " + query);
//			for (Component c1 : ((JLayeredPane)e.getComponent().getParent().getParent()).getComponents()) {
//				if (c1 instanceof JScrollPane) {
//					filter(query, (JTable)((JScrollPane) c1).getViewport().getView());
//				}
//			}
//		}
//	}
//	
//	
//	
//	public static void setKeyListener(JButton bSearch, JLayeredPane panePromptText) {
//		for (Component c : panePromptText.getComponents()) {
//			if (c instanceof JTextField) {
//				c.addKeyListener(new keyListener());
//			}
//		}
//	}
	
	public static void changePassword(JPasswordField pfCurrentPassword, JPasswordField pfNewPassword,
			CardLayout cardLayout, JPanel cardHolder) {
		String currentPassword = new String(pfCurrentPassword.getPassword());
		String newPassword = new String(pfNewPassword.getPassword());
		if (currentPassword.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Пожалуйста введите текущий пароль", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		else {
			if (newPassword.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Пожалуйста введите новый пароль", "Warning",
						JOptionPane.WARNING_MESSAGE);
			}
			else {
				try {
					openConnection();
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("SELECT password FROM worker WHERE worker.id = " + userID);
					if (rs.next()) {
						if (currentPassword.equals(rs.getString("password"))) {
							if (newPassword.length() < 8) {
								JOptionPane.showMessageDialog(null, "Новый пароль не должен быть короче 8 символов",
										"Warning", JOptionPane.WARNING_MESSAGE);
							}
							else {
								st.executeUpdate("UPDATE worker SET worker.password = '" + newPassword
										+ "' WHERE worker.id = " + userID);
								JOptionPane.showMessageDialog(null, "Пароль успешно изменен", "Info",
										JOptionPane.PLAIN_MESSAGE);
								cardLayout.show(cardHolder, "Start");
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Неправильный текущий пароль, попробуйте снова",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					closeConnection();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова",
							"Error", JOptionPane.ERROR_MESSAGE);
					System.out.println("Error in changePassword: " + e);
				}
			}
		}
	}
	
	
	
	public static void declineRequest(JTable tableRequests) {
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		for (int index : tableRequests.getSelectedRows()) {
			selectedRows.add(index);
		}
		if (selectedRows.isEmpty()) {
//			JOptionPane.showMessageDialog(null, "Заявок нет", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			int id = 0;
			try {
				openConnection();
				Statement st = conn.createStatement();
				for (int i = selectedRows.get(0); i < selectedRows.get(selectedRows.size() - 1) + 1; i++) {
					id = (int)tableRequests.getValueAt(i, 0);
					System.out.println("DELETE FROM request WHERE request.id = " + id);
					st.executeUpdate("DELETE FROM request WHERE request.id = " + id);
				}
				closeConnection();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error in declineRequest: " + e);
			}
		}
	}
	
	
	
	public static void acceptRequest(JTable tableRequests) {
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		for (int index : tableRequests.getSelectedRows()) {
			selectedRows.add(index);
		}
		if (selectedRows.isEmpty()) {
//			JOptionPane.showMessageDialog(null, "Заявка не выбрана", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			int id = 0;
			try {
				openConnection();
				HashMap<Integer, ArrayList<Object>> mapData = new HashMap<Integer, ArrayList<Object>>();
				Statement st = conn.createStatement();
				for (int i = selectedRows.get(0); i < selectedRows.get(selectedRows.size() - 1) + 1; i++) {
					ArrayList<Object> row = new ArrayList<Object>();
					id = (int)tableRequests.getValueAt(i, 0);
					System.out.println("SELECT * FROM request WHERE request.id = " + id);
					ResultSet rs = st.executeQuery("SELECT * FROM request WHERE request.id = " + id);
					while (rs.next()) {
						row.add(rs.getString("request.last_name"));
						row.add(rs.getString("request.first_name"));
						row.add(rs.getString("request.middle_name"));
						row.add(rs.getString("request.email"));
						row.add(rs.getString("request.password"));
						row.add(rs.getString("request.profession"));
						row.add(rs.getInt("request.isadmin"));
						row.add(rs.getInt("request.house"));
						mapData.put(i, row);
					}
				}
				for (int x = 0; x < mapData.size(); x++) {
					String last_name = (String)mapData.get(x).get(0);
					String first_name = (String)mapData.get(x).get(1);
					String middle_name = (String)mapData.get(x).get(2);
					String email = (String)mapData.get(x).get(3);
					String password = (String)mapData.get(x).get(4);
					String profession = (String)mapData.get(x).get(5);
					int isadmin = (int)mapData.get(x).get(6);
					int house = (int)mapData.get(x).get(7);
					System.out.println(
							"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
									+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name + "', '"
									+ email + "', '" + password + "', '" + profession + "', '" + isadmin + "', '"
									+ house + "')");
					st.executeUpdate(
							"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
									+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name + "', '"
									+ email + "', '" + password + "', '" + profession + "', '" + isadmin + "', '"
									+ house + "')");
					System.out.println("DELETE FROM request WHERE request.id = " + id);
					st.executeUpdate("DELETE FROM request WHERE request.id = " + id);
				}
				closeConnection();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error in acceptRequest: " + e);
			}
		}
	}
	
	
	
	public static void acceptAllRequests(JTable tableRequests) {
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		for (int index = 0; index < tableRequests.getRowCount(); index++) {
			selectedRows.add(index);
		}
		if (selectedRows.isEmpty()) {
//			JOptionPane.showMessageDialog(null, "Заявок нет", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			int id = 0;
			try {
				openConnection();
				HashMap<Integer, ArrayList<Object>> mapData = new HashMap<Integer, ArrayList<Object>>();
				Statement st = conn.createStatement();
				for (int i = 0; i < tableRequests.getRowCount(); i++) {
					ArrayList<Object> row = new ArrayList<Object>();
					id = (int)tableRequests.getValueAt(i, 0);
					System.out.println("SELECT * FROM request WHERE request.id = " + id);
					ResultSet rs = st.executeQuery("SELECT * FROM request WHERE request.id = " + id);
					while (rs.next()) {
						row.add(rs.getString("request.last_name"));
						row.add(rs.getString("request.first_name"));
						row.add(rs.getString("request.middle_name"));
						row.add(rs.getString("request.email"));
						row.add(rs.getString("request.password"));
						row.add(rs.getString("request.profession"));
						row.add(rs.getInt("request.isadmin"));
						row.add(rs.getInt("request.house"));
						mapData.put(i, row);
					}
					for (int x = 0; x < mapData.size(); x++) {
						String last_name = (String)mapData.get(x).get(0);
						String first_name = (String)mapData.get(x).get(1);
						String middle_name = (String)mapData.get(x).get(2);
						String email = (String)mapData.get(x).get(3);
						String password = (String)mapData.get(x).get(4);
						String profession = (String)mapData.get(x).get(5);
						int isadmin = (int)mapData.get(x).get(6);
						int house = (int)mapData.get(x).get(7);
						System.out.println(
								"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
										+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name + "', '"
										+ email + "', '" + password + "', '" + profession + "', '" + isadmin + "', '"
										+ house + "')");
						st.executeUpdate(
								"INSERT INTO worker (last_name, first_name, middle_name, email, password, profession, isadmin, house) "
										+ "VALUES ('" + last_name + "', '" + first_name + "', '" + middle_name + "', '"
										+ email + "', '" + password + "', '" + profession + "', '" + isadmin + "', '"
										+ house + "')");
						System.out.println("DELETE FROM request WHERE request.id = " + id);
						st.executeUpdate("DELETE FROM request WHERE request.id = " + id);
					}
				}
				closeConnection();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Упс! Что-то пошло не так... Пожалуйста, попробуйте снова", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println("Error in acceptAllRequests: " + e);
			}
		}
	}
	
}
