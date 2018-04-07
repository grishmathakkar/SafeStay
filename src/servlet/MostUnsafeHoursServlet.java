package servlet;

	
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.*;
import SafeStay.model.UnsafeHour;

	@WebServlet("/findunsafehour")
	public class MostUnsafeHoursServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	       protected MostUnsafeHour uh;
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public MostUnsafeHoursServlet() {
	     uh = MostUnsafeHour.getInstance();
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String location = request.getParameter("location");
			
			try {

		        request.setAttribute("hours", uh.getMostUnsafeHour(location));
		    } catch (SQLException e) {
		        request.setAttribute("error", "Retrieving rows failed.");
		        e.printStackTrace();
		    }
		
		request.getRequestDispatcher("/FindUnsafeHour.jsp").forward(request, response);
	}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}

	}
