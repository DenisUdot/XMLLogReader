package denisudot.gmail.com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputLog {
	private Date date;
	private String userId, url;
	private long seconds;
	
	public String getAverage() {
		return Long.toString(seconds);
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getData() {
		SimpleDateFormat sd = new SimpleDateFormat("dd-MMMM-yyyy");
		System.out.println(sd.format(date));
		return sd.format(date);
	}
	
	public void setTimeStamp(long timestamp) {
		date = new Date(timestamp);
//		date.setTimeInMillis(timestamp);
		System.out.println(date.toString());
	}
	
	public void setSpentTime(long seconds) {
		this.seconds = seconds;
		System.out.println(seconds);
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
		System.out.println(userId);
	}
	
	public void setUrl(String url) {
		this.url = url;
		System.out.println(url);		
	}
	
	@Override
	public String toString() {
		return userId;
	}	
}
