package SafeStay.dal;

/**
 * author - sonalsingh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import SafeStay.model.Offense;

public class MostOffenseForLocation {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static MostOffenseForLocation instance = null;

	protected MostOffenseForLocation() {
		connectionManager = new ConnectionManager();
	}

	public static MostOffenseForLocation getInstance() {
		if (instance == null) {
			instance = new MostOffenseForLocation();
		}
		return instance;
	}

	public String getOffenseCodeForLocation(String location) throws SQLException {
		String mostoffsense = "SELECT i1.OffenseCode,i1.Location\n" + "FROM incidents i1\n" + "WHERE i1.IncidentId=(\n"
				+ "SELECT i.IncidentId\n" + "FROM incidents i WHERE i.location =? \n" + "GROUP BY i.OffenseCode\n"
				+ "ORDER BY count(IncidentId) DESC\n" + "LIMIT 1);";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(mostoffsense);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int code = results.getInt("OffenseCode");
				OffenseDao offenseDao = OffenseDao.getInstance();
				Offense offense = offenseDao.getOffenseByOffenseCode(code);
				return offense.getDescription();

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
