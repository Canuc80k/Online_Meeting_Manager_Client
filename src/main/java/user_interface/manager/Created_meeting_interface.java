package user_interface.manager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import app_activity.App_activity_analyst;
import client.Client;
import general_function.FileTool;
import init.Font_init;
import javafx.util.Pair;
import user_interface.Notify_interface;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class Created_meeting_interface extends JFrame {
	public static final String CREATED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final Dimension BUTTON_SIZE = new Dimension(400, 150);
	private static final String JOINER_APP_ACTIVITY_SPLIT_SIGNAL = "\n~!~#@~\n";
	
	private JPanel contentPane;
	public static JPanel panel = new JPanel();
	public static Created_meeting_interface frame;
	
	private static List<Pair<String, String>> created_meeting_list;
	private static List<JButton> created_meeting_button;
	
	private static String focused_meeting_id;
	
	public static void create_new_window() throws Exception {
		if (!(new File(CREATED_MEETING_FOLDER_PATH)).exists()) new File(CREATED_MEETING_FOLDER_PATH).mkdirs();
		
		init();
		frame = new Created_meeting_interface();
		frame.setVisible(true);
	}

	public Created_meeting_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// @part ScrollPane setup
		panel = new JPanel();
		panel.setLayout(new GridLayout(created_meeting_list.size(), 1));

		for (int i = 0; i < created_meeting_button.size(); i++)
			panel.add(created_meeting_button.get(i));
		panel.revalidate();
		panel.repaint();
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(25, 115, 551, 438);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		JLabel title = new JLabel("Cuộc Họp Đã Tạo");
		title.setBounds(25, 10, 177, 84);
		title.setFont(Font_init.SanFranciscoText_Bold.deriveFont(20f));
		contentPane.add(title);
		
		JButton moreDetailButton = new JButton("Chi Tiết Cuộc Họp");
		moreDetailButton.setBounds(399, 18, 177, 68);
		moreDetailButton.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		moreDetailButton.addActionListener(e -> {
			try {
				Meeting_information_changer_interface.create_new_window(focused_meeting_id);
			} catch (Exception e1) {}
		});
		contentPane.add(moreDetailButton);
		
		JButton statisticButton = new JButton("⬇️ + 🔬");
		statisticButton.setFont(statisticButton.getFont().deriveFont(Font.BOLD));
		statisticButton.setBounds(321, 18, 68, 68);
		statisticButton.addActionListener(e -> {
			try {
				get_and_storage_joiner_app_activity(focused_meeting_id);
				new App_activity_analyst("CREATED_MEETING", focused_meeting_id);
				dispose();
				Notify_interface.create_new_window("Tải Và Phân Tích Dữ Liệu Của Người Dùng Có ID " + focused_meeting_id + " Thành Công");
			} catch (Exception e1) {}
		});
		contentPane.add(statisticButton);
	}
	
	public static void init() throws Exception {
		created_meeting_list = new ArrayList<Pair<String, String>>();
		created_meeting_button = new ArrayList<JButton>();
		
		File[] files = new File(CREATED_MEETING_FOLDER_PATH).listFiles();

		for (File file : files) {
			String file_name = file.getName().trim();
			String file_data = FileTool.read_file(CREATED_MEETING_FOLDER_PATH + file_name + "/meeting_information");
			created_meeting_list.add(new Pair<String, String>(file_name, file_data));
			created_meeting_button.add(create_created_meeting_button(file_name, file_data));
		}
	}
	
	private static JButton create_created_meeting_button(String meeting_id, String meeting_data) {
		JButton button = new JButton();
		button.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		List<String> meeting_data_list = Arrays.asList(meeting_data.split("\n"));
		String button_text = "";
		button_text += meeting_data_list.get(3) + "\n";
		button_text += "ID: " + meeting_id;
		button.setText("<html>" + button_text.replaceAll("\n", "<br>") + "</html>");
		button.setPreferredSize(BUTTON_SIZE);
		
		button.addActionListener(e -> {
			focused_meeting_id = meeting_id;
		});
		
		return button;
	}
	
	private static void get_and_storage_joiner_app_activity(String meeting_id) {
		try {
			String all_joiner_app_activity_data = Client.get_joiner_app_activity(meeting_id);
			if (!all_joiner_app_activity_data.equals("FAIL_TO_GET")) {
				String meeting_joiner_app_activity_data_file_path = CREATED_MEETING_FOLDER_PATH + meeting_id + "/joiner_app_activity/";
				File file = new File(meeting_joiner_app_activity_data_file_path);
				if (file.exists()) {
					
				} else {
					file.mkdirs();
					List<String> all_joiner_app_activity_data_list = Arrays.asList(all_joiner_app_activity_data.split(JOINER_APP_ACTIVITY_SPLIT_SIGNAL));
					
					for (int i = 0; i < all_joiner_app_activity_data_list.size(); i ++) {
						List<String> joiner_app_activity_data_list = Arrays.asList(all_joiner_app_activity_data_list.get(i).split("\n"));
						String file_name = joiner_app_activity_data_list.get(0);
						String file_data = "";
						for (int j = 1; j < joiner_app_activity_data_list.size(); j ++) {
							file_data += joiner_app_activity_data_list.get(j) + '\n';
						}
						FileTool.write_file(file_data, meeting_joiner_app_activity_data_file_path + file_name);
					}
				}
			}
		} catch (Exception e1) {}
	}
}
