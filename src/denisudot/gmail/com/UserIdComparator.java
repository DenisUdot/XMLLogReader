package denisudot.gmail.com;

import java.util.Comparator;

public class UserIdComparator implements Comparator<InputLog>{
		@Override
		public int compare(InputLog one, InputLog two) {
			return extractInt(one.getUserId()) - extractInt(two.getUserId());
		}
		
		int extractInt(String s) {
		    String num = s.replaceAll("\\D", "");
		    // return 0 if no digits found
		    return num.isEmpty() ? 0 : Integer.parseInt(num);
		}		
}
