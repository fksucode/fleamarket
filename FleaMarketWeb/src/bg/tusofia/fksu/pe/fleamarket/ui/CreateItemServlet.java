package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

@WebServlet("/actions/create-item")
public class CreateItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DateFormat formatter;

	@EJB
	private ItemManager itemManager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Double initialPrice = Double.parseDouble(request.getParameter("initialPrice"));
			Date bidEndDate = formatter.parse(request.getParameter("bidEndDate"));
			Item item = itemManager.addItem(request.getParameter("title"), initialPrice, bidEndDate,
					request.getParameter("description"), request.getRemoteUser());
			request.setAttribute("item", item);
			getServletContext().getRequestDispatcher("/pages/create-item-success.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			getServletContext().getRequestDispatcher("/pages/create-item-failure.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
