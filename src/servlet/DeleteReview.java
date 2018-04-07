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

@WebServlet("/deletereview")
public class DeleteReview extends HttpServlet {

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
		// Provide a title and render the JSP.
		messages.put("title", "Delete Review");
		req.getRequestDispatcher("/DeleteReview.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String reviewid = req.getParameter("reviewid");
		if (reviewid == null || reviewid.trim().isEmpty()) {
			messages.put("title", "Invalid reviewid");
			messages.put("disableSubmit", "true");
		} else {
			Reviews review = new Reviews(Integer.parseInt(reviewid));
			try {
				review = reviewsDao.delete(Integer.parseInt(reviewid));

				// Update the message.
				if (review == null) {
					messages.put("title", "Successfully deleted " + reviewid);
					messages.put("disableSubmit", "true");
				} else {
					messages.put("title", "Failed to delete " + reviewid);
					messages.put("disableSubmit", "false");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
		}

		req.getRequestDispatcher("/DeleteReview.jsp").forward(req, resp);
	}

}
