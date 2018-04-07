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
		String rReviewRecommendation = "DROP FUNCTION IF EXISTS resident_user;\n" +
						"DELIMITER\n" +
						"CREATE FUNCTION resident_user(location varchar(255))\n" +
						"RETURNS INT\n" + 
						"BEGIN\n" +
						"RETURN (select count(username) from user_address_log  as ual where\n" + 
								"ual.location = location);\n" +
						"END;\n" + 
						"//\n" +
						"DELIMITER;\n" +

						"DROP FUNCTION IF EXISTS recommendation_user;\n" +
						"DELIMITER //\n" +
						"CREATE FUNCTION recommendation_user(location varchar(255))\n" +
						"RETURNS INT\n" + 
						"BEGIN\n" +
						"RETURN ((select count(ual.username) from\n" +
								"user_address_log as ual inner join recommendation as r\n" +
								"on ual.username = r.username and\n" +
								"ual.location = r.Location\n" +
								"and ual.location = location\n" +
								"group by r.location));\n" +
						"END;\n" + 
						"//\n" +
						"DELIMITER ;\n" +

						"DROP FUNCTION IF EXISTS percentage;\n" +
						"DELIMITER //\n" +
						"CREATE FUNCTION percentage(location varchar(255))\n" +
						"RETURNS INT\n" +
						"BEGIN\n" +
						"SET @resident_user = (select resident_user(location));\n" +
						"SET @recommendation_user = (select recommendation_user(location));\n" +
						"RETURN (@recommendation_user / @resident_user) * 100;\n" +
						"END;\n" +
						"//\n" +
						"DELIMITER ;\n" +
						"select percentage(?) as percent;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(rReviewRecommendation);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if (results.next()) {
				float k=results.getFloat("percent");
				return k;
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