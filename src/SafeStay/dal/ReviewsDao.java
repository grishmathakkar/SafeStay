package SafeStay.dal;

import java.sql.*;

import java.util.*;

import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Reviews;

public class ReviewsDao {
	protected ConnectionManager connectionManager;

	private static ReviewsDao instance = null;

	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReviewsDao getInstance() {
		if (instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}

	public Reviews updateContent(int reviewid, String newContent) throws SQLException {
		String updateReview = "UPDATE Review SET Content=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, newContent);
			updateStmt.setInt(2, reviewid);

			updateStmt.executeUpdate();
			Reviews reviews = new Reviews(reviewid);
			// Update the Expiration param before returning to the caller.
			reviews.setContent(newContent);
			return reviews;
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

	public Reviews create(Reviews review) throws SQLException {
		String createReview = "INSERT INTO Review(Content, UserName, Location) " + "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(createReview, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getContent());
			insertStmt.setString(2, review.getEndusers().getUserName());
			insertStmt.setString(3, review.getAddress().getLocation());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if (resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			return review;
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

	public Reviews getReviewById(int reviewId) throws SQLException {
		String selReview = "SELECT * " + "FROM Review " + "WHERE Reviewid=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rReviewId = results.getInt("ReviewId");
				String rContent = results.getString("Content");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				Reviews rev = new Reviews(rReviewId, rContent, renduser, raddress);
				return rev;
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

	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews> revList = new ArrayList<>();
		String selectReview = "SELECT * " + "FROM Review " + "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rReviewId = results.getInt("ReviewId");
				String rContent = results.getString("Content");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				Reviews rev = new Reviews(rReviewId, rContent, renduser, raddress);
				revList.add(rev);

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
		return revList;

	}

	public List<Reviews> getReviewsBylocation(String location) throws SQLException {
		List<Reviews> revList = new ArrayList<>();
		String selectReview = "SELECT * " + "FROM Review " + "WHERE location=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, location);
			results = selectStmt.executeQuery();
			EndUsersDao endusersDao = EndUsersDao.getInstance();
			AddressDao addressDao = AddressDao.getInstance();

			while (results.next()) {
				int rReviewId = results.getInt("ReviewId");
				String rContent = results.getString("Content");
				String rUserName = results.getString("UserName");
				String rlocation = results.getString("Location");
				EndUsers renduser = endusersDao.getEndUserByUserName(rUserName);
				Address raddress = addressDao.getAddressByLocation(rlocation);
				Reviews rev = new Reviews(rReviewId, rContent, renduser, raddress);
				revList.add(rev);

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
		return revList;

	}

	public Reviews delete(int reviewid) throws SQLException {
		String delReview = "DELETE FROM Review WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(delReview);
			deleteStmt.setInt(1, reviewid);
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
