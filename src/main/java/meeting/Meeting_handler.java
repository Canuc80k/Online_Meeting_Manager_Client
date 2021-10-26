package meeting;

import java.util.List;

import general_function.FileTool;

public class Meeting_handler {
	public static final String MEETING_CREATED_FOLDER_PATH = "src/main/resources/meeting/meeting_created/";
	public static final String MEETING_JOINED_FOLDER_PATH = "src/main/resources/meeting/meeting_joined/";
	
	public static String create_meeting_information(String type, String name, List<List<String>> daysInWeek, String start_date,String state, String host) {
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
		
		meeting_information += start_date + '\n';
		meeting_information += state + '\n';
		meeting_information += host;
		return meeting_information;
	}

	public static String getCreatedMeetingInfomation(String meetingID) { 
		String meeting_informaiton = "";
		
		try {
			meeting_informaiton = FileTool.read_file(MEETING_CREATED_FOLDER_PATH + meetingID + "/meeting_information");
		} catch(Exception e) {}
		
		return meeting_informaiton;
	}
	
	public static String getJoinedMeetingInfomation(String meetingID) { 
		String meeting_informaiton = "";
		
		try {
			meeting_informaiton = FileTool.read_file(MEETING_JOINED_FOLDER_PATH + meetingID + "/meeting_information");
		} catch(Exception e) {}
		
		return meeting_informaiton;
	}
}
