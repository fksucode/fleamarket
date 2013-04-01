package bg.tusofia.fksu.pe.fleamarket.sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dynamic")
public class DynamicContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		// NOTE: servlet/objects - request & response
		Integer count = (Integer) request.getSession().getAttribute("count");
		if (count == null) {
			count = 0;
		}
		// NOTE: servlet/objects - session
		request.getSession().setAttribute("count", ++count);

		// NOTE: servlet/objects - out
		PrintWriter out = response.getWriter();
		out.println(formatDynamicContent((Integer) request.getSession().getAttribute("count")));
		out.close();
	}

	private String formatDynamicContent(int count) {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<head>").append("<title>");
		builder.append("Dynamic Servlet");
		builder.append("</title>").append("</head>");
		builder.append("<body>");
		builder.append("<h2>Timestamp: " + new Date() + "<br>");
		builder.append("Times: " + count + "</h2>");
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
