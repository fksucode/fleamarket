package bg.tusofia.fksu.pe.fleamarket.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-01T01:59:46.781+0200")
@StaticMetamodel(Bid.class)
public class Bid_ {
	public static volatile SingularAttribute<Bid, Long> bidId;
	public static volatile SingularAttribute<Bid, Date> bidDate;
	public static volatile SingularAttribute<Bid, Double> bidPrice;
	public static volatile SingularAttribute<Bid, BidStatus> bidStatus;
	public static volatile SingularAttribute<Bid, Item> item;
	public static volatile SingularAttribute<Bid, Bidder> bidder;
}
