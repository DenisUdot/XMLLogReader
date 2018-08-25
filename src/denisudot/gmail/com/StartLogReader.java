package denisudot.gmail.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartLogReader {

	public static void main(String[] args) {
		DirectoryReader.getInstance();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(int i = 0; i < DirectoryReader.getNumbersOfFile(); i++) {
			executor.execute(new MyXMLReader(DirectoryReader.getFile()));
		}
		executor.shutdown();
		
	}

}
