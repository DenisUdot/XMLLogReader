package denisudot.gmail.com;

import java.io.File;
import java.util.List;

public interface Observer {
	public void update(List<File> fileList);
}
