package bg.tusofia.fksu.pe.fleamarket.buslogic;

import java.util.List;

import javax.ejb.Local;

import bg.tusofia.fksu.pe.fleamarket.domain.Bid;

//NOTE: ejb/beanTypes - interface: local
@Local
public interface BidManager {

	Bid addBid(String bidderId, Long itemId, Double price);

	void removeBid(Long bidId);
	
	List<Bid> getBids(String userId);
	
	List<Bid> getWinnerBids(String userId);

}
