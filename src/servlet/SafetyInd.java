package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.SafetyIndex;

/**
 * Servlet implementation class SafetyInd
 */
@WebServlet("/SafetyInd")
public class SafetyInd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected SafetyIndex si;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SafetyInd() {
		si = SafetyIndex.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			request.setAttribute("rat", si.getSafetyIndex());
		} catch (SQLException e) {
			request.setAttribute("error", "Retrieving rows failed.");
			e.printStackTrace();
		}

		request.getRequestDispatcher("/safetyindex.jsp").forward(request, response);
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
