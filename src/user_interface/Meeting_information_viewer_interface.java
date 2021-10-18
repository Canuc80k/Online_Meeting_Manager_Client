package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import init.Font_init;
import meeting.Meeting_handler;
import storage.Storage_statistic;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Meeting_information_viewer_interface extends JFrame {
	public static final String MEETING_JOINED_FOLDER_PATH = "meeting/meeting_joined/";
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
	
	private static String meeting_id;
	
	public static void create_new_window(String meeting_id) {
		if (!(new File(MEETING_JOINED_FOLDER_PATH)).exists()) new File(MEETING_JOINED_FOLDER_PATH).mkdirs();
		
		Meeting_information_viewer_interface.meeting_id = meeting_id;
		Meeting_information_viewer_interface frame = new Meeting_information_viewer_interface();
		init(meeting_id);
		frame.setVisible(true);
	}
	
	public Meeting_information_viewer_interface() {
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
		
		JLabel statisticLabel = new JLabel("Đi Đến Thư Mục Thống Kê");
		statisticLabel.setBounds(60, 568, 244, 60);
		Map<TextAttribute, Object> attributes = new HashMap<>(Font_init.SanFranciscoText_Medium.getAttributes());
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		statisticLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(attributes));
		statisticLabel.setFont(statisticLabel.getFont().deriveFont(15f));
		statisticLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				try {Storage_statistic.show_my_statistic_folder(meeting_id);} 
				catch (Exception e1) {}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(statisticLabel);
	}
	
	private static void init(String meeting_id) {
		String meeting_information = Meeting_handler.getMeetingInfomation(meeting_id);
		List<String> meeting_information_list = Arrays.asList(meeting_information.split("\n"));
		
		meetingNameTextField.setText(meeting_information_list.get(0));
		hourStartTextField.setText(Arrays.asList(meeting_information_list.get(1).split(" ")).get(0));
		minuteStartTextField.setText(Arrays.asList(meeting_information_list.get(1).split(" ")).get(1));
		
		int meeting_length = Integer.parseInt(meeting_information_list.get(2));
		hourLengthTextField.setText(String.valueOf(meeting_length / 60));
		minuteLengthTextField.setText(String.valueOf(meeting_length % 60));
		
		String dayStartDateString = meeting_information_list.get(3);
		String daysInWeekHaveMeetingString = meeting_information_list.get(4);
		
		if (!daysInWeekHaveMeetingString.equals("null")) {
			weeklyMeetingRadioButton.setSelected(true);
			dayDate.setEnabled(false);
			monthDate.setEnabled(false);
			yearDate.setEnabled(false);		
			
			List<String> daysInWeekHaveMeetingList = Arrays.asList(daysInWeekHaveMeetingString.split(" "));
			for (int i = 0; i < daysInWeekHaveMeetingList.size(); i ++) {
				if (daysInWeekHaveMeetingList.get(i).equals("2")) mondayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("3")) tuesdayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("4")) wednesdayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("5")) thursdayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("6")) fridayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("7")) saturdayBox.setSelected(true);
				if (daysInWeekHaveMeetingList.get(i).equals("8")) sundayBox.setSelected(true);
			}
		}
		
		if (!dayStartDateString.equals("null")) {
			oneTimeMeetingRadioButton.setSelected(true);
			mondayBox.setEnabled(false);
			tuesdayBox.setEnabled(false);
			wednesdayBox.setEnabled(false);
			thursdayBox.setEnabled(false);
			fridayBox.setEnabled(false);
			saturdayBox.setEnabled(false);
			sundayBox.setEnabled(false);
		
			List<String> dayStartDateList = Arrays.asList(dayStartDateString.split(" "));
			Integer day = Integer.parseInt(dayStartDateList.get(0));
			Integer month = Integer.parseInt(dayStartDateList.get(1));
			Integer year = Integer.parseInt(dayStartDateList.get(2));
		
			dayDate.setSelectedItem(day);
			monthDate.setSelectedItem(month);
			yearDate.setSelectedItem(year);
		}
	}
}
