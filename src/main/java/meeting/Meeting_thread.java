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

import app_activity.App_activity_analyst;
import app_activity.App_activity_reader;
import client.Client;
import clipboard.Clipboard_reader;
import general_function.FileTool;
import screenshot.Screenshot_datapack_sender;
import user_interface.Main_interface;

public class Meeting_thread implements Runnable {
	public static final String JOINED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	public static final String MEETING_IN_SAME_DAY_SPLIT_SIGNAL = "AAasdGGasdBB";
	private static final String COLUMN_SPLIT_SIGNAL = "\nsadwffws\n";
	
	private static String user_account_id = null;
	private static List<String> previous_running_meetings_id = new ArrayList<>();
	public static Map<String, List<Object>> map = new HashMap<String, List<Object>>();
	
	@Override
	public synchronized void run() {
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
				
				/*
				 * @do
				 *  + write running meetings 
				 *  + update main frame screen state
				 */
				if (Main_interface.frame != null) {
					if (running_meetings_id.size() > 0) {
						String temp = "Các cuộc họp đang diễn ra: ";
						for (int i = 0; i < running_meetings_id.size(); i ++) {
							if (i + 1 >= running_meetings_id.size()) {
								temp += running_meetings_id.get(i) + ".";
								break;
							}
							temp += running_meetings_id.get(i) + ", ";
						}
						Main_interface.stateLabel.setText(temp);
					} else Main_interface.stateLabel.setText("Không có cuộc họp nào đang diễn ra");
				}
				
				/*
				 * @do Create threads for each running meeting
				 * There are 1 thread:
				 * 		+ App activity 
				 * 		+ Clipboard
				 */
				for (int i = 0; i < running_meetings_id.size(); i ++) {
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
						try {
							if (Screenshot_datapack_sender.screenshot_datapack_thread == null) 
								new Screenshot_datapack_sender();	

							if (!Screenshot_datapack_sender.screenshot_datapack_thread.isAlive()) {
								Screenshot_datapack_sender.set_running_state(true);
								Screenshot_datapack_sender.screenshot_datapack_thread.start();
							}
						} catch(Exception e) {e.printStackTrace();}
						
						App_activity_reader app_log_thread = new App_activity_reader();
						Clipboard_reader clipboard_log_thread = new Clipboard_reader();
						List<Object> objects = new ArrayList<Object>();
						objects.add(app_log_thread); 
						objects.add(clipboard_log_thread);
						map.put(id, objects);
						((App_activity_reader) map.get(id).get(0)).set_running_state(true);
						((App_activity_reader) map.get(id).get(0)).start();
						((Clipboard_reader) map.get(id).get(1)).set_running_state(true);
						((Clipboard_reader) map.get(id).get(1)).start();
						
						System.out.println("Meeting " + id + " is start");
					}
				}

				/*
				 * @do Detect end meeting + send data to database
				*/
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
						((App_activity_reader) map.get(id).get(0)).set_running_state(false);
						((App_activity_reader) map.get(id).get(0)).join();
						((Clipboard_reader) map.get(id).get(1)).set_running_state(false);
						((Clipboard_reader) map.get(id).get(1)).join();

						App_activity_analyst analyst = new App_activity_analyst();
						
						String app_activity_log = ((App_activity_reader) map.get(id).get(0)).get_app_activity_log();
						String app_activity_time_usage = analyst.anaylize(app_activity_log);
						int change_tab_times = analyst.get_tab_change_times();
						int change_app_times = analyst.get_app_change_times();
						String clipboard = ((Clipboard_reader) map.get(id).get(1)).get_clipboard_log();
						String app_activity_data = "";
						app_activity_data += String.valueOf(change_tab_times) + COLUMN_SPLIT_SIGNAL;
						app_activity_data += String.valueOf(change_app_times) + COLUMN_SPLIT_SIGNAL;
						app_activity_data += app_activity_time_usage + COLUMN_SPLIT_SIGNAL;
						app_activity_data += app_activity_log + COLUMN_SPLIT_SIGNAL;
						app_activity_data += clipboard;
						
						System.out.println(app_activity_data);
						if (Client.send_meeting_data(user_account_id, id, app_activity_data)) {
							File joiner_app_activity_folder_path = new File(
									JOINED_MEETING_FOLDER_PATH + id + "/joiner_app_activity/");
							if (!joiner_app_activity_folder_path.exists())
								joiner_app_activity_folder_path.mkdirs();
							FileTool.write_file(app_activity_log,
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
	
	public static synchronized List<String> get_running_meetings_id() {
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
