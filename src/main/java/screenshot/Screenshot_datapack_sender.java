package screenshot;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import client.Client;
import general_function.FileTool;
import meeting.Meeting_thread;

public class Screenshot_datapack_sender extends Thread {
    private static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";
    private static final int THREAD_SLEEP_TIME = 1000;

    private static String account_id;
    private static boolean is_running;
    private static List<String> running_meetings_id;
    public static Thread screenshot_datapack_thread;

    public Screenshot_datapack_sender() throws Exception {
        account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();
        
        screenshot_datapack_thread = new Thread(new Runnable() {
            public synchronized void run() {
                while (is_running) {
                    try {
                        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        
                        running_meetings_id = Meeting_thread.get_running_meetings_id();
                        if (running_meetings_id.size() == 0) return;
                        for (int i = 0; i < running_meetings_id.size(); i ++)
                            Client.send_screenshot(image, account_id, running_meetings_id.get(i).trim());
                        
                        Thread.sleep(THREAD_SLEEP_TIME);
                    } catch (Exception e) {e.printStackTrace();}
                }
            }
        });
    }
         
    public static void set_running_state(boolean running_state) {
        is_running = running_state;
    }
}
