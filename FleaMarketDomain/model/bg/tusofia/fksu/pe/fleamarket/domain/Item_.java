package bg.tusofia.fksu.pe.fleamarket.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-13T23:43:29.328+0200")
@StaticMetamodel(Item.class)
public class Item_ {
	public static volatile SingularAttribute<Item, Long> itemId;
	public static volatile SingularAttribute<Item, String> title;
	public static volatile SingularAttribute<Item, String> description;
	public static volatile SingularAttribute<Item, Date> bidEndDate;
	public static volatile SingularAttribute<Item, Date> createdDate;
	public static volatile SingularAttribute<Item, Double> initialPrice;
	public static volatile SingularAttribute<Item, Seller> seller;
	public static volatile SetAttribute<Item, Bid> bids;
}
