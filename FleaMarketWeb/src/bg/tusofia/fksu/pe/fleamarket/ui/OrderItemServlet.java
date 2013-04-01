package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bg.tusofia.fksu.pe.fleamarket.buslogic.PlaceOrder;
import bg.tusofia.fksu.pe.fleamarket.domain.Address;
import bg.tusofia.fksu.pe.fleamarket.domain.BillingInfo;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;
import bg.tusofia.fksu.pe.fleamarket.domain.ShippingInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class OrderItemServlet
 */
@WebServlet("/actions/order-item")
public class OrderItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		HttpSession session = request.getSession();
		PlaceOrder placeOrderBean = (PlaceOrder) session.getAttribute("placeOrderBean");
		if (placeOrderBean == null) {
			try {
				placeOrderBean = (PlaceOrder) new InitialContext()
						.lookup("java:global/FleaMarketApp/FleaMarketEJB/PlaceOrderBean");
			} catch (NamingException e) {
				throw new ServletException("Unable to lookup bean.", e);
			}
			session.setAttribute("placeOrderBean", placeOrderBean);
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String step = request.getParameter("step");
		String stepData = request.getParameter("stepData");
		System.out.println(step);
		System.out.println(stepData);
		if ("finish".equalsIgnoreCase(step)) {
			placeOrderBean.setBidderId(request.getRemoteUser());
			try {
				Long orderId = placeOrderBean.confirmOrder();
				request.setAttribute("orderId", orderId);
				session.removeAttribute("placeOrderBean");
				getServletContext().getRequestDispatcher("/pages/create-order-success.jsp").forward(request, response);
			} catch (Exception ex) {
				request.setAttribute("errorMessage", ex.getMessage());
				session.removeAttribute("placeOrderBean");
				getServletContext().getRequestDispatcher("/pages/create-order-failure.jsp").forward(request, response);
			}
		} else {
			if ("item".equalsIgnoreCase(step)) {
				Item item = gson.fromJson(stepData, Item.class);
				placeOrderBean.setItemId(item.getItemId());
			} else if ("billing".equalsIgnoreCase(step)) {
				placeOrderBean.setBillingInfo(gson.fromJson(stepData, BillingInfo.class));
				response.setStatus(HttpServletResponse.SC_OK);
			} else if ("shipping".equalsIgnoreCase(step)) {
				ShippingInfo shippingInfo = new ShippingInfo();
				shippingInfo.setAddress(gson.fromJson(stepData, Address.class));
				placeOrderBean.setShippingInfo(shippingInfo);
			}
			response.setStatus(HttpServletResponse.SC_OK);
		}

	}
}
