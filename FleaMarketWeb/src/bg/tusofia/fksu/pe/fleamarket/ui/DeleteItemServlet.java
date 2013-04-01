package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

/**
 * Servlet implementation class DeleteItemServlet
 */
@WebServlet("/actions/delete-item")
public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ItemManager itemManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Item item = itemManager.getItem(Long.parseLong(request.getParameter("itemId")));
			if (item != null && item.getSeller().getUserId().equals(request.getRemoteUser())) {
				itemManager.deleteItem(item.getItemId());
			}
			getServletContext().getRequestDispatcher("/actions/items").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			getServletContext().getRequestDispatcher("/pages/delete-item-failure.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
