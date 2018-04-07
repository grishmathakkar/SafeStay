package SafeStay.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import SafeStay.model.Comments;
import SafeStay.model.Incidents;
import SafeStay.model.Users;

public class CommentsDao {

	protected ConnectionManager connectionManager;

	private static CommentsDao instance = null;

	protected CommentsDao() {
		connectionManager = new ConnectionManager();
	}

	public static CommentsDao getInstance() {
		if (instance == null) {
			instance = new CommentsDao();
		}
		return instance;
	}

	public Comments create(Comments comment) throws SQLException {
		String insertComment = "INSERT INTO Comments(UserName,IncidentId,Description) " + "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertComment, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, comment.getUsers().getUserName());
			insertStmt.setInt(2, comment.getIncidents().getIncidentId());
			insertStmt.setString(3, comment.getDescription());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int commentId = -1;
			if (resultKey.next()) {
				commentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			comment.setCommentId(commentId);
			return comment;
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

	public Comments getCommentbyId(int commentId) throws SQLException {
		String selectComment = "SELECT CommentId,UserName,IncidentId,Description " + "FROM Comments "
				+ "WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			IncidentsDao incidentsDao = IncidentsDao.getInstance();

			while (results.next()) {
				int resultCommentId = results.getInt("CommentId");
				String resultUserName = results.getString("UserName");
				int resultIncidentId = results.getInt("IncidentId");
				String resultDescription = results.getString("Description");
				Users user = usersDao.getUserByUserName(resultUserName);
				Incidents incidents = incidentsDao.getIncidentByIncidentId(resultIncidentId);
				Comments comments = new Comments(resultCommentId, user, incidents, resultDescription);
				return comments;
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

	public List<Comments> getCommentsByUserName(String userName) throws SQLException {
		List<Comments> commentsList = new ArrayList<>();
		String selectComment = "SELECT CommentId,UserName,IncidentId,Description  " + "FROM Comments "
				+ "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			IncidentsDao incidentsDao = IncidentsDao.getInstance();

			while (results.next()) {
				int resultCommentId = results.getInt("CommentId");
				String resultUserName = results.getString("UserName");
				int resultIncidentId = results.getInt("IncidentId");
				String resultDescription = results.getString("Description");
				Users user = usersDao.getUserByUserName(resultUserName);
				Incidents incidents = incidentsDao.getIncidentByIncidentId(resultIncidentId);
				Comments comments = new Comments(resultCommentId, user, incidents, resultDescription);
				commentsList.add(comments);

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
		return commentsList;

	}

	public List<Comments> getCommentsByIncidentId(int incidentId) throws SQLException {
		List<Comments> commentsList = new ArrayList<>();
		String selectComment = "SELECT CommentId,UserName,IncidentId,Description  " + "FROM Comments "
				+ "WHERE IncidentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, incidentId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			IncidentsDao incidentsDao = IncidentsDao.getInstance();

			while (results.next()) {
				int resultCommentId = results.getInt("CommentId");
				String resultUserName = results.getString("UserName");
				int resultIncidentId = results.getInt("IncidentId");
				String resultDescription = results.getString("Description");
				Users user = usersDao.getUserByUserName(resultUserName);
				Incidents incidents = incidentsDao.getIncidentByIncidentId(resultIncidentId);
				Comments comments = new Comments(resultCommentId, user, incidents, resultDescription);
				commentsList.add(comments);

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
		return commentsList;

	}

	public Comments delete(Comments comment) throws SQLException {
		String deleteComment = "DELETE FROM Comments WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteComment);
			deleteStmt.setInt(1, comment.getCommentId());
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
