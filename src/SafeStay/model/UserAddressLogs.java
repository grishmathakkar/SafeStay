package SafeStay.model;

import java.util.Date;

public class UserAddressLogs {
	protected int userAddressId;
	protected EndUsers endUser;
	protected Address address;
	protected Date startDate;
	protected Date endDate;

	public UserAddressLogs(int userAddressId, EndUsers endUser, Address address, Date startDate, Date endDate) {
		this.userAddressId = userAddressId;
		this.endUser = endUser;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UserAddressLogs(int userAddressId) {
		this.userAddressId = userAddressId;
	}

	public UserAddressLogs(EndUsers endUser, Address address, Date startDate, Date endDate) {
		this.endUser = endUser;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(int userAddressId) {
		this.userAddressId = userAddressId;
	}

	public EndUsers getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUsers endUser) {
		this.endUser = endUser;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
