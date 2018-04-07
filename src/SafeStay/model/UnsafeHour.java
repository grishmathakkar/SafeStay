package SafeStay.model;
//query 8
public class UnsafeHour {
	protected int hour;
	protected String location;
	
	public UnsafeHour(int hour, String location) {
		this.hour = hour;
		this.location = location;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
