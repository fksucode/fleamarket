package bg.tusofia.fksu.pe.fleamarket.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "T_BILLING_DETAILS")
public class BillingInfo implements java.io.Serializable {

	private static final long serialVersionUID = 6167257580396321164L;

	private Long billingId;
	private String accountNumber;
	private String secretCode;
	private Date expiryDate;
	private Address address;

	public BillingInfo() {
	}

	@Id
	@GeneratedValue
	@Column(name = "BILLING_ID")
	public Long getBillingId() {
		return this.billingId;
	}

	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}

	@Column(name = "ACCOUNT_NO")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Column(name = "EXPIRY_DATE")
	@Temporal(TemporalType.DATE)
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "SECRET_CODE")
	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CD")) })
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("billingId: ").append(billingId);
		builder.append(", accountNumber: ").append(accountNumber);
		builder.append(", secretCode: ").append(secretCode);
		builder.append(", expiryDate: ").append(expiryDate);
		builder.append("}");
		return builder.toString();
	}

}
