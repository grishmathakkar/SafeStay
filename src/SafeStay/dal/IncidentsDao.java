package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.*;
import SafeStay.model.Incidents.Shootings;
import SafeStay.model.Incidents.UCRs;

public class IncidentsDao {
	private static final Character INFILE_FIELD_SEPARATION_CHAR = ',';
	private static final Character INFILE_ENCLOSED_CHAR = '"';
	protected ConnectionManager connectionManager;

	private static IncidentsDao instance = null;

	protected IncidentsDao() {
		connectionManager = new ConnectionManager();
	}

	public static IncidentsDao getInstance() {
		if (instance == null) {
			instance = new IncidentsDao();
		}
		return instance;
	}

	public Incidents create(Incidents incident) throws SQLException {
		String insertIncident = "INSERT INTO Incidents(OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,"
				+ "DayOfWeek,Hours,UCR,Location) " + "VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;

		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertIncident, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, incident.getOffense().getOffenceCode());
			insertStmt.setString(2, incident.getDistrict());
			insertStmt.setInt(3, incident.getReportingArea());
			insertStmt.setString(4, incident.getShootings().name());
			insertStmt.setTimestamp(5, new Timestamp(incident.getOccuredOnDate().getTime()));
			insertStmt.setString(6, incident.getDayOfWeek());
			insertStmt.setInt(7, incident.getHour());
			insertStmt.setString(8, incident.getUcRs().name());
			insertStmt.setString(9, incident.getAddress().getLocation());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int incidentId = -1;
			if (resultKey.next()) {
				incidentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			incident.setIncidentId(incidentId);
			return incident;
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

	public Incidents delete(Incidents incident) throws SQLException {
		String deleteIncident = "DELETE FROM Incidents WHERE IncidentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteIncident);
			deleteStmt.setInt(1, incident.getIncidentId());
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

	public Incidents getIncidentByIncidentId(int incidentId) throws SQLException {
		String selectIncident = "SELECT IncidentId,OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,"
				+ "DayOfWeek,Hours,UCR,Location " + "FROM Incidents " + "WHERE IncidentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIncident);
			selectStmt.setInt(1, incidentId);
			results = selectStmt.executeQuery();
			OffenseDao offensesDao = OffenseDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();
			while (results.next()) {
				int resultincidentId = results.getInt("IncidentId");
				int resultOffensecode = results.getInt("OffenseCode");
				String resultDistrict = results.getString("District");
				int resultReportingArea = results.getInt("ReportingArea");
				Shootings resultShooting = Shootings.valueOf(results.getString("Shooting"));
				Date resultOccured = new Date(results.getTimestamp("OccuredOnDate").getTime());
				String resultDayOfWeek = results.getString("DayOfWeek");
				int resultHour = results.getInt("Hours");
				UCRs resultUcr = UCRs.valueOf(results.getString("UCR"));
				String resultLocation = results.getString("Location");
				Offense offense = offensesDao.getOffenseByOffenseCode(resultOffensecode);
				Address address = addressDao.getAddressByLocation(resultLocation);
				Incidents incidents = new Incidents(resultincidentId, offense, resultDistrict, resultReportingArea,
						resultShooting, resultOccured, resultDayOfWeek, resultHour, resultUcr, address);

				return incidents;
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

	public List<Incidents> getIncidentByOffenceCode(int offenceCode) throws SQLException {
		List<Incidents> incidentsList = new ArrayList<Incidents>();
		String selectIncidents = "SELECT IncidentId,Incidents.OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,"
				+ "DayOfWeek,Hours,UCR,Location " + "FROM Incidents INNER JOIN Offense "
				+ "  ON Incidents.OffenseCode = Offense.OffenseCode " + "WHERE Incidents.offenseCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIncidents);
			selectStmt.setInt(1, offenceCode);
			results = selectStmt.executeQuery();
			OffenseDao offensesDao = OffenseDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();
			while (results.next()) {
				int resultincidentId = results.getInt("IncidentId");
				int resultOffensecode = results.getInt("OffenseCode");
				String resultDistrict = results.getString("District");
				int resultReportingArea = results.getInt("ReportingArea");
				Shootings resultShooting = Shootings.valueOf(results.getString("Shooting"));
				Date resultOccured = new Date(results.getTimestamp("OccuredOnDate").getTime());
				String resultDayOfWeek = results.getString("DayOfWeek");
				int resultHour = results.getInt("Hours");
				UCRs resultUcr = UCRs.valueOf(results.getString("UCR"));
				String resultLocation = results.getString("Location");
				Offense offense = offensesDao.getOffenseByOffenseCode(resultOffensecode);
				Address address = addressDao.getAddressByLocation(resultLocation);
				Incidents incidents = new Incidents(resultincidentId, offense, resultDistrict, resultReportingArea,
						resultShooting, resultOccured, resultDayOfWeek, resultHour, resultUcr, address);
				incidentsList.add(incidents);
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
		return incidentsList;
	}

	public List<Incidents> getIncidentByLocation(String location) throws SQLException {
		List<Incidents> incidentsList = new ArrayList<Incidents>();
		String selectIncidents = "SELECT IncidentId,OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,"
				+ "DayOfWeek,Hours,UCR,Incidents.Location " + "FROM Incidents INNER JOIN Address "
				+ "  ON Incidents.Location = Address.Location " + "WHERE Incidents.Location=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		OffenseDao offensesDao = OffenseDao.getInstance();
		AddressDao addressDao = AddressDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIncidents);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int resultincidentId = results.getInt("IncidentId");
				int resultOffensecode = results.getInt("OffenseCode");
				String resultDistrict = results.getString("District");
				int resultReportingArea = results.getInt("ReportingArea");
				Shootings resultShooting = Shootings.valueOf(results.getString("Shooting"));
				Date resultOccured = new Date(results.getTimestamp("OccuredOnDate").getTime());
				String resultDayOfWeek = results.getString("DayOfWeek");
				int resultHour = results.getInt("Hours");
				UCRs resultUcr = UCRs.valueOf(results.getString("UCR"));
				String resultLocation = results.getString("Location");
				Offense offense = offensesDao.getOffenseByOffenseCode(resultOffensecode);
				Address address = addressDao.getAddressByLocation(resultLocation);
				Incidents incidents = new Incidents(resultincidentId, offense, resultDistrict, resultReportingArea,
						resultShooting, resultOccured, resultDayOfWeek, resultHour, resultUcr, address);
				incidentsList.add(incidents);
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
		return incidentsList;
	}

	public void importData(String dataFilePath, String tableName) {
		String sql = "LOAD DATA LOCAL INFILE '" + dataFilePath + "' into table " + tableName + " FIELDS TERMINATED BY '"
				+ INFILE_FIELD_SEPARATION_CHAR + "' " + "ENCLOSED BY '" + INFILE_ENCLOSED_CHAR + "' "
				+ "LINES TERMINATED BY '" + System.lineSeparator() + "' "
				+ "IGNORE 1 LINES (OffenseCode,District,ReportingArea,Shooting,OccuredOnDate,DayOfWeek,Hours,UCR,Location);";

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
