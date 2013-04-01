package bg.tusofia.fksu.pe.fleamarket.sample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setBufferSize(8192);

		String username = request.getParameter("username");
		if (username != null && username.length() > 0) {
			// if response is received
			// NOTE: servlet/redirect - forward
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/response");
			if (dispatcher != null) {
				dispatcher.forward(request, response);
			}
		} else {
			// if response is NOT received
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head><title>Request Servlet</title></head>");

			out.println("<body bgcolor=\"#ffffff\">" //
					+ "<img src=\"wave.jpeg\" alt=\"Waving\">" //
					+ "<h2>Hello, my name is OPA. What's yours?</h2>" //
					+ "<form method=\"get\">" //
					+ "<input type=\"text\" name=\"username\" size=\"25\">"
					+ "<p></p>"
					+ "<input type=\"submit\" value=\"Submit\">" //
					+ "<input type=\"reset\" value=\"Reset\">" //
					+ "</form>" // /
					+ "</body>");
			out.println("</html>");
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
