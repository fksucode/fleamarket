package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import bg.tusofia.fksu.pe.fleamarket.buslogic.ItemManager;
import bg.tusofia.fksu.pe.fleamarket.buslogic.exception.ItemCreationException;
import bg.tusofia.fksu.pe.fleamarket.domain.Bid;
import bg.tusofia.fksu.pe.fleamarket.domain.BidStatus;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;
import bg.tusofia.fksu.pe.fleamarket.domain.Item_;
import bg.tusofia.fksu.pe.fleamarket.domain.Order;
import bg.tusofia.fksu.pe.fleamarket.domain.Order_;
import bg.tusofia.fksu.pe.fleamarket.domain.Seller;

/**
 * Session Bean implementation class ItemManagerBean
 */
@Stateless
public class ItemManagerBean implements ItemManager {

	private static final Logger LOGGER = Logger.getLogger(ItemManagerBean.class.getName());
	
	private static final int BID_PERIOD = 3;

	@EJB
	private Notifier notifier;

	@Resource
	private TimerService timerService;

	@PersistenceContext
	private EntityManager entityManager;

	// NOTE: ejb/exceptions - application exception usage
	@Override
	public Item addItem(String title, Double initialPrice, Date bidEndDate, String description, String userId)
			throws ItemCreationException {
		//FIXME : use actual date
		bidEndDate = hardcodeBidEndDate();
		
		Item item = createItem(title, initialPrice, bidEndDate, description);

		validateItem(item);

		Seller seller = entityManager.find(Seller.class, userId);
		if (seller == null) {
			throw new ItemCreationException("Unable to find seller with id " + userId);
		}
		item.setSeller(seller);
		entityManager.persist(item);

		// NOTE: jpa/operations - refresh
		entityManager.flush();
		entityManager.refresh(item); // get generated id

		// NOTE: ejb/scheduling - programmatic scheduling
		timerService.createTimer(bidEndDate, item.getItemId());

		return item;
	}
	
	private Date hardcodeBidEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, BID_PERIOD);
		return calendar.getTime();
	}

	private Item createItem(String title, Double initialPrice, Date bidEndDate, String description) {
		Item item = new Item();
		item.setTitle(title);
		item.setInitialPrice(initialPrice);
		item.setBidEndDate(bidEndDate);
		item.setDescription(description);
		return item;
	}

	private void validateItem(Item item) throws ItemCreationException {
		if (item.getBidEndDate() == null) {
			throw new ItemCreationException("Missing bid end date");
		}
		Date currentDate = new Date();
		if (item.getBidEndDate().before(currentDate)) {
			throw new ItemCreationException("Bid end date is in the past: " + item.getBidEndDate());
		}
		if (item.getInitialPrice() == null || item.getInitialPrice() <= 0.0) {
			throw new ItemCreationException("Invalid initial price: " + item.getInitialPrice());
		}
	}

	// NOTE: ejb/scheduling - one-time timer
	@Timeout
	private void bidExpired(Timer timer) {
		Long itemId = (Long) timer.getInfo();
		LOGGER.info("Bidding for item '" + itemId + "' has expired.");
		TypedQuery<Bid> query = entityManager.createNamedQuery("fleamarket.getWinningBid", Bid.class);
		query.setParameter("item_id", itemId);
		query.setMaxResults(1);
		List<Bid> bids = query.getResultList();
		if (bids.isEmpty()) {
			// send message to seller
			LOGGER.info("No winning bid fond.");
			Item item = entityManager.find(Item.class, itemId);
			if (item != null) {
				notifier.sendMessage(item.getSeller().getEmail(), "Bidding expired without winner",
						"At " + item.getBidEndDate() + " the bidding for item '" + item.getTitle() + "' (with id "
								+ item.getItemId() + ") expired without a winner.");
			}
		} else {
			Bid winningBid = bids.get(0);
			winningBid.setBidStatus(BidStatus.WINNER);
			LOGGER.info("Bidding was won by bid '" + winningBid.getBidId() + "' by '"
					+ winningBid.getBidder().getUserId() + "'");
			// send message to bidder
			notifier.sendMessage(winningBid.getBidder().getEmail(), "Congratulations on won bid",
					"You have won the bid for item '" + winningBid.getItem().getTitle() + "' (with id "
							+ winningBid.getItem().getItemId() + ").");
		}
	}

	@Override
	public void deleteItem(Long itemId) {
		Item item = entityManager.find(Item.class, itemId);
		if (item != null) {
			entityManager.remove(item);
		}
	}

	// NOTE: ejb/transaction - supports
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Item> getItems(String userId) {
		// NOTE: jpa/query - query with params
		TypedQuery<Item> query = entityManager.createQuery("select i from Item i where i.seller.userId = :userId",
				Item.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Item> getAvaiableItems() {
		TypedQuery<Item> query = entityManager.createNamedQuery("fleamarket.getAvailableItems", Item.class);
		query.setParameter("current_date", new Date(), TemporalType.DATE);
		return query.getResultList();
	}

	@Override
	public Item getItem(Long itemId) {
		return entityManager.find(Item.class, itemId);
	}

	@Override
	public boolean isItemWonByUser(Long itemId, String userId) {
		TypedQuery<Bid> query = entityManager.createNamedQuery("fleamarket.isItemWonByBidder", Bid.class);
		query.setParameter("item_id", itemId);
		query.setParameter("bidder_id", userId);
		List<Bid> winningBids = query.getResultList();
		return !winningBids.isEmpty();
	}

	@Override
	public boolean isItemWon(Long itemId) {
		TypedQuery<Bid> query = entityManager.createNamedQuery("fleamarket.isItemWon", Bid.class);
		query.setParameter("item_id", itemId);
		List<Bid> winningBids = query.getResultList();
		return !winningBids.isEmpty();
	}

	@Override
	public boolean isItemOrdered(Long itemId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> order = criteriaQuery.from(Order.class);
		Join<Order, Item> item = order.join(Order_.item);
		criteriaQuery.where(builder.equal(item.get(Item_.itemId), itemId));
		criteriaQuery.select(order);
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		List<Order> itemOrders = query.getResultList();
		return !itemOrders.isEmpty();
	}

}
