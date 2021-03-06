package user_interface;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
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
import user_interface.login_and_register.Login_interface;
import user_interface.manager.Created_meeting_interface;
import user_interface.manager.Joined_meeting_interface;
import user_interface.meeting_creator_and_joiner.Meeting_creator_interface;
import user_interface.meeting_creator_and_joiner.Meeting_joiner_interface;

public class Main_interface extends JFrame {
	private static final String APPLICATION_NAME = "Online Meeting Manager";
	private static final String IS_LOGINED_FILE_PATH = "src/main/resources/config/is_logined"; 
	private static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	private static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	private static final String ACCOUNT_USER_NAME_FILE_PATH = "src/main/resources/account/account_username";
	private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	private static final String BUG_REPORT_LINK = "https://paste.ubuntu.com/p/GkfmsZXfFs/";
	private static final String HELP_LINK = "https://paste.ubuntu.com/p/HQTpGc6yxB/";

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
		setTitle(APPLICATION_NAME);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		stateLabel = new JLabel("Kh??ng c?? cu???c h???p n??o ??ang di???n ra");
		stateLabel.setBounds(49, 129, 377, 36);
		contentPane.add(stateLabel);
		
		// @part JMenu setup 
		
		JMenuBar menuBar = new JMenuBar();  
		JMenu notificationMenu, featureMenu, settingMenu, helplMenu;
		JMenuItem createMeetingMenuItem, joinMeetingMenuItem, createdMeetingMenuItem, joinedMeetingMenuItem;
		JMenuItem changeAccountInfomationMenuItem, changeStoragePathMenuItem, changePasswordMenuItem; 
		JMenuItem helpingMenuItem, bugReportMenuItem;
		JButton logOutButton;
		
		notificationMenu = new JMenu("Th??ng b??o m???i");
		featureMenu = new JMenu("T??nh n??ng");
		settingMenu = new JMenu("C??i ?????t");
		helplMenu = new JMenu("Tr??? gi??p");
		logOutButton = new JButton("????ng Xu???t");
		
		createMeetingMenuItem = new JMenuItem("T???o cu???c h???p");
		joinMeetingMenuItem = new JMenuItem("Tham gia cu???c h???p");
		createdMeetingMenuItem = new JMenuItem("Cu???c h???p ???? t???o");
		joinedMeetingMenuItem = new JMenuItem("Cu???c h???p ???? tham gia");
		changeAccountInfomationMenuItem = new JMenuItem("S???a th??ng tin t??i kho???n");
		changeStoragePathMenuItem = new JMenuItem("L??u tr??? cu???c h???p");
		changePasswordMenuItem = new JMenuItem("?????i m???t kh???u");
		helpingMenuItem = new JMenuItem("Tr??? gi??p");
		bugReportMenuItem = new JMenuItem("B??o l???i");
		
		for (int i = 0; i < notificationMenuItem.size(); i ++) {
			notificationMenu.add(notificationMenuItem.get(i));
		}
		
		featureMenu.add(createMeetingMenuItem);
		featureMenu.add(joinMeetingMenuItem);
		featureMenu.add(createdMeetingMenuItem);
		featureMenu.add(joinedMeetingMenuItem);
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

		helpingMenuItem.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URL(HELP_LINK).toURI());
			} catch (Exception e1) {}
		});

		bugReportMenuItem.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URL(BUG_REPORT_LINK).toURI());
			} catch (Exception e1) {}
		});
	}
}
