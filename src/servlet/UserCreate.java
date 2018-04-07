package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.EndUsersDao;
import SafeStay.model.EndUsers;

@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {

	protected EndUsersDao endUsersDao;

	@Override
	public void init() throws ServletException {
		endUsersDao = EndUsersDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else {
			// Create the BlogUser.
			String password = req.getParameter("password");
			String firstName = req.getParameter("firstname");
			String lastName = req.getParameter("lastname");
			String age = req.getParameter("age");
			int userage = Integer.parseInt(age);
			String email = req.getParameter("email");
			String phone = req.getParameter("phone");
			String address = req.getParameter("address");
			// dob must be in the format yyyy-mm-dd.
			String stringDob = req.getParameter("dob");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsed = null;
			try {
				parsed = dateFormat.parse(stringDob);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			// Date dob = new Date();
			// try {
			// dob = dateFormat.parse(stringDob);
			// } catch (ParseException e) {
			// e.printStackTrace();
			// throw new IOException(e);
			// }
			try {
				EndUsers endUser = new EndUsers(userName, password, firstName, lastName, userage, email, phone, sql);
				endUser = endUsersDao.create(endUser);
				messages.put("success", "Successfully created " + userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
	}
}
