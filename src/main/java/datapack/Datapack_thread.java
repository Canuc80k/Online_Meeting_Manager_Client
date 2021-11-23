package datapack;

import java.util.List;

import app_activity.App_activity_reader;
import client.Client;
import general_function.FileTool;
import meeting.Meeting_thread;

public class Datapack_thread {
    private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
    private static final String MEETING_IN_DATAPACK_SPLIT_SIGNAL = "MEETING_SPLIT_SIGNAL";
    private static final String MEETING_DATA_IN_DATAPACK_SPLIT_SIGNAL = "MEETING_DATA_SPLIT_SIGNAL";
    private static final int THREAD_SLEEP_TIME = 500;

    private static String sent_datapack, received_datapack;
    public static Thread datapack_thread;

    public Datapack_thread() {
        datapack_thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        update_sent_datapack();
                        if (sent_datapack != null) {
                            received_datapack = Client.send_datapack(sent_datapack);
                            Datapack_receiver.handle_received_datapack(received_datapack);
                            Datapack_receiver.send_notify();
                        }
                        Thread.sleep(THREAD_SLEEP_TIME);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }
        });
        datapack_thread.start();
    }

    public static synchronized String get_received_datapack() {
        return received_datapack;
    }

    /*
     * @do get current data of meetings
     * Format:
     *      ACCOUNT_ID + '\n'
     *      MEETING_ID.get(0 -> size - 1) + '\n' {
     *          MEETING_APP_ACTIVITY
     *      } + SPLIT_SIGNAL
     */
    public static synchronized void update_sent_datapack() throws Exception {
        List<String> running_meetings_id = Meeting_thread.get_running_meetings_id();

        if (running_meetings_id.size() == 0) sent_datapack = null;
        if (running_meetings_id.size() > 0) {
            sent_datapack = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim() + '\n';

            for (int i = 0; i < running_meetings_id.size(); i ++) {
                String current_meeting_id = running_meetings_id.get(i);

                sent_datapack += current_meeting_id + MEETING_DATA_IN_DATAPACK_SPLIT_SIGNAL;
                sent_datapack += ((App_activity_reader) Meeting_thread.map.get(current_meeting_id).get(0)).get_app_activity_log();
                sent_datapack += MEETING_IN_DATAPACK_SPLIT_SIGNAL;
            }
        }
    }
}
