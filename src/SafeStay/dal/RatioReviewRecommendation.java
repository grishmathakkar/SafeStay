package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RatioReviewRecommendation {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static RatioReviewRecommendation instance = null;
	protected RatioReviewRecommendation() {
		connectionManager = new ConnectionManager();
	}

	public static RatioReviewRecommendation getInstance() {
		if (instance == null) {
			instance = new RatioReviewRecommendation();
		}
		return instance;

	}
	public float getRatioOfRecommendationAndReview() throws SQLException {
		String rReviewRecommendation = "SELECT (SELECT COUNT(*) FROM recommendation) as totalRecommendations ,\n" + 
				"(SELECT COUNT(*) FROM review)as totalReviews,\n" + 
				"((SELECT COUNT(*) FROM recommendation) / (SELECT COUNT(*) FROM review)) as\n" + 
				"ratio;";
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
				float k=results.getFloat("ratio");
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
