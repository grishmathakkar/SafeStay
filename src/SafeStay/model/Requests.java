package SafeStay.model;

public class Requests {

	protected int requestId;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	protected EndUsers user;

	public EndUsers getUser() {
		return user;
	}

	public void setUser(EndUsers user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	protected Address address;

	public Requests(int requestId, EndUsers user, Address address) {
		this.requestId = requestId;
		this.user = user;
		this.address = address;
	}

	public Requests(EndUsers user, Address address) {
		this.user = user;
		this.address = address;
	}

	public Requests(int requestId) {
		this.requestId = requestId;
	}

}
