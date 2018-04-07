package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SafeStay.model.*;


public class MostUnsafeHour {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static MostUnsafeHour instance = null;
	protected MostUnsafeHour() {
		connectionManager = new ConnectionManager();
	}

	public static MostUnsafeHour getInstance() {
		if (instance == null) {
			instance = new MostUnsafeHour();
		}
		return instance;

	}
	public UnsafeHour getMostUnsafeHour(String location) throws SQLException {
		String unsafeHour = "select i1.Hours as Hours,i1.Location as Location from incidents i1 where i1.IncidentId=(" +
							"select i.IncidentId from incidents i where i.location = ?" +
							" group by Hours order by count(IncidentId) desc limit 1);";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(unsafeHour);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int hour = results.getInt("Hours");
				String rlocation = results.getString("Location");
				UnsafeHour unsafeHours = new UnsafeHour(hour, rlocation);
				return unsafeHours;
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

}