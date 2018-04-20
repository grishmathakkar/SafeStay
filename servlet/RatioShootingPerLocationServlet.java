package servlet;
/**
 * @author sonalsingh
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.*;
import SafeStay.model.ShootingLocation;

/**
 * Servlet implementation class RatioReviewRecommendationServlet
 */
@WebServlet("/ratioshootingperlocationservlet")
public class RatioShootingPerLocationServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected RatioShootingPerLocation ratioShooting;
    
	@Override
	public void init() throws ServletException {
		ratioShooting = RatioShootingPerLocation.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ShootingLocation> listOfLocations = new ArrayList<ShootingLocation>();
		
		try {
			listOfLocations = ratioShooting.getRatioshootingIncidents();
		        request.setAttribute("locations", listOfLocations);
		    } catch (SQLException e) {
		        request.setAttribute("error", "Retrieving rows failed.");
		        e.printStackTrace();
		    }
		
		request.getRequestDispatcher("/RatioShootingPerLocationServlet.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
