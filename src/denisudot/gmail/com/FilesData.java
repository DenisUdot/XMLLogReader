package denisudot.gmail.com;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilesData implements Subject{
	private List<Observer> observers;
	private List<File> fileList;
	
	public FilesData() {
		observers = new ArrayList<Observer>();
		fileList = new ArrayList<File>();
		Collections.synchronizedList(fileList);
	}
	
	@Override
	public void registerOserver(Observer o) {
		observers.add(o);		
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if(i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i++) {
			observers.get(i).update(fileList);
		}
	}
	
	public void addFileName(File fileName) {
		fileList.add(fileName);
		notifyObservers();
	}
	
	public void addFileList(List<File> fileList) {
		this.fileList.addAll(fileList);
		notifyObservers();
	}

}
