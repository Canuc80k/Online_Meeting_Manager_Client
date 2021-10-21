package meeting;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app_activity.App_activity_reader;
import client.Client;
import general_function.FileTool;

public class Meeting_thread implements Runnable {
	public static final String JOINED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	
	private static String meeting_id = "";
	private static String user_account_id = null;
	private static List<String> previous_running_meetings_id = new ArrayList<>();
	private static Map<String, App_activity_reader> map = new HashMap<String, App_activity_reader>();
	
	@Override
	public void run() {
		try {user_account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();} catch (Exception e2) {}

		try {
			while (true) {
				List<String> running_meetings_id = get_running_meetings_id();

				for (int i = 0; i < running_meetings_id.size(); i++) {
					String id = running_meetings_id.get(i);
					boolean is_new_running_meeting = true;

					for (int j = 0; j < previous_running_meetings_id.size(); j++) {
						String old_id = previous_running_meetings_id.get(j);
						if (id.equals(old_id)) {
							System.out.println("Meeting " + id + " is running");
							is_new_running_meeting = false;
						}
					}

					if (is_new_running_meeting) {
						App_activity_reader thread = new App_activity_reader();
						map.put(id, thread);
						map.get(id).set_running_state(true);
						map.get(id).start();

						System.out.println("Meeting " + id + " is start");
					}
				}

				for (int i = 0; i < previous_running_meetings_id.size(); i++) {
					String id = previous_running_meetings_id.get(i);
					boolean is_meeting_end = true;

					for (int j = 0; j < running_meetings_id.size(); j++) {
						String new_id = running_meetings_id.get(j);
						if (id.equals(new_id)) {
							is_meeting_end = false;
						}
					}

					if (is_meeting_end) {
						System.out.println("Meeting " + id + " is end");
						map.get(id).set_running_state(false);
						map.get(id).join();

						String app__activity_log = map.get(id).get_app_activity_log();
						if (Client.send_meeting_data(user_account_id, id, app__activity_log)) {
							File joiner_app_activity_folder_path = new File(
									JOINED_MEETING_FOLDER_PATH + id + "/joiner_app_activity/");
							if (!joiner_app_activity_folder_path.exists())
								joiner_app_activity_folder_path.mkdirs();
							FileTool.write_file(app__activity_log,
									JOINED_MEETING_FOLDER_PATH + id + "/joiner_app_activity/" + user_account_id);

							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Cuá»™c há»�p " + id + " Ä‘Ã£ káº¿t thÃºc", "ThÃ´ng bÃ¡o",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				try {
					Thread.sleep(200);
				} catch (Exception e) {
				}

				previous_running_meetings_id = running_meetings_id;
			}
		} catch (Exception e) {
		}
	}
	
	public static List<String> get_running_meetings_id() throws Exception {
		LocalDateTime current_time = LocalDateTime.now();
		List<String> running_meeting_id = new ArrayList<String>();
		
		File[] files = new File(JOINED_MEETING_FOLDER_PATH).listFiles();

		for (File file : files) {
			String file_name = file.getName();
			String file_data = FileTool.read_file(JOINED_MEETING_FOLDER_PATH + file_name + "/meeting_information");
			
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
						running_meeting_id.add(file_name.trim());
					}
				}
			}
			else {
				List<String> meeting_starting_date_list = Arrays.asList(file_data_list.get(4).trim().split(" "));
				for (int i = 0; i < meeting_starting_date_list.size(); i ++) {
					if (Integer.parseInt(meeting_starting_date_list.get(i)) == current_time.getDayOfWeek().getValue() + 1) {
						if (is_meeting_running_now(current_time, starting_meeting_time_data, meeting_length)) {
							running_meeting_id.add(file_name.trim());
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
