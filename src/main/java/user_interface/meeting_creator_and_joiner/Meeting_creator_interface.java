package user_interface.meeting_creator_and_joiner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import init.Font_init;
import user_interface.Notify_interface;
import user_interface.meeting_creator_and_joiner.meeting_creator_by_type.Meeting_onetime_type_creator;
import user_interface.meeting_creator_and_joiner.meeting_creator_by_type.Meeting_weekly_sametime_type_creator;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Meeting_creator_interface extends JFrame {
	public static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;
	
	public static void create_new_window() {
		if (!(new File(MEETING_CREATED_FOLDER_PATH)).exists()) new File(MEETING_CREATED_FOLDER_PATH).mkdirs();
		
		Meeting_creator_interface frame = new Meeting_creator_interface();
		frame.setVisible(true);
	}

	public Meeting_creator_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel typeChooserLabel = new JLabel("Chọn Loại Cuộc Họp Cần Tạo");
		typeChooserLabel.setBounds(100, 10, 300, 60);
		typeChooserLabel.setFont(Font_init.SanFranciscoText_Bold.deriveFont(22f));
		contentPane.add(typeChooserLabel);
		
		JButton onetime_button = new JButton("Một Lần");
		onetime_button.setBounds(100, 100, 300, 60);
		onetime_button.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		onetime_button.addActionListener(e -> {
			Meeting_onetime_type_creator.create_new_window();
			dispose();
		});
		contentPane.add(onetime_button);
		
		JButton weekly_sametime_button = new JButton("Hàng Tuần Loại 1");
		weekly_sametime_button.setBounds(100, 170, 300, 60);
		weekly_sametime_button.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		weekly_sametime_button.addActionListener(e -> {
			Meeting_weekly_sametime_type_creator.create_new_window();
			dispose();
		});
		contentPane.add(weekly_sametime_button);
		
		JButton weekly_difftime_button = new JButton("Hàng Tuần Loại 2");
		weekly_difftime_button.setBounds(100, 240, 300, 60);
		weekly_difftime_button.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		contentPane.add(weekly_difftime_button);
		
		JButton weekly_multip_time_button = new JButton("Tuỳ Chỉnh");
		weekly_multip_time_button.setBounds(100, 310, 300, 60);
		weekly_multip_time_button.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		contentPane.add(weekly_multip_time_button);
		
		JLabel question1Label = new JLabel("?");
		question1Label.setBounds(420, 194, 20, 20);
		question1Label.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		question1Label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Notify_interface.create_new_window("Là Loại Cuộc Họp Mà Có Các Khoảng Thời Gian Như Nhau Trong Các Ngày Diễn Ra");
			}
			
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(question1Label);
		
		JLabel question2Label = new JLabel("?");
		question2Label.setBounds(420, 260, 20, 20);
		question2Label.setFont(Font_init.SanFranciscoText_Bold.deriveFont(15f));
		question2Label.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Notify_interface.create_new_window("Là Loại Cuộc Họp Mà Có Các Khoảng Thời Gian Khác Nhau Trong Các Ngày Diễn Ra");
			}
			
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		contentPane.add(question2Label);

	}
}
