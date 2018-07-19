package bankingApp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionServlet extends HttpServlet{
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String recieverAccountNumber = req.getParameter("accountNumber");
		String amountValue = req.getParameter("amount");
		String senderEmail = (String) req.getSession().getAttribute("email");
		
		JdbcConn jdbc = new JdbcConn();
		
		AccountInfo fromAccount = jdbc.getAccountInfo(senderEmail);
		double amount = Double.parseDouble(amountValue);
		if (amount<fromAccount.getBalance()) {
			double amt = fromAccount.getBalance()-amount;
			fromAccount.setBalance(amt);
			try {
				jdbc.updateAccountInfo(fromAccount);
			} catch (Exception e1) {
				
			}
			AccountInfo toAccount = jdbc.getAccountInfo(Long.parseLong(recieverAccountNumber));
			
			if(toAccount != null) {
			    amt = toAccount.getBalance()+amount;
			    toAccount.setBalance(amt);
				try {
					jdbc.updateAccountInfo(toAccount);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			try {
				jdbc.insertActivities(toAccount.getAccountNumber(), "Credit", amount);
				jdbc.insertActivities(fromAccount.getAccountNumber(), "Debit", amount);
				req.setAttribute("message", "Amount $"+amount+" has been transfered to account "+toAccount.getAccountNumber()+ " successfully");
				req.getRequestDispatcher("transfer.jsp").forward(req, resp);
				
			} catch (Exception e) {
				req.setAttribute("error", "There is some internal issue. Please try again");
				req.getRequestDispatcher("transfer.jsp").forward(req, resp);
			}
			} else {
				req.setAttribute("error", "Entered account number does not exist");
				req.getRequestDispatcher("transfer.jsp").forward(req, resp);
			}
			
		}
		else {
			req.setAttribute("error", "Insufficient Balance");
			req.getRequestDispatcher("transfer.jsp").forward(req, resp);
		}
	
		

}
}
