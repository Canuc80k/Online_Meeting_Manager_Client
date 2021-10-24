package user_interface.login_and_register;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import user_interface.Main_interface;
import user_interface.Notify_interface;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Login_interface extends JFrame {
	private static final String IS_LOGINED_FILE_PATH = "src/main/resources/config/is_logined"; 
	private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	private static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
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
		setSize(500, 500);	
		setResizable(false);
		setLocationRelativeTo(null);
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
		login_submit_button.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				try {
					String account_id = Client.login(username_textField.getText(), password_textField.getText());
					if (!account_id.equals(FAIL_TO_LOGIN_SIGNAL)) {
						if (! new File(MEETING_JOINED_FOLDER_PATH).exists()) new File(MEETING_JOINED_FOLDER_PATH).mkdirs();
						
						FileTool.write_file("true", IS_LOGINED_FILE_PATH);
						FileTool.write_file(account_id, ACCOUNT_ID_FILE_PATH);
						Main_interface.create_new_window();
						
						Thread load_data_from_database_thread = new Thread(new Runnable() {
							public void run() {
								String all_joined_meetings = "";
								try {all_joined_meetings = Client.get_joined_meetings(account_id).trim();} catch (Exception e) {}
								if (all_joined_meetings.equals("FAIL_TO_GET_JOINED_MEETINGS")) return;
								
								List<String> all_joined_meetings_list = Arrays.asList(all_joined_meetings.split("\n"));
								for (int i = 0; i < all_joined_meetings_list.size(); i ++)
									try {
										String joined_meeting_data_folder_path = MEETING_JOINED_FOLDER_PATH + all_joined_meetings_list.get(i);
										if (!new File(joined_meeting_data_folder_path).exists()) new File(joined_meeting_data_folder_path).mkdirs();
										FileTool.write_file(
												Client.get_meeting_info(all_joined_meetings_list.get(i)), 
												joined_meeting_data_folder_path + "/meeting_information"
										);
									} catch (Exception e) {}
							}
						});
						load_data_from_database_thread.start();
	
						dispose();
						Notify_interface.create_new_window("Đăng Nhập Thành Công");
					} 
				} catch (Exception e1) {}
			}
		});
		contentPane.add(login_submit_button);
	}
}
