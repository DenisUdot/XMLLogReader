package denisudot.gmail.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartLogReader implements Observer {
	
	private String writingFolder, readingFolder;
	private ExecutorService executor;
	
	public static void main(String[] args) {
		StartLogReader startLogReader = new StartLogReader();
		startLogReader.go();		
	}

	public void go() {
		 new Thread() {
		        @Override
		        public void run() {
		        	while(true) {
		        		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			    			try {
								if(br.readLine().toLowerCase().equals("exit")){
									System.exit(0);
								}
								sleep(1000);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
		        	}
		     }
		}.start();
		PropertyReader pr = new PropertyReader();
		pr.readProperty();
		readingFolder = pr.getReadingFolder();
		writingFolder = pr.getWritingFolder();
		FilesData filesData = new FilesData();
		filesData.registerOserver(this);
		DirectoryListener dirReader = new DirectoryListener(filesData, readingFolder);
		dirReader.readFileList();
		
		
	}
	
	@Override
	public void update(List<File> fileList) {
		if(executor == null) {
			executor = Executors.newFixedThreadPool(10);
		}
		
		for(int i = 0; i < fileList.size(); i++) {
			executor.execute(new MyXMLReader(fileList.get(0),writingFolder));
			fileList.remove(0);
		}
	}
	


}
