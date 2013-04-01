package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.BidManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Bid;

/**
 * Servlet implementation class AddBidServlet
 */
@WebServlet("/actions/add-bid")
public class AddBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private BidManager placeBid;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long itemId = Long.parseLong(request.getParameter("itemId"));
		Double price = Double.parseDouble(request.getParameter("price"));

		Bid bid = placeBid.addBid(request.getParameter("bidderId"), itemId, price);

		response.setContentType("application/json");
		response.getWriter().write("{ \"bidId\": " + bid.getBidId() + " }");
		response.getWriter().flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
