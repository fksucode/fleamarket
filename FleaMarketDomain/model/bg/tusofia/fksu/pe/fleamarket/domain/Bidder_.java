package bg.tusofia.fksu.pe.fleamarket.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-01T01:59:46.781+0200")
@StaticMetamodel(Bidder.class)
public class Bidder_ extends User_ {
	public static volatile SingularAttribute<Bidder, BidderStatus> bidderStatus;
	public static volatile SingularAttribute<Bidder, Long> creditRating;
	public static volatile SetAttribute<Bidder, Bid> bids;
}
