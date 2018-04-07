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

import SafeStay.dal.*;
import SafeStay.model.*;

@WebServlet("/deleterecommendation")
public class DeleteRecommendation extends HttpServlet {

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
		// Provide a title and render the JSP.
		messages.put("title", "Delete Recommendations");
		req.getRequestDispatcher("/DeleteRecommendation.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String recommendationId = req.getParameter("recommendationid");

		Recommendations recommendation = new Recommendations(Integer.parseInt(recommendationId));
		try {
			recommendation = recommendationsDao.delete(Integer.parseInt(recommendationId));

			// Update the message.
			if (recommendation == null) {
				messages.put("title", "Successfully deleted ");
				// messages.put("disableSubmit", "true");
			} else {
				messages.put("title", "Failed to delete ");
				// messages.put("disableSubmit", "false");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		/*
		 * if (reviewId == null || reviewId.trim().isEmpty()) { messages.put("title",
		 * "Invalid reviewId"); messages.put("disableSubmit", "true"); } else {
		 * 
		 * }
		 */

		req.getRequestDispatcher("/DeleteRecommendation.jsp").forward(req, resp);
	}

}
