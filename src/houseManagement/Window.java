package houseManagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;

public class Window extends JFrame {
	static Connection conn;
	static CardLayout cardLayout = new CardLayout();
	static CardLayout cl_lpSelect = new CardLayout();
	static JPanel cardHolder;
	private JPasswordField passwordField;
	private JTextField textFieldEmail;
	private JTable tableData;
	private JTextField tfFirstName;
	private JTextField tfMiddleName;
	private JTextField tfEmail;
	private JPasswordField pfPassword2;
	private JTextField tfLastName;
	private JTextField tfProf;
	private JTable tableRequests;
	private JTextField tfIDHouse;
	private JPasswordField pfNewPassword;
	private JPasswordField pfCurrentPassword;
	private JPasswordField pfPassword1;
	
	public Window() {
		cardHolder = new JPanel(cardLayout);
		getContentPane().add(cardHolder, BorderLayout.CENTER);
		
		JLayeredPane lpStart = new JLayeredPane();
		cardHolder.add(lpStart, "Start");
		lpStart.setLayout(null);
		
		JLabel lWelcome = new JLabel("\u0414\u043E\u0431\u0440\u043E \u043F\u043E\u0436\u0430\u043B\u043E\u0432\u0430\u0442\u044C \u0432 \u0430\u0432\u0442\u043E\u043C\u0430\u0442\u0438\u0437\u0438\u0440\u043E\u0432\u0430\u043D\u043D\u0443\u044E \u0441\u0438\u0441\u0442\u0435\u043C\u0443 \u0434\u043E\u043C\u043E\u0443\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u044F (\u0410\u0421\u0414) \u0433\u043E\u0440\u043E\u0434\u0430 \u0410\u0440\u0445\u0430\u043D\u0433\u0435\u043B\u044C\u0441\u043A");
		lWelcome.setBounds(46, 102, 654, 19);
		lpStart.add(lWelcome);
		lWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lWelcome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton bLogin = new JButton("Log in");
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Login");
			}
		});
		lpStart.add(bLogin);
		bLogin.setBounds(639, 11, 68, 23);
		
		JButton bLists = new JButton("\u0421\u043F\u0438\u0441\u043A\u0438");
		bLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "List");
			}
		});
//		bLists.setVisible(false);
		bLists.setBounds(10, 11, 89, 23);
		lpStart.add(bLists);
		
		JButton bRegistration = new JButton("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044F");
		bRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Registration");
			}
		});
		bRegistration.setBounds(509, 11, 120, 23);
		lpStart.add(bRegistration);
		
		JButton bRequests = new JButton("\u0417\u0430\u044F\u0432\u043A\u0438");
		bRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.updateRequestsTable(tableRequests);
				cardLayout.show(cardHolder, "Requests");
			}
		});
//		bRequests.setVisible(false);
		bRequests.setBounds(266, 11, 81, 23);
		lpStart.add(bRequests);
		
		JButton bChangePassword = new JButton("\u0421\u043C\u0435\u043D\u0438\u0442\u044C \u043F\u0430\u0440\u043E\u043B\u044C");
		bChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Change password");
			}
		});
