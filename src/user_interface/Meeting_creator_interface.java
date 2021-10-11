package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Meeting_creator_interface extends JFrame {
	public static final String MEETING_CREATED_FOLDER_PATH = "meeting/meeting_created/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;

	private static JLabel meetingNameLabel;
	private static JLabel startTimeLabel;
	private static JLabel meetingLengthLabel;
	private static JLabel meetingStateLabel;
	private static JLabel dayStartLabel;
	private static JLabel dayRepeatLabel;
	
	private static JTextField meetingNameTextField;
	private static JTextField hourLengthTextField;
	private static JTextField minuteLengthTextField;
	private static JTextField hourStartTextField;
	private static JTextField minuteStartTextField;
	
	private static JRadioButton oneTimeMeetingRadioButton;
	private static JRadioButton weeklyMeetingRadioButton;
	
	private static JComboBox<Integer> dayDate;
	private static JComboBox<Integer> monthDate;
	private static JComboBox<Integer> yearDate;
	
	private static JCheckBox mondayBox;
	private static JCheckBox tuesdayBox;
	private static JCheckBox wednesdayBox;
	private static JCheckBox thursdayBox;
	private static JCheckBox fridayBox;
	private static JCheckBox saturdayBox;
	private static JCheckBox sundayBox;
	
	private static String meetingName;
	private static Point timeStartPoint;
	private static int meetingLengthValue;
	private static String dayStartDate;
	private static List<Integer> daysInWeekHaveMeeting;
	
	public static void create_new_window() {
		Meeting_creator_interface frame = new Meeting_creator_interface();
		frame.setVisible(true);
	}

	public Meeting_creator_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// @part JLabel setup
		meetingNameLabel = new JLabel("Tên Cuộc Họp");
		startTimeLabel = new JLabel("Thời gian bắt đầu");
		meetingLengthLabel = new JLabel("Độ Dài Cuộc Họp");
		meetingStateLabel = new JLabel("Cuộc Họp Này");
		dayStartLabel = new JLabel("Ngày Diễn Ra");
		dayRepeatLabel = new JLabel("Lặp Lại Hàng Tuần");
		meetingStateLabel.setBounds(29, 218, 83, 13);
		contentPane.add(meetingStateLabel);
		meetingNameLabel.setBounds(29, 10, 96, 39);
		startTimeLabel.setBounds(29, 75, 179, 33);
		dayStartLabel.setBounds(22, 328, 129, 39);
		meetingLengthLabel.setBounds(29, 130, 200, 52);
		dayRepeatLabel.setBounds(29, 396, 45, 13);
		contentPane.add(meetingNameLabel);
		contentPane.add(startTimeLabel);
		contentPane.add(dayStartLabel);
		contentPane.add(meetingLengthLabel);
		contentPane.add(dayRepeatLabel);

		// @part JTextField setup
		meetingNameTextField = new JTextField();
		hourLengthTextField = new JTextField();
		minuteLengthTextField = new JTextField();
		hourStartTextField = new JTextField();
		minuteStartTextField = new JTextField();
		
		meetingNameTextField.setBounds(168, 7, 239, 46);
		contentPane.add(meetingNameTextField);
		meetingNameTextField.setColumns(10);

		hourLengthTextField.setBounds(291, 134, 53, 46);
		contentPane.add(hourLengthTextField);
		hourLengthTextField.setColumns(10);

		minuteLengthTextField.setColumns(10);
		minuteLengthTextField.setBounds(354, 134, 53, 46);
		contentPane.add(minuteLengthTextField);

		hourStartTextField.setColumns(10);
		hourStartTextField.setBounds(291, 62, 53, 46);
		contentPane.add(hourStartTextField);

		minuteStartTextField.setColumns(10);
		minuteStartTextField.setBounds(354, 62, 53, 46);
		contentPane.add(minuteStartTextField);

		// @part JRadioButton setup
		oneTimeMeetingRadioButton = new JRadioButton("Chỉ Diễn Ra Một Lần");
		oneTimeMeetingRadioButton.setBounds(126, 214, 179, 21);
		oneTimeMeetingRadioButton.addActionListener(e -> {
			if (oneTimeMeetingRadioButton.isSelected()) {
				weeklyMeetingRadioButton.setSelected(false);
				dayDate.setEnabled(true);
				monthDate.setEnabled(true);
				yearDate.setEnabled(true);
				mondayBox.setEnabled(false);
				tuesdayBox.setEnabled(false);
				wednesdayBox.setEnabled(false);
				thursdayBox.setEnabled(false);
				fridayBox.setEnabled(false);
				saturdayBox.setEnabled(false);
				sundayBox.setEnabled(false);
			} else {
				mondayBox.setEnabled(true);
				tuesdayBox.setEnabled(true);
				wednesdayBox.setEnabled(true);
				thursdayBox.setEnabled(true);
				fridayBox.setEnabled(true);
				saturdayBox.setEnabled(true);
				sundayBox.setEnabled(true);
			}
		});
		contentPane.add(oneTimeMeetingRadioButton);
		
		weeklyMeetingRadioButton = new JRadioButton("Diễn Ra Hàng Tuần");
		weeklyMeetingRadioButton.setBounds(126, 250, 179, 21);
		weeklyMeetingRadioButton.addActionListener(e -> {
			if (weeklyMeetingRadioButton.isSelected()) {
				oneTimeMeetingRadioButton.setSelected(false);
				dayDate.setEnabled(false);
				monthDate.setEnabled(false);
				yearDate.setEnabled(false);			
				mondayBox.setEnabled(true);
				tuesdayBox.setEnabled(true);
				wednesdayBox.setEnabled(true);
				thursdayBox.setEnabled(true);
				fridayBox.setEnabled(true);
				saturdayBox.setEnabled(true);
				sundayBox.setEnabled(true);
			} else {
				dayDate.setEnabled(true);
				monthDate.setEnabled(true);
				yearDate.setEnabled(true);
			}
		});
		contentPane.add(weeklyMeetingRadioButton);
		
		// @part JComboBox setup
		dayDate = new JComboBox<Integer>();
		monthDate = new JComboBox<Integer>();
		yearDate = new JComboBox<Integer>();
		
		dayDate.addItem(0);
		monthDate.addItem(0);
		yearDate.addItem(0);

		dayDate.setBounds(135, 324, 53, 46);
		if (dayDate.getItemCount() == 1)
			for (Integer i = 1; i <= 31; i++)
				dayDate.addItem(i);
		contentPane.add(dayDate);

		monthDate.setBounds(228, 324, 53, 46);
		if (monthDate.getItemCount() == 1)
			for (Integer i = 1; i <= 12; i++)
				monthDate.addItem(i);
		contentPane.add(monthDate);

		yearDate.setBounds(330, 324, 53, 46);
		if (yearDate.getItemCount() == 1)
			for (Integer i = 2021; i <= 2121; i++)
				yearDate.addItem(i);
		contentPane.add(yearDate);

		// @part JCheckBox setup
		mondayBox = new JCheckBox("Thứ Hai");
		tuesdayBox = new JCheckBox("Thứ Ba");
		wednesdayBox = new JCheckBox("Thứ Tư");
		thursdayBox = new JCheckBox("Thứ Năm");
		fridayBox = new JCheckBox("Thứ Sáu");
		saturdayBox = new JCheckBox("Thứ Bảy");
		sundayBox = new JCheckBox("Chủ Nhật");
		
		mondayBox.setBounds(80, 392, 93, 21);
		contentPane.add(mondayBox);

		tuesdayBox.setBounds(183, 392, 93, 21);
		contentPane.add(tuesdayBox);

		wednesdayBox.setBounds(280, 392, 93, 21);
		contentPane.add(wednesdayBox);

		thursdayBox.setBounds(80, 421, 93, 21);
		contentPane.add(thursdayBox);

		fridayBox.setBounds(183, 421, 93, 21);
		contentPane.add(fridayBox);

		saturdayBox.setBounds(280, 421, 93, 21);
		contentPane.add(saturdayBox);

		sundayBox.setBounds(80, 450, 93, 21);
		contentPane.add(sundayBox);

		// @part JButton setup
		JButton createMeetingButton = new JButton("Tạo cuộc họp");
		createMeetingButton.setBounds(360, 506, 116, 46);
		createMeetingButton.addActionListener(e -> {
				updateInputMeetingData();				
				try {
					String meetingDataString = covertMeetingDataToString(
							meetingName,
							timeStartPoint, 
							meetingLengthValue, 
							dayStartDate, 
							daysInWeekHaveMeeting
					);
					
					String meeting_id = Client.create_meeting(meetingDataString);
					if (meeting_id != null) {
						Meeting_creator_notify_interface.set_meeting_ID(meeting_id);
						Meeting_creator_notify_interface.create_new_window();
						FileTool.write_file(meetingDataString, MEETING_CREATED_FOLDER_PATH + meeting_id + ".txt");
					}
				} catch (Exception e1) {}
				dispose();
			});
		contentPane.add(createMeetingButton);

		JButton cancelButton = new JButton("Huỷ");
		cancelButton.setBounds(228, 506, 116, 47);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
	}

	private static void updateInputMeetingData() {
		meetingName = meetingNameTextField.getText();
		timeStartPoint = new Point();
		timeStartPoint.x = Integer.valueOf(hourStartTextField.getText());
		timeStartPoint.y = Integer.valueOf(minuteStartTextField.getText());
		
		meetingLengthValue = Integer.valueOf(hourLengthTextField.getText()) * 60;
		meetingLengthValue += Integer.valueOf(minuteLengthTextField.getText());
		
		dayStartDate = "";
		if (oneTimeMeetingRadioButton.isSelected()) {
			dayStartDate = dayDate.getSelectedItem() + " ";
			dayStartDate += monthDate.getSelectedItem() + " ";
			dayStartDate += yearDate.getSelectedItem();
		} else dayStartDate = null;
		
		if (weeklyMeetingRadioButton.isSelected()) {
			daysInWeekHaveMeeting = new ArrayList<Integer>();
			if (mondayBox.isSelected()) daysInWeekHaveMeeting.add(2);
			if (tuesdayBox.isSelected()) daysInWeekHaveMeeting.add(3);
			if (wednesdayBox.isSelected()) daysInWeekHaveMeeting.add(4);
			if (thursdayBox.isSelected()) daysInWeekHaveMeeting.add(5);
			if (fridayBox.isSelected()) daysInWeekHaveMeeting.add(6);
			if (saturdayBox.isSelected()) daysInWeekHaveMeeting.add(7);
			if (sundayBox.isSelected()) daysInWeekHaveMeeting.add(8);
		}
		else daysInWeekHaveMeeting = null;
	}
	
	private static String covertMeetingDataToString(String meetingName, Point timeStartPoint, int meetingLength, String dayStartDate, List<Integer> daysInWeekHaveMeeting) {
		String meeting_data_string = "";
		
		meeting_data_string += meetingName + '\n';
		meeting_data_string += String.valueOf(timeStartPoint.x) + " " + String.valueOf(timeStartPoint.y) + '\n';
		meeting_data_string += String.valueOf(meetingLength) + '\n';
		if (dayStartDate == null) meeting_data_string += "null" + '\n';
		else meeting_data_string += dayStartDate + '\n';
		if (daysInWeekHaveMeeting == null) meeting_data_string += "null" + '\n';
		else {
			for (int i = 0; i < daysInWeekHaveMeeting.size(); i ++) {
				meeting_data_string += daysInWeekHaveMeeting.get(i) + " ";
			}
			meeting_data_string += '\n';
		}
		
		return meeting_data_string;
	}
}
