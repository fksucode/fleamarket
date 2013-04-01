package bg.tusofia.fksu.pe.fleamarket.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-03-06T00:58:46.156+0200")
@StaticMetamodel(BillingInfo.class)
public class BillingInfo_ {
	public static volatile SingularAttribute<BillingInfo, Long> billingId;
	public static volatile SingularAttribute<BillingInfo, String> accountNumber;
	public static volatile SingularAttribute<BillingInfo, Date> expiryDate;
	public static volatile SingularAttribute<BillingInfo, String> secretCode;
	public static volatile SingularAttribute<BillingInfo, Address> address;
}