//		bChangePassword.setVisible(false);
		bChangePassword.setBounds(109, 11, 147, 23);
		lpStart.add(bChangePassword);
		
		JButton bLogout = new JButton("Logout");
		bLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bLists.setVisible(false);
				bChangePassword.setVisible(false);
				bRequests.setVisible(false);
				bRegistration.setVisible(true);
				bLogin.setVisible(true);
				bLogout.setVisible(false);
			}
		});
		bLogout.setVisible(false);
		bLogout.setBounds(618, 11, 89, 23);
		lpStart.add(bLogout);
		
		JLayeredPane lpLogin = new JLayeredPane();
		cardHolder.add(lpLogin, "Login");
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton rdbtnAdministrator = new JRadioButton("\u0410\u0434\u043C\u0438\u043D\u0438\u0441\u0442\u0440\u0430\u0442\u043E\u0440");
		rdbtnAdministrator.setBounds(342, 82, 121, 23);
		lpLogin.add(rdbtnAdministrator);
		bg.add(rdbtnAdministrator);
		
		JRadioButton rdbtnUser = new JRadioButton("\u041F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044C");
		rdbtnUser.setBounds(211, 82, 129, 23);
		lpLogin.add(rdbtnUser);
		bg.add(rdbtnUser);
		
		JLabel lblAuthorization = new JLabel("\u0410\u0432\u0442\u043E\u0440\u0438\u0437\u0430\u0446\u0438\u044F");
		lblAuthorization.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthorization.setBounds(292, 38, 117, 19);
		lpLogin.add(lblAuthorization);
		
		JLabel lblPassword = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(211, 156, 48, 14);
		lpLogin.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(269, 153, 136, 20);
		lpLogin.add(passwordField);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.authorization(rdbtnUser, rdbtnAdministrator, textFieldEmail, passwordField, cardLayout, cardHolder, bLists, bChangePassword, bRequests, bRegistration, bLogin, bLogout);
			}
		});
		btnOk.setBounds(415, 152, 58, 23);
		lpLogin.add(btnOk);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(211, 125, 48, 14);
		lpLogin.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(269, 125, 136, 20);
		lpLogin.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JButton button_1 = new JButton("\u041D\u0430\u0437\u0430\u0434");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
				DbWork.emptyTextFields(textFieldEmail, passwordField);
			}
		});
		button_1.setBounds(10, 11, 79, 23);
		lpLogin.add(button_1);
		
		JLayeredPane lpData = new JLayeredPane();
		cardHolder.add(lpData, "List");
		GridBagLayout gbl_lpData = new GridBagLayout();
		gbl_lpData.columnWidths = new int[]{132, 451};
		gbl_lpData.rowHeights = new int[]{235, 26, 0};
		gbl_lpData.columnWeights = new double[]{1.0, 1.0};
		gbl_lpData.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		lpData.setLayout(gbl_lpData);
		
		JLayeredPane lpSelect = new JLayeredPane();
		GridBagConstraints gbc_lpSelect = new GridBagConstraints();
		gbc_lpSelect.insets = new Insets(0, 0, 5, 5);
		gbc_lpSelect.fill = GridBagConstraints.BOTH;
		gbc_lpSelect.gridx = 0;
		gbc_lpSelect.gridy = 0;
		lpData.add(lpSelect, gbc_lpSelect);
		lpSelect.setLayout(cl_lpSelect);
		
		JLayeredPane lpSelect2 = new JLayeredPane();
		lpSelect.add(lpSelect2, "Select");
		
		JCheckBox chbWorkers = new JCheckBox("\u0421\u043E\u0442\u0440\u0443\u0434\u043D\u0438\u043A\u0438");
		chbWorkers.setBounds(10, 61, 97, 23);
		lpSelect2.add(chbWorkers);
		
		JCheckBox chbDepartments = new JCheckBox("\u041E\u0442\u0434\u0435\u043B\u0435\u043D\u0438\u044F");
		chbDepartments.setBounds(10, 87, 97, 23);
		lpSelect2.add(chbDepartments);
		
		JCheckBox chbHouses = new JCheckBox("\u0414\u043E\u043C\u0430");
		chbHouses.setBounds(10, 113, 97, 23);
		lpSelect2.add(chbHouses);
		
		JCheckBox chbResidents = new JCheckBox("\u0416\u0438\u0442\u0435\u043B\u0438");
		chbResidents.setBounds(109, 61, 97, 23);
		lpSelect2.add(chbResidents);
		
		JCheckBox chbBills = new JCheckBox("\u0421\u0447\u0435\u0442\u0430");
		chbBills.setBounds(109, 87, 97, 23);
		lpSelect2.add(chbBills);
		
		JCheckBox chbReports = new JCheckBox("\u041E\u0442\u0447\u0435\u0442\u044B");
		chbReports.setBounds(109, 113, 87, 23);
		lpSelect2.add(chbReports);
		
		JLayeredPane panePromptText = new JLayeredPane();
		GridBagConstraints gbc_panePromptText = new GridBagConstraints();
		gbc_panePromptText.fill = GridBagConstraints.BOTH;
		gbc_panePromptText.gridx = 1;
		gbc_panePromptText.gridy = 1;
		JButton btnOk_1 = new JButton("OK");
		
		JLayeredPane lpAddDelete = new JLayeredPane();
		GridBagConstraints gbc_lpAddDelete = new GridBagConstraints();
		gbc_lpAddDelete.anchor = GridBagConstraints.EAST;
		gbc_lpAddDelete.insets = new Insets(0, 0, 5, 0);
		gbc_lpAddDelete.fill = GridBagConstraints.VERTICAL;
		gbc_lpAddDelete.gridx = 0;
		gbc_lpAddDelete.gridy = 1;
		
		JCheckBox chbApartments = new JCheckBox("\u041A\u0432\u0430\u0440\u0442\u0438\u0440\u044B");
		chbApartments.setBounds(10, 139, 97, 23);
		lpSelect2.add(chbApartments);
		
