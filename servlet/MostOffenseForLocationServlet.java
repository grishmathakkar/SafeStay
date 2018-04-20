package servlet;

/**
 * author - sonalsingh
 */
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
@WebServlet("/mostoffenseforlocationservlet")
public class MostOffenseForLocationServlet extends HttpServlet {
	protected MostOffenseForLocation offenseLoc;

	@Override
	public void init() throws ServletException {
		offenseLoc = MostOffenseForLocation.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/MostOffenseForLocationServlet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String location = request.getParameter("location");
		String k = null;
		try {
			k = offenseLoc.getOffenseCodeForLocation(location);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("offense", k);
		request.getRequestDispatcher("/MostOffenseOutput.jsp").forward(request, response);
	}
}
