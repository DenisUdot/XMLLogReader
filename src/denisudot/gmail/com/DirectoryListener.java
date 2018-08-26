package denisudot.gmail.com;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public final class DirectoryListener {
	private File[] files;
	private FilesData filesData;
	private String readingFolder;
	
	public DirectoryListener(FilesData filesData, String readingFolder) {
		this.filesData = filesData;
		this.readingFolder = readingFolder;
	}
	
	public void readFileList() {
		files = new File(readingFolder).listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) { 
				return name.endsWith(".xml"); 
				} 
			});
	
		for(File f:files) {
			filesData.addFileName(f);
		}
		
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(readingFolder);
			WatchKey key = path.register( watchService, StandardWatchEventKinds.ENTRY_CREATE);
			
	        while ((key = watchService.take()) != null) {
	        	
	            for (WatchEvent<?> event : key.pollEvents()) {
	                System.out.println("File created: " + event.context());
	                String eventName = event.context().toString();
	                String extension = "";
	                int i = eventName.lastIndexOf('.');
	                if (i >= 0) {
	                    extension = eventName.substring(i+1);
	                    if(extension.equals("xml")) {
	                    	filesData.addFileName(new File(readingFolder+"/"+eventName));
	                    }
	                }             
	            }
	            key.reset();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
