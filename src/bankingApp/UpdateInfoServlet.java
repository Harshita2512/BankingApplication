package bankingApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateInfoServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String phoneNumber = req.getParameter("phoneNumber");
		String password = req.getParameter("password");
		String email = (String) req.getSession().getAttribute("email");
		
		JdbcConn jdbc = new JdbcConn();
		Customer c = new Customer();
		
		c.setPhoneNumber(Long.parseLong(phoneNumber));
		c.setPassword(password);
		c.setEmail(email);
		
		 try {
			jdbc.updateInformation(c);
			req.getSession().setAttribute("phoneNumber", phoneNumber);
			AccountInfo a = jdbc.getAccountInfo(email);
			req.setAttribute("accountInfo", a);
			req.setAttribute("message", "Your Information is updated");
			req.getRequestDispatcher("updateInfo.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", "There is some internal issue. Please try again");
			req.getRequestDispatcher("updateInfo.jsp").forward(req, resp);
		}
	}
	

}
