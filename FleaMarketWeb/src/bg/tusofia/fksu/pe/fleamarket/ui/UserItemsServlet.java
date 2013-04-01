package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

/**
 * Servlet implementation class UserItemsServlet
 */
@WebServlet("/actions/user-items")
public class UserItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ItemManager itemManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Item> items = itemManager.getItems(request.getRemoteUser());
		request.setAttribute("items", items);
		getServletContext().getRequestDispatcher("/pages/items.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
