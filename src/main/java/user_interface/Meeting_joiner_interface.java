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
public class Meeting_joiner_interface extends JFrame {
	public static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;
	private static String meetingID;
	private JTextField meetingIDTextField;
	private static String meetingData;
	private static String accountID;
	
	public static void create_new_window() {
		Meeting_joiner_interface frame = new Meeting_joiner_interface();
		frame.setVisible(true);
	}

	public Meeting_joiner_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel meetingIDLabel = new JLabel();
		meetingIDLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		meetingIDLabel.setText("Nhập ID cuộc họp");
		meetingIDLabel.setBounds(33, 47, 119, 82);
		contentPane.add(meetingIDLabel);
		
		meetingIDTextField = new JTextField();
		meetingIDTextField.setBounds(216, 62, 173, 54);
		meetingIDTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(meetingIDTextField);
		meetingIDTextField.setColumns(10);
		
		JButton joinMeetingButton = new JButton("Tham gia");
		joinMeetingButton.setBounds(270, 175, 119, 60);
		joinMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		joinMeetingButton.addActionListener(e -> {
			meetingID = meetingIDTextField.getText();
			if (!check_valid_meeting_id(meetingID)) return;
			try {accountID = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();} catch (Exception e2) {}

			File meetingIDFile = new File(MEETING_JOINED_FOLDER_PATH + meetingID);
			if (!meetingIDFile.exists()) {
				try {
					meetingData = Client.join_meeting(meetingID, accountID);
					if (meetingData.equals("FAIL_TO_JOIN_MEETING")) {
						return;
					}
				} catch (Exception e1) {
				}
				
				if (meetingData != null) {
					meetingIDFile.mkdirs();
					try {
						FileTool.write_file(meetingData, meetingIDFile.getPath() + "/meeting_information");
					} catch (Exception e1) {}
					dispose();
					Notify_interface.create_new_window("Tham Gia Cuộc Họp Thành Công");
				}
			}
		});
		contentPane.add(joinMeetingButton);
	}
	
	private static boolean check_valid_meeting_id(String meeting_id) {
		boolean result = true;
		
		if (meeting_id.length() != 10) result = false;
		try {
			Integer.parseInt(meeting_id);
		} catch(Exception e) {
			result = false;
		}
		
		return result;		
	}
}
