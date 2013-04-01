package bg.tusofia.fksu.pe.fleamarket.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

// NOTE: jpa/orm - table
// NOTE: jpa/orm - inheritance
@Entity
@Table(name = "T_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@SecondaryTable(name = "T_USER_PICTURE", pkJoinColumns = @PrimaryKeyJoinColumn(name = "USER_ID"))
public class User implements Serializable {

	private static final long serialVersionUID = 714585778202704362L;

	private String userId;
	private String password;
	private List<Group> groups;

	private String email;
	private String firstName;
	private String lastName;
	private String fullName;
	private byte[] picture;
	private Date birthDate;
	private String telephone;
	private Address address;

	public User() {
	}

	public User(String userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	@Column(name = "USER_ID", nullable = false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "PASS", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany
	@JoinTable(name = "T_USER_GROUP", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID"))
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void addGroup(Group group) {
		if (groups == null) {
			groups = new ArrayList<Group>();
		}
		groups.add(group);
	}

	@Column(name = "EMAIL", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FIRST_NAME", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// NOTE: jpa/orm - transient
	@Transient
	public String getFullName() {
		return fullName;
	}

	// NOTE: jpa/listener - local (post-load)
	@PostLoad
	public void formatFullName() {
		fullName = firstName + " " + lastName;
	}

	// NOTE: jpa/orm - binary
	// NOTE: jpa/orm - lazy fetching
	@Column(name = "PICTURE", table = "T_USER_PICTURE")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	// NOTE: jpa/orm - temporal
	@Column(name = "BIRTH_DATE", nullable=true)
	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "TEL_NUMBER")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	// NOTE: jpa/orm - embedded
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
		builder.append("id: ").append(userId);
		builder.append(", firstName: ").append(firstName);
		builder.append(", lastName: ").append(lastName);
		builder.append(", email: ").append(email);
		builder.append("}");
		return builder.toString();
	}

}
