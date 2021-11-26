package microphone;

import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import client.Client;
import general_function.FileTool;
import meeting.Meeting_thread;

public class Record_reader {
    public static final String ACCOUNT_ID_FILE_PATH = "src/main/resources/account/account_id";

    private static AudioFormat format;
    private static DataLine.Info info;
    private static TargetDataLine targetLine;
    public static Thread record_thread;

    private static List<String> running_meetings_id; 

    public static synchronized void create_new_record_reader() {
        format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        info = new DataLine.Info(TargetDataLine.class, format); 

        try {
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
        } catch (Exception e) {}
    }

    public static synchronized void start_record() {
        running_meetings_id = Meeting_thread.get_running_meetings_id();

        try {
            targetLine.open(); 
            targetLine.start();
        } catch (Exception e) {e.printStackTrace();}

        record_thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                AudioInputStream audioStream = new AudioInputStream(targetLine); 
                File audioFile = new File("Record.wav");
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, audioFile); 
                } catch (Exception e) {e.printStackTrace();}
            }
        });
        record_thread.start();
    }

    public static synchronized void stop_record() throws Exception {
        record_thread.interrupt();
        targetLine.stop(); 
        targetLine.close();
        record_thread = null;
    
        String account_id = FileTool.read_file(ACCOUNT_ID_FILE_PATH).trim();
        for (int i = 0; i < running_meetings_id.size(); i ++) {
            Client.send_microphone_record(account_id, running_meetings_id.get(i).trim());
        }
    }
}