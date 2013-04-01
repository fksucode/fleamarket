package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import bg.tusofia.fksu.pe.fleamarket.domain.Order;
import bg.tusofia.fksu.pe.fleamarket.domain.OrderStatus;

/**
 * Message-Driven Bean implementation class for: ShipOrderBean
 * 
 * NOTE: ejb/beanTypes - message-driven
 */
@MessageDriven(mappedName = "java:app/jms_ShippingQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class ShipOrderBean implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(ShipOrderBean.class.getName());

	// NOTE: jpa/entityManager - create application managed
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	// no annotation!
	private EntityManager entityManager;

	// NOTE: ejb/injection - inject ejb
	@EJB
	private Notifier notifier;

	/**
	 * {@inheritDoc}
	 */
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			Order order = (Order) objectMessage.getObject();
			syncOrder(order);
			try {
				if (order.getStatus() != OrderStatus.READY_FOR_SHIPMENT) {
					throw new IllegalStateException("Order with status '" + order.getStatus()
							+ "' received for shipping");
				}

				// perform shipping ...
				notifyShippingSuccess(order);
				order.setStatus(OrderStatus.COMPLETE);
			} catch (Exception ex) {
				notifyShippingFailure(order, ex);
				order.setStatus(OrderStatus.SHIPMENT_FAILED);
			} finally {
				updateOrder(order);
			}
		} catch (Exception e) {
			LOGGER.severe("Error received during shipping: " + e.getMessage());
		}
	}

	private void syncOrder(Order order) {
		entityManager.refresh(entityManager.merge(order));
	}

	private void updateOrder(Order order) {
		entityManager.merge(order);
	}

	// NOTE: ejb/lifecycle - creation
	@PostConstruct
	private void initialize() {
		entityManager = entityManagerFactory.createEntityManager();
	}

	@PreDestroy
	private void cleanup() {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private void notifyShippingSuccess(Order order) {
		LOGGER.severe("Successful shipment of order " + order.getOrderId());
		notifier.sendMessage(order.getUser().getEmail(), "Successful shipment of order " + order.getOrderId(), ":)");
	}

	private void notifyShippingFailure(Order order, Exception exception) {
		LOGGER.severe( "Failure during shipment of order " + order.getOrderId());
		notifier.sendMessage(order.getUser().getEmail(), "Failure during shipment of order " + order.getOrderId(),
				"Details: " + exception.getMessage());
	}

}
