package laucher;

import datapack.Datapack_thread;
import general_function.FileTool;
import init.Font_init;
import meeting.Meeting_thread;
import user_interface.Tray_Icon;
import user_interface.login_and_register.Login_interface;

public class Laucher implements Runnable {
	public static final String IS_LOGINED_FILE_PATH = "src/main/resources/config/is_logined";

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
		new Datapack_thread();
	}

	@Override
	public void run() {
		if (isLogined) 
			try {
				Tray_Icon.create_tray_icon();
			} catch (Exception e) {}
		else Login_interface.create_new_window();
	}
}
