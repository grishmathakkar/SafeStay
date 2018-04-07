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

import SafeStay.dal.AdministratorsDao;
import SafeStay.model.Administrators;


@WebServlet("/admindelete")
public class AdminDelete extends HttpServlet {

	protected AdministratorsDao administratorsDao;

	@Override
	public void init() throws ServletException {
		administratorsDao = AdministratorsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Provide a title and render the JSP.
		messages.put("title", "Delete Admin");
		req.getRequestDispatcher("/AdminDelete.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("title", "Invalid UserName");
			messages.put("disableSubmit", "true");
		} else {
			// Delete the BlogUser.
			Administrators administrators = new Administrators(userName);
			try {
				administrators = administratorsDao.delete(userName);
				// Update the message.
				if (administrators == null) {
					messages.put("title", "Successfully deleted " + userName);
					messages.put("disableSubmit", "true");
				} else {
					messages.put("title", "Failed to delete " + userName);
					messages.put("disableSubmit", "false");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/AdminDelete.jsp").forward(req, resp);
	}
}
