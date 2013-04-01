package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import bg.tusofia.fksu.pe.fleamarket.domain.ShippingInfo;

@Entity
@Table(name = "T_ORDER")
public class Order implements Serializable {

	private static final long serialVersionUID = 9199339936782403817L;

	private Long orderId;
	private User user;
	private Item item;
	private OrderStatus orderStatus;
	private ShippingInfo shippingInfo;
	private BillingInfo billingInfo;

	public Order() {
	}

	public Order(Item item, User user) {
		this.item = item;
		this.user = user;
	}

	@Id
	@GeneratedValue
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@ManyToOne
	@JoinColumn
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne
	@JoinColumn
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Enumerated
	public OrderStatus getStatus() {
		return orderStatus;
	}

	public void setStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(ShippingInfo param) {
		this.shippingInfo = param;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public BillingInfo getBillingInfo() {
		return billingInfo;
	}

	public void setBillingInfo(BillingInfo billingInfo) {
		this.billingInfo = billingInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("id: ").append(orderId);
		builder.append(", user: ").append(user);
		builder.append(", item: ").append(item);
		builder.append(", billingInfo: ").append(billingInfo);
		builder.append(", shippingInfo: ").append(shippingInfo);
		builder.append(", status: ").append(orderStatus);
		builder.append("}");
		return builder.toString();
	}

}
