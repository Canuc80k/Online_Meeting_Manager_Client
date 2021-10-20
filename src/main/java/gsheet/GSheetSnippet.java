package gsheet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class GSheetSnippet {
    public static final String APPLICATION_NAME = "Meeting Online Manager";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = Testing.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    public static File createNewSpreadSheet(String fileName, Drive driveService) throws Exception {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");

        java.io.File filePath = new java.io.File("src/main/resources/new_spreadsheet.csv");
        FileContent mediaContent = new FileContent("text/csv", filePath);
        File file = driveService.files().create(fileMetadata, mediaContent)
            .setFields("id")
            .execute();
        
        return file;
    }

    public static File createNewFolder(String fileName, Drive driveService) throws Exception {
    	File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = driveService.files().create(fileMetadata)
            .setFields("id")
            .execute();
        
        return file;
    }

    public File createSubFolder(String parentFolderId, String subFolderName, Drive driveService) {
		System.out.println("Sub Folder Name: " + subFolderName);
		File file = null;
		try {
			File fileMetadata = new File();
			fileMetadata.setName(subFolderName);
			fileMetadata.setMimeType("application/vnd.google-apps.folder");
			if (parentFolderId != null) {
				List<String> parents = Arrays.asList(parentFolderId);
				fileMetadata.setParents(parents);
			}
			file = driveService.files().create(fileMetadata).setFields("id, name").execute();
			return file;
		} catch (IOException e) {e.printStackTrace();}
		return null;
	}
}
