package bankingApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("firstName");
		String lastName =req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phoneNumber = req.getParameter("phoneNumber");
		
		JdbcConn jdbc = new JdbcConn();
		Customer c = new Customer();
		c.setFirstName(firstName);
		c.setLastName(lastName);
		c.setEmail(email);
		c.setPassword(password);
		c.setPhoneNumber( Long.parseLong(phoneNumber));
		
		Customer existingUser = jdbc.getCusometrInfo(email);
		
		if(existingUser==null) {
			try {
				jdbc.RegisterUser(c);
				jdbc.insertAccountInfo(email);
				long a = jdbc.getAccountInfo(email).getAccountNumber();
				
				SendEmail.sendAccountInfo(email, a);
				req.setAttribute("message", "Registration Complete");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			} catch (Exception e) {
				req.setAttribute("error", "There is some internal issue. Please register again");
				req.getRequestDispatcher("signup.jsp").forward(req, resp);
			}
			
		}else {
			req.setAttribute("error", "User is already exist with matching this email Id");
			req.getRequestDispatcher("signup.jsp").forward(req, resp);
		}
		
		
		
	}
}
