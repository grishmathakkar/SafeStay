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

import SafeStay.dal.AddressDao;
import SafeStay.dal.EndUsersDao;
import SafeStay.dal.RecommendationsDao;
import SafeStay.dal.ReviewsDao;
import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Recommendations;
import SafeStay.model.Reviews;

@WebServlet("/reviewcreate")
public class ReviewCreate extends HttpServlet {

	protected ReviewsDao reviewsDao;

	@Override
	public void init() throws ServletException {
		reviewsDao = ReviewsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Create recommendation
		String content = req.getParameter("content");
		String userName = req.getParameter("username");
		EndUsersDao endUsersDao = EndUsersDao.getInstance();
		EndUsers endUsers = null;
		try {
			endUsers = endUsersDao.getEndUserByUserName(userName);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String location = req.getParameter("location");
		AddressDao addressDao = AddressDao.getInstance();
		Address address = null;
		try {
			address = addressDao.getAddressByLocation(location);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			Reviews reviews = new Reviews(content, endUsers, address);
			reviews = reviewsDao.create(reviews);
			messages.put("success", "Successfully created review by " + endUsers.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
	}
}
