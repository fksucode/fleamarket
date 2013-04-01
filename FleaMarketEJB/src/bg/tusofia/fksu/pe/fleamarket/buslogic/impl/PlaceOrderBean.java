package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.buslogic.PlaceOrder;
import bg.tusofia.fksu.pe.fleamarket.buslogic.exception.OrderingException;
import bg.tusofia.fksu.pe.fleamarket.domain.BillingInfo;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;
import bg.tusofia.fksu.pe.fleamarket.domain.Order;
import bg.tusofia.fksu.pe.fleamarket.domain.OrderStatus;
import bg.tusofia.fksu.pe.fleamarket.domain.ShippingInfo;
import bg.tusofia.fksu.pe.fleamarket.domain.User;

/**
 * Session Bean implementation class PlaceOrderBean
 * 
 * NOTE: ejb/beanTypes - stateful
 */
@Stateful
public class PlaceOrderBean implements PlaceOrder {

	private static final Logger LOGGER = Logger.getLogger(PlaceOrderBean.class.getName());

	// NOTE: ejb/injection - inject resource
	@Resource(name = "jms/FleaConnectionFactory", mappedName = "java:app/jms/FleaConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(name = "jms_ShippingQueue", mappedName = "java:app/jms_ShippingQueue")
	private Queue shippingQueue;

	// NOTE: jpa/entityManager - create with extended scope
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	@EJB
	private ItemManager itemManager;

	private String bidderId;
	private Long itemId;
	private ShippingInfo shippingInfo;
	private BillingInfo billingInfo;

	public void setBidderId(String bidderId) {
		this.bidderId = bidderId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}

	@Remove
	public Long confirmOrder() throws OrderingException {
		Order order = new Order();
		if (itemManager.isItemWonByUser(itemId, bidderId)) {
			order.setUser(entityManager.find(User.class, bidderId));
			order.setItem(entityManager.find(Item.class, itemId));
			order.setShippingInfo(shippingInfo);
			order.setBillingInfo(billingInfo);
			

			saveOrder(order);
			billOrder(order);
			shipOrder(order);

			return order.getOrderId();
		} else {
			throw new OrderingException(String.format("Item %1 is not won by user %2", itemId, bidderId));
		}
	}

	private void saveOrder(Order order) {
		entityManager.persist(order);
	}

	private void billOrder(Order order) throws OrderingException {
		LOGGER.info("Billing order: " + order);
		BillingInfo billingInfo = order.getBillingInfo();
		Date currentDate = new Date();
		if (currentDate.before(billingInfo.getExpiryDate())) {
			// billing...
			LOGGER.info("Account " + billingInfo.getAccountNumber() + " has been charged.");
		} else {
			throw new OrderingException("Client's billing account has expired");
		}

		order.setStatus(OrderStatus.READY_FOR_SHIPMENT);
	}

	private void shipOrder(Order order) {
		try {
			Connection connection = null;
			Session session = null;
			try {
				LOGGER.fine("Sending shipping message for order: " + order);
				connection = connectionFactory.createConnection();
				session = connection.createSession(true, Session.SESSION_TRANSACTED);
				MessageProducer producer = session.createProducer(shippingQueue);
				ObjectMessage message = session.createObjectMessage();
				message.setObject(order);
				producer.send(message);
				LOGGER.info("Shipping message sent");
			} finally {
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Unable to send shipping message", ex);
		}
	}

	// NOTE: ejb/lifecycle - passivation
	@PrePassivate
	private void prepare() {
		LOGGER.info("Passivaing stateful session bean of type " + getClass().getName());
	}

	@PostActivate
	private void restore() {
		LOGGER.info("Activating stateful session bean of type " + getClass().getName());
	}

}
