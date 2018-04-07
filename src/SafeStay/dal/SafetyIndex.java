package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SafetyIndex {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static SafetyIndex instance = null;

	protected SafetyIndex() {
		connectionManager = new ConnectionManager();
	}

	public static SafetyIndex getInstance() {
		if (instance == null) {
			instance = new SafetyIndex();
		}
		return instance;

	}

	public List<String> getSafetyIndex() throws SQLException {
		List<String> safAreas = new ArrayList<String>();
		String safetyIndex = "select i.Location,a.Street,\n"
				+ "(count(*)/(select count(*) from incidents)) as safetyIndex\n"
				+ "from incidents i inner join address a\n" + "on i.Location = a.Location\n" + "group by i.Location;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(safetyIndex);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String k = results.getString("street");
				String m = results.getString("safetyIndex");
				safAreas.add(k + " 	" + m);
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
