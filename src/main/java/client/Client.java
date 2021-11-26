package client;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import org.apache.pdfbox.io.IOUtils;

public class Client {
	public static int port = 9000;
	public static String hostname = "127.0.0.1";

	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static Socket socket;
	
	public static synchronized String send_datapack(String datapack) throws Exception {
		Client.start();
		dos.writeUTF("SEND_DATAPACK\n" + datapack);
		
		String send_successfully = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return send_successfully;
	}

	public static synchronized String login(String username, String password) throws Exception {
		Client.start();
		dos.writeUTF("LOGIN\n" + username + " " + password);

		String account_id = dis.readUTF(); 
		
		dos.close();
		dis.close();
		socket.close();
		
		return account_id;
	}
	
	public static synchronized boolean register(String register_data) throws Exception {
		Client.start();
		dos.writeUTF("REGISTER\n" + register_data);

		boolean register_successful = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return register_successful;
	}

	public static synchronized String create_meeting(String account_id, String meeting_info) throws Exception {	
		Client.start();
		dos.writeUTF("CREATE_MEETING\n" + account_id + '\n' + meeting_info);

		String meeting_id = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_id;
	}
	
	public static synchronized String join_meeting(String meeting_id, String joiner_meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("JOIN_MEETING\n" + meeting_id + '\n' + joiner_meeting_id);

		String meeting_information = dis.readUTF();

		dos.close();
		dis.close();
		socket.close();

		return meeting_information;
	}

	public static synchronized boolean send_meeting_data(String user_id, String meeting_id, String app_activity_data) throws Exception {
		Client.start();
		dos.writeUTF("SEND_MEETING_DATA\n" + user_id + '\n' + meeting_id + '\n' + app_activity_data);

		boolean send_succesfully = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return send_succesfully;
	}
	
	public static synchronized String get_joiner_app_activity(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_MEETING_DATA\n" + meeting_id);
		String meeting_data = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_data;
	}
	
	public static synchronized boolean change_account_information(String account_id, String account_info) throws Exception {
		Client.start();
		dos.writeUTF("CHANGE_ACCOUNT_INFO\n" + account_id + '\n' + account_info);

		boolean change_succesfully = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return change_succesfully;
	}
	
	public static synchronized String get_account_info(String account_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_ACCOUNT_INFO\n" + account_id);
		
		String account_info = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return account_info;
	}
	
	public static synchronized String get_created_meetings(String account_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_CREATED_MEETINGS\n" + account_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String get_joined_meetings(String account_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_JOINED_MEETINGS\n" + account_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String get_meeting_info(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_MEETING_INFO\n" + meeting_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String stop_meeting(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("STOP_MEETING\n" + meeting_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String start_meeting(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("START_MEETING\n" + meeting_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String out_meeting(String account_id, String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("OUT_MEETING\n" + account_id + '\n' + meeting_id);
		
		String joined_meetings = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return joined_meetings;
	}
	
	public static synchronized String get_spreadSheet_id(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_SPREADSHEET_ID\n" + meeting_id);
		
		String spreadSheetID = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return spreadSheetID;
	}
	
	public static synchronized String get_user_acitivity_raw_data(String account_id, String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_USER_ACTIVITY_RAW_DATA\n" + account_id + '\n' + meeting_id);
		
		String user_activity_raw_data = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return user_activity_raw_data;
	}
	
	public static synchronized String send_screenshot(BufferedImage image, String account_id, String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("SEND_SCREENSHOT\n" + account_id + '\n' + meeting_id);
        ImageIO.write(image, "png", socket.getOutputStream());

		String send_successfully = dis.readUTF();
		dos.close();
		dis.close();
		socket.close();

		return send_successfully;
	}

	public static synchronized String send_microphone_record(String account_id, String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("SEND_MICROPHONE_RECORD\n" + account_id + '\n' + meeting_id);
		byte[] file = IOUtils.toByteArray(new FileInputStream(new File("Record.wav")));
		dos.writeLong(file.length);
		dos.write(file, 0, file.length);
		dos.flush();

		String send_successfully = dis.readUTF();
		dos.close();
		dis.close();
		socket.close();

		return send_successfully;
	}

	public static synchronized void start() {
		try {
			socket = new Socket(hostname, port);

			dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {}
	}
}
