package SafeStay.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SafeStay.model.*;

public class EndUsersDao extends UsersDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static EndUsersDao instance = null;

	protected EndUsersDao() {
		connectionManager = new ConnectionManager();
	}

	public static EndUsersDao getInstance() {
		if (instance == null) {
			instance = new EndUsersDao();
		}
		return instance;
	}

	public EndUsers create(EndUsers endUser) throws SQLException {
		UsersDao usersDao = UsersDao.getInstance();
		Users users = new Users(endUser.getUserName(), endUser.getPassword(), endUser.getFirstName(),
				endUser.getLastName(), endUser.getAge(), endUser.getEmail(), endUser.getPhone());
		users = usersDao.create(users);
		String insertUser = "INSERT INTO EndUsers(UserName,DateOfBirth) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, endUser.getUserName());
			insertStmt.setDate(2, endUser.getDateOfBirth());
			insertStmt.executeUpdate();
			return endUser;
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

	public EndUsers getEndUserByUserName(String userName) throws SQLException {
		String selectUser = "SELECT Users.UserName,UserPassword,FirstName,LastName, Age,Email,Phone,DateOfBirth "
				+ "FROM EndUsers INNER JOIN Users " + "  ON  EndUsers.UserName = Users.UserName "
				+ "WHERE EndUsers.UserName=?;";
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
				String resultPassword = results.getString("UserPassword");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				int resultAge = results.getInt("Age");
				String resultEmail = results.getString("Email");
				String resultPhone = results.getString("Phone");
				Date resultDob = results.getDate("DateOfBirth");
				EndUsers user = new EndUsers(resultUserName, resultPassword, resultFirstName, resultLastName, resultAge,
						resultEmail, resultPhone, resultDob);
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

	public EndUsers delete(String username) throws SQLException {
		String deleteUser = "DELETE FROM EndUsers WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, username);
			deleteStmt.executeUpdate();
			EndUsers user = new EndUsers(username);
			super.delete(username);
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
