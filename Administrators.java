package SafeStay.model;

import java.sql.Timestamp;

// An administrator inherits Users
public class Administrators extends Users {
	protected Timestamp lastLogin;
	protected String userName;

	// A constructor to create an administrator object
	public Administrators(String userName, String password, String firstName, String lastName, int age, String email,
			String phone, Timestamp lastLogin) {
		super(userName, password, firstName, lastName, age, email, phone);
		this.lastLogin = lastLogin;
		this.userName = userName;
	}

	public Administrators(String username2) {
		super(username2);
	}
	
	

	// getters and setters to access the data members
	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
