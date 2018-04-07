//Query 4
/**
 * Author - Sonal Singh
 */
package SafeStay.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.LocationRequestPercentage;

public class PercentageRequestsHighestLocations {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static PercentageRequestsHighestLocations instance = null;
	protected PercentageRequestsHighestLocations() {
		connectionManager = new ConnectionManager();
	}

	public static PercentageRequestsHighestLocations getInstance() {
		if (instance == null) {
			instance = new PercentageRequestsHighestLocations();
		}
		return instance;
	}
	
	//gets the streets with highest number of requests i.e, searched most often
	public List<LocationRequestPercentage> getMostRequestedLocations() throws SQLException{
		//List<String> TopLocations = new ArrayList<String>();
		List<LocationRequestPercentage> TopLocations = new ArrayList<LocationRequestPercentage>();
		String mostRequested = "SELECT a.Street, count(r.requestid) as HighestRequest,\n" + 
				"(SELECT count(*) FROM requests) as TotalRequests,\n" + 
				"(count(r.requestid) / (SELECT count(*) FROM requests) * 100)\n" + 
				"as percentage\n" + 
				"FROM requests r\n" + 
				"INNER JOIN address a\n" + 
				"ON r.location = a.Location\n" + 
				"GROUP BY r.location;" ;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(mostRequested);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String street =results.getString("Street");
				String reqCount = results.getString("HighestRequest");
				String totReq = results.getString("TotalRequests");
				String per = results.getString("percentage");
				String res = street+" , "+ reqCount+" , "+per;
				LocationRequestPercentage lrp = new LocationRequestPercentage(street,reqCount,totReq,per);
				TopLocations.add(lrp);
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
		return TopLocations;
	}
	
}
