package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.AdministratorsDao;
import SafeStay.dal.EndUsersDao;
import SafeStay.model.Administrators;
import SafeStay.model.EndUsers;

@WebServlet("/findadmins")
public class FindAdmins extends HttpServlet {

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

		Administrators administrators = null;
		// Retrieve and validate name.
		// firstname is retrieved from the URL query string.
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid name.");
		} else {
			// Retrieve BlogUsers, and store as a message.
			try {
				administrators = administratorsDao.getAdministratorByUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + userName);
			// Save the previous search term, so it can be used as the default
			// in the input box when rendering FindUsers.jsp.
			messages.put("previousUserName", userName);
		}
		req.setAttribute("admins", administrators);

		req.getRequestDispatcher("/FindAdmins.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// List<EndUsers> endUsers = new ArrayList<EndUsers>();
		Administrators administrators = null;
		// Retrieve and validate name.
		// firstname is retrieved from the form POST submission. By default, it
		// is populated by the URL query string (in FindUsers.jsp).
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Please enter a valid name.");
		} else {
			// Retrieve BlogUsers, and store as a message.
			try {
				administrators = administratorsDao.getAdministratorByUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + userName);
		}
		req.setAttribute("admins", administrators);

		req.getRequestDispatcher("/FindAdmins.jsp").forward(req, resp);
	}
}
