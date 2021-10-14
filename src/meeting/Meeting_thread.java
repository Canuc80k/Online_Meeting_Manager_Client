package meeting;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app_activity.App_activity_reader;
import client.Client;
import general_function.FileTool;

public class Meeting_thread implements Runnable {
	public static final String JOINED_MEETING_FILE_PATH = "meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "config/account_id.txt";
	
	@Override
	public void run() {
		String user_account_id = null;
		try {
			user_account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();
		} catch (Exception e2) {}
		
		Thread read_app_activity_thread = new Thread(new App_activity_reader());
		
		String last_running_meeting_id = "";
		try {
			while (true) {
				String running_meeting_id = get_running_meeting_id();
				
				if (running_meeting_id != null) {
					last_running_meeting_id = running_meeting_id;
					
					App_activity_reader.set_running_state(true);
					if (!read_app_activity_thread.isAlive()) {
						read_app_activity_thread.start();
					}
				}
				else {
					if (read_app_activity_thread.isAlive()) {
						App_activity_reader.set_running_state(false);
						try {
							read_app_activity_thread.join();
							read_app_activity_thread = new Thread(new App_activity_reader());
						} catch (Exception e1) {}
						
						if (Client.send_meeting_data(user_account_id, last_running_meeting_id, App_activity_reader.get_app_activity_log())) {
							JFrame frame = new JFrame();
					        JOptionPane.showMessageDialog(frame,
					        		"Cuộc họp " + last_running_meeting_id + " đã kết thúc",
					                "Thông báo",
					                JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else try {Thread.sleep(200);} catch(Exception e) {}
				}
			}
		} catch(Exception e) {}
	}
	
	public static String get_running_meeting_id() throws Exception {
		LocalDateTime current_time = LocalDateTime.now();  
		String running_meeting_id = null;
		
		File[] files = new File(JOINED_MEETING_FILE_PATH).listFiles();

		for (File file : files) {
			String file_name = file.getName();
			String file_data = FileTool.read_file(JOINED_MEETING_FILE_PATH + file_name);
			
			List<String> file_data_list = Arrays.asList(file_data.split("\n")); 
			String starting_meeting_time_data = file_data_list.get(1);
			String meeting_length = file_data_list.get(2);
					
			if (file_data_list.get(4).trim().equals("null")) {
				List<String> meeting_starting_date_list = Arrays.asList(file_data_list.get(3).trim().split(" "));
				int day = Integer.parseInt(meeting_starting_date_list.get(0));
				int month = Integer.parseInt(meeting_starting_date_list.get(1));
				int year = Integer.parseInt(meeting_starting_date_list.get(2));
			
				if (current_time.getDayOfMonth() == day && current_time.getMonthValue() == month && current_time.getYear() == year) {
					if (is_meeting_running_now(current_time, starting_meeting_time_data, meeting_length)) {
						running_meeting_id = file_name.substring(0, file_name.length() - 4);
						break;
					}
				}
			}
			else {
				List<String> meeting_starting_date_list = Arrays.asList(file_data_list.get(4).trim().split(" "));
				for (int i = 0; i < meeting_starting_date_list.size(); i ++) {
					if (Integer.parseInt(meeting_starting_date_list.get(i)) == current_time.getDayOfWeek().getValue() + 1) {
						if (is_meeting_running_now(current_time, starting_meeting_time_data, meeting_length)) {
							running_meeting_id = file_name.substring(0, file_name.length() - 4);
							break;
						}
					}
				}
			}
		}
		
		return running_meeting_id;
	}
	
	private static boolean is_meeting_running_now(LocalDateTime current_time, String starting_meeting_time_data, String meeting_length) {
		List<String> starting_meeting_time_data_list = Arrays.asList(starting_meeting_time_data.split(" "));
		int starting_meeting_time = Integer.parseInt(starting_meeting_time_data_list.get(0)) * 60 + Integer.parseInt(starting_meeting_time_data_list.get(1));
		int finishing_meeting_time = starting_meeting_time + Integer.parseInt(meeting_length);
		int current_time_value = current_time.getHour() * 60 + current_time.getMinute();
		
		if (starting_meeting_time <= current_time_value && current_time_value < finishing_meeting_time) return true;
		return false;
	}
}
