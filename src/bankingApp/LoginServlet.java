package bankingApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		JdbcConn jdbc = new JdbcConn();
		Customer c = jdbc.getCusometrInfo(email);
		
		if (c!=null) {
			if(c.getPassword().equals(password)) {
			AccountInfo a = jdbc.getAccountInfo(email);
			HttpSession session = req.getSession();
			session.setAttribute("name",c.getFirstName());
			session.setAttribute("email",c.getEmail());
			session.setAttribute("phoneNumber",c.getPhoneNumber());
			req.setAttribute("accountInfo", a);
			req.getRequestDispatcher("summary.jsp").forward(req, resp);
			} else {
				req.setAttribute("error", "Password does not match");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				
			}
		}
		else {
			req.setAttribute("error", "User does not exist");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
				
		}
	}

}
