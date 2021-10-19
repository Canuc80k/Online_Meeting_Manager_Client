package laucher;

import general_function.FileTool;
import init.Font_init;
import meeting.Meeting_thread;
import user_interface.Login_interface;
import user_interface.Main_interface;

public class Laucher implements Runnable {
	public static final String IS_LOGINED_FILE_PATH = "config/is_logined";
	
	public static boolean isLogined;
	public static Thread main_thread, meeting_thread;
    
	private static void init() throws Exception {		
		new Font_init();
		main_thread = new Thread(new Laucher());
		meeting_thread = new Thread(new Meeting_thread());	
		try {
			isLogined = Boolean.parseBoolean(FileTool.read_file(IS_LOGINED_FILE_PATH).trim());
		} catch(Exception e) {
			FileTool.write_file("false", IS_LOGINED_FILE_PATH);
			isLogined = Boolean.parseBoolean(FileTool.read_file(IS_LOGINED_FILE_PATH).trim());
		}
	}

	public static void main(String[] args) throws Exception {
		init();
		main_thread.start();
		meeting_thread.start();
	}

	@Override
	public void run() {
		if (isLogined) try {Main_interface.create_new_window();} catch (Exception e) {}
		else Login_interface.create_new_window();
	}
}
