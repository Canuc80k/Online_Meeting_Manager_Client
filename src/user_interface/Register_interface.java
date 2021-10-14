package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
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
public class Register_interface extends JFrame {
	private JPanel contentPane;
	private static JTextField username_textField;
	private static JTextField password_textField;

	public static void create_new_window() {
		Register_interface frame = new Register_interface();
		frame.setVisible(true);
	}
	
	public Register_interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel register_page_label = new JLabel("Trang Đăng Ký");
		register_page_label.setBounds(148, 21, 261, 69);
		register_page_label.setFont(Font_init.SanFranciscoText_Bold.deriveFont(22f));
		contentPane.add(register_page_label);
		
		JLabel username_label = new JLabel("Tên Đăng Nhập");
		username_label.setBounds(33, 145, 128, 34);
		username_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(username_label);
		
		JLabel password_label = new JLabel("Mật Khẩu");
		password_label.setBounds(33, 233, 128, 34);
		password_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(password_label);
		
		JLabel login_label = new JLabel("Đã có tài khoản");
		Map<TextAttribute, Object> attributes = new HashMap<>(Font_init.SanFranciscoText_Medium.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		login_label.setFont(Font_init.SanFranciscoText_Medium.deriveFont(attributes));
		login_label.setFont(login_label.getFont().deriveFont(12f));
		login_label.setBounds(33, 387, 150, 13);
		login_label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login_interface.create_new_window();
				dispose();
			}
			
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(login_label);
		
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
		
		JButton register_submit_button = new JButton("Đăng Ký");
		register_submit_button.setBounds(342, 364, 119, 59);
		register_submit_button.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		register_submit_button.addActionListener(e -> {
			try {
				if (Client.register(username_textField.getText(), password_textField.getText())) {
					Login_interface.create_new_window();
					dispose();
				}
			} catch (Exception e1) {}
		});
		contentPane.add(register_submit_button);
	}
}
