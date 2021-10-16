package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Change_storage_path_interface extends JFrame {
	public static final String STORAGE_PATH_FILE_PATH = "config/storage_path";

	private static JPanel contentPane;
	private static JTextField meetingIDTextField;
	
	public static void create_new_window() {
		Change_storage_path_interface frame = new Change_storage_path_interface();
		frame.setVisible(true);
	}
	
	public Change_storage_path_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel meetingIDLabel = new JLabel();
		meetingIDLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		meetingIDLabel.setText("Thư mục lưu trữ cuộc họp");
		meetingIDLabel.setBounds(24, 10, 214, 82);
		contentPane.add(meetingIDLabel);
		
		meetingIDTextField = new JTextField();
		try {
			meetingIDTextField.setText(FileTool.read_file(STORAGE_PATH_FILE_PATH).trim());
		} catch (Exception e1) {}
		meetingIDTextField.setBounds(24, 84, 382, 75);
		meetingIDTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(meetingIDTextField);
		meetingIDTextField.setColumns(10);
		
		JButton joinMeetingButton = new JButton("Thay đổi");
		joinMeetingButton.setBounds(287, 180, 119, 60);
		joinMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		joinMeetingButton.addActionListener(e -> {
			try {
				FileTool.write_file(meetingIDTextField.getText().trim(), STORAGE_PATH_FILE_PATH);
				dispose();
			} catch (Exception e1) {}
		});
		contentPane.add(joinMeetingButton);
	}
}
