package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SafestAreasTop20 {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static SafestAreasTop20 instance = null;

	protected SafestAreasTop20() {
		connectionManager = new ConnectionManager();
	}

	public static SafestAreasTop20 getInstance() {
		if (instance == null) {
			instance = new SafestAreasTop20();
		}
		return instance;

	}

	public List<String> getTop20SafestAreas() throws SQLException {
		List<String> safAreas = new ArrayList<String>();
		String top20SafestAreas = "SELECT i.location ,count(*)\n" + "FROM incidents I LEFT JOIN address a\n"
				+ "ON i.Location = a.Location\n" + "GROUP BY i.Location\n" + "ORDER BY count(*)\n" + "LIMIT 20;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(top20SafestAreas);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String k = results.getString("location");
				safAreas.add(k);

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
		return safAreas;
	}

}
