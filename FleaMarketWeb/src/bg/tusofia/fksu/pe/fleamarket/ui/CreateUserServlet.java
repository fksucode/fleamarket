package bg.tusofia.fksu.pe.fleamarket.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bg.tusofia.fksu.pe.fleamarket.buslogic.UserManager;
import bg.tusofia.fksu.pe.fleamarket.domain.Address;
import bg.tusofia.fksu.pe.fleamarket.domain.Bidder;
import bg.tusofia.fksu.pe.fleamarket.domain.Group;
import bg.tusofia.fksu.pe.fleamarket.domain.Seller;
import bg.tusofia.fksu.pe.fleamarket.domain.User;

@WebServlet("/createuser")
@MultipartConfig
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String BIDDER_ROLE = "bidder";

	private DateFormat formatter;

	@EJB
	private UserManager manager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = createUser(request);
		try {
			manager.create(user);
			getServletContext().getRequestDispatcher("/create-user-success.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
			getServletContext().getRequestDispatcher("/create-user-failure.jsp").forward(request, response);
		}
	}

	private User createUser(HttpServletRequest request) throws IOException, ServletException {
		User user = null;

		String role = request.getParameter("role");
		if (BIDDER_ROLE.equals(role)) {
			user = new Bidder();
		} else {
			user = new Seller();
		}
		user.setUserId(request.getParameter("username")); // exists
		user.setPassword(request.getParameter("password"));
		Group userGroup = manager.getGroup("USER");
		if (userGroup == null) {
			userGroup = new Group("USER");
			manager.createGroup(userGroup);
		}
		user.addGroup(userGroup);

		user.setFirstName(request.getParameter("firstname"));
		user.setLastName(request.getParameter("lastname"));
		user.setEmail(request.getParameter("email"));

		Part photoPart = request.getPart("picture");
		if (photoPart != null) {
			user.setPicture(readBinaryData(photoPart.getInputStream()));
		}
		Date date = null;
		try {
			date = formatter.parse(request.getParameter("birthdate"));
		} catch (ParseException pe) {
			// ignore
		}
		user.setBirthDate(date);
		user.setTelephone(request.getParameter("telephone"));

		Address address = new Address();
		address.setStreetName(request.getParameter("street"));
		address.setCity(request.getParameter("city"));
		address.setZipCode(request.getParameter("zipcode"));
		address.setCountry(request.getParameter("country"));
		user.setAddress(address);

		return user;
	}

	private byte[] readBinaryData(InputStream input) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int numberOfReadBytes;
			byte[] batch = new byte[16384];
			while ((numberOfReadBytes = input.read(batch, 0, batch.length)) != -1) {
				buffer.write(batch, 0, numberOfReadBytes);
			}
			buffer.flush();
		} catch (IOException ioe) {
			// ignore
		}
		return buffer.toByteArray();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
