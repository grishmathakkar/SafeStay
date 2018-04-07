package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnsafeStreets {
	protected ConnectionManager connectionManager;

	private static UnsafeStreets instance = null;

	protected UnsafeStreets() {
		connectionManager = new ConnectionManager();
	}

	public static UnsafeStreets getInstance() {
		if (instance == null) {
			instance = new UnsafeStreets();
		}
		return instance;

	}

	public List<String> getUnsafeStreets() throws SQLException {
		List<String> unsafAreas = new ArrayList<String>();
		String unsafeStreets = "select street,count(*) as incidentcnt from\n" + "incidents i inner join address a\n"
				+ "on i.Location = a.Location\n" + "where a.Street is not null\n" + "group by a.street\n"
				+ "order by incidentcnt desc\n" + "limit 20;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(unsafeStreets);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String km = results.getString("street");
				unsafAreas.add(km);

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
		return unsafAreas;
	}

}
