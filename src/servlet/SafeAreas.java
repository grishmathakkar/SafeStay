package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.SafestAreasTop20;

/**
 * Servlet implementation class SafeAreas
 */
@WebServlet("/SafeAreas")
public class SafeAreas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected SafestAreasTop20 rrr;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SafeAreas() {

		rrr = SafestAreasTop20.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			request.setAttribute("rat", rrr.getTop20SafestAreas());
		} catch (SQLException e) {
			request.setAttribute("error", "Retrieving rows failed.");
			e.printStackTrace();
		}

		request.getRequestDispatcher("/SafeAreas.jsp").forward(request, response);
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
