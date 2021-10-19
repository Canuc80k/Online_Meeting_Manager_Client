package user_interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import general_function.FileTool;
import init.Font_init;

import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Change_storage_path_interface extends JFrame {
	public static final String STORAGE_PATH_FILE_PATH = "config/storage_path";

	private static JPanel contentPane;
	private static JTextField storagePathTextField;
	
	public static void create_new_window() {
		Change_storage_path_interface frame = new Change_storage_path_interface();
		frame.setVisible(true);
	}
	
	public Change_storage_path_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel storagePathLabel = new JLabel();
		storagePathLabel.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		storagePathLabel.setText("Thư mục lưu trữ cuộc họp");
		storagePathLabel.setBounds(24, 10, 214, 82);
		contentPane.add(storagePathLabel);
		
		storagePathTextField = new JTextField();
		try {
			storagePathTextField.setText(FileTool.read_file(STORAGE_PATH_FILE_PATH).trim());
		} catch (Exception e1) {}
		storagePathTextField.setBounds(24, 84, 382, 75);
		storagePathTextField.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		contentPane.add(storagePathTextField);
		storagePathTextField.setColumns(10);
		
		JButton submitButton = new JButton("Thay đổi");
		submitButton.setBounds(287, 180, 119, 60);
		submitButton.setFont(Font_init.SanFranciscoText_Medium.deriveFont(15f));
		submitButton.addActionListener(e -> {
			try {
				FileTool.write_file(storagePathTextField.getText().trim(), STORAGE_PATH_FILE_PATH);
				dispose();
				Notify_interface.create_new_window("Đổi Đường Dẫn Lưu Trữ Thành Công");
			} catch (Exception e1) {}
		});
		contentPane.add(submitButton);
	}
}
