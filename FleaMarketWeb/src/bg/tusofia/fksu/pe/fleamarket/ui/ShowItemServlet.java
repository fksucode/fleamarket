package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

/**
 * Servlet implementation class ShowItemServlet
 */
@WebServlet("/actions/show-item")
public class ShowItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private ItemManager itemManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemId = request.getParameter("itemId");
		Item item = itemManager.getItem(Long.parseLong(itemId));
		request.setAttribute("item", item);
		boolean isOrdered = itemManager.isItemOrdered(item.getItemId());
		boolean isWonByUser = itemManager.isItemWonByUser(item.getItemId(), request.getRemoteUser());
		boolean isWon = itemManager.isItemWon(item.getItemId());
		boolean hasBiddingExpired = item.getBidEndDate().before(new Date()); 
				System.out.println(hasBiddingExpired);
		request.setAttribute("canOrder", !isOrdered && isWonByUser);
		request.setAttribute("canBid", !hasBiddingExpired && !isWon);
		request.setAttribute("canDelete", !isOrdered && request.getRemoteUser().equals(item.getSeller().getUserId()));
		getServletContext().getRequestDispatcher("/pages/show-item.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
