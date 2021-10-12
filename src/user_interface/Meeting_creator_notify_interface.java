package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

import java.awt.Font;

@SuppressWarnings("serial")
public class Meeting_creator_notify_interface extends JFrame {
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;
	private static String meetingID;
	
	public static void create_new_window() {
		Meeting_creator_notify_interface frame = new Meeting_creator_notify_interface();
		frame.setVisible(true);
	}

	public Meeting_creator_notify_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel notifyLabel = new JLabel();
		notifyLabel.setText("ID cuộc họp là: " + meetingID);
		notifyLabel.setBounds(10, 32, 172, 116);
		contentPane.add(notifyLabel);
	}
	
	public static void set_meeting_ID(String newMeetingID) {
		meetingID = newMeetingID;
	}
}