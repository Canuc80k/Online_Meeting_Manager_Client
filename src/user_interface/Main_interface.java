package user_interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import general_function.FileTool;

@SuppressWarnings("serial")
public class Main_interface extends JFrame {
	public static final String IS_LOGINED_FILE_PATH = "config/is_logined.txt"; 
	
	private JPanel contentPane;
	private static List<JMenuItem> notificationMenuItem;
	
	private static void init() {
		notificationMenuItem = new ArrayList<JMenuItem>();
	}
	
	public static void create_new_window() {
		init();
		Main_interface frame = new Main_interface();
		frame.setVisible(true);
	}
	
	public Main_interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();  
		JMenu notificationMenu, featureMenu, settingMenu, helplMenu;
		JMenuItem createMeetingMenuItem, joinMeetingMenuItem, getMeetingDataMenuItem, sendMeetingDataMenuItem;
		JMenuItem setAccountInfomationMenuItem, changePasswordMenuItem; 
		JMenuItem helpingMenuItem, bugReportMenuItem;
		JButton logOutButton;
		
		notificationMenu = new JMenu("Thông báo mới");
		featureMenu = new JMenu("Tính năng");
		settingMenu = new JMenu("Cài đặt");
		helplMenu = new JMenu("Trợ giúp");
		logOutButton = new JButton("Đăng Xuất");
		logOutButton.addActionListener(e -> {
			try {
				FileTool.write_file("false", IS_LOGINED_FILE_PATH);
				Login_interface.create_new_window();
				dispose();
			} catch (Exception e1) {}
		});
		
		createMeetingMenuItem = new JMenuItem("Tạo cuộc họp");
		joinMeetingMenuItem = new JMenuItem("Tham gia cuộc họp");
		getMeetingDataMenuItem = new JMenuItem("Xem thông tin cuộc họp");
		sendMeetingDataMenuItem = new JMenuItem("Gửi thông tin cuộc họp");
		setAccountInfomationMenuItem = new JMenuItem("Sửa thông tin tài khoản");
		changePasswordMenuItem = new JMenuItem("Đổi mật khẩu");
		helpingMenuItem = new JMenuItem("Trợ giúp");
		bugReportMenuItem = new JMenuItem("Báo lỗi");
		
		for (int i = 0; i < notificationMenuItem.size(); i ++) {
			notificationMenu.add(notificationMenuItem.get(i));
		}
		
		featureMenu.add(createMeetingMenuItem);
		featureMenu.add(joinMeetingMenuItem);
		featureMenu.add(getMeetingDataMenuItem);
		featureMenu.add(sendMeetingDataMenuItem);
		settingMenu.add(setAccountInfomationMenuItem);
		settingMenu.add(changePasswordMenuItem);
		helplMenu.add(helpingMenuItem);
		helplMenu.add(bugReportMenuItem);
		
		menuBar.add(notificationMenu);
		menuBar.add(featureMenu);
		menuBar.add(settingMenu);
		menuBar.add(helplMenu);
		menuBar.add(logOutButton);
		setJMenuBar(menuBar);
	}
}
