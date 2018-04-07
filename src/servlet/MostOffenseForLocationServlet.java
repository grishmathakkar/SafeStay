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
	//private static final long serialVersionUID = 1L;
		protected MostOffenseForLocation offenseLoc;
	    @Override
	    public void init() throws ServletException {
	       offenseLoc = MostOffenseForLocation.getInstance();
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
					// To get the data from JSP, pass the location as a parameter to the function
					int k=offenseLoc.getOffenseCodeForLocation();
			        request.setAttribute("offense", k);
			    } catch (SQLException e) {
			        request.setAttribute("error", "Retrieving rows failed.");
			        e.printStackTrace();
			    }
			
			request.getRequestDispatcher("/MostOffenseForLocationServlet.jsp").forward(request, response);
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}
}
