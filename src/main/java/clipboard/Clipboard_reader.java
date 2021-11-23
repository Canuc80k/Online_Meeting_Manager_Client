package clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

public class Clipboard_reader extends Thread {
    private static final int TIME_THREAD_SLEEP = 200;
    private static final String CLIPBOARD_DATA_SPLIT_SIGNAL = "____";

    private boolean is_reading_clipboard;
    private String clipboard_log, previous_clipboard_data;

    @Override
    public synchronized void run() {
        clipboard_log = "";
        try {
            previous_clipboard_data = (String) Toolkit.getDefaultToolkit()
            .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (Exception e) {}

        while (is_reading_clipboard) {
            try {Thread.sleep(TIME_THREAD_SLEEP);} catch (Exception e) {}

            try {
                String current_clipboard_data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
                if (!current_clipboard_data.equals(previous_clipboard_data)) {
                    clipboard_log += current_clipboard_data;
                    clipboard_log += CLIPBOARD_DATA_SPLIT_SIGNAL;
                    previous_clipboard_data = current_clipboard_data;
                }
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public void set_running_state(boolean running_state) {
        is_reading_clipboard = running_state;
    }

    public String get_clipboard_log() {
        return clipboard_log;
    }
}
