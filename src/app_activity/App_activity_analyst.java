package app_activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import general_function.FileTool;
import storage.Storage_statistic;

public class App_activity_analyst {
	public static final String CREATED_MEETING_FOLDER_PATH = "meeting/meeting_created/";
	public static final String JOINED_MEETING_FOLDER_PATH = "meeting/meeting_joined/";
	private static final String APP_ACTIVITY_DATA_SPLIT_SIGNAL = " _-.,1sdSA22efa,.-_ ";
    
	private static Map<String, String> map;
	
	public App_activity_analyst(String type_of_meeting, String meeting_id) throws Exception {
		read_meeting_data(type_of_meeting, meeting_id);
		write_anaysis(type_of_meeting, meeting_id);
	}
	
	public static void read_meeting_data(String type_of_meeting, String meeting_id) throws Exception {
		String meeting_data_folder_path = ""; 
		if (type_of_meeting.equals("CREATED_MEETING")) meeting_data_folder_path = CREATED_MEETING_FOLDER_PATH + meeting_id;
		if (type_of_meeting.equals("JOINED_MEETING")) meeting_data_folder_path = JOINED_MEETING_FOLDER_PATH + meeting_id;
		
		map = new HashMap<String, String>();
		File files[] = new File(meeting_data_folder_path + "/joiner_app_activity" + '/').listFiles();
		
		for (int i = 0; i < files.length; i ++)
			map.put(files[i].getName(), FileTool.read_file(files[i].getPath()));
	}
	
	public static void write_anaysis(String type_of_meeting, String meeting_id) throws Exception {
		String path = Storage_statistic.get_storage_path();
		
		if (type_of_meeting.equals("CREATED_MEETING")) {
			File file = new File(path + "/created_meeting_statistic");
			if (!file.exists()) file.mkdirs();
			file = new File(file.getPath() + '/' + meeting_id);
			if (!file.exists()) file.mkdirs();
			
			path = path + "/created_meeting_statistic" + '/' + meeting_id; 
		}
		
		if (type_of_meeting.equals("JOINED_MEETING")) {
			File file = new File(path + "/joined_meeting_statistic");
			if (!file.exists()) file.mkdirs();
			file = new File(file.getPath() + '/' + meeting_id);
			if (!file.exists()) file.mkdirs();
			
			path = path + "/joined_meeting_statistic" + '/' + meeting_id; 
		}
		
		for (Entry<String, String> entry : map.entrySet()) {
		    File file = new File(path + '/' + entry.getKey());
		    if (!file.exists()) file.mkdirs();
		    
		    FileTool.write_file(anaylize(entry.getValue()), path + '/' + entry.getKey() + "/general_statistic");
		}
	}
	
	public static String anaylize(String data) {
		List<String> app_title = new ArrayList<String>();
		List<String> app_process = new ArrayList<String>();
		List<Double> app_running_time = new ArrayList<Double>();
		List<String> app_name = new ArrayList<String>();
		
		String result = "";
		int change_app_times = 0;
		int change_tab_times = 0;
		Map<String, Double> time_use_app = new HashMap<String, Double>();
		
		List<String> data_line_list = Arrays.asList(data.split("\n"));
		for (int i = 0; i < data_line_list.size(); i ++) {
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
		}
		
		result += String.valueOf(change_app_times) + '\n';
		result += String.valueOf(change_tab_times) + '\n';
		for (Entry<String, Double> entry : time_use_app.entrySet()) {
			result += "Tên Ứng Dụng:" + entry.getKey() + "&& Thời Gian Sử Dụng:" + String.valueOf(entry.getValue()) + '\n';
		}
		
		return result;
	}
}
