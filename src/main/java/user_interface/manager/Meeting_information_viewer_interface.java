package user_interface.manager;

import javax.swing.JFrame;
import meeting.Meeting_handler;
import user_interface.manager.manage_by_type.One_time_meeting_information_viewer;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class Meeting_information_viewer_interface extends JFrame {
	private static String meeting_id;
	
	public static void create_new_window(String meeting_id) {
		Meeting_information_viewer_interface.meeting_id = meeting_id;
		new Meeting_information_viewer_interface();
	}
	
	public Meeting_information_viewer_interface() {
		String meeting_information = Meeting_handler.getJoinedMeetingInfomation(meeting_id);
		List<String> meeting_information_list = Arrays.asList(meeting_information.split("\n"));
		
		if (meeting_information_list.get(2).trim().equals("ONE_TIME")) {
			One_time_meeting_information_viewer.create_new_window(meeting_id, meeting_information_list);
		}
	}
}
