package denisudot.gmail.com;

import java.io.File;
import java.io.FilenameFilter;

public final class DirectoryReader {
	private static DirectoryReader instance;
	private static File[] files;
	private static int i;
	private DirectoryReader() {
	}
	
	public static synchronized DirectoryReader getInstance() {
		if (instance == null) {
			instance = new DirectoryReader();
			getFileList();
		}
		return instance;
	}
	
	private static void getFileList() {
		//FilenameFilet return only xml file
		//scr directory need to change
		files = new File("src").listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) { 
				return name.endsWith(".xml"); 
				} 
			});
		i = 0;
	}
	
	public static synchronized File getFile() {
			i++;
			return i < files.length ? files[i-1]: null;
	}
}
