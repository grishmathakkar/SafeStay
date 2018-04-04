package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SafeStay.model.*;
import SafeStay.dal.ConnectionManager;

public class UsersDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;

	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}

	public static UsersDao getInstance() {
		if (instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users user) throws SQLException {
		String insertUser = "INSERT INTO Users(UserName,UserPassword,FirstName,LastName, Age,Email,Phone) VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getPassword());
			insertStmt.setString(3, user.getFirstName());
			insertStmt.setString(4, user.getLastName());
			insertStmt.setInt(5, user.getAge());
			insertStmt.setString(6, user.getEmail());
			insertStmt.setString(7, user.getPhone());
			insertStmt.executeUpdate();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	public Users getUserByUserName(String userName) throws SQLException {
		String selectUser = "SELECT UserName,UserPassword,FirstName,LastName, Age,Email,Phone FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("UserPassword");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				int age = results.getInt("Age");
				String email = results.getString("Email");
				String phone = results.getString("Phone");
				Users user = new Users(resultUserName, password, firstName, lastName, age, email, phone);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	public Users delete(String username) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, username);
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

}
