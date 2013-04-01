package bg.tusofia.fksu.pe.fleamarket.buslogic;

import javax.ejb.Local;

import bg.tusofia.fksu.pe.fleamarket.domain.BillingInfo;
import bg.tusofia.fksu.pe.fleamarket.domain.ShippingInfo;

@Local
public interface PlaceOrder {

	void setBidderId(String bidderId);

	void setItemId(Long itemId);

	void setShippingInfo(ShippingInfo shippingInfo);

	void setBillingInfo(BillingInfo billingInfo);

	Long confirmOrder();
}
