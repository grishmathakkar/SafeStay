package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PercentageRecommendLocation {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static PercentageRecommendLocation instance = null;

	protected PercentageRecommendLocation() {
		connectionManager = new ConnectionManager();
	}

	public static PercentageRecommendLocation getInstance() {
		if (instance == null) {
			instance = new PercentageRecommendLocation();
		}
		return instance;

	}

	public float getPercentageRecommendLocation(String location) throws SQLException {
		String rReviewRecommendation = "select (select count(ual.username) as recommendation_user from\r\n"
				+ "user_address_log as ual inner join recommendation as r\r\n" + "on ual.username = r.username and\r\n"
				+ "ual.location = r.Location\r\n" +
				// "and ual.location = '(42.23241330, -71.12971531)'\r\n" +
				"and ual.location =? \r\n"
				+ "group by r.location) / (select count(username) as resident_user from user_address_log  as ual where \r\n"
				+ "ual.location = ?) as finalpercent";
		// "ual.location = '(42.23241330, -71.12971531)') as finalpercent;";

		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(rReviewRecommendation);
			selectStmt.setString(1, location);
			selectStmt.setString(2, location);
			results = selectStmt.executeQuery();
			while (results.next()) {
				float k = results.getFloat("finalpercent");
				return k * 100;
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
		return 0.0f;
	}

}