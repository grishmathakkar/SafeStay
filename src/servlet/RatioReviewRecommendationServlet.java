package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.RatioReviewRecommendation;

/**
 * Servlet implementation class RatioReviewRecommendationServlet
 */
@WebServlet("/ratioreviewrecommendationsservlet")
public class RatioReviewRecommendationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected RatioReviewRecommendation rrr;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatioReviewRecommendationServlet() {
       rrr= RatioReviewRecommendation.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		
		try {
				float k=rrr.getRatioOfRecommendationAndReview();
		        request.setAttribute("rat", k);
		    } catch (SQLException e) {
		        request.setAttribute("error", "Retrieving rows failed.");
		        e.printStackTrace();
		    }
		
		request.getRequestDispatcher("/RatioReviewRecommendationServlet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
