package datapack;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import user_interface.Tray_Icon;

public class Datapack_receiver {
    private static final String MEETING_IN_DATAPACK_SPLIT_SIGNAL = "MEETING_SPLIT_SIGNAL";
    private static final String MEETING_DATA_IN_DATAPACK_SPLIT_SIGNAL = "MEETING_DATA_SPLIT_SIGNAL";

    private static List<SubResponseDatapack> meetings_notify;

    /*
     * @do get response data for meeting notify from server
     * Format: 
     *      Meetings {
     *          - Change tab times in previous seconds
     *          - Change app times in previous seconds
     *      }
     */
    public static synchronized void handle_received_datapack(String received_datapack) {
        if (meetings_notify == null) meetings_notify = new ArrayList<SubResponseDatapack>();
        meetings_notify.clear();
        
        List<String> meetings_response_list = Arrays.asList(received_datapack.split(MEETING_IN_DATAPACK_SPLIT_SIGNAL));
        
        for (int i = 0; i < meetings_response_list.size(); i ++) 
            if (!meetings_response_list.get(i).trim().equals("")) {
                    List<String> specified_meeting_response_list = Arrays.asList(meetings_response_list.get(i).split(MEETING_DATA_IN_DATAPACK_SPLIT_SIGNAL));
                    int change_tab_times = Integer.parseInt(specified_meeting_response_list.get(0).trim());
                    int change_app_times = Integer.parseInt(specified_meeting_response_list.get(1).trim());
                    SubResponseDatapack subResponseDatapack = new SubResponseDatapack(change_tab_times, change_app_times);
                    meetings_notify.add(subResponseDatapack);
                }
    }

    public static synchronized void send_notify() {
        if (meetings_notify == null) return;

        for (int i = 0; i < meetings_notify.size(); i ++) {
            if (meetings_notify.get(i).change_app_times > 0 || meetings_notify.get(i).change_tab_times > 0) {
                Tray_Icon.trayIcon.displayMessage(
                    "Không được chuyển ứng dụng khi làm bài", 
                    "Thí sinh không được chuyển tab hoặc chuyển ứng dụng khi làm bài kiểm tra. Nếu vi phạm nhiều lần kết quả sẽ bị huỷ.", 
                    MessageType.WARNING
                );
            }
        }
    }
}
