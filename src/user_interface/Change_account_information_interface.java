package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Change_account_information_interface extends JFrame {
	public static final String ACCOUNT_ID_FILE_PATH = "account/account_id";
	public static final String ACCOUNT_INFORMATION_FILE_PATH = "account/account_info";
	
	private JPanel contentPane;

	private static JLabel nameLabel;
	private static JLabel classLabel;
	private static JLabel schoolLabel;
	private static JLabel phoneLabel;
	private static JLabel sexLabel;
	
	private static JTextField nameTextField;
	private static JTextField phoneTextField;
	private static JTextField classTextField;
	private static JTextField schoolTextField;
	
	private static JRadioButton maleRadioButton;
	private static JRadioButton femaleRadioButton;
	
	private static String account_id;
	private static String user_name;
	private static String user_class;
	private static String user_school;
	private static String user_phone;
	private static String user_sex;
	
	public static void create_new_window() throws Exception {
		account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();
		Change_account_information_interface frame = new Change_account_information_interface();
		init();
		frame.setVisible(true);
	}

	public Change_account_information_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 630, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// @part JLabel setup
		nameLabel = new JLabel("Họ Và Tên");
		classLabel = new JLabel("Lớp");
		schoolLabel = new JLabel("Trường");
		phoneLabel = new JLabel("Số Điện Thoại");
		sexLabel = new JLabel("Giới Tính");
		
		nameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		classLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		schoolLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		phoneLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		sexLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		
		phoneLabel.setBounds(60, 240, 145, 70);
		contentPane.add(phoneLabel);
		nameLabel.setBounds(60, 30, 129, 70);
		classLabel.setBounds(60, 100, 179, 70);
		sexLabel.setBounds(60, 310, 129, 70);
		schoolLabel.setBounds(60, 170, 200, 70);
		contentPane.add(nameLabel);
		contentPane.add(classLabel);
		contentPane.add(sexLabel);
		contentPane.add(schoolLabel);

		// @part JTextField setup
		nameTextField = new JTextField();
		phoneTextField = new JTextField();
		classTextField = new JTextField();
		schoolTextField = new JTextField();
		
		nameTextField.setBounds(330, 31, 240, 50);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);

		phoneTextField.setBounds(330, 251, 240, 50);
		contentPane.add(phoneTextField);
		phoneTextField.setColumns(10);

		classTextField.setColumns(10);
		classTextField.setBounds(330, 111, 240, 50);
		contentPane.add(classTextField);

		schoolTextField.setColumns(10);
		schoolTextField.setBounds(330, 181, 240, 50);
		contentPane.add(schoolTextField);

		nameTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		phoneTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		classTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		schoolTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		
		// @part JRadioButton setup
		maleRadioButton = new JRadioButton("Nam");
		maleRadioButton.setBounds(330, 335, 179, 21);
		maleRadioButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		maleRadioButton.addActionListener(e -> {
			if (maleRadioButton.isSelected()) {
				femaleRadioButton.setSelected(false);
			}
		});
		contentPane.add(maleRadioButton);
		
		femaleRadioButton = new JRadioButton("Nữ");
		femaleRadioButton.setBounds(330, 365, 179, 21);
		femaleRadioButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		femaleRadioButton.addActionListener(e -> {
			if (femaleRadioButton.isSelected()) {
				maleRadioButton.setSelected(false);
			} 
		});
		contentPane.add(femaleRadioButton);
		
		// @part JButton setup
		JButton submitButton = new JButton("Chỉnh sửa");
		submitButton.setBounds(412, 406, 158, 80);
		submitButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		submitButton.addActionListener(e -> {
				user_name = nameTextField.getText();
				user_class = classTextField.getText();
				user_school = schoolTextField.getText();
				user_phone = phoneTextField.getText();
				if (maleRadioButton.isSelected()) user_sex = "MALE";
				else user_sex = "FEMALE";
				
				String user_information = user_name + '\n' + user_class + '\n' + user_school + '\n' + user_phone + '\n' + user_sex;
				try {
					Client.change_account_information(account_id, user_information);
					FileTool.write_file(user_information, ACCOUNT_INFORMATION_FILE_PATH);
					dispose();
				} catch (Exception e1) {}
		});
		contentPane.add(submitButton);

		JButton cancelButton = new JButton("Huỷ");
		cancelButton.setBounds(309, 406, 80, 80);
		cancelButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
	}
	
	public static void init() throws Exception {
		String user_information = FileTool.read_file(ACCOUNT_INFORMATION_FILE_PATH);
		List<String> user_information_list = Arrays.asList(user_information.split("\n"));
		
		nameTextField.setText(user_information_list.get(0).trim());
		classTextField.setText(user_information_list.get(1).trim());
		schoolTextField.setText(user_information_list.get(2).trim());
		phoneTextField.setText(user_information_list.get(3).trim());
		if (user_information_list.get(4).trim().equals("MALE")) maleRadioButton.setSelected(true);
		else femaleRadioButton.setSelected(true);
	}
	
}
