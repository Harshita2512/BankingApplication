package bankingApp;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivityServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = (String) req.getSession().getAttribute("email");
		JdbcConn jdbc = new JdbcConn();
		AccountInfo a = jdbc.getAccountInfo(email);
		ArrayList<Activities> at = jdbc.getAllActivites(a.getAccountNumber());
		
		req.setAttribute("activities", at);
		req.getRequestDispatcher("activities.jsp").forward(req, resp);
		
	}

	
}
