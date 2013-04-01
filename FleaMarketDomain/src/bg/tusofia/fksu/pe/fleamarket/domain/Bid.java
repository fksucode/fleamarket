package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "T_BID")
public class Bid implements Serializable, Comparable<Bid> {

	private static final long serialVersionUID = -5777442218489530300L;

	private Long bidId;
	private Date bidDate;
	private Double bidPrice;
	private BidStatus bidStatus;
	private Item item;
	private Bidder bidder;

	public Bid() {
	}

	public Bid(Bidder bidder, Item item, Double bidPrice) {
		this.item = item;
		this.bidder = bidder;
		this.bidPrice = bidPrice;
	}

	// NOTE: jpa/orm - id generation (sequence)
	@SequenceGenerator(name = "BID_SEQ_GEN", sequenceName = "BID_SEQUENCE", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BID_SEQ_GEN")
	@Column(name = "BID_ID")
	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	@Column(name = "BID_DATE")
	@Temporal(TemporalType.DATE)
	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	@Column(name = "BID_PRICE")
	public Double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Double bidPrice) {
		this.bidPrice = bidPrice;
	}

	// NOTE: jpa/orm - enum
	@Column(name = "BID_STATUS")
	@Enumerated(EnumType.STRING)
	public BidStatus getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(BidStatus bidStatus) {
		this.bidStatus = bidStatus;
	}

	// NOTE: jpa/orm - relation (may-to-one)
	@ManyToOne
	@JoinColumn(name = "BID_ITEM_ID", referencedColumnName = "ITEM_ID")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne
	@JoinColumn(name = "BID_BIDDER", referencedColumnName = "USER_ID")
	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	@Override
	public int hashCode() {
		return bidId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bid) {
			return bidId.equals(((Bid) obj).getBidId());
		}
		return false;
	}

	@Override
	public String toString() {
		return "{bidId: " + bidId + //
				", bidDate: " + bidDate + //
				", bidPrice: " + bidPrice + "}";
	}

	@Override
	public int compareTo(Bid other) {
		// the highest price is placed first
		int priceComparison = other.getBidPrice().compareTo(bidPrice);
		if (priceComparison == 0) {
			// if prices match, the faster bidder is placed first
			return bidDate.compareTo(other.getBidDate());
		}
		return priceComparison;
	}
}
