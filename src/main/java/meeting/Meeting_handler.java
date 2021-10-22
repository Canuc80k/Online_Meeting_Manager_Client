package meeting;

import java.util.List;

import general_function.FileTool;

public class Meeting_handler {
	public static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final String JOINED_MEETING_FILE_PATH = "src/main/resources/meeting/meeting_joined/";
	
	public static String create_meeting_information(String type, String name, List<String> t2, List<String> t3, List<String> t4, List<String> t5, List<String> t6, List<String> t7, List<String> cn, String state) {
		String meeting_information = "";
		
		meeting_information += type + '\n';
		meeting_information += name + '\n';
		
		if (t2.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t2.size(); i ++) 
				meeting_information += t2.get(i) + " "; 
			meeting_information += '\n';
		}
		if (t3.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t3.size(); i ++) 
				meeting_information += t3.get(i) + " "; 
			meeting_information += '\n';
		}
		if (t4.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t4.size(); i ++) 
				meeting_information += t4.get(i) + " "; 
			meeting_information += '\n';
		}
		if (t5.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t5.size(); i ++) 
				meeting_information += t5.get(i) + " "; 
			meeting_information += '\n';
		}
		if (t6.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t6.size(); i ++) 
				meeting_information += t6.get(i) + " "; 
			meeting_information += '\n';
		}
		if (t7.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < t7.size(); i ++) 
				meeting_information += t7.get(i) + " "; 
			meeting_information += '\n';
		}
		if (cn.size() == 0) meeting_information += "null\n";
		else {
			for (int i = 0; i < cn.size(); i ++) 
				meeting_information += cn.get(i) + " "; 
			meeting_information += '\n';
		}
		
		meeting_information += state;
		 
		return meeting_information;
	}
	
	public static String getMeetingInfomation(String meeting_id) {
		meeting_id = meeting_id.trim();
		String meeting_information = "";
		try {
			meeting_information = FileTool.read_file(MEETING_CREATED_FOLDER_PATH + meeting_id + "/meeting_information");
		} catch (Exception e) {}
		
		return meeting_information;
	}
}
