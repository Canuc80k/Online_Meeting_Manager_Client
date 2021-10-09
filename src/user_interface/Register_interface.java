package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		register_page_label.setBounds(152, 10, 168, 69);
		contentPane.add(register_page_label);
		
		JLabel username_label = new JLabel("Tên Đăng Nhập");
		username_label.setBounds(33, 136, 107, 13);
		contentPane.add(username_label);
		
		JLabel password_label = new JLabel("Mật Khẩu");
		password_label.setBounds(33, 180, 107, 13);
		contentPane.add(password_label);
		
		JLabel login_label = new JLabel("Đã có tài khoản");
		login_label.setBounds(33, 413, 107, 13);
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
		username_textField.setBounds(224, 133, 96, 19);
		contentPane.add(username_textField);
		username_textField.setColumns(10);
		
		password_textField = new JTextField();
		password_textField.setBounds(224, 177, 96, 19);
		contentPane.add(password_textField);
		password_textField.setColumns(10);
		
		JButton register_submit_button = new JButton("Đăng Ký");
		register_submit_button.setBounds(357, 394, 85, 21);
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
