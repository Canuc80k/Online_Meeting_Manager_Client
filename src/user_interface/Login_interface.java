package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Login_interface extends JFrame {
	public static final String IS_LOGINED_FILE_PATH = "config/is_logined"; 
	public static final String ACCOUNT_ID_FILE_PATH = "account/account_id";
	private static final String FAIL_TO_LOGIN_SIGNAL = "FAIL_TO_LOGIN";
	
	private JPanel contentPane;
	private static JTextField username_textField;
	private static JTextField password_textField;

	public static void create_new_window() {
		Login_interface frame = new Login_interface();
		frame.setVisible(true);
	}
	
	public Login_interface() {			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel login_page_label = new JLabel("Trang Đăng Nhập");
		login_page_label.setBounds(148, 21, 261, 69);
		login_page_label.setFont(Font_init.SanFranciscoText_Bold.deriveFont(22f));
		contentPane.add(login_page_label);
		
		JLabel username_label = new JLabel("Tên Đăng Nhập");
		username_label.setBounds(33, 145, 128, 34);
		username_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(username_label);
		
		JLabel password_label = new JLabel("Mật Khẩu");
		password_label.setBounds(33, 233, 128, 34);
		password_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(password_label);
		
		JLabel register_label = new JLabel("Chưa có tài khoản");
		Map<TextAttribute, Object> attributes = new HashMap<>(Font_init.SanFranciscoText_Medium.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		register_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(attributes));
		register_label.setFont(register_label.getFont().deriveFont(12f));
		register_label.setBounds(33, 387, 150, 13);
		register_label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Register_interface.create_new_window();
				dispose();
			}
			
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(register_label);
		
		username_textField = new JTextField();
		username_textField.setBounds(224, 133, 210, 60);
		username_textField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(username_textField);
		username_textField.setColumns(10);
		
		password_textField = new JTextField();
		password_textField.setBounds(224, 221, 210, 60);
		password_textField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(password_textField);
		password_textField.setColumns(10);
		
		JButton login_submit_button = new JButton("Đăng Nhập");
		login_submit_button.setBounds(342, 364, 119, 59);
		login_submit_button.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		login_submit_button.addActionListener(e -> {
			try {
				String account_id = Client.login(username_textField.getText(), password_textField.getText());
				if (!account_id.equals(FAIL_TO_LOGIN_SIGNAL)) {
					FileTool.write_file("true", IS_LOGINED_FILE_PATH);
					FileTool.write_file(account_id, ACCOUNT_ID_FILE_PATH);
					Main_interface.create_new_window();
					dispose();
				}
			} catch (Exception e1) {}
		});
		contentPane.add(login_submit_button);
	}
}
