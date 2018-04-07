package SafeStay.dal;

import java.sql.*;
import java.util.*;

import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Recommendations;

public class RecommendationsDao {
	protected ConnectionManager connectionManager;

	private static RecommendationsDao instance = null;

	protected RecommendationsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RecommendationsDao getInstance() {
		if (instance == null) {
			instance = new RecommendationsDao();
		}
		return instance;
	}

	public Recommendations create(Recommendations recommendation) throws SQLException {
		String insertRec = "INSERT INTO Recommendation ( Rating, Petfriendly, Childfriendly, UserName, Location) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRec, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setDouble(1, recommendation.getRating());
			insertStmt.setDouble(2, recommendation.getPetfriendly());
			insertStmt.setDouble(3, recommendation.getChildfriendly());
			insertStmt.setString(4, recommendation.getEndusers().getUserName());
			insertStmt.setString(5, recommendation.getAddress().getLocation());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if (resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(recommendationId);
			return recommendation;
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

	public Recommendations getRecommendationById(int recomid) throws SQLException {
		String selReview = "SELECT * " + "FROM Recommendation " + "WHERE recommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selReview);
			selectStmt.setInt(1, recomid);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rRecommendationId = results.getInt("RecommendationId");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				double rrating = results.getDouble("Rating");
				double rpetfriendly = results.getDouble("petfriendly");
				double rchildfriendly = results.getDouble("Childfriendly");
				Recommendations recomm = new Recommendations(rRecommendationId, rpetfriendly, rchildfriendly, rrating,
						renduser, raddress);

				return recomm;
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

	public List<Recommendations> getRecommendationsByLocation(String location) throws SQLException {
		List<Recommendations> recomList = new ArrayList<>();
		String selRecommendations = "SELECT * " + "FROM Recommendation " + "WHERE location=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selRecommendations);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rRecommendationId = results.getInt("RecommendationId");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				double rrating = results.getDouble("Rating");
				double rpetfriendly = results.getDouble("petfriendly");
				double rchildfriendly = results.getDouble("Childfriendly");
				Recommendations recomm = new Recommendations(rRecommendationId, rpetfriendly, rchildfriendly, rrating,
						renduser, raddress);
				recomList.add(recomm);

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
		return recomList;

	}

	public List<Recommendations> getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations> recomList = new ArrayList<>();
		String selReview = "SELECT * " + "FROM Recommendation " + "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selReview);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rRecommendationId = results.getInt("RecommendationId");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				double rrating = results.getDouble("Rating");
				double rpetfriendly = results.getDouble("Petfriendly");
				double rchildfriendly = results.getDouble("Childfriendly");
				Recommendations recomm = new Recommendations(rRecommendationId, rpetfriendly, rchildfriendly, rrating,
						renduser, raddress);
				recomList.add(recomm);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			// very good
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
		return recomList;

	}

	public Recommendations updateRating(Recommendations recommendation, Double newRating) throws SQLException {
		String updateRecommendation = "UPDATE Recommendation SET Rating=? WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateRecommendation);
			updateStmt.setDouble(1, newRating);
			updateStmt.setInt(2, recommendation.getRecommendationId());

			updateStmt.executeUpdate();

			// Update the Expiration param before returning to the caller.
			recommendation.setRating(newRating);
			return recommendation;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	public Recommendations delete(int recommendationid) throws SQLException {
		String delRecom = "DELETE FROM Recommendation WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(delRecom);
			deleteStmt.setInt(1, recommendationid);
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
