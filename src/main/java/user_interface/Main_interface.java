package user_interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import general_function.FileTool;
import init.Font_init;
import meeting.Meeting_thread;
import user_interface.login_and_register.Login_interface;
import user_interface.manager.Created_meeting_interface;
import user_interface.manager.Joined_meeting_interface;
import user_interface.meeting_creator_and_joiner.Meeting_creator_interface;
import user_interface.meeting_creator_and_joiner.Meeting_joiner_interface;

@SuppressWarnings("serial")
public class Main_interface extends JFrame {
	private static final String IS_LOGINED_FILE_PATH = "src/main/resources/config/is_logined"; 
	private static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	private static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	private static final String ACCOUNT_USER_NAME_FILE_PATH = "src/main/resources/account/account_username";
	private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	
	private JPanel contentPane;
	public static Main_interface frame;
	public static JLabel stateLabel;
	private static List<JMenuItem> notificationMenuItem;
	
	private static void init() {
		notificationMenuItem = new ArrayList<JMenuItem>();
	}
    
	public static void create_new_window() throws Exception {
		init();
		frame = new Main_interface();
		frame.setVisible(true);
	}
    
	public Main_interface() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// @part JLabel setup
		JLabel nameLabel = new JLabel();
		nameLabel.setText(FileTool.read_file(ACCOUNT_USER_NAME_FILE_PATH));
		nameLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(30f));
		nameLabel.setForeground(new Color(164,	189	,218));
		nameLabel.setBounds(49, 29, 185, 36);
		contentPane.add(nameLabel);
		
		JLabel idLabel = new JLabel();
		idLabel.setText("ID: " + FileTool.read_file(ACCOUNT_ID_FILE_PATH));
		idLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(18f));
		idLabel.setBounds(49, 79, 185, 36);
		contentPane.add(idLabel);
		
		stateLabel = new JLabel("Không có cuộc họp nào đang diễn ra");
		stateLabel.setBounds(49, 129, 377, 36);
		contentPane.add(stateLabel);
		
		// @part JMenu setup 
		
		JMenuBar menuBar = new JMenuBar();  
		JMenu notificationMenu, featureMenu, settingMenu, helplMenu;
		JMenuItem createMeetingMenuItem, joinMeetingMenuItem, createdMeetingMenuItem, joinedMeetingMenuItem, sendMeetingDataMenuItem;
		JMenuItem changeAccountInfomationMenuItem, changeStoragePathMenuItem, changePasswordMenuItem; 
		JMenuItem helpingMenuItem, bugReportMenuItem;
		JButton logOutButton;
		
		notificationMenu = new JMenu("Thông báo mới");
		featureMenu = new JMenu("Tính năng");
		settingMenu = new JMenu("Cài đặt");
		helplMenu = new JMenu("Trợ giúp");
		logOutButton = new JButton("Đăng Xuất");
		
		createMeetingMenuItem = new JMenuItem("Tạo cuộc họp");
		joinMeetingMenuItem = new JMenuItem("Tham gia cuộc họp");
		createdMeetingMenuItem = new JMenuItem("Cuộc họp đã tạo");
		joinedMeetingMenuItem = new JMenuItem("Cuộc họp đã tham gia");
		sendMeetingDataMenuItem = new JMenuItem("Gửi thông tin cuộc họp");
		changeAccountInfomationMenuItem = new JMenuItem("Sửa thông tin tài khoản");
		changeStoragePathMenuItem = new JMenuItem("Lưu trữ cuộc họp");
		changePasswordMenuItem = new JMenuItem("Đổi mật khẩu");
		helpingMenuItem = new JMenuItem("Trợ giúp");
		bugReportMenuItem = new JMenuItem("Báo lỗi");
		
		for (int i = 0; i < notificationMenuItem.size(); i ++) {
			notificationMenu.add(notificationMenuItem.get(i));
		}
		
		featureMenu.add(createMeetingMenuItem);
		featureMenu.add(joinMeetingMenuItem);
		featureMenu.add(createdMeetingMenuItem);
		featureMenu.add(joinedMeetingMenuItem);
		featureMenu.add(sendMeetingDataMenuItem);
		settingMenu.add(changeAccountInfomationMenuItem);
		settingMenu.add(changeStoragePathMenuItem);
		settingMenu.add(changePasswordMenuItem);
		helplMenu.add(helpingMenuItem);
		helplMenu.add(bugReportMenuItem);
		
		menuBar.add(notificationMenu);
		menuBar.add(featureMenu);
		menuBar.add(settingMenu);
		menuBar.add(helplMenu);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(logOutButton);
		
		menuBar.setFont(Font_init.SanFranciscoText_Medium.deriveFont(12f));
		setJMenuBar(menuBar);
		
		createMeetingMenuItem.addActionListener(e -> {
			Meeting_creator_interface.create_new_window();
		});
		
		joinMeetingMenuItem.addActionListener(e -> {
			Meeting_joiner_interface.create_new_window();
		});
		
		createdMeetingMenuItem.addActionListener(e -> {
			try {Created_meeting_interface.create_new_window();} catch (Exception e1) {}
		});
		
		joinedMeetingMenuItem.addActionListener(e -> {
			try {Joined_meeting_interface.create_new_window();} catch (Exception e1) {}
		});
		
		changeStoragePathMenuItem.addActionListener(e -> {
			Change_storage_path_interface.create_new_window();
		});
		
		changeAccountInfomationMenuItem.addActionListener(e -> {
			try {Change_account_information_interface.create_new_window();} catch (Exception e1) {}
		});
		
		logOutButton.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				try {
					FileTool.deleteFolder(new File(MEETING_JOINED_FOLDER_PATH));
					FileTool.deleteFolder(new File(MEETING_CREATED_FOLDER_PATH));
					FileTool.write_file("false", IS_LOGINED_FILE_PATH);
					Login_interface.create_new_window();
					dispose();
				} catch (Exception e1) {}
			}
		});
	}
}
