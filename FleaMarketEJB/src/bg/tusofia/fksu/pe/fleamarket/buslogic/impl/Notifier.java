package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bg.tusofia.fksu.pe.fleamarket.domain.User;

/**
 * Session Bean implementation class NotificationBean
 */
// NOTE: ejb/beanTypes - interface: none
// NOTE: ejb/transaction - container managed
// NOTE: ejb/injection - inject resource at class level
@Resource(name = "mail/FksuExamples", mappedName = "java:app/mail/FksuExamples", type = javax.mail.Session.class)
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Notifier {

	private static final String PREFIX = "[FleaMarket] ";

	// NOTE: jpa/entityManager - create container managed
	@PersistenceContext
	private EntityManager entityManager;

	// no annotation!
	private Session mailSession;

	// NOTE: ejb/injection - inject ejb context
	@Resource
	private SessionContext sessionContext;

	// NOTE: ejb/injection - alternative: ejb context lookup
	@PostConstruct
	private void initialize() {
		mailSession = (Session) sessionContext.lookup("mail/FksuExamples");

		// NOTE: ejb/injection - alternative: jndi lookup
		// try {
		// InitialContext jndiContext = new InitialContext();
		// mailSession = (Session)
		// jndiContext.lookup("java:comp/env/mail/fleaadmi");
		// } catch (Exception ex) {
		// // ...
		// } */
	}

	// NOTE: ejb/transaction - requires_new
	// NOTE: ejb/other - async method
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Asynchronous
	public void sendMessage(String email, String subject, String message) {
		Message msg = new MimeMessage(mailSession);
		try {
			msg.setSubject(PREFIX + subject);
			msg.setRecipient(RecipientType.TO, new InternetAddress(email));
			msg.setText(message);
			Transport.send(msg);
		} catch (MessagingException me) {
			me.printStackTrace();
			// manage exception
		}
	}

	// NOTE: ejb/scheduling - declarative scheduling
	// NOTE: ejb/scheduling - periodic timer
	@Schedule(dayOfWeek = "0-5")
	public void sendNewsletter() {
		// NOTE: jpa/query - named query usage
		List<User> users = entityManager.createNamedQuery("fleamarket.getAllUsers", User.class).getResultList();
		for (User user : users) {
			sendMessage(user.getEmail(), "Daily notification", "How You Doin'..?");
		}
	}
}
