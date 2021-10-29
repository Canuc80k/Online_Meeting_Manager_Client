package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import init.Font_init;

import java.awt.Font;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class Notify_interface extends JFrame {
	public static final Font FONT = new Font("SansSerif", Font.BOLD, 14);

	private JPanel contentPane;
	private static String notify;
	
	public static void create_new_window(String notify) {
		Notify_interface.notify = notify;
		Notify_interface frame = new Notify_interface();
		frame.setVisible(true);
	}

	public Notify_interface() {
		setTitle("Thông Báo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
        getContentPane().setLayout(new GridBagLayout());
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea notifyTextArea = new JTextArea(notify);
		notifyTextArea.setFont(Font_init.SanFranciscoText_Medium.deriveFont(20f));
		notifyTextArea.setBounds(35, 31, 383, 203);
		notifyTextArea.setLineWrap(true);
		notifyTextArea.setWrapStyleWord(true);
		notifyTextArea.setOpaque(false);
		
		contentPane.add(notifyTextArea);
	}

}
