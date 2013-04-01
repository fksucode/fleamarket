package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import bg.tusofia.fksu.pe.fleamarket.buslogic.UserManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Seller;
import bg.tusofia.fksu.pe.fleamarket.domain.User;

/**
 * Servlet Filter implementation class AttachUserFilter
 */
@WebFilter("/actions/*")
public class AttachUserFilter implements Filter {

	@EJB
	private UserManager userManager;

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getRemoteUser() != null && httpRequest.getSession().getAttribute("user") == null) {
			User user = userManager.getUser(httpRequest.getRemoteUser());
			httpRequest.getSession().setAttribute("user", user);
			if (user instanceof Seller) {
				httpRequest.getSession().setAttribute("isCurrentUserSeller", true);
			} else {
				httpRequest.getSession().setAttribute("isCurrentUserBidder", true);
			}
		}

		chain.doFilter(request, response);
		
		if (httpRequest.getRemoteUser() != null && httpRequest.getSession().getAttribute("user") == null) {
			User user = userManager.getUser(httpRequest.getRemoteUser());
			httpRequest.getSession().setAttribute("user", user);
			if (user instanceof Seller) {
				httpRequest.getSession().setAttribute("isCurrentUserSeller", true);
			} else {
				httpRequest.getSession().setAttribute("isCurrentUserBidder", true);
			}
		}
	}

}
