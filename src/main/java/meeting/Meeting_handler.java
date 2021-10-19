package meeting;

import java.awt.Point;
import java.util.List;

import general_function.FileTool;

public class Meeting_handler {
	public static final String MEETING_CREATED_FOLDER_PATH = "meeting/meeting_created/";
	public static final String JOINED_MEETING_FILE_PATH = "meeting/meeting_joined/";
	
	public static String covertMeetingDataToString(String meetingName, Point timeStartPoint, int meetingLength, String dayStartDate, List<Integer> daysInWeekHaveMeeting) {
		String meeting_data_string = "";
		
		meeting_data_string += meetingName + '\n';
		meeting_data_string += String.valueOf(timeStartPoint.x) + " " + String.valueOf(timeStartPoint.y) + '\n';
		meeting_data_string += String.valueOf(meetingLength) + '\n';
		if (dayStartDate == null) meeting_data_string += "null" + '\n';
		else meeting_data_string += dayStartDate + '\n';
		if (daysInWeekHaveMeeting == null) meeting_data_string += "null" + '\n';
		else {
			for (int i = 0; i < daysInWeekHaveMeeting.size(); i ++) {
				meeting_data_string += daysInWeekHaveMeeting.get(i) + " ";
			}
			meeting_data_string += '\n';
		}
		
		return meeting_data_string;
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
