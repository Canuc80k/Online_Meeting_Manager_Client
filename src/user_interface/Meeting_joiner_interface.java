package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Meeting_joiner_interface extends JFrame {
	public static final String MEETING_JOINED_FOLDER_PATH = "meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "config/account_id.txt";
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
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel meetingIDLabel = new JLabel();
		meetingIDLabel.setText("Nhập ID cuộc họp");
		meetingIDLabel.setBounds(10, 52, 110, 60);
		contentPane.add(meetingIDLabel);
		
		meetingIDTextField = new JTextField();
		meetingIDTextField.setBounds(174, 62, 136, 54);
		contentPane.add(meetingIDTextField);
		meetingIDTextField.setColumns(10);
		
		JButton joinMeetingButton = new JButton("Tham gia");
		joinMeetingButton.setBounds(241, 184, 104, 54);
		joinMeetingButton.addActionListener(e -> {
			meetingID = meetingIDTextField.getText();
			try {accountID = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();} catch (Exception e2) {}

			File meetingIDFile = new File(MEETING_JOINED_FOLDER_PATH + meetingID + ".txt");
			if (!meetingIDFile.exists()) {
				System.out.println(meetingIDFile.getPath());
				
				try {
					meetingData = Client.join_meeting(meetingID, accountID);
					if (meetingData != null) {
						FileTool.write_file(meetingData, meetingIDFile.getPath());
						dispose();
					}
				} catch (Exception e1) {}
			}
		});
		contentPane.add(joinMeetingButton);
	}
}
