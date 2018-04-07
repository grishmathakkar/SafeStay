package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Requests;

public class RequestsDao {

	protected ConnectionManager connectionManager;

	private static RequestsDao instance = null;

	protected RequestsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RequestsDao getInstance() {
		if (instance == null) {
			instance = new RequestsDao();
		}
		return instance;
	}

	public Requests create(Requests request) throws SQLException {
		String insertRequest = "INSERT INTO Requests(UserName,Location) " + "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, request.getUser().getUserName());
			insertStmt.setString(2, request.getAddress().getLocation());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int requestId = -1;
			if (resultKey.next()) {
				requestId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			request.setRequestId(requestId);
			return request;
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
			if (resultKey != null) {
				resultKey.close();
			}
		}
	}

	public Requests getRequestById(int requestId) throws SQLException {
		String selectRequest = "SELECT RequestId,UserName,Location " + "FROM Requests " + "WHERE RequestId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRequest);
			selectStmt.setInt(1, requestId);
			results = selectStmt.executeQuery();
			EndUsersDao usersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int resultRequestId = results.getInt("RequestId");
				String resultUserName = results.getString("UserName");
				String resultLocation = results.getString("Location");
				EndUsers resultUser = usersDao.getEndUserByUserName(resultUserName);
				Address resultAddress = addressDao.getAddressByLocation(resultLocation);
				Requests requests = new Requests(resultRequestId, resultUser, resultAddress);
				return requests;
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

	public List<Requests> getRequestsByLocation(String location) throws SQLException {
		List<Requests> requestsList = new ArrayList<>();
		String selectRequests = "SELECT RequestId,UserName,Location " + "FROM Requests " + "WHERE Location=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRequests);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			EndUsersDao usersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int resultRequestId = results.getInt("RequestId");
				String resultUserName = results.getString("UserName");
				String resultLocation = results.getString("Location");
				EndUsers resultUser = usersDao.getEndUserByUserName(resultUserName);
				Address resultAddress = addressDao.getAddressByLocation(resultLocation);
				Requests requests = new Requests(resultRequestId, resultUser, resultAddress);
				requestsList.add(requests);

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
		return requestsList;

	}

	public Requests delete(Requests request) throws SQLException {
		String deleteRequest = "DELETE FROM Requests WHERE RequestId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRequest);
			deleteStmt.setInt(1, request.getRequestId());
			deleteStmt.executeUpdate();
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
