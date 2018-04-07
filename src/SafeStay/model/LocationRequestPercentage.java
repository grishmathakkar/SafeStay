package SafeStay.model;
//query 4 
public class LocationRequestPercentage {
	protected String locStreet;
	protected String highRequest;
	protected String totalRequest;
	protected String percentage;
	
	public LocationRequestPercentage(String locStreet, String highRequest,String totalRequest,String percentage) {
		this.locStreet = locStreet;
		this.highRequest = highRequest;
		this.totalRequest = totalRequest;
		this.percentage = percentage;
	}

	public String getLocStreet() {
		return locStreet;
	}

	public void setLocStreet(String locStreet) {
		this.locStreet = locStreet;
	}

	public String getHighRequest() {
		return highRequest;
	}

	public void setHighRequest(String highRequest) {
		this.highRequest = highRequest;
	}

	public String getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(String totalRequest) {
		this.totalRequest = totalRequest;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	
}
