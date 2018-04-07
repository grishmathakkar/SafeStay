package SafeStay.dal;
/**
 * @author sonalsingh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.ShootingLocation;

public class RatioShootingPerLocation {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.

	private static RatioShootingPerLocation instance = null;
	protected RatioShootingPerLocation() {
		connectionManager = new ConnectionManager();
	}

	public static RatioShootingPerLocation getInstance() {
		if (instance == null) {
			instance = new RatioShootingPerLocation();
		}
		return instance;
	}
	public List<ShootingLocation> getRatioshootingIncidents() throws SQLException{
		List<String> shootingLocations = new ArrayList<String>();
		//List of shootingLocation Objects to store the objects and display list
		List<ShootingLocation> slList = new ArrayList<ShootingLocation>();
		String shootRatio = "SELECT num/den as ratio, overall.location\n" + 
				"FROM (select count(*) as num,i.Location FROM incidents i\n" + 
				"WHERE i.Shooting = 'Y'\n" + 
				"GROUP BY i.location) as shooting INNER JOIN\n" + 
				"(SELECT count(*) as den, i.location FROM incidents i\n" + 
				"GROUP BY i.location) as overall\n" + 
				"ON overall.location = shooting.location;" ;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(shootRatio);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			while (results.next()) {
				String loc = results.getString("location");
				String ratio = results.getString("ratio");
				ShootingLocation sl = new ShootingLocation(loc,ratio);
				slList.add(sl);
				//String res = loc+" , "+ ratio;
				//shootingLocations.add(res);
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
		return slList;
	}
}
