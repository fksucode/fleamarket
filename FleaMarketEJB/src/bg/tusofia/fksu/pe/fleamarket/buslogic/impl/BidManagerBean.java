package bg.tusofia.fksu.pe.fleamarket.buslogic.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import bg.tusofia.fksu.pe.fleamarket.buslogic.BidManager;
import bg.tusofia.fksu.pe.fleamarket.buslogic.intercept.PerformanceAuditingInterceptor;
import bg.tusofia.fksu.pe.fleamarket.domain.Bid;
import bg.tusofia.fksu.pe.fleamarket.domain.BidStatus;
import bg.tusofia.fksu.pe.fleamarket.domain.Bidder;
import bg.tusofia.fksu.pe.fleamarket.domain.Item;

/**
 * Session Bean implementation class PlaceBidBean
 */
// NOTE: ejb/beanTypes - stateless
// NOTE: ejb/interceptor - binding
@Stateless
@Interceptors(PerformanceAuditingInterceptor.class)
public class BidManagerBean implements BidManager {

	// NOTE: jpa/entityManager - create with transactional scope
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	@Override
	public Bid addBid(String bidderId, Long itemId, Double price) {
		Bid bid = createBid(bidderId, itemId, price);
		if (bid.getBidPrice().compareTo(bid.getItem().getInitialPrice()) < 0) {
			throw new IllegalArgumentException("Bid price lower than minimum bid price.");
		}
		save(bid);

		return bid;
	}

	private Bid createBid(String bidderId, Long itemId, Double price) {
		Bid bid = new Bid();
		bid.setBidStatus(BidStatus.NEW);
		bid.setBidPrice(price);
		bid.setBidDate(new Date());
		bid.setBidder(entityManager.find(Bidder.class, bidderId));
		bid.setItem(entityManager.find(Item.class, itemId));
		return bid;
	}

	private void save(Bid bid) {
		entityManager.persist(bid);
	}

	// NOTE: ejb/interceptor - excluding
	@ExcludeClassInterceptors
	@Override
	public void removeBid(Long bidId) {
		// remove bid
	}

	@Override
	public List<Bid> getBids(String userId) {
		TypedQuery<Bid> query = entityManager.createNamedQuery("fleamarket.getBidsByUser", Bid.class);
		query.setParameter("bidder_id", userId);
		return query.getResultList();
	}

	@Override
	public List<Bid> getWinnerBids(String userId) {
		TypedQuery<Bid> query = entityManager.createNamedQuery("fleamarket.getBidsByUserAndStatus", Bid.class);
		query.setParameter("bidder_id", userId);
		query.setParameter("status", BidStatus.WINNER);
		return query.getResultList();
	}

}
