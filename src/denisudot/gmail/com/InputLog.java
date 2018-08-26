package denisudot.gmail.com;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
	
	public String getDate() {
		SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		sd.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sd.format(date).toUpperCase();
	}
	
	public void setTimeStamp(long timestamp) {
		date = new Date(timestamp);
	}
	
	public void setSpentTime(long seconds) {
		this.seconds = seconds;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public void setUrl(String url) {
		this.url = url;		
	}
	
	@Override
	public String toString() {
		return userId;
	}	
}
