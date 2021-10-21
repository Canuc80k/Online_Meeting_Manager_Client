package user_interface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import app_activity.App_activity_analyst;
import general_function.FileTool;
import init.Font_init;
import javafx.util.Pair;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class Joined_meeting_interface extends JFrame {
	public static final String JOINED_MEETING_FILE_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);
	public static final Dimension BUTTON_SIZE = new Dimension(400, 150);
	
	private JPanel contentPane;
	public static JPanel panel = new JPanel();
	public static Joined_meeting_interface frame;
	
	private static List<Pair<String, String>> created_meeting_list;
	private static List<JButton> created_meeting_button;
	
	private static String focused_meeting_id;
	
	public static void create_new_window() throws Exception {
		init();
		frame = new Joined_meeting_interface();
		frame.setVisible(true);
	}

	public Joined_meeting_interface() {
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

		JLabel title = new JLabel("Cuộc Họp Đã Tham Gia");
		title.setBounds(25, 10, 349, 84);
		title.setFont(Font_init.SanFranciscoText_Bold.deriveFont(20f));
		contentPane.add(title);
		
		JButton moreDetailButton = new JButton("Chi Tiết Cuộc Họp");
		moreDetailButton.setBounds(399, 18, 177, 68);
		moreDetailButton.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		moreDetailButton.addActionListener(e -> {
			try {
				Meeting_information_viewer_interface.create_new_window(focused_meeting_id);
			} catch (Exception e1) {}
		});
		contentPane.add(moreDetailButton);
		
		JButton statisticButton = new JButton("🔬");
		statisticButton.setFont(statisticButton.getFont().deriveFont(Font.BOLD));
		statisticButton.setBounds(307, 18, 82, 68);
		statisticButton.addActionListener(e -> {
			try {
				new App_activity_analyst("JOINED_MEETING", focused_meeting_id);
				dispose();
				Notify_interface.create_new_window("Phân Tích Dữ Liệu Của Người Dùng Có ID " + focused_meeting_id + " Thành Công");
				
			} catch (Exception e1) {}
		});
		contentPane.add(statisticButton);
	}
	
	public static void init() throws Exception {
		created_meeting_list = new ArrayList<Pair<String, String>>();
		created_meeting_button = new  ArrayList<JButton>();
		
		File[] files = new File(JOINED_MEETING_FILE_PATH).listFiles();

		for (File file : files) {
			String file_name = file.getName();
			String file_data = FileTool.read_file(JOINED_MEETING_FILE_PATH + file_name + "/meeting_information");
			created_meeting_list.add(new Pair<String, String>(file_name, file_data));
			created_meeting_button.add(create_created_meeting_button(file_name, file_data));
		}
	}
	
	public static JButton create_created_meeting_button(String meeting_id, String meeting_data) {
		JButton button = new JButton();
		button.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		List<String> meeting_data_list = Arrays.asList(meeting_data.split("\n"));
		String button_text = "";
		button_text += meeting_data_list.get(0) + "\n";
		button_text += "ID: " + meeting_id;
		button.setText("<html>" + button_text.replaceAll("\n", "<br>") + "</html>");
		button.setPreferredSize(BUTTON_SIZE);

		button.addActionListener(e -> {
			focused_meeting_id = meeting_id;
		});
		
		return button;
	}
}
