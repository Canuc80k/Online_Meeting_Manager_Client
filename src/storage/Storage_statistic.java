package storage;

import java.awt.Desktop;
import java.io.File;

import general_function.FileTool;

public class Storage_statistic {
	public static final String STORAGE_PATH_FILE_PATH = "config/storage_path";
	
	public static String get_storage_path() {
		String path = "";
		
		try {
			path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
		} catch(Exception e) {
			path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
		}
		
		return path;
	}
	
	
	// TODO: Add param meeting_id to iz to search
	public static void show_recieved_user_statistic_folder() throws Exception {
		if (!(new File(STORAGE_PATH_FILE_PATH)).exists()) FileTool.write_file("", STORAGE_PATH_FILE_PATH);
	
		try {
			String folder_path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
			Desktop.getDesktop().open(new File(folder_path));
		} catch(Exception e) {
			String folder_path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
			Desktop.getDesktop().open(new File(folder_path));
		}
	}
	
	public static void show_my_statistic_folder() throws Exception {
		if (!(new File(STORAGE_PATH_FILE_PATH)).exists()) FileTool.write_file("", STORAGE_PATH_FILE_PATH);
	
		try {
			String folder_path = FileTool.read_file(STORAGE_PATH_FILE_PATH).trim();
			Desktop.getDesktop().open(new File(folder_path));
		} catch(Exception e) {
			String folder_path = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getPath();
			Desktop.getDesktop().open(new File(folder_path));
		}
	}
}
