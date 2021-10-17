package client;

import java.net.*;

import java.io.*;

public class Client {
	public static int port = 9000;
	public static String hostname = "127.0.0.1";

	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static Socket socket;
	
	public static String login(String username, String password) throws Exception {
		Client.start();
		dos.writeUTF("LOGIN\n" + username + " " + password);

		String account_id = dis.readUTF(); 
		
		dos.close();
		dis.close();
		socket.close();
		
		return account_id;
	}
	
	public static boolean register(String username, String password) throws Exception {
		Client.start();
		dos.writeUTF("REGISTER\n" + username + " " + password);

		boolean register_successful = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return register_successful;
	}

	public static String create_meeting(String meeting_data) throws Exception {	
		Client.start();
		dos.writeUTF("CREATE_MEETING\n" + meeting_data);

		String meeting_id = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_id;
	}
	
	public static String join_meeting(String meeting_id, String joiner_meeting_id) throws Exception {	
		Client.start();
		dos.writeUTF("JOIN_MEETING\n" + meeting_id + '\n' + joiner_meeting_id);

		String meeting_information = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_information;
	}

	public static boolean send_meeting_data(String user_id, String meeting_id, String app_activity_log) throws Exception {
		Client.start();
		dos.writeUTF("SEND_MEETING_DATA\n" + user_id + '\n' + meeting_id + '\n' + app_activity_log);

		boolean send_succesfully = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return send_succesfully;
	}
	
	public static String get_joiner_app_activity(String meeting_id) throws Exception {
		Client.start();
		dos.writeUTF("GET_MEETING_DATA\n" + meeting_id);
		String meeting_data = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_data;
	}
	

	public static boolean change_meeting_information(String meeting_id, String meetingDataString) throws Exception {
		Client.start();
		dos.writeUTF("CHANGE_MEETING_INFO\n" + meeting_id + '\n' + meetingDataString);

		boolean change_succesfully = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return change_succesfully;
	}
	
	public static boolean change_account_information(String account_id, String account_info) throws Exception {
		Client.start();
		dos.writeUTF("CHANGE_ACCOUNT_INFO\n" + account_id + '\n' + account_info);

		boolean change_succesfully = Boolean.parseBoolean(dis.readUTF());
		
		dos.close();
		dis.close();
		socket.close();
		
		return change_succesfully;
	}
	
	public static String get_account_info(String account_id) throws IOException {
		Client.start();
		dos.writeUTF("GET_ACCOUNT_INFO\n" + account_id);
		
		String account_info = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return account_info;
	}
	
	public static void start() {
		try {
			socket = new Socket(hostname, port);

			dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {}
	}
}
