package bankingApp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
    
        String path = ((HttpServletRequest) request).getServletPath();
        
       
        boolean loggedIn = session != null && session.getAttribute("email") != null;

        if (loggedIn || path.equals("/login.jsp") ||path.equals("/signup.jsp") || path.equals("/login") ||path.equals("/registration")) {
            chain.doFilter(request, response);
        } else {
        	req.setAttribute("message", "Session Timeout");
			req.getRequestDispatcher("login.jsp").forward(req, response);
        }
    }

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	

    // ...
}