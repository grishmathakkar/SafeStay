package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.CommentsDao;
import SafeStay.dal.IncidentsDao;
import SafeStay.dal.UsersDao;
import SafeStay.model.Comments;
import SafeStay.model.Incidents;
import SafeStay.model.Users;

@WebServlet("/commentonincident")
public class CommentOnIncident extends HttpServlet {

	protected CommentsDao commentsDao;

	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/commentonincident.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Create recommendation
		int incidentId = Integer.parseInt(req.getParameter("incidentid"));
		IncidentsDao incidentsDao = IncidentsDao.getInstance();
		Incidents incidents = null;
		try {
			incidents = incidentsDao.getIncidentByIncidentId(incidentId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String username = req.getParameter("username");
		UsersDao usersDao = UsersDao.getInstance();
		Users users = null;
		try {
			users = usersDao.getUserByUserName(username);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String comment_description = req.getParameter("description");
		try {
			Comments comments = new Comments(users, incidents, comment_description);
			comments = commentsDao.create(comments);
			messages.put("success", "Successfully created comment by " + users.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/commentonincident.jsp").forward(req, resp);
	}
}
