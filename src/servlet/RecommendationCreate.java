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
import SafeStay.model.Address;
import SafeStay.model.EndUsers;
import SafeStay.model.Recommendations;

@WebServlet("/recommendationcreate")
public class RecommendationCreate extends HttpServlet {

	protected RecommendationsDao recommendationsDao;

	@Override
	public void init() throws ServletException {
		recommendationsDao = RecommendationsDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		// Just render the JSP.
		req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Create recommendation
		Double rating = Double.parseDouble(req.getParameter("rating"));
		Double petFriendly = Double.parseDouble(req.getParameter("pet-friendly"));
		Double childFriendly = Double.parseDouble(req.getParameter("child-friendly"));
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
			Recommendations recommendations = new Recommendations(rating, petFriendly, childFriendly, endUsers,
					address);
			recommendations = recommendationsDao.create(recommendations);
			messages.put("success", "Successfully created recommendation by " + endUsers.getUserName());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
	}
}
