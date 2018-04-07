package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import SafeStay.dal.AdministratorsDao;
import SafeStay.dal.EndUsersDao;
import SafeStay.model.Administrators;
import SafeStay.model.EndUsers;

@WebServlet("/admincreate")
public class AdminCreate extends HttpServlet {

	protected AdministratorsDao adminDao;

	@Override
	public void init() throws ServletException {
		adminDao = AdministratorsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/AdminCreate.jsp").forward(req, resp);
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
			String lastlogin = req.getParameter("lastlogin");
			Timestamp timestamp = null;
			try {
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			    Date parsedDate = dateFormat.parse(lastlogin);
			    timestamp = new java.sql.Timestamp(parsedDate.getTime());
			} catch(Exception e) { //this generic but you can control another types of exception
			    // look the origin of excption 
			}
			try {
				Administrators admin = new Administrators(userName, password, firstName, lastName, userage, email, phone, timestamp);
				admin = adminDao.create(admin);
				messages.put("success", "Successfully created " + userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/AdminCreate.jsp").forward(req, resp);
	}
}
