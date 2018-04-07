package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.Administrators;
import SafeStay.model.Incidents;
import SafeStay.model.Maintains;

public class MaintainsDao {

	protected ConnectionManager connectionManager;

	private static MaintainsDao instance = null;

	protected MaintainsDao() {
		connectionManager = new ConnectionManager();
	}

	public static MaintainsDao getInstance() {
		if (instance == null) {
			instance = new MaintainsDao();
		}
		return instance;
	}

	public Maintains create(Maintains maintain) throws SQLException {
		String insertMaintain = "INSERT INTO Maintains(UserName,IncidentId,Created) " + "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertMaintain);
			insertStmt.setString(1, maintain.getAdministrators().getUserName());
			insertStmt.setInt(2, maintain.getIncidents().getIncidentId());
			insertStmt.setTimestamp(3, maintain.getCreated());

			insertStmt.executeUpdate();
			return maintain;
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
			if (resultKey != null) {
				resultKey.close();
			}
		}
	}

	public List<Maintains> getMaintainRecordByUserName(String userName) throws SQLException {
		List<Maintains> maintainsList = new ArrayList<>();
		String selectMaintains = "SELECT UserName,IncidentId,Created " + "FROM Maintains " + "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectMaintains);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			AdministratorsDao administratorsDao = AdministratorsDao.getInstance();
			IncidentsDao incidentsDao = IncidentsDao.getInstance();

			while (results.next()) {
				String resultUserName = results.getString("UserName");
				int resultIncidentId = results.getInt("IncidentId");
				Timestamp resultCreated = results.getTimestamp("Created");
				Administrators resultUser = administratorsDao.getAdministratorByUserName(resultUserName);
				Incidents resultIncidents = incidentsDao.getIncidentByIncidentId(resultIncidentId);
				Maintains maintains = new Maintains(resultUser, resultIncidents, resultCreated);
				maintainsList.add(maintains);

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
		return maintainsList;
	}

	public Maintains delete(Maintains maintain) throws SQLException {
		String deleteMaintain = "DELETE FROM Maintains WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteMaintain);
			deleteStmt.setString(1, maintain.getAdministrators().getUserName());
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

}
