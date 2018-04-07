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

@WebServlet("/reviewupdate")
public class ReviewUpdate extends HttpServlet {

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

		// Retrieve user and validate.
		String reviewId = req.getParameter("reviewid");
		if (reviewId == null || reviewId.trim().isEmpty()) {
			messages.put("success", "Please enter a valid ReviewId.");
		} else {
			try {
				Reviews review = reviewsDao.getReviewById(Integer.parseInt(reviewId));
				if (review == null) {
					messages.put("success", "Review does not exist.");
				}
				req.setAttribute("review", review);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve user and validate.
		String reviewId = req.getParameter("reviewid");
		try {

			Reviews review = reviewsDao.getReviewById(Integer.parseInt(reviewId));
			if (review == null) {
				messages.put("success", "Review does not exist. No update to perform.");
			} else {
				String newContent = req.getParameter("content");
				if (newContent == null || newContent.trim().isEmpty()) {
					messages.put("success", "Please enter a valid Content.");
				} else {
					review = reviewsDao.updateContent(review.getReviewId(), newContent);
					messages.put("success", "Successfully updated " + reviewId);
				}
			}
			req.setAttribute("review", review);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		req.getRequestDispatcher("/ReviewUpdate.jsp").forward(req, resp);
	}

}
