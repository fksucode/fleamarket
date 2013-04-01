package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.BidManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Bid;

/**
 * Servlet implementation class WonBidsServlet
 */
@WebServlet("/actions/user-winner-bids")
public class UserWinnerBidsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BidManager bidManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Bid> bids = bidManager.getWinnerBids(request.getRemoteUser());
		request.setAttribute("bids", bids);
		getServletContext().getRequestDispatcher("/pages/bids.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
