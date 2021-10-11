package client;

import java.net.*;

import java.io.*;

public class Client {
	public static int port = 9000;
	public static String hostname = "127.0.0.1";

	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static Socket socket;
	
	public static boolean login(String username, String password) throws Exception {
		Client.start();
		dos.writeUTF("LOGIN\n" + username + " " + password);

		boolean able_to_login = Boolean.parseBoolean(dis.readUTF()); 
		
		dos.close();
		dis.close();
		socket.close();
		
		return able_to_login;
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
	
	public static String join_meeting(String meeting_id) throws Exception {	
		Client.start();
		dos.writeUTF("JOIN_MEETING\n" + meeting_id);

		String meeting_data = dis.readUTF();
		
		dos.close();
		dis.close();
		socket.close();
		
		return meeting_data;
	}

	public static void start() {
		try {
			socket = new Socket(hostname, port);

			dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {}
	}
}
