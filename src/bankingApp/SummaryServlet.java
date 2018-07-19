package bankingApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SummaryServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JdbcConn jdbc = new JdbcConn();
		String email = (String) req.getSession().getAttribute("email");
		AccountInfo a = jdbc.getAccountInfo(email);
		req.setAttribute("accountInfo", a);
		req.getRequestDispatcher("summary.jsp").forward(req, resp);
		
	}

}
