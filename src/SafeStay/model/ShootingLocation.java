package SafeStay.model;

public class ShootingLocation {
	protected String location;
	protected String ratio;
	
	public ShootingLocation(String loc, String rat) {
		this.location = loc;
		this.ratio = rat;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


	
	
}
