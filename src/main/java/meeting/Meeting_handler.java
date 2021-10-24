package meeting;

import java.util.List;

public class Meeting_handler {
	public static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final String JOINED_MEETING_FILE_PATH = "src/main/resources/meeting/meeting_joined/";
	
	public static String create_meeting_information(String type, String name, List<List<String>> daysInWeek, String state, String host) {
		String meeting_information = "";
		
		meeting_information += type + '\n';
		meeting_information += name + '\n';
		
		for (int i = 0; i < daysInWeek.size(); i++) {
			List<String> day = daysInWeek.get(i);
			if (day.size() == 0) meeting_information += "null\n";
			else {
				for (int j = 0; j < day.size(); j++) 
					meeting_information += day.get(j) + " ";
				meeting_information += '\n';
			}
		}
		
		meeting_information += state + '\n';
		meeting_information += host;
		return meeting_information;
	}

}
