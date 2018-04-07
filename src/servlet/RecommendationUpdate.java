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

@WebServlet("/recommendationupdate")
public class RecommendationUpdate extends HttpServlet {

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

		// Retrieve user and validate.
		String recommendationId = req.getParameter("recommendationid");
		if (recommendationId == null || recommendationId.trim().isEmpty()) {
			messages.put("success", "Please enter a valid RecommendationId.");
		} else {
			try {
				Recommendations recommendations = recommendationsDao
						.getRecommendationById(Integer.parseInt(recommendationId));
				if (recommendations == null) {
					messages.put("success", "Recommendation does not exist.");
				}
				req.setAttribute("recommendation", recommendations);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/RecommendationUpdate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve user and validate.
		String recommendationId = req.getParameter("recommendationId");
		try {
			Recommendations recommendations = recommendationsDao
					.getRecommendationById(Integer.parseInt(recommendationId));
			if (recommendations == null) {
				messages.put("success", "Recommendation does not exist. No update to perform.");
			} else {
				String newRating = req.getParameter("rating");
				if (newRating == null || newRating.trim().isEmpty()) {
					messages.put("success", "Please enter a valid Content.");
				} else {
					recommendations = recommendationsDao.updateRating(recommendations, Double.parseDouble(newRating));
					messages.put("success", "Successfully updated " + recommendationId);
				}
			}
			req.setAttribute("recommendation", recommendations);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
	}

}
