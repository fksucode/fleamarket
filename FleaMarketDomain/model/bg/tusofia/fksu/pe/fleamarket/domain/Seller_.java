package bg.tusofia.fksu.pe.fleamarket.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-01T01:59:46.796+0200")
@StaticMetamodel(Seller.class)
public class Seller_ extends User_ {
	public static volatile SingularAttribute<Seller, Double> commissionRate;
	public static volatile SingularAttribute<Seller, Long> maxItemsAllowed;
	public static volatile SetAttribute<Seller, Item> items;
}
