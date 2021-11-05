package app_activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.Client;
import general_function.FileTool;

public class App_activity_analyst {
	public static final String CREATED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final String JOINED_MEETING_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	private static final String APP_ACTIVITY_DATA_SPLIT_SIGNAL = "_   _";
	public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
	private static final String SHEET_SPLIT_SIGNAL = "\nPPPPPPPP\n";
	
	private String account_id;
	private List<String> sheets;
	
	public int change_app_times = 0;
	public int change_tab_times = 0;
	
	public App_activity_analyst() {
		
	}
	
	public App_activity_analyst(String parent_folder_path, String meeting_id) throws Exception {
		account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();
		
		read_meeting_data(meeting_id);
		write_anaysis(parent_folder_path, meeting_id);
	}
	
	public synchronized void read_meeting_data(String meeting_id) throws Exception {
		String all_raw_data = Client.get_user_acitivity_raw_data(account_id, meeting_id);
		sheets = Arrays.asList(all_raw_data.split(SHEET_SPLIT_SIGNAL));
	}
	
	public synchronized void write_anaysis(String parent_folder_path, String meeting_id) throws Exception {
		String folder_path = parent_folder_path + "/DATA AFTER ANALYTIC/";
		if (! new File(folder_path).exists()) new File(folder_path).mkdirs();
		for (int i = 0; i < sheets.size(); i ++) {
			String file_path = folder_path + String.valueOf(i + 1);
			String data = anaylize(sheets.get(i).trim());
			new App_activity_drawer().draw_pieChart_tofile(data, file_path);
		}
	}
	
	public synchronized String anaylize(String data) {
		change_app_times = change_tab_times = 0;
		
		List<String> app_title = new ArrayList<String>();
		List<String> app_process = new ArrayList<String>();
		List<Double> app_running_time = new ArrayList<Double>();
		List<String> app_name = new ArrayList<String>();
		
		String result = "";
		Map<String, Double> time_use_app = new HashMap<String, Double>();
		
		List<String> data_line_list = Arrays.asList(data.split("\n"));
		for (int i = 0; i < data_line_list.size(); i ++) {
			try {
				List<String> data_part_list = Arrays.asList(data_line_list.get(i).split(APP_ACTIVITY_DATA_SPLIT_SIGNAL));
				app_title.add(data_part_list.get(0).trim());
				app_process.add(data_part_list.get(1).trim());
				app_running_time.add(Double.valueOf(data_part_list.get(2).trim()));
				
				List<String> app_title_list = Arrays.asList(app_title.get(i).split(" - "));
				app_name.add(app_title_list.get(app_title_list.size() - 1).trim());
				
				if (i > 0) {
					if (!app_name.get(i).equals(app_name.get(i - 1))) change_app_times ++;
					else if (!app_title.get(i).equals(app_title.get(i - 1))) change_tab_times ++;
				}
		
				if (time_use_app.get(app_name.get(i)) == null) time_use_app.put(app_name.get(i), app_running_time.get(i));
				else {
					Double time = time_use_app.get(app_name.get(i));
					time += app_running_time.get(i);
					time_use_app.put(app_name.get(i), time);
				}
			} catch(Exception e) {e.printStackTrace();}
		}
		
		for (Entry<String, Double> entry : time_use_app.entrySet()) {
			String name = entry.getKey();
			if (name.trim().equals("")) name = "Unknow";
			result += name + APP_ACTIVITY_DATA_SPLIT_SIGNAL + String.valueOf(entry.getValue()) + '\n';
		}
		
		return result;
	}
	
	public synchronized int get_app_change_times() {
		return change_app_times;
	}
	
	public synchronized int get_tab_change_times() {
		return change_tab_times;
	}
}
