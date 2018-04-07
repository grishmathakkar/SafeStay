package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import SafeStay.dal.UnsafeStreets;

/**
 * Servlet implementation class UnsafeStr
 */
@WebServlet("/UnsafeStr")
public class UnsafeStr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       protected UnsafeStreets us;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnsafeStr() {
     us=UnsafeStreets.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

	        request.setAttribute("rat", us.getUnsafeStreets());
	    } catch (SQLException e) {
	        request.setAttribute("error", "Retrieving rows failed.");
	        e.printStackTrace();
	    }
	
	request.getRequestDispatcher("/UnsafeSt.jsp").forward(request, response);
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
