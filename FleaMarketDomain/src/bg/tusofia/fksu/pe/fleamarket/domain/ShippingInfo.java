package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_SHIPPING_DETAILS")
public class ShippingInfo implements Serializable {

	private static final long serialVersionUID = -5401001522211770859L;

	private Long shippingId;
	private Address address;
	private BigDecimal cost;

	@Id
	@GeneratedValue
	@Column(name = "SHIPPING_ID")
	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("shippingId: ").append(shippingId);
		builder.append(", cost: ").append(cost);
		builder.append(", address: ").append(address);
		builder.append("}");
		return builder.toString();
	}

}
