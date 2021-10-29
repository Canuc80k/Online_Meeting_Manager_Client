package user_interface.meeting_creator_and_joiner.meeting_creator_by_type;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import init.Font_init;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class Meeting_weekly_sametime_type_creator extends JFrame {
	public static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;

	private static JLabel meetingNameLabel;
	private static JLabel startTimeLabel;
	private static JLabel finishTimeLabel;
	private static JLabel repeatDayLabel;
	
	private static JTextField meetingNameTextField;
	private static JTextField hourLengthTextField;
	private static JTextField minuteLengthTextField;
	private static JTextField hourStartTextField;
	private static JTextField minuteStartTextField;
	
	private static JCheckBox mondayBox;
	private static JCheckBox tuesdayBox;
	private static JCheckBox wednesdayBox;
	private static JCheckBox thursdayBox;
	private static JCheckBox fridayBox;
	private static JCheckBox saturdayBox;
	private static JCheckBox sundayBox;
	
	public static void create_new_window() {
		if (!(new File(MEETING_CREATED_FOLDER_PATH)).exists()) new File(MEETING_CREATED_FOLDER_PATH).mkdirs();
		
		Meeting_weekly_sametime_type_creator frame = new Meeting_weekly_sametime_type_creator();
		frame.setVisible(true);
	}

	public Meeting_weekly_sametime_type_creator() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 550);
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
		repeatDayLabel = new JLabel("Lặp Lại Vào Các Ngày");
		
		meetingNameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		startTimeLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		finishTimeLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		repeatDayLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(16f));
		meetingNameLabel.setBounds(60, 26, 129, 52);
		startTimeLabel.setBounds(60, 90, 179, 48);
		finishTimeLabel.setBounds(60, 151, 179, 48);
		repeatDayLabel.setBounds(60, 229, 179, 48);
		contentPane.add(meetingNameLabel);
		contentPane.add(startTimeLabel);
		contentPane.add(finishTimeLabel);
		contentPane.add(repeatDayLabel);

		// @part JTextField setup
		meetingNameTextField = new JTextField();
		hourLengthTextField = new JTextField();
		minuteLengthTextField = new JTextField();
		hourStartTextField = new JTextField();
		minuteStartTextField = new JTextField();
		
		meetingNameTextField.setBounds(330, 30, 239, 46);
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
		
		mondayBox.setBounds(330, 238, 100, 30);
		contentPane.add(mondayBox);

		tuesdayBox.setBounds(430, 238, 100, 30);
		contentPane.add(tuesdayBox);

		wednesdayBox.setBounds(530, 238, 100, 30);
		contentPane.add(wednesdayBox);

		thursdayBox.setBounds(330, 278, 100, 30);
		contentPane.add(thursdayBox);

		fridayBox.setBounds(430, 278, 100, 30);
		contentPane.add(fridayBox);

		saturdayBox.setBounds(530, 278, 100, 30);
		contentPane.add(saturdayBox);

		sundayBox.setBounds(330, 318, 100, 30);
		contentPane.add(sundayBox);
		
		// @part JButton setup
		JButton createMeetingButton = new JButton("Tạo cuộc họp");
		createMeetingButton.setBounds(437, 388, 158, 80);
		createMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		createMeetingButton.addActionListener(e -> {	

			});
		contentPane.add(createMeetingButton);

		JButton cancelButton = new JButton("Huỷ");
		cancelButton.setBounds(347, 388, 80, 80);
		cancelButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(cancelButton);
	}
}
