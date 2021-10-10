package laucher;

import general_function.FileTool;
import user_interface.Login_interface;
import user_interface.Main_interface;

public class Laucher implements Runnable {
	public static final String IS_LOGINED_FILE_PATH = "config/is_logined.txt";

	public static boolean isLogined;
	public static Thread main_thread;

	private static void init() throws Exception {
		main_thread = new Thread(new Laucher());
		isLogined = Boolean.parseBoolean(FileTool.read_file(IS_LOGINED_FILE_PATH).trim());
	}

	public static void main(String[] args) throws Exception {
		init();
		main_thread.start();
	}

	@Override
	public void run() {
		if (isLogined) Main_interface.create_new_window();
		else Login_interface.create_new_window();
	}
}
