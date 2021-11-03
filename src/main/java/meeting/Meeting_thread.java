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
import user_interface.Main_interface;

public class Meeting_thread implements Runnable {
	public static final String JOINED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	public static final String MEETING_IN_SAME_DAY_SPLIT_SIGNAL = "AAasdGGasdBB";
	
	private static String user_account_id = null;
	private static List<String> previous_running_meetings_id = new ArrayList<>();
	private static Map<String, App_activity_reader> map = new HashMap<String, App_activity_reader>();
	
	@Override
	public void run() {
		try {user_account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();} catch (Exception e2) {}

		try {
			while (true) {
				List<String> running_meetings_id = get_running_meetings_id();
				if (running_meetings_id == null) {
					if (Main_interface.frame == null) continue;
					Main_interface.stateLabel.setText("Không có cuộc họp nào đang diễn ra");
					Main_interface.frame.invalidate();
					Main_interface.frame.validate();
					Main_interface.frame.repaint();
					continue;
				}
				
				if (Main_interface.frame != null) {
					if (running_meetings_id.size() > 0) {
						String temp = "Các cuộc họp đang diễn ra: ";
						for (int i = 0; i < running_meetings_id.size(); i ++) {
							temp += running_meetings_id.get(i) + ", ";
						}
						Main_interface.stateLabel.setText(temp);
					} else {
						Main_interface.stateLabel.setText("Không có cuộc họp nào đang diễn ra");
						Main_interface.frame.invalidate();
						Main_interface.frame.validate();
						Main_interface.frame.repaint();
					}
				}
				
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
							JOptionPane.showMessageDialog(frame, "Cuộc Họp " + id + " Kết Thúc", "Thông Báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

				try {
					Thread.sleep(200);
				} catch (Exception e) {}

				previous_running_meetings_id = running_meetings_id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> get_running_meetings_id() {
		LocalDateTime current_time = LocalDateTime.now();
		List<String> running_meeting_id = new ArrayList<String>();
		
		try {
			File[] files = new File(JOINED_MEETING_FOLDER_PATH).listFiles();
			if (files == null) return null;
				
			for (File file : files) {
				String file_name = file.getName();
				String file_data = FileTool.read_file(JOINED_MEETING_FOLDER_PATH + file_name + "/meeting_information").trim();
				
				List<String> file_data_list = Arrays.asList(file_data.split("\n"));
				
				String index = file_data_list.get(0).trim();
				String T2 = file_data_list.get(4).trim();
				String T3 = file_data_list.get(5).trim();
				String T4 = file_data_list.get(6).trim();
				String T5 = file_data_list.get(7).trim();
				String T6 = file_data_list.get(8).trim();
				String T7 = file_data_list.get(9).trim();
				String CN = file_data_list.get(10).trim();
				String state = file_data_list.get(12).trim();
				
				if (state.equals("OFF")) continue;
	
				String meetings_today = null;
				if (current_time.getDayOfWeek().getValue() == 1) meetings_today = T2;
				if (current_time.getDayOfWeek().getValue() == 2) meetings_today = T3;
				if (current_time.getDayOfWeek().getValue() == 3) meetings_today = T4;
				if (current_time.getDayOfWeek().getValue() == 4) meetings_today = T5;
				if (current_time.getDayOfWeek().getValue() == 5) meetings_today = T6;
				if (current_time.getDayOfWeek().getValue() == 6) meetings_today = T7;
				if (current_time.getDayOfWeek().getValue() == 7) meetings_today = CN;
				if (meetings_today.equals("null")) continue;
				
				List<String> meetings_today_list = Arrays.asList(meetings_today.split(MEETING_IN_SAME_DAY_SPLIT_SIGNAL));
				for (int i = 0; i < meetings_today_list.size(); i ++) {
					List<String> times = Arrays.asList(meetings_today_list.get(i).split(" "));
					int start_time_value = Integer.parseInt(times.get(0).trim()) * 60 + Integer.parseInt(times.get(1).trim());
					int finish_time_value = Integer.parseInt(times.get(2).trim()) * 60 + Integer.parseInt(times.get(3).trim());
					int current_time_value = current_time.getHour() * 60 + current_time.getMinute();
					
					if (start_time_value <= current_time_value && current_time_value < finish_time_value) {
						running_meeting_id.add(index);
						break;
					}
				}
			}
			return running_meeting_id;
		} catch(Exception e) {e.printStackTrace();}
		
		return null;
	}
}