//		JButton bSearch = new JButton("\u041F\u043E\u0438\u0441\u043A");
//		bSearch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				DbWork.setKeyListener(bSearch, panePromptText);
//			}
//		});
//		lpAddDelete.add(bSearch);
		
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.updateDataTable(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
				DbWork.updatePromptTable(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports, lpData, panePromptText, gbc_panePromptText);
			}
		});
		btnOk_1.setBounds(71, 175, 65, 23);
		lpSelect2.add(btnOk_1);
		
		JButton bBackSelect = new JButton("\u041D\u0430\u0437\u0430\u0434");
		bBackSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
				DbWork.deselectCheckBoxes(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
				DbWork.resetTable(tableData);
//				DbWork.updateDataTable(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
				DbWork.updatePromptTable(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports, lpData, panePromptText, gbc_panePromptText);
			}
		});
		bBackSelect.setBounds(10, 11, 75, 23);
		lpSelect2.add(bBackSelect);
		
		JScrollPane scpTableData = new JScrollPane();
		GridBagConstraints gbc_scpTableData = new GridBagConstraints();
		gbc_scpTableData.insets = new Insets(0, 0, 5, 0);
		gbc_scpTableData.fill = GridBagConstraints.BOTH;
		gbc_scpTableData.gridx = 1;
		gbc_scpTableData.gridy = 0;
		lpData.add(scpTableData, gbc_scpTableData);
		
		tableData = new JTable();
		tableData.setPreferredScrollableViewportSize(new Dimension (500, 50));
		tableData.setFillsViewportHeight(true);
		scpTableData.setViewportView(tableData);
		
		JLayeredPane lpRegistration = new JLayeredPane();
		cardHolder.add(lpRegistration, "Registration");
		
		JLabel label_1 = new JLabel("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044F");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_1.setBounds(311, 27, 111, 19);
		lpRegistration.add(label_1);
		
		JLabel label_2 = new JLabel("\u0418\u043C\u044F");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(97, 93, 48, 14);
		lpRegistration.add(label_2);
		
		JLabel label_3 = new JLabel("\u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(81, 118, 64, 14);
		lpRegistration.add(label_3);
		
		JLabel lblEmail_1 = new JLabel("Email");
		lblEmail_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail_1.setBounds(102, 143, 48, 14);
		lpRegistration.add(lblEmail_1);
		
		JLabel lblPassword_1 = new JLabel("\u041F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C");
		lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword_1.setBounds(307, 93, 144, 14);
		lpRegistration.add(lblPassword_1);
		
		tfFirstName = new JTextField();
		tfFirstName.setBounds(155, 90, 142, 20);
		lpRegistration.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		tfMiddleName = new JTextField();
		tfMiddleName.setBounds(155, 115, 142, 20);
		lpRegistration.add(tfMiddleName);
		tfMiddleName.setColumns(10);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(155, 140, 142, 20);
		lpRegistration.add(tfEmail);
		tfEmail.setColumns(10);
		
		pfPassword2 = new JPasswordField();
		pfPassword2.setBounds(456, 90, 142, 20);
		lpRegistration.add(pfPassword2);
		
		JButton btnOk_2 = new JButton("OK");
		btnOk_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.registration(tfLastName, tfFirstName, tfMiddleName, tfEmail, pfPassword1, pfPassword2, tfProf, tfIDHouse, cardLayout, cardHolder);
			}
		});
		btnOk_2.setBounds(323, 184, 64, 23);
		lpRegistration.add(btnOk_2);
		
		JButton button_3 = new JButton("\u041D\u0430\u0437\u0430\u0434");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
				DbWork.emptyTextFields(tfLastName, tfFirstName, tfMiddleName, tfEmail, pfPassword1, pfPassword2, tfIDHouse, tfProf);
			}
		});
		button_3.setBounds(10, 11, 77, 23);
		lpRegistration.add(button_3);
		
		JLabel label_4 = new JLabel("\u0424\u0430\u043C\u0438\u043B\u0438\u044F");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(81, 68, 64, 14);
		lpRegistration.add(label_4);
		
		tfLastName = new JTextField();
		tfLastName.setBounds(155, 65, 142, 20);
		lpRegistration.add(tfLastName);
		tfLastName.setColumns(10);
		
		JLabel label_5 = new JLabel("\u041F\u0440\u043E\u0444\u0435\u0441\u0441\u0438\u044F");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(367, 118, 84, 14);
		lpRegistration.add(label_5);
		
		tfProf = new JTextField();
		tfProf.setBounds(456, 115, 142, 20);
		lpRegistration.add(tfProf);
		tfProf.setColumns(10);
		
		tfIDHouse = new JTextField();
		tfIDHouse.setBounds(456, 140, 142, 20);
		lpRegistration.add(tfIDHouse);
		tfIDHouse.setColumns(10);
		
		JLabel lblId = new JLabel("ID \u0434\u043E\u043C\u0430");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(387, 143, 64, 14);
		lpRegistration.add(lblId);
		
		pfPassword1 = new JPasswordField();
		pfPassword1.setBounds(456, 65, 142, 20);
		lpRegistration.add(pfPassword1);
		
		JLabel label = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(387, 68, 64, 14);
		lpRegistration.add(label);
		gbc_scpTableData.insets = new Insets(0, 0, 0, 0);
		gbc_scpTableData.fill = GridBagConstraints.BOTH;
		gbc_scpTableData.gridx = 1;
		gbc_scpTableData.gridy = 0;
		
		
		lpData.add(lpAddDelete, gbc_lpAddDelete);
		lpAddDelete.setLayout(new BoxLayout(lpAddDelete, BoxLayout.X_AXIS));
		
		JButton bInsert = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		bInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.insert(chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports, lpData, panePromptText, gbc_panePromptText, lpAddDelete, gbc_lpAddDelete, tableData);
