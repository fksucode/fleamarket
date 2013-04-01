package bg.tusofia.fksu.pe.fleamarket.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-04T23:20:47.953+0200")
@StaticMetamodel(Order.class)
public class Order_ {
	public static volatile SingularAttribute<Order, Long> orderId;
	public static volatile SingularAttribute<Order, User> user;
	public static volatile SingularAttribute<Order, Item> item;
	public static volatile SingularAttribute<Order, OrderStatus> status;
	public static volatile SingularAttribute<Order, ShippingInfo> shippingInfo;
	public static volatile SingularAttribute<Order, BillingInfo> billingInfo;
}
