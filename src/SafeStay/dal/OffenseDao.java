package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SafeStay.model.Offense;

public class OffenseDao {

	private static final Character INFILE_FIELD_SEPARATION_CHAR = ',';
	private static final Character INFILE_ENCLOSED_CHAR = '"';

	protected ConnectionManager connectionManager;

	private static OffenseDao instance = null;

	protected OffenseDao() {
		connectionManager = new ConnectionManager();
	}

	public static OffenseDao getInstance() {
		if (instance == null) {
			instance = new OffenseDao();
		}
		return instance;
	}

	public Offense create(Offense offense) throws SQLException {
		String insertOffense = "INSERT INTO Offense(OffenseCode,Description) " + "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertOffense);
			insertStmt.setInt(1, offense.getOffenceCode());
			insertStmt.setString(2, offense.getDescription());
			insertStmt.executeUpdate();
			return offense;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	public Offense delete(Offense offense) throws SQLException {
		String deleteOffense = "DELETE FROM Offense WHERE OffenseCode=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteOffense);
			deleteStmt.setInt(1, offense.getOffenceCode());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	public Offense getOffenseByOffenseCode(int offenseCode) throws SQLException {
		String selectOffense = "SELECT OffenseCode,Description " + "FROM Offense " + "WHERE OffenseCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectOffense);
			selectStmt.setInt(1, offenseCode);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int resultOffenseCode = results.getInt("OffenseCode");
				String description = results.getString("Description");
				Offense offense = new Offense(resultOffenseCode, description);
				return offense;
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
		return null;
	}

	public void importData(String dataFilePath, String tableName) {
		String sql = "LOAD DATA LOCAL INFILE '" + dataFilePath + "' into table " + tableName + " FIELDS TERMINATED BY '"
				+ INFILE_FIELD_SEPARATION_CHAR + "' " + "ENCLOSED BY '" + INFILE_ENCLOSED_CHAR + "' "
				+ "LINES TERMINATED BY '" + System.lineSeparator() + "' " + "IGNORE 1 LINES;";

		Connection conn = null;
		Statement statement = null;
		try {
			conn = connectionManager.getConnection();
			conn.setAutoCommit(true);
			statement = conn.createStatement();
			statement.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
