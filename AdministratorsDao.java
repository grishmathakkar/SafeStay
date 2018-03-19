package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.*;

public class AdministratorsDao extends UsersDao {
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static AdministratorsDao instance = null;

	protected AdministratorsDao() {
		connectionManager = new ConnectionManager();
	}

	public static AdministratorsDao getInstance() {
		if (instance == null) {
			instance = new AdministratorsDao();
		}
		return instance;
	}

	public Administrators create(Administrators admin) throws SQLException {
		// Insert into users first
		UsersDao usersDao = UsersDao.getInstance();
		Users users = new Users(admin.getUserName(), admin.getPassword(), admin.getFirstName(), admin.getLastName(),
				admin.getAge(), admin.getEmail(), admin.getPhone());
		users = usersDao.create(users);
		String insertUser = "INSERT INTO Administrators(UserName,LastLogin) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, admin.getUserName());
			insertStmt.setTimestamp(2, admin.getLastLogin());
			insertStmt.executeUpdate();
			return admin;
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

	public Administrators getAdministratorByUserName(String userName) throws SQLException {
		String selectUser = "SELECT Users.UserName,UserPassword,FirstName,LastName, Age,Email,Phone,LastLogin "
				+ "FROM Administrators INNER JOIN Users " + "  ON  Administrators.UserName = Users.UserName "
				+ "WHERE Administrators.UserName=?;";

		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String resultUserName = results.getString("UserName");
				String resultpassword = results.getString("UserPassword");
				String resultFirstName = results.getString("FirstName");
				String resultLastName = results.getString("LastName");
				int resultAge = results.getInt("Age");
				String resultEmail = results.getString("Email");
				String resultPhone = results.getString("Phone");
				Timestamp resultLastlLogin = results.getTimestamp("LastLogin");
				Administrators admin = new Administrators(resultUserName, resultpassword, resultFirstName,
						resultLastName, resultAge, resultEmail, resultPhone, resultLastlLogin);
				return admin;
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
	
	 public List<Administrators> getAllUsers() throws SQLException {
	        List<Administrators> users = new ArrayList<Administrators>();
	        String selectUser = "SELECT * FROM Administrators;";

			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectUser);
//				selectStmt.setString(1, userName);
				results = selectStmt.executeQuery();
				if (results.next()) {
					String resultUserName = results.getString("UserName");
					String resultpassword = results.getString("UserPassword");
					String resultFirstName = results.getString("FirstName");
					String resultLastName = results.getString("LastName");
					int resultAge = results.getInt("Age");
					String resultEmail = results.getString("Email");
					String resultPhone = results.getString("Phone");
					Timestamp resultLastlLogin = results.getTimestamp("LastLogin");
					Administrators admin = new Administrators(resultUserName, resultpassword, resultFirstName,
							resultLastName, resultAge, resultEmail, resultPhone, resultLastlLogin);
					users.add(admin);
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
			return users;
		}
		

	

	public Administrators delete(String username) throws SQLException {
		String deleteUser = "DELETE FROM Administrators WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, username);
			deleteStmt.executeUpdate();
			Administrators admin = new Administrators(username);
			super.delete(admin);
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
