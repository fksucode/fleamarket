package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import bg.tusofia.fksu.pe.fleamarket.listeners.ItemListerner;

// NOTE: jpa/listener - attach listener
@EntityListeners(ItemListerner.class)
@Entity
@Table(name = "T_ITEM")
public class Item implements Serializable {

	private static final long serialVersionUID = -7236610580762979611L;

	private Long itemId;
	private String title;
	private String description;
	private String shortDescription;
	private Date createdDate;
	private Date bidEndDate;
	private Double initialPrice;
	private Set<Bid> bids;
	private Seller seller;

	public Item() {
	}

	public Item(Long itemId) {
		this.itemId = itemId;
	}

	// NOTE: jpa/orm - id generation (auto)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID", nullable = false)
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	@Column(name = "ITEM_NAME", length = 50, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ITEM_DESC", length = 500)
	public String getDescription() {
		return description;
	}

	@Transient
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BID_END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(Date bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.DATE)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "INITIAL_PRICE", scale = 2, nullable = false)
	public Double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}

	@ManyToOne
	@JoinColumn(name = "SELLER_ID", referencedColumnName = "USER_ID")
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	// NOTE: jpa/orm - cascading
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public Bid addBid(Bid bid) {
		getBids().add(bid);
		return bid;
	}

	public Bid removeBid(Bid bid) {
		getBids().remove(bid);
		return bid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("itemId: ").append(itemId);
		builder.append(", title: ").append(title);
		builder.append("}");
		return builder.toString();
	}

}
