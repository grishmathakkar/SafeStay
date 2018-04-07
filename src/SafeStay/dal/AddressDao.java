package SafeStay.dal;

import SafeStay.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressDao {
	private static final Character INFILE_FIELD_SEPARATION_CHAR = ',';
	private static final Character INFILE_ENCLOSED_CHAR = '"';
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static AddressDao instance = null;

	protected AddressDao() {
		connectionManager = new ConnectionManager();
	}

	public static AddressDao getInstance() {
		if (instance == null) {
			instance = new AddressDao();
		}
		return instance;

	}

	public Address create(Address address) throws SQLException {
		// Grishma Thakkar
		String createAddress = "INSERT INTO Address(Location,Street,Longitude, Latitude) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(createAddress);
			insertStmt.setString(1, address.getLocation());
			insertStmt.setString(2, address.getStreet());
			insertStmt.setString(3, address.getLongitude());
			insertStmt.setString(4, address.getLatitude());
			insertStmt.executeUpdate();
			return address;

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

	public Address getAddressByLocation(String location) throws SQLException {
		String selAddress = "SELECT * FROM Address WHERE location=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selAddress);
			selectStmt.setString(1, location);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String rlocation = results.getString("Location");
				String rstreet = results.getString("Street");
				String rlatitude = results.getString("Latitude");
				String rlongitude = results.getString("Longitude");
				Address address = new Address(rlocation, rstreet, rlongitude, rlatitude);
				return address;
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

	public Address delete(Address address) throws SQLException {
		String delAddress = "DELETE FROM Address WHERE location=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(delAddress);
			deleteStmt.setString(1, address.getLocation());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available");
			}
			// Return null so the caller can no longer operate on the Users instance.
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
	
	public void importData(String dataFilePath, String tableName) {
		String sql = "LOAD DATA LOCAL INFILE '" + dataFilePath + "' into table " + tableName + " FIELDS TERMINATED BY '"
				+ INFILE_FIELD_SEPARATION_CHAR + "' " + "ENCLOSED BY '" + INFILE_ENCLOSED_CHAR + "' "
				+ "LINES TERMINATED BY '" + System.lineSeparator() + "' " + "IGNORE 1 LINES;";

		Connection conn = null;
		Statement statement = null;
		try {
			conn = connectionManager.getConnection();
			conn.setAutoCommit(true);
			statement = conn.createStatement();
			statement.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
