package SafeStay.model;

import java.sql.Date;

public class EndUsers extends Users {
	protected String userName;
	protected Date dateOfBirth;

	public EndUsers(String userName, String password, String firstName, String lastName, int age, String email,
			String phone, Date dob) {
		super(userName, password, firstName, lastName, age, email, phone);
		this.userName = userName;
		this.dateOfBirth = dob;
	}

	public EndUsers(String userName) {
		super(userName);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dob) {
		dateOfBirth = dob;
	}

}
