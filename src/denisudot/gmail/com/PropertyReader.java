package denisudot.gmail.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	private String readingFolder;
	private String writingFolder;
	
	public void readProperty() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("resources/config.properties");
			prop.load(input);
			readingFolder = prop.getProperty("readingfolder");
			writingFolder = prop.getProperty("writingfolder");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getReadingFolder() {
		if(readingFolder == null) {
			readProperty();
		}
		return readingFolder;
	}
	
	public String getWritingFolder() {
		if(writingFolder == null) {
			readProperty();
		}
		return writingFolder;
	}
}
