package bg.tusofia.fksu.pe.fleamarket.domain;

import javax.persistence.*;

// NOTE: jpa/orm - embeddable
@Embeddable
public class Address implements java.io.Serializable {

	private static final long serialVersionUID = -3481213651327974650L;

	private String streetName;
	private String city;
	private String zipCode;
	private String country;

	public Address() {
	}

	public Address(String streetName, String city, String state, String zipCode, String country) {
		this.streetName = streetName;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}

	@Column(name = "STREET")
	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		builder.append("streetName: ").append(streetName);
		builder.append(", city: ").append(city);
		builder.append(", zipCode: ").append(zipCode);
		builder.append(", country: ").append(country);
		builder.append("}");
		return builder.toString();
	}

}