//				DbWork.updateDataTable(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
			}
		});
		
		bInsert.setHorizontalAlignment(SwingConstants.RIGHT);
		lpAddDelete.add(bInsert);
		
		JButton bDelete = new JButton("\u0412 \u0430\u0440\u0445\u0438\u0432");
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.deleteSelectedRows(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
				DbWork.updateDataTable(tableData, chbWorkers, chbDepartments, chbHouses, chbApartments, chbResidents, chbBills, chbReports);
			}
		});
		bDelete.setHorizontalAlignment(SwingConstants.RIGHT);
		lpAddDelete.add(bDelete);
		
		JLayeredPane lpRequests = new JLayeredPane();
		cardHolder.add(lpRequests, "Requests");
		GridBagLayout gbl_lpRequests = new GridBagLayout();
		gbl_lpRequests.columnWidths = new int[]{120, 504, 0};
		gbl_lpRequests.rowHeights = new int[]{0, 0};
		gbl_lpRequests.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_lpRequests.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		lpRequests.setLayout(gbl_lpRequests);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		GridBagConstraints gbc_layeredPane_1 = new GridBagConstraints();
		gbc_layeredPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_layeredPane_1.fill = GridBagConstraints.BOTH;
		gbc_layeredPane_1.gridx = 0;
		gbc_layeredPane_1.gridy = 0;
		lpRequests.add(layeredPane_1, gbc_layeredPane_1);
		
		JButton button_6 = new JButton("\u041D\u0430\u0437\u0430\u0434");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
			}
		});
		button_6.setBounds(10, 11, 80, 23);
		layeredPane_1.add(button_6);
		
		JButton bAccept = new JButton("\u041F\u0440\u0438\u043D\u044F\u0442\u044C");
		bAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.acceptRequest(tableRequests);
				DbWork.updateRequestsTable(tableRequests);
			}
		});
		bAccept.setBounds(10, 184, 131, 23);
		layeredPane_1.add(bAccept);
		
		JButton bAcceptAll = new JButton("\u041F\u0440\u0438\u043D\u044F\u0442\u044C \u0432\u0441\u0435");
		bAcceptAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.acceptAllRequests(tableRequests);
				DbWork.updateRequestsTable(tableRequests);
			}
		});
		bAcceptAll.setBounds(10, 218, 131, 23);
		layeredPane_1.add(bAcceptAll);
		
		JButton bDecline = new JButton("\u041E\u0442\u043A\u043B\u043E\u043D\u0438\u0442\u044C");
		bDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.declineRequest(tableRequests);
				DbWork.updateRequestsTable(tableRequests);
			}
		});
		bDecline.setBounds(10, 150, 131, 23);
		layeredPane_1.add(bDecline);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		lpRequests.add(scrollPane_1, gbc_scrollPane_1);
		
		tableRequests = new JTable();
		tableRequests.setPreferredScrollableViewportSize(new Dimension (500, 50));
		tableRequests.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tableRequests);
		
		JLayeredPane lpChangePassword = new JLayeredPane();
		cardHolder.add(lpChangePassword, "Change password");
		
		JLabel label_6 = new JLabel("\u0421\u043C\u0435\u043D\u0430 \u043F\u0430\u0440\u043E\u043B\u044F");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_6.setBounds(310, 55, 151, 14);
		lpChangePassword.add(label_6);
		
		JLabel lCurrentPassword = new JLabel("\u0422\u0435\u043A\u0443\u0449\u0438\u0439 \u043F\u0430\u0440\u043E\u043B\u044C");
		lCurrentPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lCurrentPassword.setBounds(178, 109, 137, 14);
		lpChangePassword.add(lCurrentPassword);
		
		JLabel lNewPassword = new JLabel("\u041D\u043E\u0432\u044B\u0439 \u043F\u0430\u0440\u043E\u043B\u044C");
		lNewPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lNewPassword.setBounds(219, 134, 96, 14);
		lpChangePassword.add(lNewPassword);
		
		JButton button_2 = new JButton("\u041D\u0430\u0437\u0430\u0434");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardHolder, "Start");
				DbWork.emptyTextFields(pfCurrentPassword,pfNewPassword);
			}
		});
		button_2.setBounds(10, 11, 89, 23);
		lpChangePassword.add(button_2);
		
		pfNewPassword = new JPasswordField();
		pfNewPassword.setBounds(325, 131, 160, 20);
		lpChangePassword.add(pfNewPassword);
		
		JButton btnOk_3 = new JButton("OK");
		btnOk_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DbWork.changePassword(pfCurrentPassword, pfNewPassword, cardLayout, cardHolder);
			}
		});
		btnOk_3.setBounds(314, 175, 89, 23);
		lpChangePassword.add(btnOk_3);
		
		pfCurrentPassword = new JPasswordField();
		pfCurrentPassword.setBounds(325, 106, 160, 20);
		lpChangePassword.add(pfCurrentPassword);
	}
}
