package bg.tusofia.fksu.pe.fleamarket.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-18T12:02:04.554+0200")
@StaticMetamodel(ShippingInfo.class)
public class ShippingInfo_ {
	public static volatile SingularAttribute<ShippingInfo, Long> shippingId;
	public static volatile SingularAttribute<ShippingInfo, Address> address;
	public static volatile SingularAttribute<ShippingInfo, BigDecimal> cost;
}
