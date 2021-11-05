package user_interface.manager.manage_by_type;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import user_interface.Notify_interface;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class One_time_meeting_information_changer extends JFrame {
	private static final String APPLICATION_NAME = "Online Meeting Manager";
	private static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	protected static final int MEETING_INFORMATION_STATE_INDEX = 12;

	private JPanel contentPane;

	private static JLabel meetingNameLabel;
	private static JLabel startTimeLabel;
	private static JLabel finishTimeLabel;
	private static JLabel dayStartLabel;
	
	private static JTextField meetingNameTextField;
	private static JTextField hourFinishTextField;
	private static JTextField minuteFinishTextField;
	private static JTextField hourStartTextField;
	private static JTextField minuteStartTextField;
	
	private static JComboBox<Integer> dayBox;
	private static JComboBox<Integer> monthBox;
	private static JComboBox<Integer> yearBox;
	
	private static String meetingID;
	private static List<String> meeting_information_list;
	
	public static void create_new_window(String meetingID, List<String> meeting_information_list) throws Exception {
		if (!(new File(MEETING_CREATED_FOLDER_PATH)).exists()) new File(MEETING_CREATED_FOLDER_PATH).mkdirs();
		
		One_time_meeting_information_changer.meetingID = meetingID;
		One_time_meeting_information_changer.meeting_information_list = meeting_information_list;
		One_time_meeting_information_changer frame = new One_time_meeting_information_changer();
		update_ui();
		frame.setVisible(true);
	}

	public One_time_meeting_information_changer() throws Exception {
		setTitle(APPLICATION_NAME);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// @part JLabel setup
		meetingNameLabel = new JLabel("Tên Cuộc Họp");
		startTimeLabel = new JLabel("Thời Gian Bắt Đầu");
		finishTimeLabel = new JLabel("Thời Gian Kết Thúc");
		dayStartLabel = new JLabel("Ngày Diễn Ra");
		
		meetingNameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		startTimeLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		finishTimeLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		dayStartLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		meetingNameLabel.setBounds(60, 26, 129, 52);
		startTimeLabel.setBounds(60, 90, 179, 48);
		finishTimeLabel.setBounds(60, 151, 179, 48);
		dayStartLabel.setBounds(60, 238, 129, 39);
		contentPane.add(meetingNameLabel);
		contentPane.add(startTimeLabel);
		contentPane.add(finishTimeLabel);
		contentPane.add(dayStartLabel);

		// @part JTextField setup
		meetingNameTextField = new JTextField();
		hourFinishTextField = new JTextField();
		minuteFinishTextField = new JTextField();
		hourStartTextField = new JTextField();
		minuteStartTextField = new JTextField();
		
		meetingNameTextField.setBounds(330, 30, 239, 46);
		contentPane.add(meetingNameTextField);
		meetingNameTextField.setColumns(10);

		hourFinishTextField.setBounds(330, 155, 60, 50);
		contentPane.add(hourFinishTextField);
		hourFinishTextField.setColumns(10);

		minuteFinishTextField.setColumns(10);
		minuteFinishTextField.setBounds(400, 155, 60, 50);
		contentPane.add(minuteFinishTextField);

		hourStartTextField.setColumns(10);
		hourStartTextField.setBounds(330, 90, 60, 50);
		contentPane.add(hourStartTextField);

		minuteStartTextField.setColumns(10);
		minuteStartTextField.setBounds(400, 90, 60, 50);
		contentPane.add(minuteStartTextField);

		meetingNameTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		hourFinishTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		minuteFinishTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		hourStartTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		minuteStartTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		
		// @part JComboBox setup
		dayBox = new JComboBox<Integer>();
		monthBox = new JComboBox<Integer>();
		yearBox = new JComboBox<Integer>();
		dayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		monthBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		yearBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		
		dayBox.addItem(0);
		monthBox.addItem(0);
		yearBox.addItem(0);

		dayBox.setBounds(330, 230, 60, 50);
		if (dayBox.getItemCount() == 1)
			for (Integer i = 1; i <= 31; i++)
				dayBox.addItem(i);
		contentPane.add(dayBox);

		monthBox.setBounds(400, 230, 60, 50);
		if (monthBox.getItemCount() == 1)
			for (Integer i = 1; i <= 12; i++)
				monthBox.addItem(i);
		contentPane.add(monthBox);

		yearBox.setBounds(470, 230, 70, 50);
		if (yearBox.getItemCount() == 1)
			for (Integer i = 2021; i <= 2121; i++)
				yearBox.addItem(i);
		contentPane.add(yearBox);

		// @part JButton setup
		JButton stopMeetingButton = new JButton();
		stopMeetingButton.setBounds(438, 331, 158, 80);
		stopMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));

		String meeting_information = FileTool.read_file(MEETING_CREATED_FOLDER_PATH + meetingID + "/meeting_information");
		List<String> meeting_information_list = Arrays.asList(meeting_information.split("\n"));
		if (meeting_information_list.get(MEETING_INFORMATION_STATE_INDEX).trim().equals("ON")) {
			stopMeetingButton.setText("Tắt Cuộc Họp");
			stopMeetingButton.addActionListener(new ActionListener() {
				public synchronized void actionPerformed(ActionEvent e) {				
					try {
						if (!Client.stop_meeting(meetingID).equals("FAIL_TO_STOP_MEETING")) {	
							String new_meeting_information = "";
							for (int i = 0; i < meeting_information_list.size(); i ++) {
								if (meeting_information_list.get(i).trim().equals("ON")) new_meeting_information += "OFF" + '\n';
								else new_meeting_information += meeting_information_list.get(i).trim() + '\n';
							}
							FileTool.write_file(new_meeting_information, MEETING_CREATED_FOLDER_PATH + meetingID + "/meeting_information");
							dispose();
							Notify_interface.create_new_window("Tắt Cuộc Họp Thành Công");
						}
					} catch (Exception e1) {}
				}
			});
		} else {
			stopMeetingButton.setText("Bật Lại Cuộc Họp");
			stopMeetingButton.addActionListener(new ActionListener() {
				public synchronized void actionPerformed(ActionEvent e) {				
					try {
						if (!Client.start_meeting(meetingID).equals("FAIL_TO_START_MEETING")) {	
							String new_meeting_information = "";
							for (int i = 0; i < meeting_information_list.size(); i ++) {
								if (meeting_information_list.get(i).trim().equals("OFF")) new_meeting_information += "ON" + '\n';
								else new_meeting_information += meeting_information_list.get(i).trim() + '\n';
							}
							FileTool.write_file(new_meeting_information, MEETING_CREATED_FOLDER_PATH + meetingID + "/meeting_information");
							dispose();
							Notify_interface.create_new_window("Bật Lại Cuộc Họp Thành Công");
						}
					} catch (Exception e1) {}
				}
			});
		}
		
		contentPane.add(stopMeetingButton);

		JButton cancelButton = new JButton("Huỷ");
		cancelButton.setBounds(348, 331, 80, 80);
		cancelButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
	}
	
	private static void update_ui() {
		meetingNameTextField.setText(meeting_information_list.get(3));
		for (int i = 4; i <= 10; i ++) 
			if (!meeting_information_list.get(i).equals("null")) {
				List<String> time_list = Arrays.asList(meeting_information_list.get(i).split(" "));
				hourStartTextField.setText(time_list.get(0).trim());
				minuteStartTextField.setText(time_list.get(1).trim());
				hourFinishTextField.setText(time_list.get(2).trim());
				minuteFinishTextField.setText(time_list.get(3).trim());
			}
		
		List<String> start_date = Arrays.asList(meeting_information_list.get(11).split(" "));
		dayBox.setSelectedItem(Integer.parseInt(start_date.get(0).trim()));
		monthBox.setSelectedItem(Integer.parseInt(start_date.get(1).trim()));
		yearBox.setSelectedItem(Integer.parseInt(start_date.get(2).trim()));
	}
	
}
