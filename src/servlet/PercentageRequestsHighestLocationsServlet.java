package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.PercentageRequestsHighestLocations;
import SafeStay.model.LocationRequestPercentage;

/**
 * Servlet implementation class RatioReviewRecommendationServlet
 */
@WebServlet("/percentagerequestshighestlocationsservlet")
public class PercentageRequestsHighestLocationsServlet extends HttpServlet {
	protected PercentageRequestsHighestLocations prhl;

	@Override
	public void init() throws ServletException {
		prhl = PercentageRequestsHighestLocations.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<LocationRequestPercentage> locationRequestPercentages = new ArrayList<>();
		try {
			// locationRequestPercentages.addAll(prhl.getMostRequestedLocations());
			locationRequestPercentages = prhl.getMostRequestedLocations();
			request.setAttribute("locations", locationRequestPercentages);
		} catch (SQLException e) {
			request.setAttribute("error", "Retrieving rows failed.");
			e.printStackTrace();
		}

		request.getRequestDispatcher("/PercentageRequestsHighestLocationsServlet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}