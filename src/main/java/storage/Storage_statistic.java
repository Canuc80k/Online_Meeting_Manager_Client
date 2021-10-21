package storage;

import java.awt.Desktop;
import java.io.File;

import general_function.FileTool;

public class Storage_statistic {
	public static final String STORAGE_PATH_FILE_PATH = "src/main/resources/config/storage_path";
	
	public static String get_storage_path() {
		String path = "";
		
		try {
			path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
		} catch(Exception e) {
			path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
		}
		
		return path;
	}
	
	public static void show_recieved_user_statistic_folder(String meeting_id) throws Exception {
		if (!(new File(STORAGE_PATH_FILE_PATH)).exists()) FileTool.write_file("", STORAGE_PATH_FILE_PATH);
		
		try {
			String folder_path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
			folder_path += "/created_meeting_statistic/";
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			folder_path += meeting_id;
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			
			Desktop.getDesktop().open(new File(folder_path));
		} catch(Exception e) {
			String folder_path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
			folder_path += "/created_meeting_statistic/";
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			folder_path += meeting_id;
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			
			Desktop.getDesktop().open(new File(folder_path));
		}
	}
	
	public static void show_my_statistic_folder(String meeting_id) throws Exception {
		if (!(new File(STORAGE_PATH_FILE_PATH)).exists()) FileTool.write_file("", STORAGE_PATH_FILE_PATH);
		
		try {
			String folder_path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
			folder_path += "/joined_meeting_statistic/";
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			folder_path += meeting_id;
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			
			Desktop.getDesktop().open(new File(folder_path));
		} catch(Exception e) {
			String folder_path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
			folder_path += "/joined_meeting_statistic/";
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			folder_path += meeting_id;
			if (!(new File(folder_path)).exists()) new File(folder_path).mkdirs();
			
			Desktop.getDesktop().open(new File(folder_path));
		}
	}
}
