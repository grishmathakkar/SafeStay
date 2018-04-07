package SafeStay.dal;
/**
 * author - sonalsingh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
	
	public int getOffenseCodeForLocation() throws SQLException{
		Scanner sc = new Scanner(System.in);
		//System.out.println("Enter the location for which you want to see the offsense that occurs the most : ");
		//String loc = sc.nextLine();
		String mostoffsense = "SELECT i1.OffenseCode,i1.Location\n" + 
				"FROM incidents i1\n" + 
				"WHERE i1.IncidentId=(\n" + 
				"SELECT i.IncidentId\n" + 
				"FROM incidents i WHERE i.location = \"(42.31200743, -71.06650940)\"\n" + 
				"GROUP BY i.OffenseCode\n" + 
				"ORDER BY count(IncidentId) DESC\n" + 
				"LIMIT 1);";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(mostoffsense);
			//selectStmt.setString(1, loc);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if (results.next()) {
				int code =results.getInt("OffenseCode");
				return code;
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
		
		return 0;
		
	}
}
