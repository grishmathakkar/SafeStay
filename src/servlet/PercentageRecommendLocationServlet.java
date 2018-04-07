package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.*;

/**
 * Servlet implementation class RatioReviewRecommendationServlet
 */
@WebServlet("/percentagerecommendlocationservlet")
public class PercentageRecommendLocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected PercentageRecommendLocation prl;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PercentageRecommendLocationServlet() {
		prl = PercentageRecommendLocation.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String location = request.getParameter("location");

		try {
			float k = prl.getPercentageRecommendLocation(location);
			request.setAttribute("prl", k);
		} catch (SQLException e) {
			request.setAttribute("error", "Retrieving rows failed.");
			e.printStackTrace();
		}

		request.getRequestDispatcher("/PercentageRecommendLocation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
