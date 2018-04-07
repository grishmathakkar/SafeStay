package SafeStay.model;

public class Address {
	protected String location;
	protected String street;
	protected String longitude;
	protected String latitude;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Address(String location, String street, String longitude, String latitude) {
		this.location = location;
		this.street = street;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Address(String location) {
		this.location = location;
	}
	

}
