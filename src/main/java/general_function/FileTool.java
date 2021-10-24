package general_function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileTool {
	public static String read_file(String path) throws Exception {
		String result = "";

		BufferedReader reader = new BufferedReader(new FileReader(path));
		String currentLine = "";
		while ((currentLine = reader.readLine()) != null)
			result += currentLine + '\n';

		reader.close();
		return result;
	}

	public static synchronized void write_file(String data, String path) throws Exception {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"))) {
			writer.write(data);
		}
	}
	
	public static synchronized void deleteFolder(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) for (File f : contents) deleteFolder(f);
	    file.delete();
	}
}
