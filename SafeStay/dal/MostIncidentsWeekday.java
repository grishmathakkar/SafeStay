package SafeStay.dal;
/**
 * Author - Sonal Singh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostIncidentsWeekday {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static MostIncidentsWeekday instance = null;
	protected MostIncidentsWeekday() {
		connectionManager = new ConnectionManager();
	}

	public static MostIncidentsWeekday getInstance() {
		if (instance == null) {
			instance = new MostIncidentsWeekday();
		}
		return instance;
	}
	
	public String getWeekdayMostIncidents() throws SQLException {
		String mostIncidentsDay = "SELECT i.DayOfWeek as IncidentDay, count(*) as IncidentsOccured\n" + 
				"FROM incidents i\n" + 
				"GROUP BY (i.DayOfWeek)\n" + 
				"ORDER BY count(*) DESC \n" + 
				"LIMIT 1;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(mostIncidentsDay);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if (results.next()) {
				String day =results.getString("IncidentDay");
				return day;
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
		
		return "";
	}
	
}
