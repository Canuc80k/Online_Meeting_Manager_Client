package user_interface;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
	public static final String JOINED_MEETING_FILE_PATH = "meeting/meeting_joined/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);
	public static final Dimension BUTTON_SIZE = new Dimension(400, 150);
	
	private JPanel contentPane;
	public static JPanel panel = new JPanel();
	
	private static List<Pair<String, String>> created_meeting_list;
	private static List<JButton> created_meeting_button;
	
	public static void create_new_window() throws Exception {
		init();
		Joined_meeting_interface frame = new Joined_meeting_interface();
		frame.setVisible(true);
	}

	public Joined_meeting_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 600);
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

		JLabel title = new JLabel("Những Cuộc Họp Đã Tham Gia");
		title.setBounds(25, 10, 349, 84);
		title.setFont(Font_init.SanFranciscoText_Bold.deriveFont(20f));
		contentPane.add(title);
	}
	
	public static void init() throws Exception {
		created_meeting_list = new ArrayList<Pair<String, String>>();
		created_meeting_button = new  ArrayList<JButton>();
		
		File[] files = new File(JOINED_MEETING_FILE_PATH).listFiles();

		for (File file : files) {
			String file_name = file.getName();
			String file_data = FileTool.read_file(JOINED_MEETING_FILE_PATH + file_name);
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
		button_text += "ID: " + meeting_id.substring(0, meeting_id.length() - 4);
		button.setText("<html>" + button_text.replaceAll("\n", "<br>") + "</html>");
		button.setPreferredSize(BUTTON_SIZE);
		
		return button;
	}
}
