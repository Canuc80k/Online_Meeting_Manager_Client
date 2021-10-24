package user_interface.meeting_creator_and_joiner.meeting_creator_by_type;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import general_function.FileTool;
import init.Font_init;
import meeting.Meeting_handler;
import user_interface.Notify_interface;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Meeting_onetime_type_creator extends JFrame {
	private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	private static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";

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
	
	public static void create_new_window() {
		if (!(new File(MEETING_CREATED_FOLDER_PATH)).exists()) new File(MEETING_CREATED_FOLDER_PATH).mkdirs();
		
		Meeting_onetime_type_creator frame = new Meeting_onetime_type_creator();
		frame.setVisible(true);
	}

	public Meeting_onetime_type_creator() {
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
		JButton createMeetingButton = new JButton("Tạo cuộc họp");
		createMeetingButton.setBounds(438, 331, 158, 80);
		createMeetingButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		createMeetingButton.addActionListener(e -> {
			LocalDate date = LocalDate.of((int)yearBox.getSelectedItem(), (int)monthBox.getSelectedItem(), (int)dayBox.getSelectedItem());
			
			List<List<String>> t = new ArrayList<List<String>>();
			for (int i = 0; i < 7; i ++) t.add(new ArrayList<String>());
			t.get(date.getDayOfWeek().getValue() - 1).add(
					hourStartTextField.getText() + " " +
					minuteStartTextField.getText() + " " +
					hourFinishTextField.getText() + " " +
					minuteFinishTextField.getText() + " "
			);
			
			try {
				String account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH);
				String meetingInformation = Meeting_handler.create_meeting_information(
						"ONE_TIME",
						meetingNameTextField.getText(),
						t,
						"ON",
						account_id
				);

				String meeting_id = Client.create_meeting(meetingInformation);
				if (meeting_id != null) {
					File file = new File(MEETING_CREATED_FOLDER_PATH + meeting_id);
					if (!file.exists()) file.mkdirs();

					FileTool.write_file(meetingInformation, file.getPath() + "/meeting_information");
					Notify_interface.create_new_window("Tạo cuộc họp thành công\nID Cuộc Họp Mới Là: " + meeting_id);
				}
			} catch (Exception e1) {}
			dispose();
		});
		contentPane.add(createMeetingButton);

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
}
