package user_interface.login_and_register;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import init.Font_init;
import user_interface.Notify_interface;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Register_interface extends JFrame {
	private JPanel contentPane;
	
	private static JLabel register_page_label;
	private static JLabel username_label;
	private static JLabel password_label;
	private static JLabel fullNameLabel;
	private static JLabel phoneNumberLabel;
	private static JLabel cityLabel;
	private static JLabel districtLabel;
	private static JLabel schoolLabel;
	private static JLabel blockLabel;
	private static JLabel classLabel;
	private static JLabel bornDateLabel;
	private static JLabel login_label;
	
	private static JTextField usernameTextField;
	private static JTextField passwordTextField;
	private static JTextField fullNameTextField;
	private static JTextField phoneNumberTextField;
	private static JTextField cityTextField;
	private static JTextField districtTextField;
	private static JTextField schoolTextField;
	private static JTextField classTextField;
	private static JTextField blockTextField;
	
	private static JComboBox<Integer> bornDayComboBox;
	private static JComboBox<Integer> bornMonthComboBox;
	private static JComboBox<Integer> bornYearComboBox;
	
	public static void create_new_window() {
		Register_interface frame = new Register_interface();
		frame.setVisible(true);
	}

	public Register_interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// @part JLabel setup
		register_page_label = new JLabel("Trang Đăng Ký");
		register_page_label.setBounds(420, 25, 300, 70);
		register_page_label.setFont(Font_init.SanFranciscoText_Bold.deriveFont(25f));
		contentPane.add(register_page_label);

		username_label = new JLabel("Tên Đăng Nhập");
		username_label.setBounds(30, 140, 150, 50);
		username_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(username_label);

		password_label = new JLabel("Mật Khẩu");
		password_label.setBounds(30, 220, 150, 50);
		password_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(password_label);
		
		fullNameLabel = new JLabel("Họ Và Tên");
		fullNameLabel.setBounds(30, 300, 150, 50);
		fullNameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(fullNameLabel);
		
		phoneNumberLabel = new JLabel("Số Điện Thoại");
		phoneNumberLabel.setBounds(30, 380, 150, 50);
		phoneNumberLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(phoneNumberLabel);
		
		cityLabel = new JLabel("Tỉnh/Thành Phố");
		cityLabel.setBounds(30, 460, 150, 50);
		cityLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(cityLabel);
		
		districtLabel = new JLabel("Quận/Huyện");
		districtLabel.setBounds(540, 140, 150, 50);
		districtLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(districtLabel);
		
		schoolLabel = new JLabel("Trường");
		schoolLabel.setBounds(540, 220, 150, 50);
		schoolLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(schoolLabel);
		
		blockLabel = new JLabel("Khối");
		blockLabel.setBounds(540, 300, 150, 50);
		blockLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(blockLabel);
		
		classLabel = new JLabel("Lớp");
		classLabel.setBounds(790, 300, 50, 50);
		classLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(classLabel);
		
		bornDateLabel = new JLabel("Ngày-Tháng-Năm Sinh");
		bornDateLabel.setBounds(540, 380, 200, 50);
		bornDateLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(bornDateLabel);
		
		login_label = new JLabel("Đã Có Tài Khoản ?");
		Map<TextAttribute, Object> attributes = new HashMap<>(Font_init.SanFranciscoText_Medium.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		login_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(attributes));
		login_label.setFont(login_label.getFont().deriveFont(12f));
		login_label.setBounds(30, 592, 200, 50);
		login_label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login_interface.create_new_window();
				dispose();
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(login_label);

		// @part JTextField setup
		usernameTextField = new JTextField();
		usernameTextField.setBounds(200, 141, 250, 50);
		usernameTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));	
		contentPane.add(usernameTextField);
	
		passwordTextField = new JTextField();
		passwordTextField.setBounds(200, 221, 250, 50);
		passwordTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(passwordTextField);

		fullNameTextField = new JTextField();
		fullNameTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		fullNameTextField.setBounds(200, 301, 250, 50);
		contentPane.add(fullNameTextField);

		phoneNumberTextField = new JTextField();
		phoneNumberTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		phoneNumberTextField.setBounds(200, 381, 250, 50);
		contentPane.add(phoneNumberTextField);

		cityTextField = new JTextField();
		cityTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		cityTextField.setBounds(200, 461, 250, 50);
		contentPane.add(cityTextField);

		districtTextField = new JTextField();
		districtTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		districtTextField.setBounds(670, 141, 250, 50);
		contentPane.add(districtTextField);
		
		schoolTextField = new JTextField();
		schoolTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		schoolTextField.setBounds(670, 221, 250, 50);
		contentPane.add(schoolTextField);

		blockTextField = new JTextField();
		blockTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		blockTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		blockTextField.setBounds(670, 301, 70, 50);
		contentPane.add(blockTextField);
		
		classTextField = new JTextField();
		classTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		classTextField.setBounds(850, 301, 70, 50);
		contentPane.add(classTextField);

		// @part JComboBox setup
		bornDayComboBox = new JComboBox<Integer>();
		bornMonthComboBox = new JComboBox<Integer>();
		bornYearComboBox = new JComboBox<Integer>();
		bornDayComboBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		bornMonthComboBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		bornYearComboBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));

		bornDayComboBox.addItem(0);
		bornMonthComboBox.addItem(0);
		bornYearComboBox.addItem(0);

		bornDayComboBox.setBounds(670, 440, 55, 50);
		if (bornDayComboBox.getItemCount() == 1)
			for (Integer i = 1; i <= 31; i++)
				bornDayComboBox.addItem(i);
		contentPane.add(bornDayComboBox);

		bornMonthComboBox.setBounds(760, 440, 55, 50);
		if (bornMonthComboBox.getItemCount() == 1)
			for (Integer i = 1; i <= 12; i++)
				bornMonthComboBox.addItem(i);
		contentPane.add(bornMonthComboBox);

		bornYearComboBox.setBounds(850, 440, 70, 50);
		if (bornYearComboBox.getItemCount() == 1)
			for (Integer i = 1900; i <= 2100; i++)
				bornYearComboBox.addItem(i);
		contentPane.add(bornYearComboBox);
		
		JButton register_submit_button = new JButton("Đăng Ký");
		register_submit_button.setBounds(782, 544, 177, 100);
		register_submit_button.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		register_submit_button.addActionListener(e -> {
			try {
				if (Client.register(get_register_data())) {
					Login_interface.create_new_window();
					dispose();
					Notify_interface.create_new_window("Đăng Ký Thành Công");
				}
			} catch (Exception e1) {
			}
		});
		contentPane.add(register_submit_button);
	}
	
	public static String get_register_data() {
		String register_data = "";
		
		register_data += usernameTextField.getText().trim() + '\n';
		register_data += passwordTextField.getText().trim() + '\n';
		register_data += fullNameTextField.getText().trim() + '\n';
		register_data += phoneNumberTextField.getText().trim() + '\n';
		register_data += cityTextField.getText().trim() + '\n';
		register_data += districtTextField.getText().trim() + '\n';
		register_data += schoolTextField.getText().trim() + '\n';
		register_data += blockTextField.getText().trim() + '\n';
		register_data += classTextField.getText().trim() + '\n';
		register_data += String.valueOf(bornDayComboBox.getSelectedItem()).trim() + ' ';
		register_data += String.valueOf(bornMonthComboBox.getSelectedItem()).trim() + ' ';
		register_data += String.valueOf(bornYearComboBox.getSelectedItem()).trim() + ' ';
				
		return register_data;
	}
}
