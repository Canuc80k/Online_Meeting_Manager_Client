package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import meeting.Meeting_handler;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
		if (!(new File(MEETING_CREATED_FOLDER_PATH)).exists()) new File(MEETING_CREATED_FOLDER_PATH).mkdirs();
		
		Meeting_creator_interface frame = new Meeting_creator_interface();
		frame.setVisible(true);
	}

	public Meeting_creator_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 700);
		setResizable(false);
		setLocationRelativeTo(null);
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
		
		meetingNameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		startTimeLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		meetingLengthLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		meetingStateLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		dayStartLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		dayRepeatLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		
		meetingStateLabel.setBounds(60, 227, 108, 39);
		contentPane.add(meetingStateLabel);
		meetingNameLabel.setBounds(60, 27, 129, 52);
		startTimeLabel.setBounds(60, 93, 179, 48);
		dayStartLabel.setBounds(60, 312, 129, 39);
		meetingLengthLabel.setBounds(60, 151, 200, 52);
		dayRepeatLabel.setBounds(60, 396, 163, 42);
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
		
		meetingNameTextField.setBounds(330, 31, 239, 46);
		contentPane.add(meetingNameTextField);
		meetingNameTextField.setColumns(10);

		hourLengthTextField.setBounds(330, 155, 60, 50);
		contentPane.add(hourLengthTextField);
		hourLengthTextField.setColumns(10);

		minuteLengthTextField.setColumns(10);
		minuteLengthTextField.setBounds(400, 155, 60, 50);
		contentPane.add(minuteLengthTextField);

		hourStartTextField.setColumns(10);
		hourStartTextField.setBounds(330, 90, 60, 50);
		contentPane.add(hourStartTextField);

		minuteStartTextField.setColumns(10);
		minuteStartTextField.setBounds(400, 90, 60, 50);
		contentPane.add(minuteStartTextField);

		meetingNameTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		hourLengthTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		minuteLengthTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		hourStartTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		minuteStartTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		
		// @part JRadioButton setup
		oneTimeMeetingRadioButton = new JRadioButton("Chỉ Diễn Ra Một Lần");
		oneTimeMeetingRadioButton.setBounds(330, 227, 179, 21);
		oneTimeMeetingRadioButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
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
		weeklyMeetingRadioButton.setBounds(330, 265, 179, 21);
		weeklyMeetingRadioButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
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
		dayDate.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		monthDate.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		yearDate.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		
		dayDate.addItem(0);
		monthDate.addItem(0);
		yearDate.addItem(0);

		dayDate.setBounds(330, 308, 53, 46);
		if (dayDate.getItemCount() == 1)
			for (Integer i = 1; i <= 31; i++)
				dayDate.addItem(i);
		contentPane.add(dayDate);

		monthDate.setBounds(424, 308, 53, 46);
		if (monthDate.getItemCount() == 1)
			for (Integer i = 1; i <= 12; i++)
				monthDate.addItem(i);
		contentPane.add(monthDate);

		yearDate.setBounds(516, 308, 70, 46);
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
		
		mondayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		tuesdayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		wednesdayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		thursdayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		fridayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		saturdayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		sundayBox.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		
		mondayBox.setBounds(330, 400, 100, 30);
		contentPane.add(mondayBox);

		tuesdayBox.setBounds(430, 400, 100, 30);
		contentPane.add(tuesdayBox);

		wednesdayBox.setBounds(530, 400, 100, 30);
		contentPane.add(wednesdayBox);

		thursdayBox.setBounds(330, 440, 100, 30);
		contentPane.add(thursdayBox);

		fridayBox.setBounds(430, 440, 100, 30);
		contentPane.add(fridayBox);

		saturdayBox.setBounds(530, 440, 100, 30);
		contentPane.add(saturdayBox);

		sundayBox.setBounds(330, 480, 100, 30);
		contentPane.add(sundayBox);

		// @part JButton setup
		JButton createMeetingButton = new JButton("Tạo cuộc họp");
		createMeetingButton.setBounds(505, 558, 158, 80);
		createMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		createMeetingButton.addActionListener(e -> {
				updateInputMeetingData();		
				if (!checkInputMeetingCondition()) return;
				try {
					String meetingDataString = Meeting_handler.covertMeetingDataToString(
							meetingName,
							timeStartPoint, 
							meetingLengthValue, 
							dayStartDate, 
							daysInWeekHaveMeeting
					);
					
					String meeting_id = Client.create_meeting(meetingDataString);
					if (meeting_id != null) {
						File file = new File(MEETING_CREATED_FOLDER_PATH + meeting_id);
						if (!file.exists()) file.mkdirs();
						
						FileTool.write_file(meetingDataString, file.getPath() + "/meeting_information");
						Notify_interface.create_new_window("Tạo cuộc họp thành công\nID Cuộc Họp Mới Là: " + meeting_id);
					}
				} catch (Exception e1) {}
				dispose();
			});
		contentPane.add(createMeetingButton);

		JButton cancelButton = new JButton("Huỷ");
		cancelButton.setBounds(400, 558, 80, 80);
		cancelButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
	}
	
	private static boolean checkInputMeetingCondition() {
		boolean result = true;
		
		if (timeStartPoint.y > 60) result = false;
		if (Integer.parseInt(minuteLengthTextField.getText()) > 60) result = false;
		if (dayStartDate != null) {
			List<String> list = Arrays.asList(dayStartDate.split(" "));
			for (int i = 0; i < list.size(); i ++) {
				if (list.get(i).equals("0")) result = false;
			}
		}
		if (daysInWeekHaveMeeting != null) {
			if (daysInWeekHaveMeeting.size() == 0) result = false;
		}
			
		return result;
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
}
