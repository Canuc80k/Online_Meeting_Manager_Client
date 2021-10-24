package user_interface.meeting_creator_and_joiner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import user_interface.Notify_interface;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Meeting_joiner_interface extends JFrame {
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	private static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;
	private static String meetingID;
	private JTextField meetingIDTextField;
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
		joinMeetingButton.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				meetingID = meetingIDTextField.getText().trim();
				if (!check_valid_meeting_id(meetingID)) return;
				try {accountID = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();} catch (Exception e2) {}
	
				try {
					String meeting_information = Client.join_meeting(meetingID, accountID);
					if (meeting_information.equals("FAIL_TO_JOIN_MEETING")) return;
					FileTool.write_file(meeting_information, MEETING_JOINED_FOLDER_PATH + meetingID);
					
					dispose();
					Notify_interface.create_new_window("Tham Gia Cuộc Họp Thành Công");
				} catch(Exception e1) {}
			}
		});
		contentPane.add(joinMeetingButton);
	}
	
	private static boolean check_valid_meeting_id(String meeting_id) {
		boolean result = true;
		if (meeting_id.length() != 10) result = false;
		try {Integer.parseInt(meeting_id);} 
		catch(Exception e) {result = false;}
		return result;		
	}
}
